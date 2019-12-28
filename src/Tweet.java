public class Tweet {
    private long idTweet;
    private String idUtilisateur;
    private String datePubli;
    private String contenu;
    private String idUtilisateurRT;

    public Tweet(long idTweet, String idUtilisateur, String datePubli, String contenu, String idUtilisateurRT) {
        this.idTweet = idTweet;
        this.idUtilisateur = idUtilisateur;
        this.datePubli = datePubli;
        this.contenu = contenu;
        this.idUtilisateurRT = idUtilisateurRT;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "idTweet=" + idTweet +
                ", idUtilisateur='" + idUtilisateur + '\'' +
                ", datePubli='" + datePubli + '\'' +
                ", contenu='" + contenu + '\'' +
                ", idUtilisateurRT='" + idUtilisateurRT + '\'' +
                '}';
    }

    public boolean hasRetweet(){
        return idUtilisateurRT != null;
        }
    
    public String getnom() {
    	return idUtilisateur;
    }
    
    public String getutirt() {
    	return idUtilisateurRT;
    }

    }





