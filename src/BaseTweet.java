import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class BaseTweet {

    private ArrayList<Tweet> tweets;
    private ArrayList<InfosTweet> infosTweets;
    private int nbTweet = 0;
    private Map<String, ArrayList<Tweet>> mapMois;
    private Map<String, ArrayList<Tweet>> mapJour;
    private Map<String, ArrayList<Tweet>> mapAnnee;
    private Map<String, ArrayList<Tweet>> mapRT;
    private int moisMin = 13;
    private int moisMax = -1;

    public void initialise() {
        tweets = new ArrayList<>();
        //infosTweets = new ArrayList<>();
        mapMois = new TreeMap<>();
        mapJour = new TreeMap<>();
        mapAnnee = new TreeMap<>();
        mapRT = new TreeMap<>();


    }


    public void ouvrirCSV() {
        try {
            FileReader fr = new FileReader("data/climat.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            Tweet montweet;
            int lineNb = 1;
            int nbErr = 0;




            long initTime = System.currentTimeMillis();
            while ((line = br.readLine()) != null) {
                String[] arrLine = line.split("\t");

                long idTweet = 0;
                try {
                    if (arrLine.length > 0) {
                        idTweet = Long.parseLong(arrLine[0]);
                    }
                } catch (Exception ex) {
                    nbErr++;
                    System.err.println("Invalid idTweet for line " + lineNb);
                }

                String idUtilisateur = null;
                if (arrLine.length > 1) {
                    idUtilisateur = arrLine[1];
                }

                Timestamp datePubli = null;
                Integer annee = null;
                Integer mois = null;
                Integer jour = null;
                if (arrLine.length > 2) {
                    datePubli = Timestamp.valueOf(arrLine[2]);
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

                montweet = new Tweet(idTweet, idUtilisateur, datePubli, contenu, idUtilisateurRT);
                tweets.add(montweet);
                String moisStr = null;
                //on recupere ensuite les infos sur le mois
                if (mois!=null){
                    moisStr=String.valueOf(mois);
                    if(mois<10){
                        // on rajoute 0 devant le numero du mois si il est compris entre 1 et 9
                        moisStr="0"+moisStr;
                    }
                    String MoisAnnee = moisStr + String.valueOf(annee) ;
                    // on initialise si l'array list n'est pas encore creee
                    ArrayList<Tweet> listMois = mapMois.get(MoisAnnee);
                    if (listMois == null){
                        listMois = new ArrayList<>();
                    }
                    listMois.add(montweet);
                    // on ajoute la string contenant le num du mois et l'annee en cle, et la liste des tweet
                    // correspondant a ce mois en valeur
                    mapMois.put(MoisAnnee, listMois);

                    moisMin=min(moisMin, mois);
                    moisMax=max(moisMax,mois);

                }

                lineNb++;
            }
            nbTweet = lineNb - nbErr;

            long elapseTime = System.currentTimeMillis() - initTime;
            System.out.println(lineNb + " lignes chargées en " + elapseTime + " ms");
            System.out.println(nbTweet + " Tweet chargés");
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

    private ArrayList<InfosTweet> infosTweetMois = new ArrayList<>();
    public Object[] graphicNbTweetMois(){
        infosTweetMois = fonctionInfosTweets(mapMois);

        ArrayList<String> abscisse = new ArrayList<>();
        ArrayList<Integer> ordonne = new ArrayList<>();
        for (int i = moisMin; i <= moisMax; i++) {
            String stri=String.valueOf(i);
            if(i<10){
                stri="0"+stri;
            }
            stri = stri + "2019";
            for(int j = 0; j <= moisMax-moisMin; j++){
                InfosTweet montweet = infosTweetMois.get(j);
                if(stri.equals(montweet.getDate())){
                    ordonne.add(montweet.getNbTweets());
                    abscisse.add(stri);
                    break;
                }
            }
        }
        return new Object[]{abscisse, ordonne};
    }

    private int jourMax= 1;
    private int jourMin=31;
    public void TweetJoursMois(){
        // On cherche a recuperer les tweet de chacun des jours pour un mois donne
        String strMois = "092019";
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
                break;
            }

        }
    }


    public void utilisateursPopulaires() {
        for (InfosTweet monMois : infosTweetMois) {
            String strMois = "092019";
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

    public Object[] graphicUtilisateursPopulaires(){
        ArrayList<InfosTweet> infosTweetUsers = fonctionInfosTweets(mapRT);
        Collections.sort(infosTweetUsers);
        Collections.reverse(infosTweetUsers);
        ArrayList<String> abscisse = new ArrayList<>();
        ArrayList<Integer> ordonne = new ArrayList<>();

        for (int i = 0; i <= 10; i++){
            InfosTweet moninfo = infosTweetUsers.get(i);
            String idUser = moninfo.getDate();
            int nbTweet = moninfo.getNbTweets();
            abscisse.add(idUser);
            ordonne.add(nbTweet);
        }


        return new Object[]{abscisse, ordonne};
    }


    public Object[] graphicNbTweetJoursMois(){
        ArrayList<InfosTweet> infosTweetJourMois = fonctionInfosTweets(mapJour);
        ArrayList<String> abscisse = new ArrayList<>();
        ArrayList<Integer> ordonne = new ArrayList<>();
        // i contient le numero de chacun des jours du mois
        for (int i = 1; i <= jourMax; i++) {
            String stri=String.valueOf(i);
            // j contient l'index de infosTweetJourMois
            for(int j = 0; j <= jourMax-jourMin; j++){
                InfosTweet monInfotweet = infosTweetJourMois.get(j);
                if(stri.equals(monInfotweet.getDate())){
                    ordonne.add(monInfotweet.getNbTweets());
                    abscisse.add(stri);
                    break;
                }
            }
        }
        return new Object[]{abscisse, ordonne};
    }




}
