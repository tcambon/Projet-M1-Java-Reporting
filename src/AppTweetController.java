import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

public class AppTweetController {
	
	//Déclaration des modules du FXML 
	
	@FXML
    private BarChart <String, Integer> barcharttweetmoth ;
	@FXML
    private BarChart <String, Integer> barcharttweetday ;
	@FXML
    private BarChart <String, Integer> barchartusermonth ;
	private BaseTweet bd = new BaseTweet();
	@FXML
	private MesTweet mesTweet = new MesTweet();
	@FXML
    private CategoryAxis XAxe = new CategoryAxis();
	@FXML
    private Label label2 = new Label();
	@FXML
    private Label label21 = new Label();
	@FXML
    private CategoryAxis XAxeday = new CategoryAxis();
	@FXML
    private CategoryAxis XAxeUM = new CategoryAxis();
	@FXML
    private Button button1 = new Button();
	@FXML
    private Button button2 = new Button();
	@FXML
    private ChoiceBox choiceboxD = new ChoiceBox<>();
	@FXML private ChoiceBox choiceboxUM = new ChoiceBox<>();

	//Quand on ouvre le fichier 
	@FXML void openc(ActionEvent event) {
		bd.initialise();
		bd.ouvrirCSV("c");
		open();
	}
	
	@FXML void openf(ActionEvent event) {
		bd.initialise();
		bd.ouvrirCSV("f");
		open();
	}
	public void open() {
		button1.setDisable(true);
		button2.setDisable(true);
		//On appelle les différentes fonctions dont on a besoin dès l'ouverture
	
		
		//Affichage du grahpique du nombre de tweet par mois
		//On va chercher series que l'on trouve dans la fonction graphicNbTweet de BaseTweet
	XYChart.Series<String, Integer> series = bd.graphicNbTweetMois();
	//On donne les information d'abscisses et d'ordonnées au 1er graphe
	barcharttweetmoth.getData().add(series);
	//On va chercher le nom des abscisses que l'on a donné
	ArrayList<String> abscisse = bd.getAbscisse();
	//On implémente ces dernières sur l'axe
    XAxe.setCategories(FXCollections.<String>observableArrayList((
    		abscisse)));
   
    
    ObservableList<String> availableChoices = FXCollections.<String>observableArrayList(abscisse); 
    choiceboxD.setItems(availableChoices);
    choiceboxUM.setItems(availableChoices);
    
	
	}
	
	@FXML void changeBCD(ActionEvent event) { 
		
		 barcharttweetday.getData().removeAll();
		 XAxeday.getCategories().removeAll();
		 barcharttweetday.getData().clear();
		 XAxeday.getCategories().clear();
		 
		 
		 label2.setText("Nombre de Tweets par jour dans la base en "+choiceboxD.getSelectionModel().getSelectedItem().toString());
		 
		bd.calcNbTweetJoursMois(choiceboxD.getSelectionModel().getSelectedItem().toString());
		//Affichage du grahpique du nombre de tweet par jour
	//On va chercher series que l'on trouve dans la fonction graphicNbTweet de BaseTweet
	XYChart.Series<String, Integer> series = bd.graphicNbTweetJoursMois();
    //On donne les information d'abscisses et d'ordonnées au 1er graphe
    barcharttweetday.getData().add(series);
    //On va chercher le nom des abscisses que l'on a donné
    ArrayList<String> abscisse = bd.getAbscisse();
    //On implémente ces dernières sur l'axe
    
    XAxeday.setCategories(FXCollections.<String>observableArrayList((
		abscisse)));

	}
	
	@FXML void changeBCUM(ActionEvent event) { 
		
		 barchartusermonth.getData().removeAll();
		 XAxeUM.getCategories().removeAll();
		 barchartusermonth.getData().clear();
		 XAxeUM.getCategories().clear();
		 
		 
		 label21.setText("Nombre de RT par mois pour Users populaires en "+choiceboxUM.getSelectionModel().getSelectedItem().toString());
		 
		bd.utilisateursPopulaires(choiceboxUM.getSelectionModel().getSelectedItem().toString());
		//Affichage du grahpique du nombre de tweet par jour
	//On va chercher series que l'on trouve dans la fonction graphicNbTweet de BaseTweet
	XYChart.Series<String, Integer> series = bd.graphicUtilisateursPopulaires();
   //On donne les information d'abscisses et d'ordonnées au 1er graphe
	barchartusermonth.getData().add(series);
   //On va chercher le nom des abscisses que l'on a donné
   ArrayList<String> abscisse = bd.getAbscisse();
   //On implémente ces dernières sur l'axe
   
   XAxeUM.setCategories(FXCollections.<String>observableArrayList((
		abscisse)));

	}

}
