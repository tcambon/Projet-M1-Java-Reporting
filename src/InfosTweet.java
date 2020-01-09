import java.util.ArrayList;

public class InfosTweet implements Comparable< InfosTweet > {
    private String date;
    private ArrayList<Tweet> tweets;
    private int nbTweets;
  
  
    public InfosTweet(String date, ArrayList<Tweet> tweets, int nbTweets) {
        this.date = date;
        this.tweets = tweets;
        this.nbTweets = nbTweets;
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }
    public String getDate() {
        return date;
    }
    public int getNbTweets() {
        return nbTweets;
    }


    @Override
    public int compareTo(InfosTweet o) {
        return String.valueOf(this.getNbTweets()).compareTo(String.valueOf(o.getNbTweets()));


    }


}
