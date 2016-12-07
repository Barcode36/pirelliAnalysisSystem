package application;
	
import gui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class PirelliAnalysisSystem extends Application {
	@Override
	public void start(Stage stage) throws Exception{
        
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PASUI.fxml"));
		
		
		Parent root = (Parent) loader.load();
		
		Controller controller = (Controller)loader.getController();
		
		controller.addChangeListenerToTxtMetersToCut();
		controller.addChangeListenerToTextFileName();
		controller.addChangeListenerToChBoxRecipesListToAnalyze();
		controller.setInitialView();
		
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon.jpg")));
		
		stage.setTitle("Pirelli Analysis System 3.0");
		stage.setScene(new Scene(root));
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

