import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class BaseTweet {

	//Déclaration des variables globales
    private ArrayList<Tweet> tweets;
    private ArrayList<InfosTweet> infosTweets;
    private ArrayList<InfosUser> infosUser;
    private  ArrayList<Tweet> listUti;
    private  ArrayList<Tweet> listRT;
    private  ArrayList<Tweet> listUtiRT;
    private int nbTweet = 0;
     private Map<String, ArrayList<Tweet>> mapMois;
    private Map<String, ArrayList<Tweet>> mapJour;
    private Map<String, ArrayList<Tweet>> mapAnnee;
    private Map<String, ArrayList<Tweet>> mapUtil;
    private Map<String, ArrayList<Tweet>> mapRT;
    private Map<String, ArrayList<Tweet>> mapUtilRT;
    private int moisMin = 13;
    private int moisMax = -1;
 
    
    public void initialise() {
    	//Initialisation des différents ArrayList et TreeMap créés précédement
        tweets = new ArrayList<>();
        infosTweets = new ArrayList<>();
        infosUser = new ArrayList<>();
        mapMois = new TreeMap<>();
        mapJour = new TreeMap<>();
        mapAnnee = new TreeMap<>();
        mapUtil = new TreeMap<>();
        mapRT = new TreeMap<>();
        mapUtilRT = new TreeMap<>();

    }


    public void ouvrirCSV(String theme) {
        try {
        	FileReader fr = null;
        	if (theme=="c") {
            fr = new FileReader("C:/Users/cedri/eclipse-workspace/data/climat.txt");
        	}
        	if (theme=="f") {
                fr = new FileReader("C:/Users/cedri/eclipse-workspace/data/Foot.txt");
            	}
        	
            BufferedReader br = new BufferedReader(fr);
            String line;
            Tweet montweet;
            int lineNb = 1;
            int nbErr = 0;




            long initTime = System.currentTimeMillis();			//Variable pour calculer le temps de chargement
            while ((line = br.readLine()) != null) {			//Pour lire chaque tweet tant que y en a
                String[] arrLine = line.split("\t");

                long idTweet = 0;
                try {											//On vérifie qu'il y a bien un tweet sur la ligne
                    if (arrLine.length > 0) {
                        idTweet = Long.parseLong(arrLine[0]);
                    }
                } catch (Exception ex) {
                    nbErr++;
                    System.err.println("Invalid idTweet for line " + lineNb);		//Si ce n'est pas le cas, erreur
                }

                String idUtilisateur = null;			//On classe les diverses données du Tweet
                if (arrLine.length > 1) {
                    idUtilisateur = arrLine[1];
                }

                Timestamp datePubli = null;
                Integer annee = null;
                Integer mois = null;
                Integer jour = null;
                if (arrLine.length > 2) {
                	 try {											//On vérifie qu'il y a bien un tweet sur la ligne
                         datePubli = Timestamp.valueOf(arrLine[2]);
                     } catch (Exception ex) {
                         nbErr++;
                         System.err.println("Invalid idTweet for line " + lineNb);		//Si ce n'est pas le cas, erreur
                     }
                    
                    // recuperation du jour, mois et annee
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(datePubli.getTime());
                    annee = cal.get(Calendar.YEAR);
                    // Calendar.MONTH commence a 0, on ajoute 1 pour avoir le bon num de mois
                    mois = cal.get(Calendar.MONTH) + 1;
                    jour = cal.get(Calendar.DAY_OF_MONTH);
                }


                String contenu = null;
                if (arrLine.length > 3) {
                    contenu = arrLine[3];
                }

                String idUtilisateurRT = null;
                if (arrLine.length > 4) {
                    idUtilisateurRT = arrLine[4];
                }

                //On crée donc un nouveau tweet avec les infos du tweet sur lequel on est 
                montweet = new Tweet(idTweet, idUtilisateur, datePubli, contenu, idUtilisateurRT);
                tweets.add(montweet);
                
                String moisStr = null;
                
                // On va ranger chaque tweet selon son mois
                if (mois!=null){
                    moisStr=String.valueOf(mois);
                    if(mois<10){
                        moisStr="0"+moisStr;
                    }
                    String MoisAnnee = moisStr + String.valueOf(annee) ;
                    // on initialise si l'array list n'est pas encore creee
                    ArrayList<Tweet> listMois = mapMois.get(MoisAnnee);
                    if (listMois == null){
                        listMois = new ArrayList<>();
                    }
                    listMois.add(montweet);
                    mapMois.put(MoisAnnee, listMois);

                    //Calcul du plus petit mois et le plus lointain
                    moisMin=min(moisMin, mois);
                    moisMax=max(moisMax,mois);

                }

                /* if (jour!=null){
                String jourMoisAnnee = String.valueOf(jour)+moisStr+ String.valueOf(annee);
                // on initialise si l'array list n'est pas encore creee
                ArrayList<Tweet> listJour = mapJour.get(jourMoisAnnee);
                if (listJour == null){
                    listJour = new ArrayList<>();
                }
                listJour.add(montweet);
                mapJour.put(jourMoisAnnee, listJour);
            } 

            if (annee!=null){
                // on initialise si l'array list n'est pas encore creee
                ArrayList<Tweet> listAnnee = mapJour.get(String.valueOf(annee));
                if (listAnnee == null){
                    listAnnee = new ArrayList<>();
                }
                listAnnee.add(montweet);
                mapAnnee.put(String.valueOf(annee), listAnnee);
            } */
                
                //On range les tweets sans les rt selon l'auteur de celui-ci
                if ((idUtilisateur!=null) &&(idUtilisateurRT==null)){
                    // on initialise si l'array list n'est pas encore creee
                    listUti = mapUtil.get(idUtilisateur);
                    if (listUti == null){
                        listUti = new ArrayList<>();
                    }
                    listUti.add(montweet);
                    mapUtil.put(idUtilisateur, listUti);
                }
                
                //On range les tweets selon le contenu, permettant d'avoir tous les retweets d'un tweet
                if (contenu!=null){
                    // on initialise si l'array list n'est pas encore creee
                    listRT = mapRT.get(contenu);
                    if (listRT == null){
                        listRT = new ArrayList<>();
                    }
                    listRT.add(montweet);
                    mapRT.put(contenu, listRT);
                }
                
                if (idUtilisateurRT!=null){
                    // on initialise si l'array list n'est pas encore creee
                    listUtiRT = mapUtilRT.get(idUtilisateurRT);
                    if (listUtiRT == null){
                        listUtiRT = new ArrayList<>();
                    }
                    listUtiRT.add(montweet);
                    mapUtilRT.put(idUtilisateurRT, listUtiRT);
                }


                lineNb++;
            }
            nbTweet = lineNb - nbErr;
            
            //Affichage du temps et du nombre de Tweets chargés
            long elapseTime = System.currentTimeMillis() - initTime;
            System.out.println(lineNb + " lignes chargÃ©es en " + elapseTime + " ms");
            System.out.println(nbTweet + " Tweet chargÃ©s");
            br.close();
            fr.close();



        } catch (Exception exception) {
            System.out.println("Erreur de lecture");
            exception.printStackTrace();
        }
    }


    public String afficher() {
        StringBuilder sb = new StringBuilder();
        //on affiche seulement les 15 premiers
        for (int ii = 0; ii < 15; ii++) {
            sb.append(ii + " " + tweets.get(ii).toString() + "\n");
        }
        return sb.toString();
    }

 private ArrayList<InfosTweet> infosTweetMois = new ArrayList<>();
    //Fonction renvoyant series pour montrer les données sur le BarChart
    public XYChart.Series<String, Integer> graphicNbTweetMois(){       
    	// Création de series au format XYChart.Series avec abscisse et ordonnée 
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        infosTweetMois = fonctionInfosTweets(mapMois);
        //Pour i du mois minimum dans la base au mois maximum
        for (int i = moisMin; i <= moisMax; i++) {       	
           String stri=String.valueOf(i);				//Transformer i en String
           if(i<10){
               stri="0"+stri;				//Transformer stri en ajoutant 0 devant stri si i est inférieur à 10
            }
          String stri1 = stri + "2019";		// Création de stri1 pour le mois que l'on cherche            
           for(int j = 0; j <= moisMax-moisMin; j++){		//Pour j de 0 au nombre de mois dans la base
              InfosTweet montweet = infosTweetMois.get(j);		//On va chercher les info tweet du mois
               if(stri1.equals(montweet.getDate())){		//Si la date qu'on cherche correspond à la date du mois des tweets
                    abscisse.add(stri+"-2019");				//On ajoute en abscisse le nom du mois
                   series.getData().add(new XYChart.Data<>(stri+"-2019", montweet.getNbTweets()));    //On ajoute à series l'abscisse et l'ordonnée
                   break;
                }

            }
       } 
        System.out.println(series);
        return series; //On retourne series
    }   
    

    private int jourMax= 1;
    private int jourMin=31;
    public void calcNbTweetJoursMois(String mothselec){
    	 jourMax= 1;
    	 jourMin=31;
    	getAbscisse();
        // On cherche a recuperer les tweet de chacun des jours pour un mois donne
        String strMois = mothselec.replaceAll("[-]","");
        System.out.println(strMois);
        infosTweetMois = fonctionInfosTweets(mapMois);
       
        for (InfosTweet monMois : infosTweetMois){
            // on cherche le mois correspondant
        	
            if (strMois.equals(monMois.getDate())){
                // on segmente ensuite par jours

                // pour cela on parcours tous les tweets de ce mois
                for (Tweet montweet : monMois.getTweets()){
                    String strJour = montweet.getJourPubli();
                    if (jourMax< Integer.parseInt(strJour)){
                        jourMax = Integer.parseInt(strJour);
                    }
                    if (jourMin> Integer.parseInt(strJour)){
                        jourMin = Integer.parseInt(strJour);
                    }
                    // on initialise si l'array list du jour si elle n'est pas encore creee
                    // mapJour est un Map<String, ArrayList<Tweet>> defini dans initialise()
                    ArrayList<Tweet> listJour = mapJour.get(strJour);
                    
                    if (listJour == null){
                        listJour = new ArrayList<>();
                    }
                    listJour.add(montweet);
                    // on ajoute la string contenant le jour en cle, et la liste des tweet
                    // correspondant a ce jour en valeur
                    mapJour.put(strJour, listJour);
                }
                System.out.println(jourMin+" et "+jourMax);
                break;
            }

        }
    }
    
    ArrayList<String> abscisse = new ArrayList<>();
    
    public ArrayList<String> getAbscisse(){
    	System.out.println(abscisse);
    	return abscisse;

    }
    
   
    private ArrayList<InfosTweet> fonctionInfosTweets(Map<String, ArrayList<Tweet>> mapPeriode) {
        infosTweets = new ArrayList<>();
        // permet à partir d'un map sur une periode, de retourneer une arraylist qui contiendra les infos tweet
        // sur cette periode
        InfosTweet monInfo;
        for (Map.Entry<String, ArrayList<Tweet>> monMap : mapPeriode.entrySet()) {
            String date = monMap.getKey();
            int nbTweets = monMap.getValue().size();
            //penser a implem Users Populaires
            monInfo = new InfosTweet(date, monMap.getValue(), nbTweets);
            infosTweets.add(monInfo);
        }
        return infosTweets;
    }
    
    public XYChart.Series<String, Integer> graphicNbTweetJoursMois(){
        ArrayList<InfosTweet> infosTweetJourMois = fonctionInfosTweets(mapJour);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.getData().clear();
        abscisse.clear();
        // i contient le numero de chacun des jours du mois
        for (int i = jourMin; i <= jourMax; i++) {
            String stri=String.valueOf(i);
            // j contient l'index de infosTweetJourMois
        
            for(int j = 0; j <= 31; j++){
            	
                InfosTweet montweet = infosTweetJourMois.get(j);
                System.out.println(montweet); 
                System.out.println(j);
                if(stri.equals(montweet.getDate())){
                	              	
                    abscisse.add(stri);
                    System.out.println(stri); 
                    series.getData().add(new XYChart.Data<>(stri, montweet.getNbTweets()));
                    break;
                }
            }
        }
        System.out.println(series);
        return series;
    }


    public void utilisateursPopulaires(String strmonth) {
        for (InfosTweet monMois : infosTweetMois) {
        	String strMois = strmonth.replaceAll("[-]","");
            // on cherche le mois correspondant
            if (strMois.equals(monMois.getDate())) {
                // on segmente ensuite par rt
                // pour cela on parcours tous les tweets de ce mois
                for (Tweet montweet : monMois.getTweets()) {
                    String rtid = montweet.getIdUtilisateurRT();
                    if(rtid != null){
                    // mapRT est un Map<String, ArrayList<Tweet>> defini dans initialise()
                    ArrayList<Tweet> listRT = mapRT.get(rtid);
                    if (listRT == null) {
                        listRT = new ArrayList<>();
                    }
                    listRT.add(montweet);
                    // on ajoute la string contenant le jour en cle, et la liste des tweet
                    // correspondant a ce jour en valeur
                    mapRT.put(rtid, listRT);
                    }
                }
                break;
            }
        }
    }

    public XYChart.Series<String, Integer> graphicUtilisateursPopulaires(){
        ArrayList<InfosTweet> infosTweetUsers = fonctionInfosTweets(mapRT);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        series.getData().clear();
        abscisse.clear();
        
        Collections.sort(infosTweetUsers);
        Collections.reverse(infosTweetUsers);


        for (int i = 0; i < 10; i++){
            InfosTweet moninfo = infosTweetUsers.get(i);
            String idUser = moninfo.getDate();
            int nbTweet = moninfo.getNbTweets();
            System.out.println(nbTweet);
            abscisse.add(idUser);
            series.getData().add(new XYChart.Data<>(idUser, nbTweet));
        }

        return series;
    }
}
