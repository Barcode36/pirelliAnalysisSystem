package view;

import java.util.ArrayList;

import gui.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Model;


// Controller has to refer to this class in order to represent the table.

public class Table extends Application {
	 
	    public TableView<ArrayList<String>> table = new TableView<ArrayList<String>>();
	    private Controller controller;
	    ObservableList<ArrayList<String>> list ;
	     
	    
	    // INSERIMENTO DEI MENU
	 
	    @Override
	    public void start(Stage stage) {
	    	
	    	//FXMLLoader loader = new FXMLLoader();//.getResource("TableAndGraphView.fxml"));
	    	
	    	Group root = new Group();
	        Scene scene = new Scene(root);
	        stage.setTitle("Calender Table");
	        stage.setWidth(768);
	        stage.setHeight(1024);
	        
	        ScrollPane sc = new ScrollPane();
	        sc.setHmin(0);
	        sc.setHmax(400);
	        sc.setHvalue(50);
	        root.getChildren().add(sc);
	        //Controller controller = loader.getController();
	        
	        stage.setTitle("Pirelli Analysis System 3.0");
	  	
	        final Label label = new Label("Table");
	        label.setFont(new Font("Arial", 14)); 
	 
	        table.setEditable(true);
	 
	        
	        //Aggiunta delle colonne alla view.
	        TableColumn<ArrayList<String>, String> temp;
			for (String s: controller.getModel().getColonneRilevanti())
	        	{
				temp = new TableColumn<ArrayList<String>, String> (s);
	        	table.getColumns().add(temp);
	        	temp.setCellValueFactory(new PropertyValueFactory<ArrayList<String>,String>(s));
	        	}
			
			list = FXCollections.observableArrayList();
		
			for (ArrayList<String> s : controller.getModel().getValuesTable() )
					{
						list.add(s); 
					for (String s2: s)
						System.out.println(s2);
					}
			
			table.setItems(list);
			
			// NEEDED A CHECK
			
	 
	        final VBox vbox = new VBox();
	        vbox.setSpacing(5);
	        vbox.setPadding(new Insets(10, 0, 0, 10));
	        vbox.getChildren().addAll(label, table);
	 
	        ((Group) scene.getRoot()).getChildren().addAll(vbox);
	 
	    	stage.setScene(scene);
	  		stage.setWidth(1024);
	  		stage.setHeight(768);
	  		stage.centerOnScreen();
	  		stage.show();
	 
	    }



		public void setController(Controller controller) {
			this.controller = controller;
			
		}
	}
	
	


