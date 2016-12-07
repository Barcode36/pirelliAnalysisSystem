package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import dataAnalysis.FileAnalysis;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Model;
import view.Table;
import javafx.stage.FileChooser.ExtensionFilter;



public class Controller{
	
	private static final int CQuadruplex = 350;
	private static final int CDuplex = 140;
	private static final int CInnerLiner = 140;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView PirelliLogo;

    @FXML
    private Button buttonAnalyze;

    @FXML
    private Button buttonBrowse;

    @FXML
    private Button buttonBrowseSAP;
    
    @FXML
    private ChoiceBox<String> chBoxDateFrom;

    @FXML
    private ChoiceBox<String> chBoxDateTo;

    @FXML
    private ChoiceBox<String> chBoxRecipesListToAnalyze;

    @FXML
    private HBox hBoxDateAnalysis;
    

    @FXML
    private VBox vBoxTot;

    @FXML
    private ImageView imgDownArrow;

    @FXML
    private ImageView imgDownArrowHighlight;

    @FXML
    private TextField textFileName;

    @FXML
    private TextField textSAP;
    
    @FXML
    private TextField txtMetersToCut;

    @FXML
    private BorderPane theRoot;
    
    @FXML
    private VBox vBoxDateAnalysis;
    
    private ControllerTableAndGraph controllerTableAndGraph;

    private ControllerIdleScene controllerIdleScene;
    
    private List<String> totParamList;
    
    private List<String> totParamSAPList;
    
    private List<String> recipeList;
    
    private List<String> dateList;
    
    private String machine;
    
    private Model model;
    
    @FXML
    private TextField textFileCalender;

    @FXML
    private Button btnBrowseCalender;

    @FXML
    private ChoiceBox<Integer> chBoxOraInizio;

    @FXML
    private ChoiceBox<Integer> chBoxOraFine;

    @FXML
    private Button btnAnalyseCalender;
    
    
  // @FXML manage interaction with the user
    public void generateTableCalender()
    {
    	
    	
    	model = new Model ();
    	
    	model.generateTable(textFileCalender.getText(), chBoxOraInizio.getValue(), chBoxOraFine.getValue());
    	
    	//TODO START TABLE VIEW 
    	
    	
    	this.setModel(model);
    	
    }
    
    public void setModel (Model model){
    	
    	this.model = model; 
    	
    }
    
    public Model getModel (){
    	
    	return model;
    }
    

    void generateGraphCalender(){
    	
    	//TODO Generate graph, interact with the view 
    	
    }
    
    
    
    @FXML
    void doAnalyseCalender(ActionEvent event) {
    	
    	//Controllo che l'indirizzo del file sia inserito
    	if (textFileCalender.getText().equals(null)){
    		JOptionPane.showMessageDialog(null, "Pay attention! File not selected!");
    	}

    	//Controllo sulle ore
    	
    	if (chBoxOraInizio.getValue()==null || chBoxOraFine.getValue() == null || chBoxOraFine.getValue()<chBoxOraInizio.getValue())
    		JOptionPane.showMessageDialog(null, "Pay attention! Hour absent or bad inserted");
    	
    	generateTableCalender();
    	
    	generateGraphCalender();
    	
    	// ====== Close initial stage ==== //

    	Stage stage  = (Stage) this.btnAnalyseCalender.getScene().getWindow(); //TODO cambiare bottone
    	stage.close();

    	// ===== Open table and graph view stage ======

    	try {
    				this.startTableAndGraphViewCalender(stage);
    		} catch (Exception e) {
    				e.printStackTrace();
    				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
    				System.exit(-1);
    		}

    			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    				public void handle(WindowEvent we) {
    					if(JOptionPane.showConfirmDialog(null, "Do you want really leave Pirelli Analysis System?", "EXIT CONFIRMATION MESSAGE",JOptionPane.YES_NO_OPTION) == 0)
    						System.exit(-1);
    					else
    						we.consume();
    				}
    			}); 
    	
    }

    private void startTableAndGraphViewCalender(Stage stage) {

//TODO modify
       
		
  		Table view = new Table();
  		view.setController(this);
  		view.start(stage);
  		
  		
		
	}

	@FXML
    /*** Seleziona l'indirizzo del File al fine di inserirlo nell'interfaccia grafica ***/
    void doBrowseCalender(ActionEvent event) {
    	
    	ObservableList<Integer> options = FXCollections.observableArrayList();

		//Popolamento ChoiceBox Ora

		for (int i = 0; i <24; i++){
			options.add(i);	
		}

		chBoxOraInizio.setItems(options);
		chBoxOraFine.setItems(options);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("../"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("csv","*.csv"));
		File file = fileChooser.showOpenDialog(null);

		if(file==null)
			this.clearScene();
		else{ 
			this.textFileCalender.setText(file.getPath());

			this.buttonAnalyze.setDisable(true);

			if(!this.textFileCalender.getText().substring(this.textFileCalender.getText().length()-4, this.textFileCalender.getText().length()).equals(".csv")){
				JOptionPane.showMessageDialog(null, "BAD REQUEST! The file's extension must be \".csv\"","ALERT MESSAGE",JOptionPane.ERROR_MESSAGE);
				this.clearScene();       	    	
			}
			
		}


		

    }
    
    
    
    
    
    public void addChangeListenerToTxtMetersToCut(){
    	
  	  this.txtMetersToCut.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	        	
	        	try{
	        		
	        		if(Integer.parseInt(txtMetersToCut.getText())<0)
	        			txtMetersToCut.setText("0");
	        		else
	        			if(Integer.parseInt(txtMetersToCut.getText())>60)
	        				txtMetersToCut.setText("60");
	        			
	        		
	        	}
	        	catch(Exception e){
	        		
	        		txtMetersToCut.setText("10");
	        		
	        	}
	        	
	        }
	        	
	    });
    	
    }
    
    public void addChangeListenerToTextFileName(){
    	
    	  this.textFileName.textProperty().addListener(new ChangeListener<String>() {
  	        @Override
  	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
  	        	
  	        	recognizeMachine(textFileName.getText());
  	        	
  	        	if(machine == null)
  	        		return;
  	        	 	        		
  	        	textSAP.setDisable(false);
  	        	buttonBrowseSAP.setDisable(false);
  	        	     	
  	        	
  	        }
  	        	
  	    });
      	
      }
    
    public void addChangeListenerToChBoxRecipesListToAnalyze(){
    	
  	  this.chBoxRecipesListToAnalyze.valueProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {

	        	if(chBoxRecipesListToAnalyze.getValue()!=null && !chBoxRecipesListToAnalyze.getValue().equals("")){
	        		txtMetersToCut.setText("0");
	        		txtMetersToCut.setDisable(true);
	        	}
	        	else{
	        		txtMetersToCut.setText("10");
	        		txtMetersToCut.setDisable(false);
	        	}
	        }
	        	
	    });
    	
    }

    @FXML
    void generateAnalysis(ActionEvent event) {
    	
    	//========= Controls if selected files are ok =========

    	try {
			Reader file = new FileReader(this.textFileName.getText());
			file.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "NOT EXISTING FILE! Please, select an existing csv file", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);	
			this.clearScene();
  	    	return;
		}
    	
    	try {
			Reader fileSAP = new FileReader(this.textSAP.getText());
			fileSAP.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "NOT EXISTING FILE! Please, select an existing csv file", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);	
			this.clearSceneSAP();
  	    	return;
		}
    
    	
    	if(!this.areCorrectCSVFiles(this.textFileName.getText(), this.textSAP.getText()))
    		return;
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	
    	if(this.chBoxRecipesListToAnalyze!=null && this.chBoxRecipesListToAnalyze.getValue()!=null && !this.chBoxRecipesListToAnalyze.getValue().equals("")){
    		
    		try {
				if(formatter.parse(this.chBoxDateFrom.getValue()).compareTo(formatter.parse(this.chBoxDateTo.getValue()))>0){
					String dateTemp = this.chBoxDateFrom.getValue();
					this.chBoxDateFrom.setValue(this.chBoxDateTo.getValue());
					this.chBoxDateTo.setValue(dateTemp);
				}
			} catch (ParseException e) {
				this.chBoxRecipesListToAnalyze.setValue("");
			}
    		
    	}
    		
    	 
    	  FileAnalysis fa = new FileAnalysis(this.textFileName.getText(), this.textSAP.getText());
    	  
    	  fa.setMetersToCut(Integer.parseInt(this.txtMetersToCut.getText()));
    	  
    	  // ====== Close initial stage ==== //
    	  
    	  Stage stage  = (Stage) this.buttonAnalyze.getScene().getWindow();
    	  stage.close();
    	  
    	  // ====== Open Idle stage ======  	
    	  
    	  try {
			this.startIdleView(stage);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
  			System.exit(-1);
		}
    	  
    	  // ======= Performances calculation operations ==========
    	      	  
    	  this.controllerIdleScene.getProgressIndicator().setProgress(0.2);
    	  fa.setMachine(this.machine);
    	  fa.setTotParamList(this.totParamList);
    	  fa.setTotParamSAPList(this.totParamSAPList);
    	  if(this.chBoxRecipesListToAnalyze!=null && this.chBoxRecipesListToAnalyze.getValue()!=null && !this.chBoxRecipesListToAnalyze.getValue().equals(""))
    		  fa.loadLimitedKPITable(this.chBoxRecipesListToAnalyze.getValue(),this.chBoxDateTo.getValue(),this.chBoxDateFrom.getValue());
    	  else
    		  fa.loadKPITable();
    	  this.controllerIdleScene.getProgressIndicator().setProgress(0.4);
    	  fa.loadSAPfile();
    	  this.controllerIdleScene.getProgressIndicator().setProgress(0.6);
    	  fa.cleanKPITable();
    	  fa.generateSummaryTable();
    	  this.controllerIdleScene.getProgressIndicator().setProgress(0.8);
    	  fa.deleteUnusefulData();
    	  this.controllerIdleScene.getProgressIndicator().setProgress(1);
    	  
    	  if(this.chBoxRecipesListToAnalyze!=null && this.chBoxRecipesListToAnalyze.getValue()!=null && !this.chBoxRecipesListToAnalyze.getValue().equals(""))
    		  fa.cleanRecipesPerformance(this.chBoxRecipesListToAnalyze.getValue());
    	  
    	  stage.close();
    	  this.controllerIdleScene = null;
    	  
    	  // ===== Open table and graph view stage ======
    	  
    	  try {
  			this.startTableAndGraphView(stage);
  		} catch (IOException e) {
  			e.printStackTrace();
  			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
  			System.exit(-1);
  		}
    	  
    	  stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  if(JOptionPane.showConfirmDialog(null, "Do you want really leave Pirelli Analysis System?", "EXIT CONFIRMATION MESSAGE",JOptionPane.YES_NO_OPTION) == 0)
	          		System.exit(-1);
	        	  else
	        		  we.consume();
	          }
	      }); 
    	  
    	//====== table and graph realization ======

    	  this.controllerTableAndGraph.setStage(stage);
    	  this.controllerTableAndGraph.setFilePath(this.textFileName.getText());
    	  this.controllerTableAndGraph.setSAPFilePath(this.textSAP.getText());
    	  this.controllerTableAndGraph.setMachine(fa.getMachine());
    	  if(this.chBoxRecipesListToAnalyze!=null && this.chBoxRecipesListToAnalyze.getValue()!=null && !this.chBoxRecipesListToAnalyze.getValue().equals(""))
    		  this.controllerTableAndGraph.setAllViewChart(true);
    	  else
    		  this.controllerTableAndGraph.setAllViewChart(false);
    	  this.controllerTableAndGraph.hideMenuItSaveAll();
    	  this.controllerTableAndGraph.setGraphStyle(); 
    	  this.controllerTableAndGraph.resetAnalysisValues();
    	  this.controllerTableAndGraph.setFileAnalysis(fa);
    	  this.controllerTableAndGraph.setRecipesLists();
    	  this.controllerTableAndGraph.setMetersToCut(fa.getMetersToCut());
    	  String machine = fa.getMachine();
    	  this.launchMachineMethod(machine, fa);
    	  this.controllerTableAndGraph.setTableDims();
    	  this.controllerTableAndGraph.setParametersToDBIndexes();
    	  this.controllerTableAndGraph.setPIOnAction(controllerTableAndGraph);
    	  this.controllerTableAndGraph.generateInitialGraph();
    	  
    	  
    }
    
    private void launchMachineMethod(String machine,FileAnalysis fa){
    	
    	this.controllerTableAndGraph.hideUnusefulTables();
    	
    	if(machine.equals("Quadruplex"))
    	    this.controllerTableAndGraph.generateTableQuadruplex(fa.getRecipesPerformanceQuadruplex()); 
    	  else
    		  if(machine.equals("Duplex"))
    			  this.controllerTableAndGraph.generateTableDuplex(fa.getRecipesPerformanceDuplex());
    		  else
    			  this.controllerTableAndGraph.generateTableInnerLiner(fa.getRecipesPerformanceInnerLiner());
    			  
    }

    @FXML
    void openFileSystemBrowsing(ActionEvent event) {
    	
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setInitialDirectory(new File("../"));
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("csv","*.csv"));
  	    File file = fileChooser.showOpenDialog(null);
  	    
  	    if(file==null)
  	    	this.clearScene();
        else{ 
        	  this.textFileName.setText(file.getPath());
        	  
        	  if(this.textSAP.getText()!=null && !this.textSAP.getText().equals(""))
        	    this.buttonAnalyze.setDisable(false);
        	  
        	  if(!this.textFileName.getText().substring(this.textFileName.getText().length()-4, this.textFileName.getText().length()).equals(".csv")){
       	    	JOptionPane.showMessageDialog(null, "BAD REQUEST! The file's extension must be \".csv\"","ALERT MESSAGE",JOptionPane.ERROR_MESSAGE);
       	    	this.clearScene();       	    	
       	     } 
        	  
        	          	  
        	  this.chBoxRecipesListToAnalyze.getItems().clear();
        	  this.chBoxDateFrom.getItems().clear();
        	  this.chBoxDateTo.getItems().clear();
        	  
        	  this.generateInitialRecipeList();
        	  

        	  if(recipeList!=null && recipeList.size()>1){
        		        		  
        		  
        		  
        		  Collections.sort(this.recipeList, new Comparator<String>(){

        				@Override
        				public int compare(String r1, String r2) {
        					
        					if(r1==null || r1.equals("") || r2==null || r2.equals("")){
        						r2=r1;
        					}
        					
        					int rec1 = Integer.parseInt(r1);       					
        					int rec2 = Integer.parseInt(r2);
        					return (rec1-rec2);
        				}
                		  
                	  });
        		  
        	      this.imgDownArrow.setVisible(true);
        	      this.imgDownArrow.setDisable(false);
        	      
        	  }
        	  
        	  this.chBoxRecipesListToAnalyze.getItems().addAll(recipeList);
        	  
        	  if(dateList!=null && dateList.size()>0){
        		  
        		  this.chBoxDateFrom.getItems().addAll(dateList);
        		  this.chBoxDateTo.getItems().addAll(dateList);
        		  
        		  if(dateList.size()==1){
        			  
        			  this.chBoxDateFrom.setValue(this.chBoxDateFrom.getItems().get(0));
            		  this.chBoxDateTo.setValue(this.chBoxDateFrom.getItems().get(0));
            		  this.chBoxDateFrom.setDisable(true);
            		  this.chBoxDateTo.setDisable(true);
        			  
        		  }
        		  else{
        			  
        			  this.chBoxDateFrom.setValue(this.chBoxDateFrom.getItems().get(0));
            		  this.chBoxDateTo.setValue(this.chBoxDateFrom.getItems().get(this.chBoxDateTo.getItems().size()-1));  
        			  
        		  }
        		  
        	  }

        	
        }
    }

    @FXML
    void openFileSystemBrowsingSAP(ActionEvent event) {
    	
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setInitialDirectory(new File("../"));
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("csv","*.csv"));
  	    File file = fileChooser.showOpenDialog(null);
  	    
  	    if(file==null)
  	    	this.clearSceneSAP();
        else{         	  
        	  this.textSAP.setText(file.getPath());
        	  
        	  if(this.textFileName.getText()!=null && !this.textFileName.getText().equals(""))
          	    this.buttonAnalyze.setDisable(false);
        	  
        	  if(!this.textSAP.getText().substring(this.textSAP.getText().length()-4, this.textSAP.getText().length()).equals(".csv")){
       	    	JOptionPane.showMessageDialog(null, "BAD REQUEST! The file's extension must be \".csv\"","ALERT MESSAGE",JOptionPane.ERROR_MESSAGE);
       	    	this.clearSceneSAP();       	    	
       	     }        	 
        }
    }

    private void clearScene(){
    	this.textFileName.clear();
    	this.textFileName.setPromptText("e.g. SemifinishedData.csv");
	    this.textFileName.selectAll();
    	
    }
    
    private void clearSceneSAP(){
    	this.textSAP.clear();
    	this.textSAP.setPromptText("e.g. SAP.csv");
	    this.textSAP.selectAll();
    	
    }
    
    private List<String> generateTotParamList(String filepath){
    	
        BufferedReader br = null;	
		List<String> totParamList = new LinkedList<String>();
		
        
		  try {
			br = new BufferedReader(new FileReader(filepath));
		  } catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		  }
		 
		  String riga = null;
		  StringTokenizer st;
		  int i=0;
		  
		  try {
			while((riga = br.readLine()) != null && i==0){
				  i++;
				  st = new StringTokenizer(riga,";");
				  
				  while(st.hasMoreTokens())
					  totParamList.add(st.nextToken());
				  
			  }
		} catch (IOException e) {}
    	
		  try {
			br.close();
		} catch (IOException e) {}
		  
		  
		  if(totParamList.contains("Calender - Line Speed Actual (m/min)")){
			  totParamList.add("Width Upper Tolerance");
			  totParamList.add("Width Lower Tolerance");
		  }
		  
		  totParamList.add("ProductionIndex");
		  
		  return totParamList;
		  
    }
    
    
    public void recognizeMachine(String filepath){
    	
    	this.machine = null;
    	
    	this.totParamList = this.generateTotParamList(filepath);
    	
    	if(totParamList.contains("Calender - Lower Cutter Actual (mm)") && totParamList.contains("Calender - Upp. Ext. Cutter Actual (mm)")){
    		this.machine = "InnerLiner";
    	    return;
    	}
    	
    	if(totParamList.contains("LINE - Width Winder Zone Measure DS (mm)") && totParamList.contains("LINE - Weight Winder Zone Measure DS (g)") && !totParamList.contains("EXTRUDER 250 mm - Feeder Speed Feedback (m/min)") && !totParamList.contains("EXTRUDER 250 mm - Dancer Position Feedback (%)")){
    		this.machine = "Duplex";
    		return;
    	}
    	
    	if(totParamList.contains("LINE - Width Winder Zone Measure OS (Or Single Stripe) (mm)") || totParamList.contains("LINE - Weight Winder Zone Measure OS (Or Single Stripe) (mm)") || totParamList.contains("EXTRUDER 250 mm - Feeder Speed Feedback (m/min)") || totParamList.contains("EXTRUDER 250 mm - Dancer Position Feedback (%)")){
    		this.machine = "Quadruplex";
    		return;
    	}
    	
    }
    
    private boolean areCorrectCSVFiles(String filepath, String SAPFilepath){
    	
    	// ======== Control data file structure =========
    	
    	if(this.machine==null){
    		
    		JOptionPane.showMessageDialog(null, "UNCORRECT FILE STRUCTURE! Please, select another csv file containing semifinished's data", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
    		this.clearScene();
			return false;
    		
    	}
    	
    	
    	//============ Control SAP data file structure ===========
    	    	
    	this.totParamSAPList = this.generateTotParamList(SAPFilepath);
    	
    	if(this.machine.equals("InnerLiner")){
    	
    		if(!totParamSAPList.contains("TargetWidth") || !totParamSAPList.contains("ShortCode")){
        		
        		JOptionPane.showMessageDialog(null, "UNCORRECT FILE STRUCTURE! Please, select another csv file containing SAP data", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
        		this.clearSceneSAP();
    			return false;
        		
        	}
    		
    	}
    	else{
    	
    		if(!totParamSAPList.contains("AVAA :Codice Corto") || !totParamSAPList.contains("FAAO :Largh totale fascia+mini") || !totParamSAPList.contains("FAAO :Racc bilancia continua") || !totParamSAPList.contains("FNAB :Codice Corto") || !totParamSAPList.contains("FNAB :Larghezza totale assembl")){
    		
    			JOptionPane.showMessageDialog(null, "UNCORRECT FILE STRUCTURE! Please, select another csv file containing SAP data", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
    			this.clearSceneSAP();
    			return false;
    		
    		}
    	
    	}
    	
    	return true;
    }
    
    private void startTableAndGraphView(Stage stage) throws IOException{
    	
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PASUITableAndGraphView.fxml"));
		
		Parent root = (Parent) loader.load();
		
		controllerTableAndGraph = (ControllerTableAndGraph)loader.getController();	
		
		stage.setTitle("Pirelli Analysis System 3.0");
		stage.setScene(new Scene(root));
		stage.setWidth(1024);
		stage.setHeight(768);
		stage.centerOnScreen();
		stage.show();
    	
    }
    
    private void startIdleView(Stage stage) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("PASUIIdleState.fxml"));

		
		Parent root = (Parent) loader.load();

		controllerIdleScene = (ControllerIdleScene)loader.getController();
		
		stage.setTitle("Pirelli Analysis System 3.0");
		stage.setScene(new Scene(root));
		stage.show();
    	
    }
    
    @FXML
    void baseArrow(MouseEvent event) {
    	this.imgDownArrowHighlight.setVisible(false);
    	this.imgDownArrow.setVisible(true);
    }

    @FXML
    void highlightArrow(MouseEvent event) {
    	this.imgDownArrow.setVisible(false);
    	this.imgDownArrowHighlight.setVisible(true);
    }
    
    
    
    public TextField getTextFileName(){
    	
    	return this.textFileName;
    	
    }
    
    public TextField getTextSAP(){
    	
    	return this.textSAP;
    	
    }
    
    public TextField getTxtMetersToCut(){
    	
    	return this.txtMetersToCut;
    	
    }
    
    public void setInitialView(){
    	
    	this.chBoxRecipesListToAnalyze.getItems().clear();
    	this.chBoxDateFrom.getItems().clear();
    	this.chBoxDateTo.getItems().clear();
    	
    	this.imgDownArrowHighlight.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				
				showOrHideDateAnalysisPad();
				
			}
    		
    		
    		
    	});
    	
    	this.vBoxTot.getChildren().remove(this.vBoxTot.getChildren().indexOf(this.vBoxDateAnalysis));
    	this.vBoxTot.setPrefHeight(-1);
    	
    	this.imgDownArrow.setVisible(false);
    	this.imgDownArrow.setDisable(true);
    	
    	
    }
    
    private void showOrHideDateAnalysisPad(){
    	
    	if(!this.vBoxTot.getChildren().contains(this.vBoxDateAnalysis)){
      	  this.vBoxTot.getChildren().add(this.vBoxDateAnalysis);
      	  this.theRoot.getScene().getWindow().setHeight(750);
      	}
      	else{
      		
      	  this.vBoxTot.getChildren().remove(this.vBoxTot.getChildren().indexOf(this.vBoxDateAnalysis));
          this.theRoot.getScene().getWindow().setHeight(600);
      		
      	}
    	
    }
    
    private void generateInitialRecipeList(){
    	
    	Map<String,int[]> recipeToLimits = new HashMap<String,int[]>();
    	
    	if(this.textFileName.getText()==null || this.textFileName.getText().equals(""))
    		return;
    	
    	BufferedReader br = null;
    	
    	try {
			br = new BufferedReader(new FileReader(this.textFileName.getText()));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "NOT EXISTING FILE! Please, select an existing csv file", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);	
			this.clearScene();
  	    	return;
		}
    	
    	String row;
    	String[] tSplit;
    	String[] dateTemp;
    	this.recipeList = new LinkedList<String>();
    	this.recipeList.add("");
    	this.dateList = new LinkedList<String>();
    	
    	try {
			while((row=br.readLine())!=null){
				
				tSplit = row.split(";");
				
				if(tSplit[0]!=null && !tSplit[0].equals("") && !tSplit[0].equals("DateTimeRec")){
					
					dateTemp = tSplit[0].split(" ");

					if(!dateList.contains(dateTemp[0]))
						dateList.add(dateTemp[0]);

					
					
				}
				
				if(tSplit[2]!=null && !tSplit[2].equals("") && !tSplit[2].equals("9999A0")  && !tSplit[2].equals("SAP Code"))
					this.addDataToRecipeToLimitsMap(""+Integer.parseInt(tSplit[2]),recipeToLimits);
								
				if(tSplit[2]!=null && !tSplit[2].equals("") && !tSplit[2].equals("9999A0")  && !tSplit[2].equals("SAP Code") && !recipeList.contains(""+Integer.parseInt(tSplit[2])))
					recipeList.add(""+Integer.parseInt(tSplit[2]));
				
			}
			
			this.clearInitialRecipeList(recipeToLimits);
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
  			System.exit(-1);
		}
    
    }
    
    private void addDataToRecipeToLimitsMap(String recipe,Map<String,int[]> recipeToLimitsMap){
    	
    	if(!recipeToLimitsMap.containsKey(recipe)){
    		
    		int[] temp = new int[1];
    		temp[0]=0;
    		recipeToLimitsMap.put(recipe,temp);
    		
    	}
       	else{
       		recipeToLimitsMap.get(recipe)[0]++;      		
    	}
    	
    }
    
    private void clearInitialRecipeList(Map<String,int[]> recipeToLimits){
    	
    	int cLimit = 0;
    	
    	switch(machine){
    	
    		case "Quadruplex":
    			cLimit = CQuadruplex;
    			break;
    			
    		case "Duplex":
    			cLimit = CDuplex;
    			break;
    			
    		case "InnerLiner":
    			cLimit = CInnerLiner;
    			break;  			
    			
    	}

    	for(String recipe: recipeToLimits.keySet()){
    		if(recipeToLimits.get(recipe)[0]<cLimit){
    			recipeList.remove(recipeList.indexOf(recipe));
    		}
    	}
    	
    }
    
    public void setMachine(String machine){
    	this.machine = machine;
    }
    
    
    @FXML
    void initialize() {
        assert theRoot != null : "fx:id=\"theRoot\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert vBoxTot != null : "fx:id=\"vBoxTot\" was not injected: check your FXML file 'PASUI.fxml'.";
       // assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'PASUI.fxml'.";
       // assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert textFileName != null : "fx:id=\"textFileName\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert buttonBrowse != null : "fx:id=\"buttonBrowse\" was not injected: check your FXML file 'PASUI.fxml'.";
       // assert x5 != null : "fx:id=\"x5\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert textSAP != null : "fx:id=\"textSAP\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert buttonBrowseSAP != null : "fx:id=\"buttonBrowseSAP\" was not injected: check your FXML file 'PASUI.fxml'.";
       // assert x4 != null : "fx:id=\"x4\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert txtMetersToCut != null : "fx:id=\"txtMetersToCut\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert imgDownArrow != null : "fx:id=\"imgDownArrow\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert imgDownArrowHighlight != null : "fx:id=\"imgDownArrowHighlight\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert vBoxDateAnalysis != null : "fx:id=\"vBoxDateAnalysis\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert hBoxDateAnalysis != null : "fx:id=\"hBoxDateAnalysis\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert chBoxRecipesListToAnalyze != null : "fx:id=\"chBoxRecipesListToAnalyze\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert chBoxDateFrom != null : "fx:id=\"chBoxDateFrom\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert chBoxDateTo != null : "fx:id=\"chBoxDateTo\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert buttonAnalyze != null : "fx:id=\"buttonAnalyze\" was not injected: check your FXML file 'PASUI.fxml'.";
       // assert x2 != null : "fx:id=\"x2\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert PirelliLogo != null : "fx:id=\"PirelliLogo\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert textFileCalender != null : "fx:id=\"textFileCalender\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert btnBrowseCalender != null : "fx:id=\"btnBrowseCalender\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert chBoxOraInizio != null : "fx:id=\"chBoxOraInizio\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert chBoxOraFine != null : "fx:id=\"chBoxOraFine\" was not injected: check your FXML file 'PASUI.fxml'.";
        assert btnAnalyseCalender != null : "fx:id=\"btnAnalyseCalender\" was not injected: check your FXML file 'PASUI.fxml'.";

    }

}
