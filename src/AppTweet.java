import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppTweet extends Application {
	
    public static void main(String[] args) {
    launch(args);
}

public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("projetM1.fxml"));
    primaryStage.setTitle("Base de Tweet");
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    
}
}
