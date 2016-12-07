package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import application.PirelliAnalysisSystem;
import dataAnalysis.FileAnalysis;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import objectClasses.RecipePerformanceDuplex;
import objectClasses.RecipePerformanceInnerLiner;
import objectClasses.RecipePerformanceQuadruplex;

public class ControllerTableAndGraph {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ImageView PirelliLogo;

	@FXML
	private ComboBox<String> choiceBoxRecipe;

	@FXML
	private LineChart<String, Double> graph;

	@FXML
	private LineChart<String, Double> graph2;

	@FXML
	private LineChart<String, Double> graph3;

	@FXML
	private LineChart<String, Double> graph4;

	@FXML
	private NumberAxis graphYAxis;

	@FXML
	private NumberAxis chart3YAxis;

	@FXML
	private NumberAxis chart4YAxis;

	@FXML
	private GridPane gridPanelCheckboxMeasureList;

	@FXML
	private ScrollPane scrollPaneDX;

	@FXML
	private ScrollPane scrollPaneSX;

	@FXML
	private HBox hboxStackPaneGraphView;

	@FXML
	private HBox hBoxGraphView;

	@FXML
	private HBox hBoxMeasureFilter;

	@FXML
	private GridPane legend;

	@FXML
	private Menu menuEdit;

	@FXML
	private Menu menuFile;

	@FXML
	private Menu menuFilter;

	@FXML
	private MenuItem menuItChangeMetersToCut;

	@FXML
	private MenuItem menuItHideFilters;

	@FXML
	private MenuItem menuItNewAnalysis;

	@FXML
	private MenuItem menuItFilterY;

	@FXML
	private MenuItem menuItSave;

	@FXML
	private MenuItem menuItSaveAll;

	@FXML
	private MenuItem menuItSetFullScreen;

	@FXML
	private MenuItem menuItExitFromFullScreen;

	@FXML
	private MenuItem menuItSetGraphTitle;

	@FXML
	private MenuItem menuItClrChartTitle;

	@FXML
	private Menu menuNamesAndLabels;

	@FXML
	private MenuItem menuitClose;

	@FXML
	private Menu menuWindow;

	@FXML
	private Tab tabGraphView;

	@FXML
	private TabPane tabPanel;

	@FXML
	private Tab tabTableView;

	@FXML
	private TableView<RecipePerformanceDuplex> tablePerformanceDuplex;

	@FXML
	private TableView<RecipePerformanceInnerLiner> tablePerformanceInnerLiner;

	@FXML
	private TableView<RecipePerformanceQuadruplex> tablePerformanceQuadruplex;

	@FXML
	private BorderPane theRoot;

	@FXML
	private VBox vBoxGraphView;

	@FXML
	private VBox vBoxParameter;

	@FXML
	private Label lblPI;

	@FXML
	private Label lblMachine;

	@FXML
	private ComboBox<Integer> chBoxPI;

	@FXML
	private StackPane stackPaneGraph;

	@FXML
	private NumberAxis YAxis;

	private Label minYLabel;

	private TextField minY;

	private Label maxYLabel;

	private TextField maxY;

	private Button btnFilter;

	private Button btnClrFilters;

	private Label chartAxisLabel;

	private ComboBox<String> chartAxis;

	private VBox FilterYParameters;

	private HBox FilterYParameters1;

	private HBox FilterYParameters2;

	private Stage stage;

	private XYChart.Series<String, Double> lowerBound;

	private XYChart.Series<String, Double> upperBound;

	private FileAnalysis fa;

	private String filePath;

	private String SAPFilePath;

	private boolean isFullScreenActive = false;

	private List<RecipePerformanceQuadruplex> recipesPerformanceList;

	private List<RecipePerformanceDuplex> recipesPerformanceListDuplex;

	private List<RecipePerformanceInnerLiner> recipesPerformanceListInnerLiner;

	private Map<String, Integer> parametersToDBIndexes;

	private Map<String, XYChart.Series<String, Double>> seriesName;

	private Map<String, RecipePerformanceQuadruplex> recipePerformanceQuadruplex;

	private Map<String, RecipePerformanceDuplex> recipePerformanceDuplex;

	private Map<String, RecipePerformanceInnerLiner> recipePerformanceInnerLiner;

	private Map<String, String[]> recipesToLimits;

	private List<String> invisibleSeries;

	private List<RecipePerformanceQuadruplex> recipesList;

	private List<RecipePerformanceDuplex> recipesListDuplex;

	private List<RecipePerformanceInnerLiner> recipesListInnerLiner;

	private List<String> fixedRecipesList;

	private String[][] KPITable;

	private int numRecords;

	private int numCols;

	private String lastMeasure;

	private Integer lastPIIndex;

	private List<String> bigMeasures;

	private String[] seriesColors;

	private int colorIndex;

	private int legendIndex;

	private int numLegendRows;

	private String[] collectionTime;

	private String[] headTime;

	private int secondChartCollectionStartIndex;

	private int secondChartCollectionStartIndexCounter;

	private int maxValueFirstChart;

	private int maxValueSecondChart;

	private int maxValueThirdChart;

	private int cellsHeadCollection;

	private boolean isFirst;

	private int stopIndex;

	private int stopHeadIndex;

	private boolean isGraphTranslated;

	private double initialChart4MaxHeight;

	private int metersToCut;

	private String machine;

	private boolean allViewChart;

	@FXML
	void SetFullScreen(ActionEvent event) {

		if (this.stage != null)
			if (!this.isFullScreenActive) {
				this.stage.setFullScreen(true);
				this.isFullScreenActive = true;
				this.menuItExitFromFullScreen.setVisible(true);
				this.menuItSetFullScreen.setVisible(false);
			} else {
				this.stage.setFullScreen(false);
				this.isFullScreenActive = false;
				this.menuItExitFromFullScreen.setVisible(false);
				this.menuItSetFullScreen.setVisible(true);

			}

	}

	@FXML
	void changeMetersToCut(ActionEvent event) {

		String meters;
		int metersToCut = 0;

		meters = JOptionPane.showInputDialog(null, "Please, insert the length value (m) which you want not consider: ");

		if (meters == null)
			return;

		try {

			metersToCut = Integer.parseInt(meters);

			if (metersToCut > 60) {
				JOptionPane.showMessageDialog(null,
						"UNCORRECT INPUT: The length value inserted exceeded the maximum limit permitted. This value will be automatically changed to \"60\".",
						"ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				metersToCut = 60;
			} else if (metersToCut < 0) {

				JOptionPane.showMessageDialog(null,
						"UNCORRECT INPUT: The length value inserted exceeded the minimum limit permitted. This value will be automatically changed to \"0\".",
						"ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				metersToCut = 0;

			}

		} catch (NumberFormatException nfe) {

			JOptionPane.showMessageDialog(null,
					"UNCORRECT INPUT: To continue, you've to insert an integer value between 0 and 60.",
					"ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			this.changeMetersToCut(new ActionEvent());

		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource("PASUI.fxml"));

		Parent root = null;

		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
		}

		Controller controller = (Controller) loader.getController();

		Stage newStage = new Stage();

		newStage.getIcons().add(new Image(getClass().getResourceAsStream("Icon.jpg")));

		newStage.setTitle("Pirelli Analysis System 3.0");
		newStage.setScene(new Scene(root));

		controller.getTextFileName().setText(this.filePath);
		controller.getTextSAP().setText(this.SAPFilePath);
		controller.getTxtMetersToCut().setText("" + metersToCut);
		controller.recognizeMachine(this.filePath);
		controller.generateAnalysis(new ActionEvent());

	}

	@FXML
	void changeMetersToCutKey(KeyEvent event) {

		if (event.getCode().equals(KeyCode.F2))
			this.changeMetersToCut(new ActionEvent());

		if (event.getCode().equals(KeyCode.F5)) {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("PASUI.fxml"));

			Parent root = null;

			try {
				root = (Parent) loader.load();
			} catch (IOException e) {
			}

			Controller controller = (Controller) loader.getController();

			stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon.jpg")));

			stage.setTitle("Pirelli Analysis System 3.0");
			stage.setScene(new Scene(root));

			controller.getTextFileName().setText(this.filePath);
			controller.getTextSAP().setText(this.SAPFilePath);
			controller.getTxtMetersToCut().setText("" + this.metersToCut);
			controller.recognizeMachine(this.filePath);
			controller.generateAnalysis(new ActionEvent());

		}
	}

	@FXML
	void hideFilters(ActionEvent event) {

		this.FilterYParameters.setVisible(false);
		this.hBoxMeasureFilter.getChildren().remove(this.FilterYParameters);

		this.minY.setText("");
		this.maxY.setText("");

		this.menuItHideFilters.setVisible(false);
		this.menuItFilterY.setVisible(true);
		this.graphYAxis.setAutoRanging(true);
		this.YAxis.setAutoRanging(true); // Y axis of the second chart
		this.chart3YAxis.setAutoRanging(true); // Y axis of the third chart

	}

	@FXML
	void launchNewAnalysis(ActionEvent event) {

		PirelliAnalysisSystem pas = new PirelliAnalysisSystem();
		try {
			pas.start(new Stage());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "An error is occurred! Please, try another time", "ALERT MESSAGE",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

	@FXML
	void filterPerYValues(ActionEvent event) {

		if (this.FilterYParameters == null) {

			this.FilterYParameters = new VBox();
			this.FilterYParameters1 = new HBox();
			this.FilterYParameters2 = new HBox();
			this.FilterYParameters1.setAlignment(Pos.CENTER);
			this.FilterYParameters2.setAlignment(Pos.CENTER);
			this.FilterYParameters.setPadding(new Insets(0, 0, 0, 100));

			this.FilterYParameters.getChildren().add(FilterYParameters1);
			this.FilterYParameters.getChildren().add(FilterYParameters2);

			this.minYLabel = new Label("Minimum Y value: ");
			this.minYLabel.setStyle(
					"-fx-font-family:Arial monospaced for SAP; -fx-font-weight:bold; -fx-font-size:15; -fx-text-fill:gold");
			this.minYLabel.setPadding(new Insets(0, 5, 0, 20));
			this.minY = new TextField();
			// this.minY.textProperty().addListener(new ChangeListener<String>()
			// {
			// @Override
			// public void changed(final ObservableValue<? extends String> ov,
			// final String oldValue, final String newValue) {
			//
			// int value;
			//
			// try{
			//
			// if (Double.parseDouble(minY.getText()) < 0) {
			// minY.setText("0");
			// }
			//
			// if(chartAxis.getValue().equals("Left"))
			// value = maxValueFirstChart;
			// else
			// value = maxValueSecondChart;
			//
			// if (Double.parseDouble(minY.getText()) > value) {
			// minY.setText(String.valueOf(value));
			// }
			//
			// }
			// catch(NumberFormatException nfe){
			// return;
			// }
			//
			// }
			// });
			this.minY.setPrefWidth(50);

			this.maxYLabel = new Label("Maximum Y value: ");
			this.maxYLabel.setStyle(
					"-fx-font-family:Arial monospaced for SAP; -fx-font-weight:bold; -fx-font-size:15; -fx-text-fill:gold");
			this.maxYLabel.setPadding(new Insets(0, 5, 0, 20));
			this.maxY = new TextField();
			// this.maxY.textProperty().addListener(new ChangeListener<String>()
			// {
			// @Override
			// public void changed(final ObservableValue<? extends String> ov,
			// final String oldValue, final String newValue) {
			// int value;
			//
			// try{
			// if(Double.parseDouble(maxY.getText()) < 0) {
			// maxY.setText("0");
			// }
			//
			// if(chartAxis.getValue().equals("Left"))
			// value = maxValueFirstChart;
			// else
			// value = maxValueSecondChart;
			//
			// if (Double.parseDouble(maxY.getText()) > value) {
			// maxY.setText(String.valueOf(value));
			// }
			// }
			// catch(NumberFormatException nfe){
			// return;
			// }
			// }
			// });
			this.maxY.setPrefWidth(50);

			this.btnFilter = new Button("Filter");
			this.btnFilter.setTranslateY(this.btnFilter.getLayoutY() + 15);
			this.btnFilter.setStyle(
					" -fx-border-color:red;-fx-border-width:2;-fx-border-style:solid;-fx-border-radius:30;-fx-background-color:linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44),linear-gradient(#ffea6a, #efaa22),linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%), linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0)); -fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0; -fx-text-fill:red; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 5 20 5 20;");
			this.btnFilter.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					filterValues();

				}

			});

			this.chartAxisLabel = new Label("Axis: ");
			this.chartAxisLabel.setStyle(
					"-fx-font-family:Arial monospaced for SAP; -fx-font-weight:bold; -fx-font-size:15; -fx-text-fill:gold");
			this.chartAxisLabel.setPadding(new Insets(0, 10, 0, 20));

			this.chartAxis = new ComboBox<String>();
			this.chartAxis.getItems().add("Left");
			this.chartAxis.getItems().add("Right");
			this.chartAxis.getItems().add("Right - Windup");
			this.chartAxis.setValue("Left");
			this.chartAxis.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					minY.setText("");
					maxY.setText("");
					minY.requestFocus();

				}

			});

			this.btnClrFilters = new Button("Clear");
			this.btnClrFilters.setStyle(
					" -fx-border-color:red;-fx-border-width:2;-fx-border-style:solid;-fx-border-radius:30;-fx-background-color:linear-gradient(#ffd65b, red),linear-gradient(red, #f2ba44),linear-gradient(#ffea6a, #efaa22),linear-gradient(gold 0%, orange 50%, red 100%), linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0)); -fx-background-radius: 30;-fx-background-insets: 0,1,2,3,0; -fx-text-fill:black; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 5 20 5 20;");
			this.btnClrFilters.setTranslateY(this.btnClrFilters.getLayoutY() + 15);
			this.btnClrFilters.setTranslateX(this.btnClrFilters.getLayoutX() + 30);
			this.btnClrFilters.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					graphYAxis.setAutoRanging(true);
					YAxis.setAutoRanging(true); // Y axis of the second chart
					chart3YAxis.setAutoRanging(true); // Y axis of the third
														// chart
					minY.setText("");
					maxY.setText("");
					minY.requestFocus();

				}

			});

			this.FilterYParameters1.getChildren().add(this.chartAxisLabel);
			this.FilterYParameters1.getChildren().add(this.chartAxis);
			this.FilterYParameters1.getChildren().add(this.minYLabel);
			this.FilterYParameters1.getChildren().add(this.minY);
			this.FilterYParameters1.getChildren().add(this.maxYLabel);
			this.FilterYParameters1.getChildren().add(this.maxY);
			this.FilterYParameters2.getChildren().add(this.btnFilter);
			this.FilterYParameters2.getChildren().add(this.btnClrFilters);

		} else {

			this.FilterYParameters.setVisible(true);

		}

		this.hBoxMeasureFilter.getChildren().add(this.FilterYParameters);

		this.minY.requestFocus();

		this.menuItFilterY.setVisible(false);
		this.menuItHideFilters.setVisible(true);

	}

	private void filterValues() {

		double tickUnit = 0;
		double minY = 0;
		double maxY = 0;
		String tempLimit;

		this.graphYAxis.setAutoRanging(true);
		this.YAxis.setAutoRanging(true);

		if (this.minY.getText() == "")
			this.minY.setText("0");

		if (this.maxY.getText() == "")
			if (this.chartAxis.getValue().equals("Left"))
				this.maxY.setText(String.valueOf(this.maxValueFirstChart));
			else if (this.chartAxis.getValue().equals("Right"))
				this.maxY.setText(String.valueOf(this.maxValueSecondChart));
			else
				this.maxY.setText(String.valueOf(this.maxValueThirdChart));

		try {
			minY = Double.parseDouble(this.minY.getText());
		} catch (NumberFormatException nfe) {
			this.minY.setText("0");
		}

		try {
			maxY = Double.parseDouble(this.maxY.getText());
		} catch (NumberFormatException nfe) {
			if (this.chartAxis.getValue().equals("Left")) {
				this.maxY.setText(String.valueOf(this.maxValueFirstChart));
				maxY = Double.parseDouble(this.maxY.getText());
			} else {
				if (this.chartAxis.getValue().equals("Right")) {
					this.maxY.setText(String.valueOf(this.maxValueSecondChart));
					maxY = Double.parseDouble(this.maxY.getText());
				} else {

					this.maxY.setText(String.valueOf(this.maxValueThirdChart));
					maxY = Double.parseDouble(this.maxY.getText());

				}
			}
		}

		if (minY < 0) {

			this.minY.setText("0");
			minY = 0;

		}

		if (this.chartAxis.getValue().equals("Left")) {
			if (maxY > this.maxValueFirstChart) {
				this.maxY.setText(String.valueOf(this.maxValueFirstChart));
				maxY = this.maxValueFirstChart;
			}
		} else {
			if (this.chartAxis.getValue().equals("Right")) {
				if (maxY > this.maxValueSecondChart) {

					this.maxY.setText(String.valueOf(this.maxValueSecondChart));
					maxY = this.maxValueSecondChart;

				}
			} else {

				if (maxY > this.maxValueThirdChart) {

					this.maxY.setText(String.valueOf(this.maxValueThirdChart));
					maxY = this.maxValueThirdChart;

				}

			}

		}

		if (minY > maxY) {
			tempLimit = this.maxY.getText();
			this.maxY.setText(this.minY.getText());
			this.minY.setText(tempLimit);
			maxY = Double.parseDouble(this.maxY.getText());
			minY = Double.parseDouble(this.minY.getText());
		}

		if (maxY - minY < 100) {

			if (maxY - minY < 51)
				tickUnit = 2;

			if (maxY - minY < 21)
				tickUnit = 1;

			if (maxY - minY < 11)
				tickUnit = 0.5;

			if (maxY - minY < 6)
				tickUnit = 0.25;

		}
		if (this.chartAxis.getValue().equals("Left")) {

			this.graph.getYAxis().setAutoRanging(false);
			this.graphYAxis.setLowerBound(minY);
			this.graphYAxis.setUpperBound(maxY);

			if (tickUnit == 0)
				tickUnit = this.graphYAxis.getTickUnit();

			this.graphYAxis.setTickUnit(tickUnit);

		} else {
			if (this.chartAxis.getValue().equals("Right")) {

				this.graph2.getYAxis().setAutoRanging(false);
				this.YAxis.setLowerBound(minY);
				this.YAxis.setUpperBound(maxY);

				if (tickUnit == 0)
					tickUnit = this.graphYAxis.getTickUnit();

				this.YAxis.setTickUnit(tickUnit);

			} else {

				this.graph3.getYAxis().setAutoRanging(false);
				this.chart3YAxis.setLowerBound(minY);
				this.chart3YAxis.setUpperBound(maxY);

				if (tickUnit == 0)
					tickUnit = this.graphYAxis.getTickUnit();

				this.chart3YAxis.setTickUnit(tickUnit);

			}

		}

	}

	@FXML
	void closeWindow(ActionEvent event) {

		if (JOptionPane.showConfirmDialog(null, "Do you want really leave Pirelli Analysis System?",
				"EXIT CONFIRMATION MESSAGE", JOptionPane.YES_NO_OPTION) == 0)
			System.exit(-1);
		else
			return;
	}

	@FXML
	void clrChartTitle(ActionEvent event) {

		this.graph.setTitle(null);
		this.graph2.setTitle(null);
		this.graph3.setTitle(null);
		this.graph4.setTitle(null);
		this.menuItClrChartTitle.setVisible(false);

	}

	@FXML
	void saveAllViews(ActionEvent event) {

//		FileChooser fileChooser = new FileChooser();
//		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("png",
//				"*.png") /* , new ExtensionFilter("jpg","*.jpg","*.jpeg") */);
//		File file = fileChooser.showSaveDialog(null);
//
//		if (file != null) {
//
//			Calendar cal = Calendar.getInstance();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//			String path = file.getPath().substring(0, file.getPath().lastIndexOf(".")) + "\\PerformanceTable"
//					+ sdf.format(cal.getTime());
//			new File(path).mkdirs();
//			File ftemp = new File(path + "\\");
//			this.writePerformanceTable(ftemp.getPath());
//			WritableImage imageTable = null;
//			
//			switch(this.machine){
//			
//				case "Quadruplex":
//					imageTable = this.tablePerformanceQuadruplex.snapshot(new SnapshotParameters(), null);
//					break;
//					
//				case "Duplex":
//					imageTable = this.tablePerformanceDuplex.snapshot(new SnapshotParameters(), null);
//					break;
//					
//				case "InnerLiner":
//					imageTable = this.tablePerformanceInnerLiner.snapshot(new SnapshotParameters(), null);
//					break;
//			
//			}
//			
//			file = new File(ftemp.getPath() + "\\" + file.getName());
//
//			try {
//				ImageIO.write(SwingFXUtils.fromFXImage(imageTable, null), "png", file);
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry later", "ALERT MESSAGE",
//						JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//
//			File fileGraph = new File(file.getPath().substring(0, file.getPath().lastIndexOf("\\")) + "Graph.png");
//
//			
//			this.graph.getStylesheets().clear();
//			this.graph.getStylesheets().add("gui/GraphicalFiles/graphStylePrint.css");
//			this.graph.setTitle("Measure: "+this.choiceBoxRecipe.getValue()+"  -  "+this.machine);
//			this.graph2.getStylesheets().clear();
//			this.graph2.getStylesheets().add("gui/GraphicalFiles/graphStyle2Print.css");
//			this.graph3.getStylesheets().clear();
//			this.graph3.getStylesheets().add("gui/GraphicalFiles/graphStyle2Print.css");
//			this.scrollPaneSX.getStylesheets().clear();
//			this.scrollPaneSX.getStylesheets().add("gui/GraphicalFiles/scrollPaneStylePrint.css");
//
//			WritableImage imageGraph = this.scrollPaneSX.snapshot(new SnapshotParameters(), null);
//
//			try {
//				ImageIO.write(SwingFXUtils.fromFXImage(imageGraph, null), "png", fileGraph);
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry later", "ALERT MESSAGE",
//						JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//
//			this.graph.getStylesheets().clear();
//			this.graph2.getStylesheets().clear();
//			this.graph3.getStylesheets().clear();
//			this.graph.getStylesheets().add("gui/GraphicalFiles/graphStyle.css");
//			this.graph2.getStylesheets().add("gui/GraphicalFiles/graphStyle2.css");
//			this.graph3.getStylesheets().add("gui/GraphicalFiles/graphStyle2.css");
//			this.scrollPaneSX.getStylesheets().clear();
//			this.scrollPaneSX.getStylesheets().add("gui/GraphicalFiles/scrollPaneStyle.css");
//			this.graph.setTitle(null);
//			
//		}

	}

	@FXML
	void saveSingleView(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("png",
				"*.png") /* , new ExtensionFilter("jpg","*.jpg","*.jpeg") */);
		File file = fileChooser.showSaveDialog(null);
		WritableImage image = null;

		if (file != null) {

			if (this.tabTableView.isSelected()) {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String path = file.getPath().substring(0, file.getPath().lastIndexOf(".")) + "PerformanceTable"
						+ sdf.format(cal.getTime());
				new File(path).mkdirs();
				File ftemp = new File(path + "\\");
				this.writePerformanceTable(ftemp.getPath());
				
				switch(this.machine){
				
					case "Quadruplex":
						image = this.tablePerformanceQuadruplex.snapshot(new SnapshotParameters(), null);
						break;
						
					case "Duplex":
						image = this.tablePerformanceDuplex.snapshot(new SnapshotParameters(), null);
						break;
						
					case "InnerLiner":
						image = this.tablePerformanceInnerLiner.snapshot(new SnapshotParameters(), null);
						break;
				
				}
				
				file = new File(ftemp.getPath() + "\\" + file.getName());
			} else {
				this.scrollPaneSX.getStylesheets().clear();
				this.scrollPaneSX.getStylesheets().add("gui/GraphicalFiles/scrollPaneStylePrint.css");
				this.graph.setTitle("Measure: "+this.choiceBoxRecipe.getValue()+"  -  "+this.machine);
				this.graph.getStylesheets().add("gui/GraphicalFiles/graphStylePrint.css");
				this.graph2.getStylesheets().add("gui/GraphicalFiles/graphStyle2Print.css");
				this.graph3.getStylesheets().add("gui/GraphicalFiles/graphStyle2Print.css");
				image = this.hboxStackPaneGraphView.snapshot(new SnapshotParameters(), null);
			}
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry later", "ALERT MESSAGE",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			this.graph.getStylesheets().clear();
			this.graph2.getStylesheets().clear();
			this.graph3.getStylesheets().clear();
			this.graph.getStylesheets().add("gui/GraphicalFiles/graphStyle.css");
			this.graph2.getStylesheets().add("gui/GraphicalFiles/graphStyle2.css");
			this.graph3.getStylesheets().add("gui/GraphicalFiles/graphStyle2.css");
			this.scrollPaneSX.getStylesheets().clear();
			this.scrollPaneSX.getStylesheets().add("gui/GraphicalFiles/scrollPaneStyle.css");
			this.graph.setTitle(null);
			
		}
	}

	@FXML
	void setGraphTitle(ActionEvent event) {

		String title = JOptionPane.showInputDialog(null, "Please, insert the title of the graph below: ");

		if (title != null && !title.equals("")) {
			this.graph.setTitle(title);
			this.graph2.setTitle(title);
			this.graph3.setTitle(title);
			this.graph4.setTitle(title);
		}

		this.menuItClrChartTitle.setVisible(true);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void generateTableQuadruplex(Map<String, RecipePerformanceQuadruplex> recipesPerformance) { // cut

		this.recipesList = new ArrayList<RecipePerformanceQuadruplex>();

		// ==== Generation and rearrangement of the recipes' list =======

		recipesPerformanceList = new ArrayList<RecipePerformanceQuadruplex>(recipesPerformance.values());

		for (String recipe : this.fixedRecipesList) {

			for (RecipePerformanceQuadruplex rp : this.recipesPerformanceList) {

				if (rp.getMeasureID().equals(recipe))
					this.recipesList.add(rp);

			}

		}

		final ObservableList<RecipePerformanceQuadruplex> data = FXCollections.observableArrayList(recipesList);

		tablePerformanceQuadruplex.setPrefHeight(data.size() * 25 + 25);
		data.addListener(new ListChangeListener<RecipePerformanceQuadruplex>() {

			public void onChanged(ListChangeListener.Change<? extends RecipePerformanceQuadruplex> c) {

				tablePerformanceQuadruplex.setPrefHeight(data.size() * 25 + 25);

			}
		});

		// ====== Realization of the view =======

		TableColumn Col1 = new TableColumn("Measure ID");

		Col1.sortTypeProperty().addListener(new ChangeListener<SortType>() {

			@Override
			public void changed(ObservableValue<? extends SortType> list, SortType arg1, SortType arg2) {

				if (list.getValue().equals(SortType.ASCENDING))
					sortChBoxMeasures("ASCENDING");
				else
					sortChBoxMeasures("DESCENDING");

			}

		});

		Col1.setMinWidth(100);
		Col1.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("measureID"));

		TableColumn Col2 = new TableColumn("Production Index");
		Col2.setMinWidth(100);
		Col2.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("productionIndex"));

		TableColumn Col3 = new TableColumn("Width");

		TableColumn Col31 = null;

		if (!this.machine.equals("InnerLiner")) {

			Col31 = new TableColumn("Target width \nfrom SAP");
			Col31.setMinWidth(100);
			Col31.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("targetWidth"));

			Col31.setCellFactory(
					new Callback<TableColumn<RecipePerformanceQuadruplex, String>, TableCell<RecipePerformanceQuadruplex, String>>() {
						@Override
						public TableCell<RecipePerformanceQuadruplex, String> call(
								TableColumn<RecipePerformanceQuadruplex, String> param) {
							return new TableCell<RecipePerformanceQuadruplex, String>() {
								@Override
								protected void updateItem(String item, boolean empty) {
									if (!empty) {
										int currentIndex = indexProperty().getValue() < 0 ? 0
												: indexProperty().getValue();
										String recipeWidth = param.getTableView().getItems().get(currentIndex)
												.getRecipeWidth();
										if (!item.equals("N/A") && !recipeWidth.equals("N/A")) {
											if (Double.parseDouble(recipeWidth) != Double.parseDouble(item)) {
												setTextFill(Color.RED);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											} else {
												setTextFill(Color.GREEN);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											}
										} else {

											setTextFill(Color.RED);
											setStyle("-fx-font-weight: BOLD;");
											setText(item);

										}

									}
								}
							};
						}
					});

		}

		TableColumn Col32 = new TableColumn("Width from \nreceipt");
		Col32.setMinWidth(100);
		Col32.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("recipeWidth"));

		if (!this.machine.equals("InnerLiner")) {

			Col32.setCellFactory(
					new Callback<TableColumn<RecipePerformanceQuadruplex, String>, TableCell<RecipePerformanceQuadruplex, String>>() {
						@Override
						public TableCell<RecipePerformanceQuadruplex, String> call(
								TableColumn<RecipePerformanceQuadruplex, String> param) {
							return new TableCell<RecipePerformanceQuadruplex, String>() {
								@Override
								protected void updateItem(String item, boolean empty) {
									if (!empty) {
										int currentIndex = indexProperty().getValue() < 0 ? 0
												: indexProperty().getValue();
										String targetWidth = param.getTableView().getItems().get(currentIndex)
												.getTargetWidth();
										if (!item.equals("N/A") && !targetWidth.equals("N/A")) {
											if (Double.parseDouble(targetWidth) != Double.parseDouble(item)) {
												setTextFill(Color.RED);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											} else {
												setTextFill(Color.GREEN);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											}
										} else {

											setTextFill(Color.RED);
											setStyle("-fx-font-weight: BOLD;");
											setText(item);

										}
									}
								}
							};
						}
					});

		}

		TableColumn Col33 = new TableColumn("Average width");
		Col33.setMinWidth(100);
		Col33.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("avgWidth"));

		TableColumn Col34 = new TableColumn("Average marker \nwidth");
		Col34.setMinWidth(100);
		Col34.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("markerAvgWidth"));

		TableColumn Col35 = new TableColumn("Delta");
		Col35.setMinWidth(70);
		Col35.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("deltaWidth"));

		TableColumn Col36 = new TableColumn("Cp");
		Col36.setMinWidth(70);
		Col36.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("cpWidth"));

		TableColumn Col37 = new TableColumn("Cpk");
		Col37.setMinWidth(70);
		Col37.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("cpkWidth"));

		Col37.setCellFactory(column -> {
			return new TableCell<RecipePerformanceQuadruplex, String>() {

				@Override
				protected void updateItem(String recipe, boolean empty) {

					if (recipe == null || empty) {
						setText(null);
						setStyle("");
					} else {
						super.updateItem(recipe, empty);

						if (Double.parseDouble(recipe) < 0.8) {
							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.RED);

						} else {

							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.GREEN);

						}
					}
				}
			};
		});

		TableColumn Col4 = new TableColumn("Weight");

		TableColumn Col41 = null;

		if (!machine.equals("InnerLiner")) {

			Col41 = new TableColumn("Target weight \nfrom SAP");
			Col41.setMinWidth(100);
			Col41.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("targetWeight"));

			Col41.setCellFactory(
					new Callback<TableColumn<RecipePerformanceQuadruplex, String>, TableCell<RecipePerformanceQuadruplex, String>>() {
						@Override
						public TableCell<RecipePerformanceQuadruplex, String> call(
								TableColumn<RecipePerformanceQuadruplex, String> param) {
							return new TableCell<RecipePerformanceQuadruplex, String>() {
								@Override
								protected void updateItem(String item, boolean empty) {
									if (!empty) {
										int currentIndex = indexProperty().getValue() < 0 ? 0
												: indexProperty().getValue();
										String recipeWeight = param.getTableView().getItems().get(currentIndex)
												.getRecipeWeight();
										if (!item.equals("N/A") && !recipeWeight.equals("N/A")) {
											if (Double.parseDouble(recipeWeight) != Double.parseDouble(item)) {
												setTextFill(Color.RED);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											} else {
												setTextFill(Color.GREEN);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											}
										} else {

											setTextFill(Color.RED);
											setStyle("-fx-font-weight: BOLD;");
											setText(item);

										}
									}
								}
							};
						}
					});

		}

		TableColumn Col42 = new TableColumn("Weight from \nreceipt");
		Col42.setMinWidth(100);
		Col42.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("recipeWeight"));

		if (!machine.equals("InnerLiner")) {

			Col42.setCellFactory(
					new Callback<TableColumn<RecipePerformanceQuadruplex, String>, TableCell<RecipePerformanceQuadruplex, String>>() {
						@Override
						public TableCell<RecipePerformanceQuadruplex, String> call(
								TableColumn<RecipePerformanceQuadruplex, String> param) {
							return new TableCell<RecipePerformanceQuadruplex, String>() {
								@Override
								protected void updateItem(String item, boolean empty) {
									if (!empty) {
										int currentIndex = indexProperty().getValue() < 0 ? 0
												: indexProperty().getValue();
										String targetWeight = param.getTableView().getItems().get(currentIndex)
												.getTargetWeight();
										if (!item.equals("N/A") && !targetWeight.equals("N/A")) {
											if (Double.parseDouble(targetWeight) != Double.parseDouble(item)) {
												setTextFill(Color.RED);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											} else {
												setTextFill(Color.GREEN);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											}
										} else {

											setTextFill(Color.RED);
											setStyle("-fx-font-weight: BOLD;");
											setText(item);

										}
									}
								}
							};
						}
					});

		}

		TableColumn Col43 = new TableColumn("Average weight");
		Col43.setMinWidth(100);
		Col43.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("avgWeight"));

		TableColumn Col44 = new TableColumn("Average marker \nweight");
		Col44.setMinWidth(100);
		Col44.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("markerAvgWeight"));

		TableColumn Col45 = new TableColumn("Delta");
		Col45.setMinWidth(70);
		Col45.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("deltaWeight"));

		TableColumn Col46 = new TableColumn("Cp");
		Col46.setMinWidth(70);
		Col46.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("cpWeight"));

		TableColumn Col47 = new TableColumn("Cpk");
		Col47.setMinWidth(70);
		Col47.setCellValueFactory(new PropertyValueFactory<RecipePerformanceQuadruplex, String>("cpkWeight"));

		Col47.setCellFactory(column -> {
			return new TableCell<RecipePerformanceQuadruplex, String>() {

				@Override
				protected void updateItem(String recipe, boolean empty) {

					if (recipe == null || empty) {
						setText(null);
						setStyle("");
					} else {
						super.updateItem(recipe, empty);

						if (Double.parseDouble(recipe) < 0.8) {
							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.RED);
						} else {

							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.GREEN);

						}
					}
				}
			};
		});

		this.tablePerformanceQuadruplex.setItems(data);
		this.tablePerformanceQuadruplex
				.setStyle("-fx-font-size:11;  -fx-border-color:BLACK;-fx-border-width:2; -fx-border-style:solid;");

		this.tablePerformanceQuadruplex.getColumns().addAll(Col1, Col2, Col3, Col4);

		if (machine.equals("InnerLiner")) {

			Col3.getColumns().addAll(Col32, Col33, Col34, Col35, Col36, Col37);
			Col4.getColumns().addAll(Col42, Col43, Col44, Col45, Col46, Col47);

		} else {

			Col3.getColumns().addAll(Col31, Col32, Col33, Col34, Col35, Col36, Col37);
			Col4.getColumns().addAll(Col41, Col42, Col43, Col44, Col45, Col46, Col47);

		}

		this.tablePerformanceQuadruplex.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void generateTableDuplex(Map<String, RecipePerformanceDuplex> recipesPerformance) { // cut

		this.recipesListDuplex = new ArrayList<RecipePerformanceDuplex>();

		// ==== Generation and rearrangement of the recipes' list =======

		recipesPerformanceListDuplex = new ArrayList<RecipePerformanceDuplex>(recipesPerformance.values());

		for (String recipe : this.fixedRecipesList) {

			for (RecipePerformanceDuplex rp : this.recipesPerformanceListDuplex) {

				if (rp.getMeasureID().equals(recipe))
					this.recipesListDuplex.add(rp);

			}

		}

		final ObservableList<RecipePerformanceDuplex> data = FXCollections.observableArrayList(recipesListDuplex);

		tablePerformanceDuplex.setPrefHeight(data.size() * 25 + 25);
		data.addListener(new ListChangeListener<RecipePerformanceDuplex>() {

			public void onChanged(ListChangeListener.Change<? extends RecipePerformanceDuplex> c) {

				tablePerformanceDuplex.setPrefHeight(data.size() * 25 + 25);

			}
		});

		// ====== Realization of the view =======

		TableColumn Col1 = new TableColumn("Measure ID");

		Col1.sortTypeProperty().addListener(new ChangeListener<SortType>() {

			@Override
			public void changed(ObservableValue<? extends SortType> list, SortType arg1, SortType arg2) {

				if (list.getValue().equals(SortType.ASCENDING))
					sortChBoxMeasures("ASCENDING");
				else
					sortChBoxMeasures("DESCENDING");

			}

		});

		Col1.setMinWidth(100);
		Col1.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("measureID"));

		TableColumn Col2 = new TableColumn("Production Index");
		Col2.setMinWidth(100);
		Col2.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("productionIndex"));

		TableColumn Col3 = new TableColumn("Width");

		TableColumn Col31 = null;

		if (!this.machine.equals("InnerLiner")) {

			Col31 = new TableColumn("Target width \nfrom SAP");
			Col31.setMinWidth(100);
			Col31.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("targetWidth"));

			Col31.setCellFactory(
					new Callback<TableColumn<RecipePerformanceDuplex, String>, TableCell<RecipePerformanceDuplex, String>>() {
						@Override
						public TableCell<RecipePerformanceDuplex, String> call(
								TableColumn<RecipePerformanceDuplex, String> param) {
							return new TableCell<RecipePerformanceDuplex, String>() {
								@Override
								protected void updateItem(String item, boolean empty) {
									if (!empty) {
										int currentIndex = indexProperty().getValue() < 0 ? 0
												: indexProperty().getValue();
										String recipeWidth = param.getTableView().getItems().get(currentIndex)
												.getRecipeWidth();
										if (!item.equals("N/A") && !recipeWidth.equals("N/A")) {
											if (Double.parseDouble(recipeWidth) != Double.parseDouble(item)) {
												setTextFill(Color.RED);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											} else {
												setTextFill(Color.GREEN);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											}
										} else {

											setTextFill(Color.RED);
											setStyle("-fx-font-weight: BOLD;");
											setText(item);

										}

									}
								}
							};
						}
					});

		}

		TableColumn Col32 = new TableColumn("Width from \nreceipt");
		Col32.setMinWidth(100);
		Col32.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("recipeWidth"));

		if (!this.machine.equals("InnerLiner")) {

			Col32.setCellFactory(
					new Callback<TableColumn<RecipePerformanceDuplex, String>, TableCell<RecipePerformanceDuplex, String>>() {
						@Override
						public TableCell<RecipePerformanceDuplex, String> call(
								TableColumn<RecipePerformanceDuplex, String> param) {
							return new TableCell<RecipePerformanceDuplex, String>() {
								@Override
								protected void updateItem(String item, boolean empty) {
									if (!empty) {
										int currentIndex = indexProperty().getValue() < 0 ? 0
												: indexProperty().getValue();
										String targetWidth = param.getTableView().getItems().get(currentIndex)
												.getTargetWidth();
										if (!item.equals("N/A") && !targetWidth.equals("N/A")) {
											if (Double.parseDouble(targetWidth) != Double.parseDouble(item)) {
												setTextFill(Color.RED);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											} else {
												setTextFill(Color.GREEN);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											}
										} else {

											setTextFill(Color.RED);
											setStyle("-fx-font-weight: BOLD;");
											setText(item);

										}
									}
								}
							};
						}
					});

		}

		TableColumn Col33 = new TableColumn("Average width OS");
		Col33.setMinWidth(100);
		Col33.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("avgWidth"));

		TableColumn Col335 = new TableColumn("Average width DS");
		Col335.setMinWidth(100);
		Col335.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("avgWidthDS"));

		TableColumn Col34 = new TableColumn("Average extruder \nwidth OS");
		Col34.setMinWidth(100);
		Col34.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("markerAvgWidth"));

		TableColumn Col345 = new TableColumn("Average extruder \nwidth DS");
		Col345.setMinWidth(100);
		Col345.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("markerAvgWidthDS"));

		TableColumn Col35 = new TableColumn("Delta OS");
		Col35.setMinWidth(70);
		Col35.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("deltaWidth"));

		TableColumn Col355 = new TableColumn("Delta DS");
		Col355.setMinWidth(70);
		Col355.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("deltaWidthDS"));

		TableColumn Col36 = new TableColumn("Cp OS");
		Col36.setMinWidth(70);
		Col36.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("cpWidth"));

		TableColumn Col365 = new TableColumn("Cp DS");
		Col365.setMinWidth(70);
		Col365.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("cpWidthDS"));

		TableColumn Col37 = new TableColumn("Cpk OS");
		Col37.setMinWidth(70);
		Col37.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("cpkWidth"));

		Col37.setCellFactory(column -> {
			return new TableCell<RecipePerformanceDuplex, String>() {

				@Override
				protected void updateItem(String recipe, boolean empty) {

					if (recipe == null || empty) {
						setText(null);
						setStyle("");
					} else {
						super.updateItem(recipe, empty);

						if (Double.parseDouble(recipe) < 0.8) {
							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.RED);

						} else {

							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.GREEN);

						}
					}
				}
			};
		});

		TableColumn Col375 = new TableColumn("Cpk DS");
		Col375.setMinWidth(70);
		Col375.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("cpkWidthDS"));

		Col375.setCellFactory(column -> {
			return new TableCell<RecipePerformanceDuplex, String>() {

				@Override
				protected void updateItem(String recipe, boolean empty) {

					if (recipe == null || empty) {
						setText(null);
						setStyle("");
					} else {
						super.updateItem(recipe, empty);

						if (Double.parseDouble(recipe) < 0.8) {
							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.RED);

						} else {

							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.GREEN);

						}
					}
				}
			};
		});

		TableColumn Col4 = new TableColumn("Weight");

		TableColumn Col41 = null;

		if (!machine.equals("InnerLiner")) {

			Col41 = new TableColumn("Target weight \nfrom SAP");
			Col41.setMinWidth(100);
			Col41.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("targetWeight"));

			Col41.setCellFactory(
					new Callback<TableColumn<RecipePerformanceDuplex, String>, TableCell<RecipePerformanceDuplex, String>>() {
						@Override
						public TableCell<RecipePerformanceDuplex, String> call(
								TableColumn<RecipePerformanceDuplex, String> param) {
							return new TableCell<RecipePerformanceDuplex, String>() {
								@Override
								protected void updateItem(String item, boolean empty) {
									if (!empty) {
										int currentIndex = indexProperty().getValue() < 0 ? 0
												: indexProperty().getValue();
										String recipeWeight = param.getTableView().getItems().get(currentIndex)
												.getRecipeWeight();
										if (!item.equals("N/A") && !recipeWeight.equals("N/A")) {
											// if(recipeWeight.contains(","))
											// recipeWeight=recipeWeight.replace(",",
											// ".");
											// if(item.contains(","))
											// item=item.replace(",", ".");
											if (Double.parseDouble(recipeWeight) != Double.parseDouble(item)) {
												setTextFill(Color.RED);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											} else {
												setTextFill(Color.GREEN);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											}
										} else {

											setTextFill(Color.RED);
											setStyle("-fx-font-weight: BOLD;");
											setText(item);

										}
									}
								}
							};
						}
					});

		}

		TableColumn Col42 = new TableColumn("Weight from \nreceipt");
		Col42.setMinWidth(100);
		Col42.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("recipeWeight"));

		if (!machine.equals("InnerLiner")) {

			Col42.setCellFactory(
					new Callback<TableColumn<RecipePerformanceDuplex, String>, TableCell<RecipePerformanceDuplex, String>>() {
						@Override
						public TableCell<RecipePerformanceDuplex, String> call(
								TableColumn<RecipePerformanceDuplex, String> param) {
							return new TableCell<RecipePerformanceDuplex, String>() {
								@Override
								protected void updateItem(String item, boolean empty) {
									if (!empty) {
										int currentIndex = indexProperty().getValue() < 0 ? 0
												: indexProperty().getValue();
										String targetWeight = param.getTableView().getItems().get(currentIndex)
												.getTargetWeight();
										if (!item.equals("N/A") && !targetWeight.equals("N/A")) {
											// if(targetWeight.contains(","))
											// targetWeight=targetWeight.replace(",",
											// ".");
											// if(item.contains(","))
											// item=item.replace(",", ".");
											if (Double.parseDouble(targetWeight) != Double.parseDouble(item)) {
												setTextFill(Color.RED);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											} else {
												setTextFill(Color.GREEN);
												setStyle("-fx-font-weight: BOLD;");
												setText(item);
											}
										} else {

											setTextFill(Color.RED);
											setStyle("-fx-font-weight: BOLD;");
											setText(item);

										}
									}
								}
							};
						}
					});

		}

		TableColumn Col43 = new TableColumn("Average weight OS");
		Col43.setMinWidth(100);
		Col43.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("avgWeight"));

		TableColumn Col435 = new TableColumn("Average weight DS");
		Col435.setMinWidth(100);
		Col435.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("avgWeightDS"));

		TableColumn Col44 = new TableColumn("Average marker \nweight OS");
		Col44.setMinWidth(100);
		Col44.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("markerAvgWeight"));

		TableColumn Col445 = new TableColumn("Average marker \nweight DS");
		Col445.setMinWidth(100);
		Col445.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("markerAvgWeightDS"));

		TableColumn Col45 = new TableColumn("Delta OS");
		Col45.setMinWidth(70);
		Col45.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("deltaWeight"));

		TableColumn Col455 = new TableColumn("Delta DS");
		Col455.setMinWidth(70);
		Col455.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("deltaWeightDS"));

		TableColumn Col46 = new TableColumn("Cp OS");
		Col46.setMinWidth(70);
		Col46.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("cpWeight"));

		TableColumn Col465 = new TableColumn("Cp DS");
		Col465.setMinWidth(70);
		Col465.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("cpWeightDS"));

		TableColumn Col47 = new TableColumn("Cpk OS");
		Col47.setMinWidth(70);
		Col47.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("cpkWeight"));

		Col47.setCellFactory(column -> {
			return new TableCell<RecipePerformanceDuplex, String>() {

				@Override
				protected void updateItem(String recipe, boolean empty) {

					if (recipe == null || empty) {
						setText(null);
						setStyle("");
					} else {
						super.updateItem(recipe, empty);

						if (Double.parseDouble(recipe) < 0.8) {
							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.RED);
						} else {

							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.GREEN);

						}
					}
				}
			};
		});

		TableColumn Col475 = new TableColumn("Cpk DS");
		Col475.setMinWidth(70);
		Col475.setCellValueFactory(new PropertyValueFactory<RecipePerformanceDuplex, String>("cpkWeightDS"));

		Col475.setCellFactory(column -> {
			return new TableCell<RecipePerformanceDuplex, String>() {

				@Override
				protected void updateItem(String recipe, boolean empty) {

					if (recipe == null || empty) {
						setText(null);
						setStyle("");
					} else {
						super.updateItem(recipe, empty);

						if (Double.parseDouble(recipe) < 0.8) {
							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.RED);
						} else {

							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.GREEN);

						}
					}
				}
			};
		});

		this.tablePerformanceDuplex.setItems(data);
		this.tablePerformanceDuplex
				.setStyle("-fx-font-size:11;  -fx-border-color:BLACK;-fx-border-width:2; -fx-border-style:solid;");

		this.tablePerformanceDuplex.getColumns().addAll(Col1, Col2, Col3, Col4);

		if (machine.equals("InnerLiner")) {

			Col3.getColumns().addAll(Col32, Col33, Col34, Col35, Col36, Col37);
			Col4.getColumns().addAll(Col42, Col43, Col44, Col45, Col46, Col47);

		} else {

			Col3.getColumns().addAll(Col31, Col32, Col33, Col335, Col34, Col345, Col35, Col355, Col36, Col365, Col37,
					Col375);
			Col4.getColumns().addAll(Col41, Col42, Col43, Col435, Col44, Col445, Col45, Col455, Col46, Col465, Col47,
					Col475);

		}

		this.tablePerformanceDuplex.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void generateTableInnerLiner(Map<String, RecipePerformanceInnerLiner> recipesPerformance) { // cut

		this.recipesListInnerLiner = new ArrayList<RecipePerformanceInnerLiner>();

		// ==== Generation and rearrangement of the recipes' list =======

		recipesPerformanceListInnerLiner = new ArrayList<RecipePerformanceInnerLiner>(recipesPerformance.values());

		for (String recipe : this.fixedRecipesList) {

			for (RecipePerformanceInnerLiner rp : this.recipesPerformanceListInnerLiner) {

				if (rp.getMeasureID().equals(recipe))
					this.recipesListInnerLiner.add(rp);

			}

		}

		final ObservableList<RecipePerformanceInnerLiner> data = FXCollections
				.observableArrayList(recipesListInnerLiner);

		tablePerformanceInnerLiner.setPrefHeight(data.size() * 25 + 25);
		data.addListener(new ListChangeListener<RecipePerformanceInnerLiner>() {

			public void onChanged(ListChangeListener.Change<? extends RecipePerformanceInnerLiner> c) {

				tablePerformanceInnerLiner.setPrefHeight(data.size() * 25 + 25);

			}
		});

		// ====== Realization of the view =======

		TableColumn Col1 = new TableColumn("Measure ID");

		Col1.sortTypeProperty().addListener(new ChangeListener<SortType>() {

			@Override
			public void changed(ObservableValue<? extends SortType> list, SortType arg1, SortType arg2) {

				if (list.getValue().equals(SortType.ASCENDING))
					sortChBoxMeasures("ASCENDING");
				else
					sortChBoxMeasures("DESCENDING");

			}

		});

		Col1.setMinWidth(100);
		Col1.setCellValueFactory(new PropertyValueFactory<RecipePerformanceInnerLiner, String>("measureID"));

		TableColumn Col2 = new TableColumn("Production Index");
		Col2.setMinWidth(100);
		Col2.setCellValueFactory(new PropertyValueFactory<RecipePerformanceInnerLiner, String>("productionIndex"));

		TableColumn Col3 = new TableColumn("Width");

		TableColumn Col31 = null;

		Col31 = new TableColumn("Target width \nfrom SAP");
		Col31.setMinWidth(100);
		Col31.setCellValueFactory(new PropertyValueFactory<RecipePerformanceInnerLiner, String>("recipeWidth"));

		TableColumn Col33 = new TableColumn("Average width");
		Col33.setMinWidth(100);
		Col33.setCellValueFactory(new PropertyValueFactory<RecipePerformanceInnerLiner, String>("avgWidth"));

		TableColumn Col35 = new TableColumn("Delta");
		Col35.setMinWidth(70);
		Col35.setCellValueFactory(new PropertyValueFactory<RecipePerformanceInnerLiner, String>("deltaWidth"));

		TableColumn Col36 = new TableColumn("Cp");
		Col36.setMinWidth(70);
		Col36.setCellValueFactory(new PropertyValueFactory<RecipePerformanceInnerLiner, String>("cpWidth"));

		TableColumn Col37 = new TableColumn("Cpk");
		Col37.setMinWidth(70);
		Col37.setCellValueFactory(new PropertyValueFactory<RecipePerformanceInnerLiner, String>("cpkWidth"));

		Col37.setCellFactory(column -> {
			return new TableCell<RecipePerformanceInnerLiner, String>() {

				@Override
				protected void updateItem(String recipe, boolean empty) {

					if (recipe == null || empty) {
						setText(null);
						setStyle("");
					} else {
						super.updateItem(recipe, empty);

						if (Double.parseDouble(recipe) < 0.8) {
							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.RED);

						} else {

							setText(recipe);
							setStyle("-fx-font-weight: BOLD;");
							setTextFill(Color.GREEN);

						}
					}
				}
			};
		});

		this.tablePerformanceInnerLiner.setItems(data);
		this.tablePerformanceInnerLiner
				.setStyle("-fx-font-size:11;  -fx-border-color:BLACK;-fx-border-width:2; -fx-border-style:solid;");

		this.tablePerformanceInnerLiner.getColumns().addAll(Col1, Col2, Col3);

		Col3.getColumns().addAll(Col31, Col33, Col35, Col36, Col37);

		this.tablePerformanceInnerLiner.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	public void writePerformanceTable(String filepath) {

		Writer writer = null;

		try {
			writer = new FileWriter(filepath + "\\PerformanceTable.csv");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process",
					"ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

		try {
			
			switch(machine){
				
				case "Quadruplex":
					writer.write("Measure ID" + ";" + "Production Index" + ";" + "Target Width from SAP" + ";"
							+ "Width from Recipe" + ";" + "Average width" + ";" + "Average marker width" + ";" + "Delta" + ";"
							+ "Cp" + ";" + "Cpk" + ";" + "Target weight from SAP" + ";" + "Weight from recipe" + ";"
							+ "Average weight" + ";" + "Average marker weight" + ";" + "Delta" + ";" + "Cp" + ";" + "Cpk" + "\n");
					break;
					
				case "Duplex":
					writer.write("Measure ID" + ";" + "Production Index" + ";" + "Target Width from SAP" + ";"
							+ "Width from Recipe" + ";" + "Average width OS" + ";" + "Average width DS" + ";" + "Average extruder width OS" + ";" + "Average extruder width DS" + ";" +"Delta OS" + ";" + "Delta DS" + ";"
							+ "Cp OS" + ";" + "Cp DS" + ";" + "Cpk OS" + ";" +  "Cpk DS" + ";" + "Target weight from SAP" + ";" + "Weight from recipe" + ";"
							+ "Average weight OS" + ";" +  "Average weight DS" + ";" + "Average marker weight OS" + ";" +  "Average marker weight DS" + ";" + "Delta OS" + ";" +  "Delta DS" + ";" + "Cp OS" + ";" +  "Cp DS" + ";" + "Cpk OS" + ";" +  "Cpk DS" + "\n");
					break;
					
				case "InnerLiner":
					writer.write("Measure ID" + ";" + "Production Index" + ";" + "Target Width from SAP" + ";"
							+ "Average width" + ";" + "Delta" + ";"
							+ "Cp" + ";" + "Cpk" + "\n");
					break;
				
			}
			
			
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process",
					"ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

		switch (machine) {

		case "Quadruplex":
			for (RecipePerformanceQuadruplex recipe : this.recipesPerformanceList) {

				try {
					writer.write(recipe.toString() + "\n");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process",
							"ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				}

			}
			break;

		case "Duplex":
			for (RecipePerformanceDuplex recipe : this.recipesPerformanceListDuplex) {

				try {
					writer.write(recipe.toString() + "\n");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process",
							"ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				}

			}
			break;

		case "InnerLiner":
			for (RecipePerformanceInnerLiner recipe : this.recipesPerformanceListInnerLiner) {

				try {
					writer.write(recipe.toString() + "\n");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process",
							"ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				}

			}
			break;

		}

		try {
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process",
					"ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

	}

	// ======== Graph generation ============//

	public void generateInitialGraph() {

		this.KPITable = this.fa.getKPITable();
		if (this.machine.equals("Quadruplex"))
			this.recipePerformanceQuadruplex = this.fa.getRecipesPerformanceQuadruplex();
		if (this.machine.equals("Duplex"))
			this.recipePerformanceDuplex = this.fa.getRecipesPerformanceDuplex();
		if (this.machine.equals("InnerLiner"))
			this.recipePerformanceInnerLiner = this.fa.getRecipesPerformanceInnerLiner();
		this.initializesRecipesToLimits();
		this.isFirst = true;
		this.setSeries();
		this.fillChBoxMeasures(this);
		this.fillChkBoxParametersList(this);
		if (this.chBoxPI != null)
			this.chBoxPI.setValue(1);
		this.destroyFileAnalyis();
		this.legendIndex = 0;
		this.generateGraph();

	}

	private void fillChBoxMeasures(ControllerTableAndGraph controller) {

		this.choiceBoxRecipe.getItems().clear();

		if (this.allViewChart) {

			switch (machine) {

			case "Quadruplex":
				for (String QPlexRec : this.recipePerformanceQuadruplex.keySet())
					this.choiceBoxRecipe.getItems().add(QPlexRec);
				break;

			case "Duplex":
				for (String DPlexRec : this.recipePerformanceDuplex.keySet())
					this.choiceBoxRecipe.getItems().add(DPlexRec);
				break;

			case "InnerLiner":
				for (String InnerLinerRec : this.recipePerformanceInnerLiner.keySet())
					this.choiceBoxRecipe.getItems().add(InnerLinerRec);
				break;

			}

		} else
			for (String recipe : this.fa.getRecipesList()) {
				this.choiceBoxRecipe.getItems().add(recipe);

			}

		this.choiceBoxRecipe.setOnAction(

				new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {

						if (controller.chBoxPI != null)
							controller.chBoxPI.setValue(1);

						controller.generateGraph();

					}
				});

		this.choiceBoxRecipe.setValue(this.choiceBoxRecipe.getItems().get(0));

	}

	private void sortChBoxMeasures(String sortType) {

		if (sortType.equals("ASCENDING")) {

			Collections.sort(this.choiceBoxRecipe.getItems(), new Comparator<String>() {

				@Override
				public int compare(String s1, String s2) {
					return s1.compareTo(s2);
				}

			});

		} else {

			Collections.sort(this.choiceBoxRecipe.getItems(), new Comparator<String>() {

				@Override
				public int compare(String s1, String s2) {
					return -(s1.compareTo(s2));
				}

			});

		}

	}

	private void fillChkBoxParametersList(ControllerTableAndGraph controller) {

		int i = 0;

		for (String param : this.getMachineParameters(this.machine)) {

			CheckBox chkBox = new CheckBox(param);

			chkBox.setId(param);

			chkBox.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.showOrHideSeries(chkBox.getId());
				}

			});

			chkBox.setStyle("-fx-text-fill:white; -fx-font-size:13; -fx-font-weight:bold; -fx-insets:30,30,30,30;");
			chkBox.setWrapText(true);

			this.gridPanelCheckboxMeasureList.add(chkBox, 0, i);

			i++;

		}

	}

	private List<String> getMachineParameters(String machine) {

		List<String> parameters = new LinkedList<String>();
		this.bigMeasures = new LinkedList<String>();

		if (machine.equals("Quadruplex")) {

			parameters.add("Width");
			parameters.add("Weight");
			parameters.add("Width Marker");
			parameters.add("Weight Marker");
			parameters.add("Width Upper Tolerance");
			parameters.add("Width Lower Tolerance");
			parameters.add("Weight Upper Tolerance");
			parameters.add("Weight Lower Tolerance");
			parameters.add("Recipe Speed");
			parameters.add("Registered Speed (m/s)");
			parameters.add("Windup Feedback Speed (m/s)");
			parameters.add("RPM");
			parameters.add("Pressure Head (bar)");
			parameters.add("Motor Torque (%)");
			parameters.add("Feeder Speed Feedback (m/min)");
			parameters.add("Dancer Position Feedback (%)");

			this.bigMeasures.add("Width");
			this.bigMeasures.add("Weight");
			this.bigMeasures.add("Width Marker");
			this.bigMeasures.add("Weight Marker");
			this.bigMeasures.add("Width Upper Tolerance");
			this.bigMeasures.add("Width Lower Tolerance");
			this.bigMeasures.add("Weight Upper Tolerance");
			this.bigMeasures.add("Weight Lower Tolerance");

		}

		if (machine.equals("Duplex")) {

			parameters.add("WidthOS");
			parameters.add("WidthDS");
			parameters.add("WeightOS");
			parameters.add("WeightDS");
			parameters.add("Width Extruder OS");
			parameters.add("Width Extruder DS");
			parameters.add("Weight Marker OS");
			parameters.add("Weight Marker DS");
			parameters.add("Width Upper Tolerance");
			parameters.add("Width Lower Tolerance");
			parameters.add("Weight Upper Tolerance");
			parameters.add("Weight Lower Tolerance");
			parameters.add("Recipe Speed");
			parameters.add("Registered Speed (m/s)");
			parameters.add("Windup Feedback Speed (m/s)");
			parameters.add("RPM");
			parameters.add("Pressure Head (bar)");
			parameters.add("Motor Torque (%)");
			parameters.add("Feeder Speed Feedback (m/min)");
			parameters.add("Dancer Position Feedback (%)");

			this.bigMeasures.add("WidthOS");
			this.bigMeasures.add("WidthDS");
			this.bigMeasures.add("WeightOS");
			this.bigMeasures.add("WeightDS");
			this.bigMeasures.add("Weight Marker OS");
			this.bigMeasures.add("Weight Marker DS");
			this.bigMeasures.add("Width Upper Tolerance");
			this.bigMeasures.add("Width Lower Tolerance");
			this.bigMeasures.add("Weight Upper Tolerance");
			this.bigMeasures.add("Weight Lower Tolerance");

		}

		if (machine.equals("InnerLiner")) {

			parameters.add("Width");
			parameters.add("Width Upper Tolerance");
			parameters.add("Width Lower Tolerance");
			parameters.add("Windup Speed Actual 1 (m/s)");
			parameters.add("Windup Speed Actual 2 (m/s)");
			parameters.add("Calender Speed");
			parameters.add("Calender Actual Speed (m/s)");
			parameters.add("Upper Cutter (mm)");
			parameters.add("Lower Cutter (mm)");

			this.bigMeasures.add("Width");
			this.bigMeasures.add("Width Upper Tolerance");
			this.bigMeasures.add("Width Lower Tolerance");
			this.bigMeasures.add("Lower Cutter (mm)");
			this.bigMeasures.add("Upper Cutter (mm)");

		}

		return parameters;

	}

	private int getVisibleSeriesNumber() {

		int c = 0;

		for (Node it : this.gridPanelCheckboxMeasureList.getChildren()) {

			if (((CheckBox) it).isSelected())
				c++;

		}

		return c;

	}

	private boolean thereAreBigMeasuresVisible() {

		for (Node it : this.gridPanelCheckboxMeasureList.getChildren()) {

			if (((CheckBox) it).isSelected()) {

				for (String bm : this.bigMeasures) {

					if (((CheckBox) it).getText().equals(bm))
						return true;
				}

			}

		}

		return false;

	}

	private boolean isWindupVisible() {

		for (Node it : this.gridPanelCheckboxMeasureList.getChildren()) {

			if (((CheckBox) it).isSelected()) {

				if (((CheckBox) it).getText().contains("Windup"))
					return true;

			}

		}

		return false;

	}
	

	@SuppressWarnings("rawtypes")
	private void generateLowerAndUpperBound() {

		String lowerTime;
		String upperTime;
		String prodIndex = "";
		String precTime = "";
		Map<XYChart.Series<String, Double>, String> tempSeriesMap = null;

		if (this.allViewChart) {
			tempSeriesMap = new HashMap<XYChart.Series<String, Double>, String>();
		}

		if (this.choiceBoxRecipe.getValue() != null && this.choiceBoxRecipe.getValue() != ""
				&& this.recipesToLimits.containsKey(this.choiceBoxRecipe.getValue())) {

			this.graph4.getData().clear();
			this.graph4.getYAxis().setAutoRanging(false);
			this.chart4YAxis.setUpperBound(100);
			this.chart4YAxis.setLowerBound(0);

			if (this.chBoxPI.getValue() != null && !this.chBoxPI.getValue().equals("")) {

				if (this.chBoxPI.getValue() == 1)
					prodIndex = "";
				else
					prodIndex = "-" + String.valueOf(this.chBoxPI.getValue());

			}

			this.lowerBound = new XYChart.Series<String, Double>();
			this.upperBound = new XYChart.Series<String, Double>();

			lowerTime = (this.recipesToLimits.get(this.choiceBoxRecipe.getValue() + prodIndex)[0].split(" "))[1];

			try {
				upperTime = (this.recipesToLimits.get(this.choiceBoxRecipe.getValue() + prodIndex)[1].split(" "))[1];
			} catch (NullPointerException npe) {
				return;
			}

			for (int i = 0; i < 100; i++) {

				this.lowerBound.getData().add(new XYChart.Data<String, Double>(lowerTime, (double) i));
				this.upperBound.getData().add(new XYChart.Data<String, Double>(upperTime, (double) i));

			}

			XYChart.Series<String, Double> invSeries = new XYChart.Series<String, Double>();

			Collections.sort(this.invisibleSeries, new Comparator<String>() {
				@Override
				public int compare(String time1, String time2) {
					return time1.compareTo(time2);
				}
			});

			String lastDate = "";
			XYChart.Series<String, Double> tempSeries = null;
			List<XYChart.Series<String, Double>> tempSeriesList = new LinkedList<XYChart.Series<String, Double>>();

			for (String time : this.invisibleSeries) {

				if (this.allViewChart) {

					String date = time.split(" ")[0];

					if (lastDate.equals(""))
						lastDate = date;

					if (!date.equals(lastDate) && this.invisibleSeries.indexOf(time)<(this.invisibleSeries.size()-2)) {

						tempSeries = new XYChart.Series<String, Double>();

						for (int i = 0; i < 100; i++)
							tempSeries.getData().add(new XYChart.Data<String, Double>(time.split(" ")[1], (double) i));

						tempSeriesList.add(tempSeries);
						tempSeriesMap.put(tempSeries, precTime + " <--> " + time.split(" ")[0]);

						lastDate = date;

					}

					precTime = time.split(" ")[0];

				}

				invSeries.getData().add(new XYChart.Data<String, Double>(time.split(" ")[1], 0.0));

			}

			this.graph4.setHorizontalGridLinesVisible(false);
			this.graph4.setVerticalGridLinesVisible(false);
			this.graph4.getData().add(invSeries);
			if (!this.allViewChart) {
				this.graph4.getData().add(this.lowerBound);
				this.graph4.getData().add(this.upperBound);
			}
			this.graph4.getData().addAll(tempSeriesList);

			if (!this.allViewChart) {
				this.lowerBound.getNode().setStyle("-fx-stroke: RED");
				this.upperBound.getNode().setStyle("-fx-stroke: RED");
			}

			invSeries.getNode().setStyle("-fx-stroke: white");
			Iterator symbols = invSeries.getData().iterator();
	        while(symbols.hasNext())
	        	try{
	        		((XYChart.Data)symbols.next()).getNode().setStyle("-fx-background-color: white, white;");
	        	}catch(Exception e){}

			for (XYChart.Series<String, Double> tempSerie : tempSeriesList) {
				tempSerie.getNode().setStyle("-fx-stroke: brown");
				symbols = tempSerie.getData().iterator();
		        
		        while(symbols.hasNext())
		            ((XYChart.Data)symbols.next()).getNode().setStyle("-fx-background-color: brown, brown;");
			}

			if (!this.allViewChart) {
				this.addTooltipToSeries(lowerBound);
				this.addTooltipToSeries(upperBound);
			} else {

				for (XYChart.Series<String, Double> series : tempSeriesList)
					this.addTooltipToSeries(series, tempSeriesMap);
			}
		}

	}

	public void showOrHideSeries(String serieName) {

		// ======= If there are less than two series visible, the sw avoids the
		// possibility to hide the last series visible ====//

		if (this.getVisibleSeriesNumber() == 0) {

			for (Node it : this.gridPanelCheckboxMeasureList.getChildren()) {

				if (((CheckBox) it).getText().equals(serieName))
					((CheckBox) it).setSelected(true);

			}

			return;

		}

		// ====== Starts chart generation ======

		int lastMeasureIndex = 0;
		int stop;
		boolean thereIsAZeroAsStart;
		int f = 0;

		thereIsAZeroAsStart = this.thereIsAZeroAsStart(this.choiceBoxRecipe.getValue(), this.stopIndex);

		/*----- 'Windup Feedback speed' parameter is explicitly considered because is the only collection parameter in the second chart. So, 
		 it has to be considered in a different way -----*/

		this.secondChartCollectionStartIndexCounter = this.secondChartCollectionStartIndex;

		if (!this.seriesName.containsKey(serieName)) {

			boolean findStart = false;

			XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
			series.setName(serieName);

			this.seriesName.put(serieName, series);

			String[] tTemp;

			// ==== To save indexes for shifting ========

			if (serieName.equals("Width") || serieName.equals("WidthOS")) {

				switch (machine) {

				case "Quadruplex":
					for (int i = 1; i < this.numRecords; i++) {

						if (this.choiceBoxRecipe.getValue()
								.equals(this.KPITable[i][this.parametersToDBIndexes.get("Recipe")])) {

							if (this.allViewChart || KPITable[i][this.numCols - 1] != null && KPITable[i][this.numCols - 1].equals(String.valueOf((this.chBoxPI.getValue())))) {

								if ((!findStart && Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]) == 0	&& f < 20))
									f++;

								if ((!findStart && Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]) == 0 && f > 19) || !thereIsAZeroAsStart)
									findStart = true;

								if (findStart && Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]) != 0) {
									this.secondChartCollectionStartIndex = i - 1;
									this.collectionTime = (KPITable[i][this.parametersToDBIndexes.get("Time")]).split(" ");
									break;

								}

							}

						}

					}
					break;

				case "Duplex":
					for (int i = 1; i < this.numRecords; i++) {

						if (this.choiceBoxRecipe.getValue().equals(this.KPITable[i][this.parametersToDBIndexes.get("Recipe")])) {

							if (this.allViewChart || KPITable[i][this.numCols - 1] != null && KPITable[i][this.numCols - 1].equals(String.valueOf((this.chBoxPI.getValue())))) {

								if ((!findStart && Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get("Width Upper Tolerance")]) != Double.parseDouble(KPITable[i - 1][this.parametersToDBIndexes.get("Width Upper Tolerance")])))
									findStart = true;

								if (findStart && Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]) != 0) {
									this.secondChartCollectionStartIndex = i;
									this.collectionTime = (KPITable[i][this.parametersToDBIndexes.get("Time")])
											.split(" ");
									break;

								}

							}

						}

					}
					break;

				case "InnerLiner":
					for (int i = 1; i < this.numRecords; i++) {

						if (this.choiceBoxRecipe.getValue().equals(this.KPITable[i][this.parametersToDBIndexes.get("Recipe")])) {

							if (this.allViewChart || KPITable[i][this.numCols - 1] != null && KPITable[i][this.numCols - 1].equals(String.valueOf((this.chBoxPI.getValue())))) {

								if ((!findStart && ((i - 1) == 0 || Integer.parseInt(KPITable[i][this.parametersToDBIndexes.get("Bobbin Number")]) < Integer.parseInt(KPITable[i - 1][this.parametersToDBIndexes.get("Bobbin Number")]) || Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get("Width Upper Tolerance")]) != Double.parseDouble(KPITable[i - 1][this.parametersToDBIndexes.get("Width Upper Tolerance")]))))
									findStart = true;

								if (findStart && Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]) != 0) {
									this.secondChartCollectionStartIndex = i;
									this.collectionTime = (KPITable[i][this.parametersToDBIndexes.get("Time")]).split(" ");
									break;

								}

							}

						}

					}
					break;

				}

			}

			if (!this.bigMeasures.contains(serieName) && this.headTime == null
					&& !serieName.equals("Windup Feedback Speed (m/s)")
					&& !serieName.equals("Windup Speed Actual 1 (m/s)")
					&& !serieName.equals("Windup Speed Actual 2 (m/s)")) {

				switch (machine) {

				case "Quadruplex":
					for (int i = 1; i < this.numRecords; i++) {

						if (this.choiceBoxRecipe.getValue()
								.equals(this.KPITable[i][this.parametersToDBIndexes.get("Recipe")])) {

							if (this.allViewChart
									|| KPITable[i][this.numCols - 1] != null && KPITable[i][this.numCols - 1]
											.equals(String.valueOf((this.chBoxPI.getValue())))) {

								if (!findStart && Double
										.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]) == 0)
									findStart = true;

								if (findStart && Double
										.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]) != 0) {
									this.headTime = (KPITable[i][this.parametersToDBIndexes.get("Time")]).split(" ");
									break;

								}

							}

						}

					}
					break;

				case "Duplex":
					for (int i = 1; i < this.numRecords; i++) {

						if (this.choiceBoxRecipe.getValue()
								.equals(this.KPITable[i][this.parametersToDBIndexes.get("Recipe")])) {

							if (this.allViewChart
									|| KPITable[i][this.numCols - 1] != null && KPITable[i][this.numCols - 1]
											.equals(String.valueOf((this.chBoxPI.getValue())))) {

								if (!findStart && Double.parseDouble(
										KPITable[i][this.parametersToDBIndexes.get("Width Upper Tolerance")]) != Double
												.parseDouble(KPITable[i - 1][this.parametersToDBIndexes
														.get("Width Upper Tolerance")]))
									findStart = true;

								if (findStart && Double
										.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]) != 0) {
									this.headTime = (KPITable[i][this.parametersToDBIndexes.get("Time")]).split(" ");
									break;

								}

							}

						}

					}
					break;

				case "InnerLiner":
					for (int i = 1; i < this.numRecords; i++) {

						if (this.choiceBoxRecipe.getValue()
								.equals(this.KPITable[i][this.parametersToDBIndexes.get("Recipe")])) {

							if (this.allViewChart
									|| KPITable[i][this.numCols - 1] != null && KPITable[i][this.numCols - 1]
											.equals(String.valueOf((this.chBoxPI.getValue())))) {

								if (!findStart && ((i - 1) == 0
										|| Integer.parseInt(
												KPITable[i][this.parametersToDBIndexes.get("Bobbin Number")]) < Integer
														.parseInt(KPITable[i - 1][this.parametersToDBIndexes
																.get("Bobbin Number")])
										|| Double.parseDouble(KPITable[i][this.parametersToDBIndexes
												.get("Width Upper Tolerance")]) != Double
														.parseDouble(KPITable[i - 1][this.parametersToDBIndexes
																.get("Width Upper Tolerance")])))
									findStart = true;

								if (findStart && Double
										.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]) != 0) {
									this.headTime = (KPITable[i][this.parametersToDBIndexes.get("Time")]).split(" ");
									break;

								}

							}

						}

					}
					break;

				}

			}

			for (int i = 1; i < this.numRecords; i++) {

				if (this.choiceBoxRecipe.getValue()
						.equals(this.KPITable[i][this.parametersToDBIndexes.get("Recipe")])) {

					if (this.allViewChart || KPITable[i][this.numCols - 1] != null
							&& KPITable[i][this.numCols - 1].equals(String.valueOf((this.chBoxPI.getValue())))) {

						tTemp = (KPITable[i][this.parametersToDBIndexes.get("Time")]).split(" ");

						// ===== Shifting ======
						if (serieName.equals("Width") || serieName.equals("WidthOS")) {

							if (this.collectionTime != null && this.generateXAxesValue(tTemp, false)
									.compareTo(this.generateXAxesValue(this.collectionTime, false)) >= 0) {

								if (this.maxValueFirstChart < (int) Math.round(
										Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]))) {
									maxValueFirstChart = (int) Math.round(
											Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]));
								}
								series.getData()
										.add(new XYChart.Data<String, Double>(this.generateXAxesValue(tTemp,
												false),
										Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)])));
								this.invisibleSeries.add(this.generateXAxesValue(tTemp, true)); // the
																								// invisible
																								// series
																								// is
																								// filled

							}

						} else if (serieName.equals("Weight") || serieName.equals("Width Upper Tolerance")
								|| serieName.equals("Width Lower Tolerance")
								|| serieName.equals("Weight Upper Tolerance")
								|| serieName.equals("Weight Lower Tolerance") || serieName.equals("Weight Marker")
								|| serieName.equals("Width Marker") || serieName.equals("WidthDS")
								|| serieName.equals("WeightOS") || serieName.equals("WeightDS")
								|| serieName.equals("Weight Marker OS") || serieName.equals("Weight Marker DS")
								|| serieName.equals("Upper Cutter (mm)") || serieName.equals("Lower Cutter (mm)")) {

							if (this.collectionTime != null && this.generateXAxesValue(tTemp, false)
									.compareTo(this.generateXAxesValue(this.collectionTime, false)) >= 0) {
								// tTemp =
								// (KPITable[this.secondChartCollectionStartIndexCounter][this.parametersToDBIndexes.get("Time")]).split("
								// ");
								if (this.maxValueFirstChart < (int) Math.round(
										Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]))) {
									maxValueFirstChart = (int) Math.round(
											Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]));
								}
								series.getData()
										.add(new XYChart.Data<String, Double>(this.generateXAxesValue(tTemp,
												false),
										Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)])));
								// this.secondChartCollectionStartIndexCounter++;
							}

						} else if (serieName.equals("Windup Feedback Speed (m/s)")
								|| serieName.equals("Windup Speed Actual 1 (m/s)")
								|| serieName.equals("Windup Speed Actual 2 (m/s)")) {
							if (this.generateXAxesValue(tTemp, false)
									.compareTo(this.generateXAxesValue(this.collectionTime, false)) >= 0) {
								tTemp = (KPITable[this.secondChartCollectionStartIndexCounter][this.parametersToDBIndexes
										.get("Time")]).split(" ");
								if (this.maxValueThirdChart < (int) Math.round(
										Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]))) {
									maxValueThirdChart = (int) Math.round(
											Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]));
								}
								series.getData()
										.add(new XYChart.Data<String, Double>(this.generateXAxesValue(tTemp,
												false),
										Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)])));
								this.secondChartCollectionStartIndexCounter++;
							}

						} else { // ===== for head measures =======

							if (this.headTime != null && this.generateXAxesValue(tTemp, false)
									.compareTo(this.generateXAxesValue(this.headTime, false)) >= 0) {

								if (this.stopHeadIndex > 0 && i > this.stopHeadIndex)
									break;

								if (this.stopHeadIndex == 0) {
									this.stopHeadIndex = this.getHeadStopIndex(serieName);
								}
								tTemp = (KPITable[this.secondChartCollectionStartIndexCounter][this.parametersToDBIndexes
										.get("Time")]).split(" ");
								if (this.maxValueSecondChart < (int) Math.round(
										Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]))) {
									maxValueSecondChart = (int) Math.round(
											Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]));
								}
								series.getData()
										.add(new XYChart.Data<String, Double>(this.generateXAxesValue(tTemp,
												false),
										Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)])));

								this.secondChartCollectionStartIndexCounter++;
							}
						}
						lastMeasureIndex = i;

					}

				}

			}

			if (serieName.equals("Width") || serieName.equals("WidthOS") || serieName.equals("Weight")
					|| serieName.equals("Width Upper Tolerance") || serieName.equals("Width Lower Tolerance")
					|| serieName.equals("Weight Upper Tolerance") || serieName.equals("Weight Lower Tolerance")
					|| serieName.equals("Windup Feedback Speed (m/s)") || serieName.equals("Weight Marker")
					|| serieName.equals("Width Marker") || serieName.equals("WidthDS") || serieName.equals("WeightOS")
					|| serieName.equals("WeightDS") || serieName.equals("Weight Marker OS")
					|| serieName.equals("Weight Marker DS") || serieName.equals("Upper Cutter (mm)")
					|| serieName.equals("Lower Cutter (mm)")) {

				int r = 0;

				if (stopIndex > 0)
					stop = stopIndex;
				else
					stop = lastMeasureIndex + this.cellsHeadCollection;

				for (r = lastMeasureIndex; r < stop; r++) {

					tTemp = (KPITable[r][this.parametersToDBIndexes.get("Time")]).split(" ");
					series.getData().add(new XYChart.Data<String, Double>(this.generateXAxesValue(tTemp, false),
							Double.parseDouble(KPITable[r][this.parametersToDBIndexes.get(serieName)])));
					this.invisibleSeries.add(this.generateXAxesValue(tTemp, true)); // the
																					// invisible
																					// series
																					// is
																					// filled

					if (this.machine.equals("Quadruplex")
							&& Double.parseDouble(KPITable[r][this.parametersToDBIndexes.get(serieName)]) == 0
							&& stopIndex == 0) {
						stopIndex = r;
						break;
					}

					if (this.machine.equals("Duplex")
							&& (Double.parseDouble(KPITable[r][this.parametersToDBIndexes
									.get("Width Upper Tolerance")]) != Double.parseDouble(
											KPITable[r - 1][this.parametersToDBIndexes.get("Width Upper Tolerance")]))
							&& stopIndex == 0) {
						stopIndex = r;
						break;
					}

					if (this.machine.equals("InnerLiner")
							&& (Integer.parseInt(KPITable[r][this.parametersToDBIndexes.get("Bobbin Number")]) < Integer
									.parseInt(KPITable[r - 1][this.parametersToDBIndexes.get("Bobbin Number")])
							|| Double.parseDouble(KPITable[r][this.parametersToDBIndexes
									.get("Width Upper Tolerance")]) != Double.parseDouble(
											KPITable[r - 1][this.parametersToDBIndexes.get("Width Upper Tolerance")]))
							&& stopIndex == 0) {
						stopIndex = r;
						break;
					}

				}

				if (r == 0)
					r = 1;
				if (this.machine.equals("Quadruplex") && stopIndex == 0 && this.thereIsAZeroAsStartForOtherRecipe(
						this.KPITable[r][this.parametersToDBIndexes.get("Recipe")], r)) {

					for (int g = r; g < this.numRecords; g++) {

						tTemp = (KPITable[g][this.parametersToDBIndexes.get("Time")]).split(" ");
						series.getData().add(new XYChart.Data<String, Double>(this.generateXAxesValue(tTemp, false),
								Double.parseDouble(KPITable[g][this.parametersToDBIndexes.get(serieName)])));
						this.invisibleSeries.add(this.generateXAxesValue(tTemp, true)); // the
																						// invisible
																						// series
																						// is
																						// filled

						if (Float.parseFloat(this.KPITable[g][this.parametersToDBIndexes.get("Width")]) == 0) {
							stopIndex = (g - 1);
							break;
						}

					}

				}

				if (machine.equals("Duplex") && stopIndex == 0) {

					for (int g = r; g < this.numRecords; g++) {
						if ((g - 1) == 0)
							break;

						if (this.machine.equals("Duplex") && (Float.parseFloat(
								this.KPITable[g][this.parametersToDBIndexes.get("Width Upper Tolerance")]) == Float
										.parseFloat(this.KPITable[g - 1][this.parametersToDBIndexes
												.get("Width Upper Tolerance")]))) {
							tTemp = (KPITable[g][this.parametersToDBIndexes.get("Time")]).split(" ");
							series.getData().add(new XYChart.Data<String, Double>(this.generateXAxesValue(tTemp, false),
									Double.parseDouble(KPITable[r][this.parametersToDBIndexes.get(serieName)])));
							this.invisibleSeries.add(this.generateXAxesValue(tTemp, true)); // the
																							// invisible
																							// series
																							// is
																							// filled
							stopIndex = (g - 1);
						} else
							break;

					}

				}

				if (machine.equals("InnerLiner") && stopIndex == 0) {

					for (int g = r; g < this.numRecords; g++) {
						if ((g - 1) == 0)
							break;

						if (this.machine.equals("InnerLiner") && (Integer
								.parseInt(KPITable[g][this.parametersToDBIndexes.get("Bobbin Number")]) >= Integer
										.parseInt(KPITable[g - 1][this.parametersToDBIndexes.get("Bobbin Number")])
								&& Double.parseDouble(
										KPITable[g][this.parametersToDBIndexes.get("Width Upper Tolerance")]) == Double
												.parseDouble(KPITable[g - 1][this.parametersToDBIndexes
														.get("Width Upper Tolerance")]))) {
							tTemp = (KPITable[g][this.parametersToDBIndexes.get("Time")]).split(" ");
							series.getData().add(new XYChart.Data<String, Double>(this.generateXAxesValue(tTemp, false),
									Double.parseDouble(KPITable[r][this.parametersToDBIndexes.get(serieName)])));
							this.invisibleSeries.add(this.generateXAxesValue(tTemp, true)); // the
																							// invisible
																							// series
																							// is
																							// filled
							stopIndex = (g - 1);
						} else
							break;

					}

				}

			}

			if (this.bigMeasures.contains(serieName))
				this.graph.getData().add(series);
			else if (!serieName.equals("Windup Feedback Speed (m/s)"))
				this.graph2.getData().add(series);
			else {
				this.graph3.setVisible(true);
				this.graph3.getData().add(series);
				// this.graph3.getYAxis().setStyle("-fx-tick-label-fill:
				// "+this.seriesColors[this.colorIndex]+";");
				this.graph3.getYAxis().setStyle("-fx-tick-label-fill: yellow;");
			}

			this.addElementToLegend(series);
			if (this.colorIndex == this.seriesColors.length - 1)
				this.colorIndex = -1;
			this.colorIndex++;

			this.addTooltipToSeries(series);

			this.hideShowOrTranslateChart();

			return;

		}

		XYChart.Series<String, Double> tempSeries;

		if (this.seriesName.containsKey(serieName))
			tempSeries = this.seriesName.get(serieName);
		else
			return;

		if (this.graph.getData().contains(tempSeries)) {

			for (Node it : this.gridPanelCheckboxMeasureList.getChildren()) {

				if (it.getId().equals(serieName))
					if (!((CheckBox) it).isSelected()) {
						this.graph.getData().remove(tempSeries);
						this.deleteElementFromLegend(serieName);
					}
			}

		} else

		if (this.graph3.getData().contains(tempSeries)) {

			for (Node it : this.gridPanelCheckboxMeasureList.getChildren()) {

				if (it.getId().equals(serieName))
					if (!((CheckBox) it).isSelected()) {
						this.graph3.getData().remove(tempSeries);
						this.deleteElementFromLegend(serieName);
					}
			}

			this.graph3.setVisible(false);

		} else if (this.graph2.getData().contains(tempSeries)) {

			for (Node it : this.gridPanelCheckboxMeasureList.getChildren()) {

				if (it.getId().equals(serieName))
					if (!((CheckBox) it).isSelected()) {
						this.graph2.getData().remove(tempSeries);
						this.deleteElementFromLegend(serieName);
					}
			}

		} else {

			if (this.bigMeasures.contains(serieName)) {

				this.graph.getData().add(tempSeries);
				this.addElementToLegend(tempSeries);
				if (this.colorIndex == this.seriesColors.length - 1)
					this.colorIndex = -1;
				this.colorIndex++;
			} else {
				if (serieName.equals("Windup Feedback Speed (m/s)")) {

					this.graph3.setVisible(true);
					this.graph3.getData().add(tempSeries);
					this.graph3.getYAxis().setStyle("-fx-tick-label-fill: " + this.seriesColors[this.colorIndex] + ";");
					this.addElementToLegend(tempSeries);
					if (this.colorIndex == this.seriesColors.length - 1)
						this.colorIndex = -1;
					this.colorIndex++;

				} else {
					this.graph2.getData().add(tempSeries);
					this.addElementToLegend(tempSeries);
					if (this.colorIndex == this.seriesColors.length - 1)
						this.colorIndex = -1;
					this.colorIndex++;
				}
			}

		}

		this.hideShowOrTranslateChart();

	}

	private String generateXAxesValue(String[] tTemp, boolean isInvisible) {

		if (!isInvisible)
			return tTemp[1];
		else
			return tTemp[0] + " " + tTemp[1];

	}

	private void addTooltipToSeries(XYChart.Series<String, Double> series) {

		for (XYChart.Data<String, Double> d : series.getData()) {
			Tooltip.install(d.getNode(),
					new Tooltip(d.getXValue().toString() + "\n" + "Number Of Events : " + d.getYValue()));
		}

		// for (final XYChart.Data<String, Double> data : series.getData()) {
		// Tooltip tooltip = new Tooltip();
		// tooltip.setText(data.getYValue().toString());
		// Tooltip.install(data.getNode(), tooltip);
		// }
		//
		// System.out.println(series.getName());

	}

	private void addTooltipToSeries(XYChart.Series<String, Double> series,
			Map<XYChart.Series<String, Double>, String> tempSeriesMap) {

		for (XYChart.Data<String, Double> d : series.getData()) {
			Tooltip.install(d.getNode(), new Tooltip(tempSeriesMap.get(series)));
		}

	}

	private boolean thereIsAZeroAsStart(String recipe, int precLI) {

		int start;

		if (precLI > -1)
			start = precLI;
		else
			start = 0;

		for (int i = start; i < this.numRecords; i++) {

			if (this.choiceBoxRecipe.getValue().equals(this.KPITable[i][this.parametersToDBIndexes.get("Recipe")])) {

				if (KPITable[i][this.numCols - 1] != null
						&& KPITable[i][this.numCols - 1].equals(String.valueOf((this.chBoxPI.getValue())))) {

					switch (machine) {

					case "Quadruplex":
						if (Double.parseDouble(this.KPITable[i][this.parametersToDBIndexes.get("Width")]) == 0)
							return true;
						break;

					case "Duplex":
						if (Double.parseDouble(this.KPITable[i][this.parametersToDBIndexes.get("WidthOS")]) == 0)
							return true;
						break;

					}

				}

			}

		}

		return false;

	}

	private boolean thereIsAZeroAsStartForOtherRecipe(String recipe, int precLI) {

		int start;

		if (precLI > -1)
			start = precLI;
		else
			start = 1;

		for (int i = start; i < this.numRecords; i++) {

			if (recipe.equals(this.KPITable[i][this.parametersToDBIndexes.get("Recipe")])) {

				switch (machine) {

				case "Quadruplex":
					if (Double.parseDouble(this.KPITable[i][this.parametersToDBIndexes.get("Width")]) == 0)
						return true;
					break;

				case "Duplex":
					if (Double.parseDouble(this.KPITable[i][this.parametersToDBIndexes
							.get("Width Upper Tolerance")]) != Double.parseDouble(
									this.KPITable[i - 1][this.parametersToDBIndexes.get("Width Upper Tolerance")]))
						return true;
					break;

				}

			}

		}

		return false;

	}

	private void hideShowOrTranslateChart() {

		// ===== Hide or resize chart, if the last series visible belongs to the
		// first, second or third chart ====//

		if (this.thereAreBigMeasuresVisible()) {
			this.graph2.getXAxis().setTickLabelsVisible(false);
			this.graph2.getXAxis().setTickMarkVisible(false);
			this.graph3.getXAxis().setTickLabelsVisible(false);
			this.graph3.getXAxis().setTickMarkVisible(false);
			this.graph.getXAxis().setTickMarkVisible(false);

			if (this.isGraphTranslated) {
				this.graph.setMaxHeight(-1.0);
				this.graph.setTranslateY(0);
				this.graph4.setMaxHeight(this.initialChart4MaxHeight);
				this.graph4.setTranslateY(0.0);
			}

			this.isGraphTranslated = false;

		} else {
			if (this.isWindupVisible() && this.graph2.getData().size() == 0) {
				this.graph3.getXAxis().setTickLabelsVisible(true);
				this.graph3.getXAxis().setStyle(" -fx-tick-label-fill: white;");
				this.graph2.setVisible(false);
				this.graph.setMaxHeight(this.graph3.getHeight() - 37);
				this.graph4.setMaxHeight(this.initialChart4MaxHeight - 37);

			}

			if (this.isWindupVisible() && this.graph2.getData().size() > 0) {
				this.graph3.getXAxis().setTickLabelsVisible(true);
				this.graph3.getXAxis().setStyle(" -fx-tick-label-fill: transparent;");
				this.graph2.setVisible(true);
				this.graph2.getXAxis().setTickLabelsVisible(true);
				this.graph2.getXAxis().setStyle(" -fx-tick-label-fill: white;");
				this.graph.setMaxHeight(this.graph3.getHeight() - 37);
				this.graph4.setMaxHeight(this.initialChart4MaxHeight - 37);

			}

			if (!this.isWindupVisible() && this.graph2.getData().size() > 0) {
				this.graph2.setVisible(true);
				this.graph2.getXAxis().setTickLabelsVisible(true);
				this.graph2.getXAxis().setStyle(" -fx-tick-label-fill: white;");
				this.graph3.getXAxis().setTickLabelsVisible(true);
				this.graph3.getXAxis().setStyle(" -fx-tick-label-fill: transparent;");
				this.graph.setMaxHeight(this.graph2.getHeight() - 37);
				this.graph4.setMaxHeight(this.initialChart4MaxHeight - 37);
			}

			this.graph.setTranslateY(-36);
			this.graph4.setTranslateY(-19);
			this.isGraphTranslated = true;

		}

	}

	private int getHeadStopIndex(String serieName) {

		boolean find = false;
		int stopHeadIndex = -1;

		for (int i = 0; i < this.KPITable.length; i++) {

			if (this.choiceBoxRecipe.getValue().equals(this.KPITable[i][this.parametersToDBIndexes.get("Recipe")])) {

				if (!this.allViewChart && KPITable[i][this.numCols - 1] != null
						&& KPITable[i][this.numCols - 1].equals(String.valueOf((this.chBoxPI.getValue())))) {

					if (Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]) < 1 && !find) {
						stopHeadIndex = i;
						find = true;
					}

					if (Double.parseDouble(KPITable[i][this.parametersToDBIndexes.get(serieName)]) > 0 && find) {
						stopHeadIndex = -1;
						find = false;
					}

				}

			}

		}

		return stopHeadIndex;

	}

	private void addElementToLegend(XYChart.Series<String, Double> series) {

		series.getNode().setStyle("-fx-stroke:" + this.seriesColors[this.colorIndex]);

		Circle tempCircle = new Circle(5, Paint.valueOf(this.seriesColors[this.colorIndex]));
		tempCircle.setStyle("-fx-border-width:1; -fx-border-color:black;");

		if (this.legendIndex > this.numLegendRows) {
			this.legend.addRow(this.numLegendRows - 1);
			this.numLegendRows++;
		}
		this.legend.add(tempCircle, 0, this.legendIndex);

		Label tempLabel = new Label(series.getName());
		tempLabel.setPadding(new Insets(0, 0, 2, 10));

		this.legend.add(tempLabel, 1, this.legendIndex);
		this.legend.setPrefHeight(this.legend.getHeight() + 7);
		this.legendIndex++;

	}

	private void deleteElementFromLegend(String seriename) {

		Label tempLabel = null;
		int i = 0;

		for (Node n : this.legend.getChildren()) {

			if (n.getClass().getName().contains("Label"))
				if (((Label) n).getText().equals(seriename)) {

					((Label) n).setText("");
					tempLabel = (Label) n;
					break;
				}

			i++;

		}

		this.legend.setPrefHeight(this.legend.getHeight() - 40);
		this.legend.getChildren().remove(tempLabel);
		this.legend.getChildren().remove(i - 1);

	}

	private void generateGraph() {

		this.graph.getData().clear();
		this.graph2.getData().clear();
		this.graph3.getData().clear();
		this.graph4.getData().clear();

		if (this.legend == null) {
			this.legend = new GridPane();
			this.legend.setStyle(
					"-fx-background-color:linear-gradient(from 0% 0% to 100% 100%, white, silver 70%);-fx-border-color:white;-fx-border-width:2;-fx-border-style:solid;-fx-border-radius:20;-fx-background-radius: 20;-fx-background-insets: 0,1,2,3,0; -fx-text-fill:red; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 5 5 5 10;");
			this.hboxStackPaneGraphView.getChildren()
					.add(this.hboxStackPaneGraphView.getChildren().indexOf(this.stackPaneGraph) + 1, this.legend);
		}
		this.legend.setGridLinesVisible(true);
		this.legend.setPrefHeight(2);
		this.legend.setPrefWidth(250);
		this.legend.setMaxWidth(300);
		this.legend.setMaxHeight(30);
		this.legend.setTranslateX(40);
		this.legend.setTranslateY(100);
		this.legend.getChildren().clear();
		this.legendIndex = 1;
		this.legend.setPrefHeight(0);
		this.colorIndex = 0;
		this.headTime = null;
		this.collectionTime = null;
		int chkBoxSelected = 0;
		this.seriesName.clear();
		String[] tTemp;
		this.cellsHeadCollection = 0;
		this.stopIndex = 0;
		this.stopHeadIndex = 0;
		boolean isChangedMeasure = false;
		boolean isChangedPIIndex = false;
		this.invisibleSeries = new LinkedList<String>();

		int stopI = this.stopIndex;

		if (this.FilterYParameters != null) {
			this.hideFilters(new ActionEvent());
		}

		if (this.seriesName != null)
			this.seriesName.clear();

		if (this.lastMeasure == null || !this.lastMeasure.equals(this.choiceBoxRecipe.getValue())) {

			this.lastMeasure = this.choiceBoxRecipe.getValue();
			isChangedMeasure = true;
		} else
			isChangedMeasure = false;

		if (this.lastPIIndex == null || this.lastPIIndex != this.chBoxPI.getValue()) {
			this.lastPIIndex = this.chBoxPI.getValue();
			isChangedPIIndex = true;
		}

		if (isChangedMeasure) {

			switch (this.machine) {

			case "Quadruplex":

				if (this.recipePerformanceQuadruplex.containsKey(this.choiceBoxRecipe.getValue())) {
					if (this.recipePerformanceQuadruplex.get(this.choiceBoxRecipe.getValue())
							.getProductionIndex() > 1) {
						this.showPIMenu(this.choiceBoxRecipe.getValue());
					} else {
						this.lblPI.setVisible(false);
						this.chBoxPI.setVisible(false);
					}
				}
				break;

			case "Duplex":

				if (this.recipePerformanceDuplex.containsKey(this.choiceBoxRecipe.getValue())) {
					if (this.recipePerformanceDuplex.get(this.choiceBoxRecipe.getValue()).getProductionIndex() > 1) {
						this.showPIMenu(this.choiceBoxRecipe.getValue());
					} else {
						this.lblPI.setVisible(false);
						this.chBoxPI.setVisible(false);
					}
				}

				break;

			case "InnerLiner":

				if (this.recipePerformanceInnerLiner.containsKey(this.choiceBoxRecipe.getValue())) {
					if (this.recipePerformanceInnerLiner.get(this.choiceBoxRecipe.getValue())
							.getProductionIndex() > 1) {
						this.showPIMenu(this.choiceBoxRecipe.getValue());
					} else {
						this.lblPI.setVisible(false);
						this.chBoxPI.setVisible(false);
					}
				}

				break;

			}

		}

		for (Node it : this.gridPanelCheckboxMeasureList.getChildren()) {

			((CheckBox) it).setSelected(false);

			if (it.getId().equals("Width") || it.getId().equals("WidthOS") || it.getId().equals("WidthDS")
					|| it.getId().equals("Width Upper Tolerance") || it.getId().equals("Width Lower Tolerance")) {

				((CheckBox) it).setSelected(true);

			}

		}

		for (Node it : this.gridPanelCheckboxMeasureList.getChildren()) {

			if (((CheckBox) it).isSelected()) {

				this.showOrHideSeries(it.getId());
				chkBoxSelected++;
			}

		}

		this.stopIndex = stopI;

		if (isFirst && chkBoxSelected > 2) {
			isChangedMeasure = true; // so, the first time the sw is running,
										// its value is "true"

			isFirst = false;
		}

		if (chkBoxSelected > 2 && (isChangedMeasure || isChangedPIIndex)) {

			// === Builds and destroys an head measure, in order to determine
			// the "head index" of the "showOrHideSeries" method

			if (this.machine.equals("InnerLiner")) {
				this.showOrHideSeries("Calender Actual Speed (m/s)");
				this.showOrHideSeries("Calender Actual Speed (m/s)");
				this.seriesName.remove("Calender Actual Speed (m/s)");
			} else {
				this.showOrHideSeries("RPM");
				this.showOrHideSeries("RPM");
				this.seriesName.remove("RPM");
			}

			for (int i = 1; i < this.numRecords; i++) {

				if (this.choiceBoxRecipe.getValue()
						.equals(this.KPITable[i][this.parametersToDBIndexes.get("Recipe")])) {

					if (KPITable[i][this.numCols - 1] != null
							&& KPITable[i][this.numCols - 1].equals(String.valueOf((this.chBoxPI.getValue())))) {

						tTemp = (KPITable[i][this.parametersToDBIndexes.get("Time")]).split(" ");

						if (this.collectionTime != null && this.headTime != null
								&& this.generateXAxesValue(tTemp, false)
										.compareTo(this.generateXAxesValue(this.headTime, false)) >= 0
								&& this.generateXAxesValue(tTemp, false)
										.compareTo(this.generateXAxesValue(this.collectionTime, false)) <= 0)
							cellsHeadCollection++;

					}

				}

			}

			this.graph.getData().clear();
			this.graph2.getData().clear();
			this.graph3.getData().clear();
			this.legend.setPrefHeight(0);
			this.legend.getChildren().clear();
			this.legendIndex = 1;
			this.legend.setPrefHeight(0);
			this.colorIndex = 0;
			this.seriesName.clear();

			for (Node it : this.gridPanelCheckboxMeasureList.getChildren()) {

				if (((CheckBox) it).isSelected()) {

					this.showOrHideSeries(it.getId());
					chkBoxSelected++;
				}

			}


			this.generateLowerAndUpperBound();

		}

	}

	private void showPIMenu(String recipe) {

		this.lblPI.setVisible(true);
		this.chBoxPI.setVisible(true);

		this.chBoxPI.getItems().clear();

		switch (this.machine) {

		case "Quadruplex":
			for (int i = 0; i < this.recipePerformanceQuadruplex.get(recipe).getProductionIndex(); i++) {
				this.chBoxPI.getItems().add(i + 1);
			}
			break;

		case "Duplex":
			for (int i = 0; i < this.recipePerformanceDuplex.get(recipe).getProductionIndex(); i++) {
				this.chBoxPI.getItems().add(i + 1);
			}
			break;

		case "InnerLiner":
			for (int i = 0; i < this.recipePerformanceInnerLiner.get(recipe).getProductionIndex(); i++) {
				this.chBoxPI.getItems().add(i + 1);
			}
			break;
		}

		this.chBoxPI.setValue(1);
	}

	private void setSeries() {

		this.seriesName = new HashMap<String, XYChart.Series<String, Double>>();

	}

	public void setGraphStyle() {

		graph.getStylesheets().add("gui/GraphicalFiles/graphStyle.css");
		graph2.getStylesheets().add("gui/GraphicalFiles/graphStyle2.css");
		graph3.getStylesheets().add("gui/GraphicalFiles/graphStyle2.css");
		graph4.getStylesheets().add("gui/GraphicalFiles/graphStyle3.css");
		this.scrollPaneSX.getStylesheets().add("gui/GraphicalFiles/scrollPaneStyle.css");
		this.scrollPaneDX.getStylesheets().add("gui/GraphicalFiles/scrollPaneStyle.css");
		this.scrollPaneDX.getParent().prefWidth(this.theRoot.getWidth());
		this.scrollPaneDX.getParent().minHeight(this.theRoot.getHeight());
		this.vBoxGraphView.minHeight(this.theRoot.getHeight());
		this.hBoxGraphView.minHeight(this.theRoot.getHeight());

		this.initialChart4MaxHeight = this.graph4.getMaxHeight();

		graph.setLegendVisible(false);
		graph2.setLegendVisible(false);
		graph3.setLegendVisible(false);

		if(this.allViewChart)
			this.graph4.setCreateSymbols(true);

		this.graphYAxis.setAutoRanging(true);
		this.graphYAxis.setAnimated(true);

		this.graph.setHorizontalGridLinesVisible(true);

		if (this.allViewChart)
			this.graph.setPrefHeight(1000);

		this.setSeriesColors();
		this.colorIndex = 0;

		this.numLegendRows = 2;

		if (this.legend != null)
			this.legend.getChildren().clear();

	}

	public void resetAnalysisValues() {

		this.secondChartCollectionStartIndex = 0;

		this.maxValueFirstChart = 0;
		this.maxValueSecondChart = 0;
		this.maxValueThirdChart = 0;

		this.cellsHeadCollection = 0;

	}

	public void setStage(Stage stage) {

		this.stage = stage;

	}

	public void setFileAnalysis(FileAnalysis fa) {

		this.fa = fa;

	}

	public void setParametersToDBIndexes() {

		this.parametersToDBIndexes = new HashMap<String, Integer>();

		switch (machine) {

		case "Quadruplex":

			this.parametersToDBIndexes.put("Time", fa.getDateIndex());
			this.parametersToDBIndexes.put("Recipe", fa.getRecipeCol());
			this.parametersToDBIndexes.put("Width", fa.getParameterIndexWidthLine());
			this.parametersToDBIndexes.put("Weight", fa.getParameterIndexWeightLine());
			this.parametersToDBIndexes.put("Width Marker",
					this.getParameterIndex("LINE - Width Marking Zone Measure OS (Or Single Stripe) (mm)"));
			this.parametersToDBIndexes.put("Weight Marker",
					this.getParameterIndex("LINE - Weight Marking Zone Measure OS (Or Single Stripe) (mm)"));
			this.parametersToDBIndexes.put("Width Upper Tolerance", fa.getWidthUpperToleranceIndex());
			this.parametersToDBIndexes.put("Width Lower Tolerance", fa.getWidthLowerToleranceIndex());
			this.parametersToDBIndexes.put("Weight Upper Tolerance", fa.getWeightUpperToleranceIndex());
			this.parametersToDBIndexes.put("Weight Lower Tolerance", fa.getWeightLowerToleranceIndex());
			this.parametersToDBIndexes.put("Recipe Speed",
					this.getParameterIndex("LINE - LINE Speed Preset from Recipe (m/min)"));
			this.parametersToDBIndexes.put("Registered Speed (m/s)",
					this.getParameterIndex("LINE - LINE Speed Feedback (m/min)"));
			this.parametersToDBIndexes.put("Windup Feedback Speed (m/s)",
					this.getParameterIndex("LINE - Windup Speed Feedback (m/min)"));
			this.parametersToDBIndexes.put("RPM",
					this.getParameterIndex("EXTRUDER 250 mm - Screw Speed Preset from Recipe (rpm)"));
			this.parametersToDBIndexes.put("Pressure Head (bar)",
					this.getParameterIndex("EXTRUDER 250 mm - Screw Pressure After Filter (Bar)"));
			this.parametersToDBIndexes.put("Motor Torque (%)",
					this.getParameterIndex("EXTRUDER 250 mm - Screw Motor Torque (%)"));
			this.parametersToDBIndexes.put("Feeder Speed Feedback (m/min)",
					this.getParameterIndex("EXTRUDER 250 mm - Feeder Speed Feedback (m/min)"));
			this.parametersToDBIndexes.put("Dancer Position Feedback (%)",
					this.getParameterIndex("EXTRUDER 250 mm - Dancer Position Feedback (%)"));
			break;

		case "Duplex":

			this.parametersToDBIndexes.put("Time", fa.getDateIndex());
			this.parametersToDBIndexes.put("Recipe", fa.getRecipeCol());
			this.parametersToDBIndexes.put("WidthOS",
					this.getParameterIndex("LINE - Width Winder Zone Measure OS (Or Single Stripe) (mm)"));
			this.parametersToDBIndexes.put("WidthDS",
					this.getParameterIndex("LINE - Width Winder Zone Measure DS (mm)"));
			this.parametersToDBIndexes.put("WeightOS",
					this.getParameterIndex("LINE - Weight Winder Zone Measure OS (Or Single Stripe) (mm)"));
			this.parametersToDBIndexes.put("WeightDS",
					this.getParameterIndex("LINE - Weight Winder Zone Measure DS (g)"));
			this.parametersToDBIndexes.put("Width Extruder OS",
					this.getParameterIndex("LINE - Width Extruder Zone Measure OS (Or Single Stripe) (mm)"));
			this.parametersToDBIndexes.put("Width Extruder DS",
					this.getParameterIndex("LINE - Width Extruder Zone Measure DS (mm)"));
			this.parametersToDBIndexes.put("Weight Marker OS",
					this.getParameterIndex("LINE - Weight Marking Zone Measure OS (Or Single Stripe) (mm)"));
			this.parametersToDBIndexes.put("Weight Marker DS",
					this.getParameterIndex("LINE - Weight Marking Zone Measure DS (g)"));
			this.parametersToDBIndexes.put("Width Upper Tolerance",
					this.getParameterIndex("LINE - Width Winder Zone Upper Tolerance  (mm)"));
			this.parametersToDBIndexes.put("Width Lower Tolerance",
					this.getParameterIndex("LINE - Width Winder Zone Lower Tolerance  (mm)"));
			this.parametersToDBIndexes.put("Weight Upper Tolerance",
					this.getParameterIndex("LINE - Weight Winder Zone Upper Tolerance  (g)"));
			this.parametersToDBIndexes.put("Weight Lower Tolerance",
					this.getParameterIndex("LINE - Weight Winder Zone Lower Tolerance  (g)"));
			this.parametersToDBIndexes.put("Recipe Speed",
					this.getParameterIndex("LINE - LINE Speed Preset from Recipe (m/min)"));
			this.parametersToDBIndexes.put("Registered Speed (m/s)",
					this.getParameterIndex("LINE - LINE Speed Feedback (m/min)"));
			this.parametersToDBIndexes.put("Windup Feedback Speed (m/s)",
					this.getParameterIndex("LINE - Windup Speed Feedback (m/min)"));
			this.parametersToDBIndexes.put("RPM",
					this.getParameterIndex("EXTRUDER 200 mm - Screw Speed Preset from Recipe (rpm)"));
			this.parametersToDBIndexes.put("Pressure Head (bar)",
					this.getParameterIndex("EXTRUDER 200 mm - Screw Pressure After Filter (Bar)"));
			this.parametersToDBIndexes.put("Motor Torque (%)",
					this.getParameterIndex("EXTRUDER 200 mm - Screw Motor Torque (%)"));
			this.parametersToDBIndexes.put("Feeder Speed Feedback (m/min)",
					this.getParameterIndex("EXTRUDER 200 mm - Feeder Speed (m/min)"));
			this.parametersToDBIndexes.put("Dancer Position Feedback (%)",
					this.getParameterIndex("EXTRUDER 200 mm - Dancer Position (%)"));
			break;

		case "InnerLiner":

			this.parametersToDBIndexes.put("Time", fa.getDateIndex());
			this.parametersToDBIndexes.put("Recipe", fa.getRecipeCol());
			this.parametersToDBIndexes.put("Width",
					this.getParameterIndex("Post Calender - Width Camera 1 Actual (mm)"));
			this.parametersToDBIndexes.put("Bobbin Number", this.getParameterIndex("Calender - Bobbin Numer (--)"));
			this.parametersToDBIndexes.put("Width Upper Tolerance", this.getParameterIndex("Width Upper Tolerance"));
			this.parametersToDBIndexes.put("Width Lower Tolerance", this.getParameterIndex("Width Lower Tolerance"));
			this.parametersToDBIndexes.put("Windup Speed Actual 1 (m/s)",
					this.getParameterIndex("Post Calender - Windup 1 Speed Actual (m/min)"));
			this.parametersToDBIndexes.put("Windup Speed Actual 2 (m/s)",
					this.getParameterIndex("Post Calender - Windup 2 Speed Actual (m/min)"));
			this.parametersToDBIndexes.put("Calender Speed",
					this.getParameterIndex("Calender - Line Speed Preset from Recipe (m/min)"));
			this.parametersToDBIndexes.put("Calender Actual Speed (m/s)",
					this.getParameterIndex("Calender - Line Speed Actual (m/min)"));
			this.parametersToDBIndexes.put("Lower Cutter (mm)",
					this.getParameterIndex("Calender - Lower Cutter Actual (mm)"));
			this.parametersToDBIndexes.put("Upper Cutter (mm)",
					this.getParameterIndex("Calender - Upp. Ext. Cutter Actual (mm)"));
			break;

		}

	}

	public int getParameterIndex(String parameter) {

		int index = -1;

		for (int j = 0; j < this.fa.getNumCols(); j++) {

			if (this.fa.getKPITable()[0][j].equals(parameter)) {
				index = j;
				break;
			} else
				index = -1;

		}

		if (index == -1) {

			JOptionPane.showMessageDialog(null,
					"CRITICAL ERROR: parameter '" + parameter
							+ "' not found. Please, control parameters name in the selected csv file",
					"CRITICAL ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return -1;

		} else
			return index;

	}

	private void destroyFileAnalyis() {

		this.fa = null;

	}

	public void setTableDims() {

		this.setNumRecords();
		this.setNumCols();

	}

	private void setNumRecords() {

		this.numRecords = this.fa.getNumRecords();

	}

	private void setNumCols() {

		this.numCols = this.fa.getNumCols();

	}

	private void setSeriesColors() {

		this.seriesColors = new String[20];

		this.seriesColors[0] = "ORANGE";
		this.seriesColors[1] = "GOLD";
		this.seriesColors[2] = "GREEN";
		this.seriesColors[3] = "TEAL";
		this.seriesColors[4] = "BLUE";
		this.seriesColors[5] = "PURPLE";
		this.seriesColors[6] = "RED";
		this.seriesColors[7] = "DARKGRAY";
		this.seriesColors[8] = "BLACK";
		this.seriesColors[9] = "ROYALBLUE";
		this.seriesColors[10] = "SALMON";
		this.seriesColors[11] = "BROWN";
		this.seriesColors[12] = "MAGENTA";
		this.seriesColors[13] = "CHOCOLATE";
		this.seriesColors[14] = "PLUM";
		this.seriesColors[15] = "MEDIUMPURPLE";
		this.seriesColors[16] = "HOTPINK";
		this.seriesColors[17] = "CYAN";
		this.seriesColors[18] = "STEELBLUE";
		this.seriesColors[19] = "YELLOW";

	}

	public void setPIOnAction(ControllerTableAndGraph controller) {

		this.chBoxPI.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				controller.generateGraph();

			}

		});

	}

	public void initializesRecipesToLimits() {

		this.recipesToLimits = this.fa.getRecipesToLimits();

	}

	public void setFilePath(String filePath) {

		this.filePath = filePath;

	}

	public void setSAPFilePath(String SAPFilePath) {

		this.SAPFilePath = SAPFilePath;

	}

	public void setRecipesLists() {

		this.fixedRecipesList = this.fa.getRecipesList();

	}

	public void setMetersToCut(int metersToCut) {

		this.metersToCut = metersToCut;

	}

	public void setMachine(String machine) {

		this.machine = machine;
		this.setlblMachine(machine);

	}

	private void setlblMachine(String machineName) {

		if (machineName.equals("InnerLiner"))
			machineName = "Inner Liner";

		this.lblMachine.setText("( " + machineName + " )");

	}

	public void setAllViewChart(boolean allViewChart) {
		this.allViewChart = allViewChart;
		
		if(this.allViewChart)
			this.menuItChangeMetersToCut.setVisible(false);
		
	}

	public void hideUnusefulTables() {

		switch (this.machine) {

		case "Quadruplex":
			this.tablePerformanceDuplex.setVisible(false);
			this.tablePerformanceInnerLiner.setVisible(false);
			break;

		case "Duplex":
			this.tablePerformanceQuadruplex.setVisible(false);
			this.tablePerformanceInnerLiner.setVisible(false);
			break;

		case "InnerLiner":
			this.tablePerformanceQuadruplex.setVisible(false);
			this.tablePerformanceDuplex.setVisible(false);
			break;

		}

	}
	
	public void hideMenuItSaveAll(){
		this.menuItSaveAll.setVisible(false);
	}

	@FXML
	void initialize() {
		assert PirelliLogo != null : "fx:id=\"PirelliLogo\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert choiceBoxRecipe != null : "fx:id=\"choiceBoxRecipe\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert graph != null : "fx:id=\"graph\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert gridPanelCheckboxMeasureList != null : "fx:id=\"gridPanelCheckboxMeasureList\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert hBoxGraphView != null : "fx:id=\"hBoxGraphView\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert hBoxMeasureFilter != null : "fx:id=\"hBoxMeasureFilter\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert menuEdit != null : "fx:id=\"menuEdit\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert menuFile != null : "fx:id=\"menuFile\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert menuItExitFromFullScreen != null : "fx:id=\"menuItExitFromFullScreen\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert menuItSave != null : "fx:id=\"menuItSave\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert menuItSaveAll != null : "fx:id=\"menuItSaveAll\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert menuItSetFullScreen != null : "fx:id=\"menuItSetFullScreen\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert menuItSetGraphTitle != null : "fx:id=\"menuItSetGraphTitle\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert menuNamesAndLabels != null : "fx:id=\"menuNamesAndLabels\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert menuWindow != null : "fx:id=\"menuWindow\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert menuitClose != null : "fx:id=\"menuitClose\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert tabGraphView != null : "fx:id=\"tabGraphView\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert tabPanel != null : "fx:id=\"tabPanel\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert tabTableView != null : "fx:id=\"tabTableView\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert theRoot != null : "fx:id=\"theRoot\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert vBoxGraphView != null : "fx:id=\"vBoxGraphView\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";
		assert vBoxParameter != null : "fx:id=\"vBoxParameter\" was not injected: check your FXML file 'PASUITableAndGraphView.fxml'.";

	}

}
