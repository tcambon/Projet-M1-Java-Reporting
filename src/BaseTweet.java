import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BaseTweet {

    private ArrayList<Tweet> tweets;
    private ArrayList<Utilisateurs> uti;
    
    public void initialise() {
        tweets = new ArrayList<>();
        uti = new ArrayList<>();
    }


    public void ouvrirCSV() {
        try {
            FileReader fr = new FileReader("C:/Users/cedri/eclipse-workspace/data/climat.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            Tweet montweet;

            int lineNb = 1;
            int nberr = 0;
            long initTime = System.currentTimeMillis();
            while ((line = br.readLine()) != null) {
                String[] arrLine = line.split("\t");

                long idTweet = 0;
                try {
                    if (arrLine.length > 0) {
                        idTweet = Long.parseLong(arrLine[0]);
                    }
                } catch (Exception ex) {
                	nberr++;
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
                //triUti(montweet);
                lineNb++;
                
            }
            int nbtweets = lineNb-nberr;
            long elapseTime=System.currentTimeMillis() - initTime;
            System.out.println(lineNb + " lignes chargées en " + elapseTime + " ms");
            System.out.println(nbtweets + " tweets chargées en " + elapseTime + " ms");
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
    
    // C'est pour tout mais on peut décomposer 
    
    public void rechercher(String ph) {
    	long initTime = System.currentTimeMillis();
    	Iterator<Tweet> it= tweets.iterator();
		Tweet t=null;
		int i = 0;
		int nbt = 0;
		while (it.hasNext()) {
			i=i+1;	
			t=it.next();
			if ((t.toString().contains(ph))) {	
				//System.out.println("Tweet "+i+" :");
				//System.out.println(t.toString());
				nbt++;
			}
		
		}
		long elapseTime=System.currentTimeMillis() - initTime;
		double pour = (nbt/i)*100;
        System.out.println(i + " tweets chargées en " + elapseTime + " ms");
        System.out.println(nbt + " tweets contiennent " + ph);
        System.out.println("soit " + pour + "% des tweets de la base.");
    }
    
    /// Créer un tableau nom_uti, nb_tweet, nb_tweet_sans_rt, nb_rt_tot (pour popularité ?)
    
    public void triUti(Tweet montweet) {
    	
    	Iterator<Utilisateurs> it= uti.iterator();
		Utilisateurs u=null;
		boolean trouvut = false;
		
		while (it.hasNext()) {
			u=it.next();
    	if (u.getnom()==montweet.getnom()) {
    		u.setnbtw();
    		trouvut=true;    		
    		if (montweet.hasRetweet()==false) {
        		u.setnbtwssrt();   			
    		}
    		
    		System.out.println(u.toString() + " modifé ");
    	}
    	
    	if ((montweet.hasRetweet()) && (u.getnom()==montweet.getutirt())) {
    			u.setnbrttot();
    			System.out.println(u.toString() + " modifé ");
    		}   
    	
    	
    }
		
		if (trouvut==false) {
			if (montweet.hasRetweet()) {
			u= new Utilisateurs(montweet.getnom(),1,0,0);
			}
			else {
				u= new Utilisateurs(montweet.getnom(),1,1,0);
			}
            uti.add(u);
		}
	
		
    }

}
