import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public class BaseTweet {

    private ArrayList<Tweet> tweets;
    private ArrayList<InfosTweet> infosTweets;
    private int nbTweet = 0;
    private Map<String, ArrayList<Tweet>> mapMois;
    private Map<String, ArrayList<Tweet>> mapJour;
    private Map<String, ArrayList<Tweet>> mapAnnee;

    public void initialise() {
        tweets = new ArrayList<>();
        infosTweets = new ArrayList<>();
        mapMois = new TreeMap<>();
        mapJour = new TreeMap<>();
        mapAnnee = new TreeMap<>();


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
                }

                if (jour!=null){
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


    public void getNbTweetMois(){
        InfosTweet monInfo;
        for (ArrayList<Tweet> at : mapMois.values()) {

            int nbTweets=at.size();
            //implemUsersPopulaires
            ArrayList<String> utilisateurs = new ArrayList<String>();
            utilisateurs.add("kevin");
            utilisateurs.add("steven");
            monInfo = new InfosTweet(at, nbTweets, utilisateurs);
            infosTweets.add(monInfo);
        }
    }


}
