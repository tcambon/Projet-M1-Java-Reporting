import java.util.Scanner;

public class MesTweet {
    private BaseTweet bt;

    public static void main(String[] args) {
        MesTweet mesTweet = new MesTweet();
        mesTweet.creer();
        mesTweet.ouvrir();
        mesTweet.afficher();
        mesTweet.rechercher();
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

    public void rechercher() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Que voulez vous chercher ?");
		String nf=sc.nextLine();
		bt.rechercher(nf);
		
	}
}
