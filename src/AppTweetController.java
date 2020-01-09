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
	private BaseTweet bd = new BaseTweet();
	@FXML
	private MesTweet mesTweet = new MesTweet();
	@FXML
    private CategoryAxis XAxe = new CategoryAxis();
	@FXML
    private CategoryAxis XAxeday = new CategoryAxis();
	@FXML
    private ChoiceBox choiceboxD = new ChoiceBox<>();

	//Quand on ouvre le fichier 
	@FXML void open(ActionEvent event) {
		
		//On appelle les différentes fonctions dont on a besoin dès l'ouverture
		bd.initialise();
		bd.ouvrirCSV();
		
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
    
	
	}
	
	@FXML void changeBCD(ActionEvent event) { 
		
		 barcharttweetday.getData().removeAll();
		 XAxeday.getCategories().removeAll();
		 barcharttweetday.getData().clear();
		 XAxeday.getCategories().clear();
		 
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

}
