import javafx.scene.chart.XYChart;

public class MesTweet {
    private BaseTweet bt;

    public static void main(String[] args) {
        MesTweet mesTweet = new MesTweet();
        mesTweet.creer();
        mesTweet.ouvrir();
        mesTweet.afficher();
        mesTweet.graphicNbTweet();
    }

    private void creer() {
        bt = new BaseTweet();
        bt.initialise();
    }


    private void ouvrir() {
        bt.ouvrirCSV();
    }

    private void afficher() {
        System.out.print(bt.afficher());
    }

  

    private void graphicNbTweet(){
    	XYChart.Series<String, Integer> obj1  = bt.graphicNbTweetMois();
        bt.calcNbTweetJoursMois();
        XYChart.Series<String, Integer> obj2 = bt.graphicNbTweetJoursMois();
        //System.out.print(obj1[0]);
        //System.out.print(obj1[1]);

        //System.out.print(obj2[0]);
        //System.out.print(obj2[1]);

        ;
    }


}
