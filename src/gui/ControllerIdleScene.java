package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class ControllerIdleScene {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView PirelliLogo;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private BorderPane theRoot;
    
    public ProgressIndicator getProgressIndicator(){
    	
    	return this.progressIndicator;
    	
    }


    @FXML
    void initialize() {
        assert PirelliLogo != null : "fx:id=\"PirelliLogo\" was not injected: check your FXML file 'PASUIIdleState.fxml'.";
        assert progressIndicator != null : "fx:id=\"progressIndicator\" was not injected: check your FXML file 'PASUIIdleState.fxml'.";
        assert theRoot != null : "fx:id=\"theRoot\" was not injected: check your FXML file 'PASUIIdleState.fxml'.";


    }

}
