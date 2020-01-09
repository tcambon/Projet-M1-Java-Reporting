import java.util.ArrayList;

public class InfosUser {
    private String date;
    private String user;
    private int nbTweets;
    private int nbRT;
    private double score;


    public InfosUser(String date, String user, int nbTweets, int nbRT, double score) {
        this.date = date;
        this.user = user;
        this.nbTweets = nbTweets;
        this.nbRT = nbRT;
        this.score = score;
    }

    public String getDate() {
        return date;
    }
      
    public String getUser() {
        return user;
    }
    
    public int getNbTweets() {
        return nbTweets;
    }
    
    public int getNbRT() {
        return nbRT;
    }
    
    public double getscore() {
        return score;
    }

}