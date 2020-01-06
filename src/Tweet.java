import java.sql.Timestamp;

public class Tweet {
    private long idTweet;
    private String idUtilisateur;
    private Timestamp datePubli;
    private String contenu;
    private String idUtilisateurRT;

    public Tweet(long idTweet, String idUtilisateur, Timestamp datePubli, String contenu, String idUtilisateurRT) {
        this.idTweet = idTweet;
        this.idUtilisateur = idUtilisateur;
        this.datePubli = datePubli;
        this.contenu = contenu;
        this.idUtilisateurRT = idUtilisateurRT;
    }

    public long getIdTweet() {
        return idTweet;
    }

    public void setIdTweet(long idTweet) {
        this.idTweet = idTweet;
    }

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Timestamp getDatePubli() {
        return datePubli;
    }

    public void setDatePubli(Timestamp datePubli) {
        this.datePubli = datePubli;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getIdUtilisateurRT() {
        return idUtilisateurRT;
    }

    public void setIdUtilisateurRT(String idUtilisateurRT) {
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

    }





