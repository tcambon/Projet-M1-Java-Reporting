public class MesTweet {
    private BaseTweet bt;

    public static void main(String[] args) {
        MesTweet mesTweet = new MesTweet();
        mesTweet.creer();
        mesTweet.ouvrir();
        mesTweet.afficher();
        mesTweet.nbTweet();
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

    private void nbTweet(){
        bt.getNbTweetMois();

    }


}
