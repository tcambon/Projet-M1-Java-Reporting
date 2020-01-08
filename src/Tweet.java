import java.sql.Timestamp;
import java.util.Calendar;

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

    public String getJourPubli(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datePubli.getTime());
        int jour = cal.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(jour);
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





