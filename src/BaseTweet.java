import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BaseTweet {

    private ArrayList<Tweet> tweets;

    public void initialise() {
        tweets = new ArrayList<>();
    }


    public void ouvrirCSV() {
        try {
            FileReader fr = new FileReader("climat.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            Tweet montweet;

            int lineNb = 1;
            long initTime = System.currentTimeMillis();
            while ((line = br.readLine()) != null) {
                String[] arrLine = line.split("\t");

                long idTweet = 0;
                try {
                    if (arrLine.length > 0) {
                        idTweet = Long.parseLong(arrLine[0]);
                    }
                } catch (Exception ex) {
                    System.err.println("Invalid idTweet for line " + lineNb);
                }

                String idUtilisateur = null;
                if (arrLine.length > 1) {
                    idUtilisateur = arrLine[1];
                }

                String datePubli = null;
                if (arrLine.length > 2) {
                    datePubli = arrLine[2];
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
                lineNb++;
            }
            long elapseTime=System.currentTimeMillis() - initTime;
            System.out.println(lineNb + " lignes chargées en " + elapseTime + " ms");
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
        for (int ii = 0; ii < 16; ii++) {
            sb.append(ii + " " + tweets.get(ii).toString() + "\n");
        }
        return sb.toString();
    }


}
