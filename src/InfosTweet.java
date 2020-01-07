import java.util.ArrayList;

public class InfosTweet {
    private String date;
    private ArrayList<Tweet> tweets;
    private int nbTweets;
    private ArrayList<String> utilisateurs;


    public InfosTweet(String date, ArrayList<Tweet> tweets, int nbTweets, ArrayList<String> utilisateurs) {
        this.date = date;
        this.tweets = tweets;
        this.nbTweets = nbTweets;
        this.utilisateurs = utilisateurs;
    }

    public String getDate() {
        return date;
    }
    public int getNbTweets() {
        return nbTweets;
    }


}
