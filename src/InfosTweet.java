import java.util.ArrayList;

public class InfosTweet {
    private ArrayList<Tweet> tweets;
    private int nbTweets;
    private ArrayList<String> utilisateurs;


    public InfosTweet(ArrayList<Tweet> tweets, int nbTweets, ArrayList<String> utilisateurs) {
        this.tweets = tweets;
        this.nbTweets = nbTweets;
        this.utilisateurs = utilisateurs;
    }
}
