package application;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class SceneController extends AnchorPane{
	@FXML
	private AnchorPane AnchorPane;
	@FXML
	private Tab notesTab;
	@FXML
	private TextField authorTB;
	@FXML
	private TextArea notesTB;
	@FXML
	private Tab solarTab;
	@FXML
	private TableView<SolarMonthly> solarTable;
	@FXML
	private TableColumn solarMonthColumn;
	@FXML
	private TableColumn solarGHIColumn;
	@FXML
	private TableColumn solarDNIColumn;
	@FXML
	private TableColumn solarClearnessColumn;
	@FXML
	private RadioButton enterMonthlyAveragesRadio;
	@FXML
	private ToggleGroup solarone;
	@FXML
	private RadioButton importHourlyDataRadio;
	@FXML
	private Button solarBrowseBtn;
	@FXML
	private TextField hourlyDataFileTB;
	@FXML
	private RadioButton solarNorthRadio;
	@FXML
	private ToggleGroup solartwo;
	@FXML
	private RadioButton solarSouthRadio;
	@FXML
	private RadioButton solarEastRadio;
	@FXML
	private ToggleGroup solarthree;
	@FXML
	private RadioButton solarWestRadio;
	@FXML
	private TextField solarLatTB;
	@FXML
	private TextField solarLonTB;
	@FXML
	private ComboBox solarTimeCB;
	@FXML
	private CheckBox solarDaylightSavingsCB;
	@FXML
	private DatePicker solarStartDP;
	@FXML
	private DatePicker solarEndDP;
	@FXML
	private LineChart solarGraph;
	@FXML
	private Tab ratesTab;
	@FXML
	private ComboBox ratesScheduleCB;
	@FXML
	private TextField ratesNameTB;
	@FXML
	private TextField ratesLoadFileTB;
	@FXML
	private Button ratesBrowse;
	@FXML
	private Button ratesLoadFileBtn;
	@FXML
	private RadioButton ratesNoNetRadio;
	@FXML
	private ToggleGroup netmetering;
	@FXML
	private RadioButton ratesMonthlyRadio;
	@FXML
	private RadioButton ratesAnnualRadio;
	@FXML
	private TextField ratesOverprodTB;
	@FXML
	private TableView<Rate> rateTable;
	@FXML
	private TableColumn rateColumn;
	@FXML
	private TableColumn priceColumn;
	@FXML
	private TableColumn feedInColumn;
	@FXML
	private TableColumn demandColumn;
	@FXML
	private ToggleButton ratesAllWeekBtn;
	@FXML
	private ToggleGroup ratesweek;
	@FXML
	private ToggleButton ratesWeekdayBtn;
	@FXML
	private ToggleButton ratesWeekendBtn;
	@FXML
	private GridPane rateGrid;
	@FXML
	private TextField ratesInterconTB;
	@FXML
	private Tab ratepayersTab;
	@FXML
	private ComboBox rpStrataCB;
	@FXML
	private ComboBox rpRateSchedule;
	@FXML
	private TextField rpNameTB;
	@FXML
	private TextField rpNoCustTB;
	@FXML
	private Pane rpSolarPane;
	@FXML
	private TextField rpSolarEOTB;
	@FXML
	private TextField rpSolarSlopeTB;
	@FXML
	private TextField rpSolarAzTB;
	@FXML
	private TextField rpSolarCapTB;
	@FXML
	private TextField rpCapFactorTB;
	@FXML
	private TextField rpSolarPVPenTB;
	@FXML
	private TextField rpSolarEOYearTB;
	@FXML
	private TextField rpSolarEODayTB;
	@FXML
	private TextField rpLoadDataTB;
	@FXML
	private TextField rpPeakLoadTB;
	@FXML
	private TextField rpAverageLoadTB;
	@FXML
	private TextField rpEUDayTB;
	@FXML
	private TextField rpEUYearTB;
	@FXML
	private TextField rpLoadFactorTB;
	@FXML
	private TextField rpDemandChargesTB;
	@FXML
	private TextField rpEnergyChargesTB;
	@FXML
	private TextField rpInterconChargesTB;
	@FXML
	private TextField rpTotalChargesTB;
	@FXML
	private TextField rpNetPurchasesTB;
	@FXML
	private TextField rpEnergySoldTB;
	@FXML
	private TextField rpEnergyPurchasedTB;
	@FXML
	private Pane rpInverterPane;
	@FXML
	private TextField rpInvertEnergyInTB;
	@FXML
	private TextField rpInvertEnergyOutTB;
	@FXML
	private TextField rpInvertLossesTB;
	@FXML
	private TextField rpInvertCapFactorTB;
	@FXML
	private TextField rpInvertCapTB;
	@FXML
	private TextField rpInvertEfficiencyTB;
	@FXML
	private Pane rpBatteryStoragePane;
	@FXML
	private TextField rpBSEnergyInTB;
	@FXML
	private TextField rpBSEnergyOutTB;
	@FXML
	private TextField rpBSLossesTB;
	@FXML
	private TextField rpBSAutonomyTB;
	@FXML
	private TextField rpBSCapTB;
	@FXML
	private TextField rpBSRoundEffTB;
	@FXML
	private TextField rpBSMaxCTB;
	@FXML
	private TextField rpBSMinSOCTB;
	@FXML
	private Pane rpDemandRespPane;
	@FXML
	private TextField rpDRDemandContTB;
	@FXML
	private TextField rpDRCapFactorTB;
	@FXML
	private TextField rpDRPercentContTB;
	@FXML
	private Pane rpElecVehiclePane;
	@FXML
	private TextField rpEVEInDayTB;
	@FXML
	private TextField rpEVEInYearTB;
	@FXML
	private TextField rpEVLossesTB;
	@FXML
	private TextField rpEVLoadPercentTB;
	@FXML
	private TextField rpEVBatteryCapTB;
	@FXML
	private TextField rpEVStartSOCTB;
	@FXML
	private TextField rpEVChargeEffTB;
	@FXML
	private ComboBox rpEVChargerCB;
	@FXML
	private TextField rpEVEndTimeTB;
	@FXML
	private ComboBox rpEVChargeStratCB;
	@FXML
	private TextField rpEVStartTimeTB;
	@FXML
	private TextField rpEVEndSOCTB;
	@FXML
	private TextField rpPeakGridTB;
	@FXML
	private TextField rpAvgGridTB;
	@FXML
	private TextField rpEUDGridTB;
	@FXML
	private TextField rpEUYGridTB;
	@FXML
	private TextField rpLoadGridTB;
	@FXML
	private Tab gridTab;
	@FXML
	private ComboBox gridStrataCB;
	@FXML
	private TextField gridPeakLoadTB;
	@FXML
	private TextField gridAvgLoadTB;
	@FXML
	private Label noCustLabel;
	@FXML
	private TextField gridEUseYearTB;
	@FXML
	private TextField gridEUseDayTB;
	@FXML
	private TextField gridLoadFactorTB;
	@FXML
	private Pane gridGraphPane;
	@FXML
	private CheckBox rpSolarCB;
	@FXML
	private CheckBox rpInvertCB;
	@FXML
	private CheckBox rpBSCB;
	@FXML
	private CheckBox rpEVCB;
	@FXML
	private CheckBox rpDRCB;
	@FXML
	private TableColumn colorColumn;
	@FXML
	private Label rateScheduleLabel;
	@FXML
	private CheckBox rpDRDeferCB;
	@FXML
	private Slider gridBeginSlider;
	@FXML
	private Slider gridEndSlider;
	@FXML
	private Label gridChartLabel;
	@FXML
	private GridPane checkBoxGrid;
	@FXML
	private TextField gridTotalChargesTB;
	@FXML
	private TextField gridInterconTB;
	@FXML
	private TextField gridEChargeTB;
	@FXML
	private TextField gridDChargeTB;
	@FXML
	private TextField gridEPurchasedTB;
	@FXML
	private TextField gridESoldTB;
	@FXML
	private TextField gridNetPurchasesTB;
	@FXML
	private ComboBox timeStepCB;

	private static final int GRID_ROWS = 24;
	private static final int GRID_COLUMNS = 12;
	private static final int GRID_PANES = 4;
	private boolean gridToggle,rpSolarToggle,rpInvertToggle,rpBSToggle,rpEVToggle,rpDRToggle;
	private int gridIndex,rateCount,ratesScheduleIndex,rpGroupIndex;
	private Pattern p;
	private Pane[][] panes;
	private String[][] series;
	private boolean[] gridChartCBBool;
	private ArrayList<String> colors = new ArrayList();
	private ObservableList<RateSchedule> rateSchedules;
	private ObservableList<String> rateTitles,rpGroupList;
	private ObservableList<RatepayerGroup> rpGroups;
	private String sessionFileName;
	private Main app;
	private Session session;
	private LineChart<Date,Number> gridChart;
	private int numStrataCalc;
	private int numStrataCalcMax;
	private int timestepMultiplier;
	private int TIMESTEP = 8760;

	public void init(Main app){
		this.app = app;
		gridIndex = 0;
		gridToggle = false;
		rpSolarToggle = true;
		rpInvertToggle = true;
		rpBSToggle = true;
		rpEVToggle = true;
		rpDRToggle = true;
		sessionFileName = "";
		p = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
		session = new Session();
		timestepMultiplier = 1;

		populateColors();
		initGrid();
		initSolar();
		initRates();
		initRatepayers();
	}
	private void populateColors(){
		colors.add("pink");
		colors.add("green");
		colors.add("blue");
		colors.add("purple");
		colors.add("red");
		colors.add("gold");
		colors.add("beige");
		colors.add("brown");
		colors.add("aquamarine");
		colors.add("grey");
		colors.add("maroon");
		colors.add("lime");
		colors.add("yellow");
		colors.add("forestgreen");
	}

	@SuppressWarnings("unchecked")
	private void initRatepayers(){
		rpGroups = FXCollections.observableArrayList();
		rpGroupList = FXCollections.observableArrayList();
		session.setRpGroups(rpGroups);
		rpStrataCB.setItems(rpGroupList);
		ObservableList<String> l1 = FXCollections.observableArrayList();
		l1.addAll("Level 1", "Level 2");
		rpEVChargerCB.setItems(l1);
		ObservableList<String> l2 = FXCollections.observableArrayList();
		l2.addAll("Max Charge Rate", "Min Charge Rate");
		rpEVChargeStratCB.setItems(l2);
	}
	@SuppressWarnings("unchecked")
	private void initRates(){
		rateSchedules = FXCollections.observableArrayList();
		session.setRateSchedules(rateSchedules);
		rateTitles = FXCollections.observableArrayList();

		ratesScheduleIndex = -1;
		ratesScheduleCB.setItems(rateTitles);
		rpRateSchedule.setItems(rateTitles);
		rateGrid.setStyle("-fx-background-color:burlywood;");
		rateColumn.setCellValueFactory(new PropertyValueFactory<Rate, String>("rate"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Rate, String>("price"));
		feedInColumn.setCellValueFactory(new PropertyValueFactory<Rate, String>("feedin"));
		demandColumn.setCellValueFactory(new PropertyValueFactory<Rate, String>("demand"));
		colorColumn.setCellValueFactory(new PropertyValueFactory<Rate, String>("color"));

		Callback<TableColumn<Rate, String>, TableCell<Rate, String>> cellFactory =
				new Callback<TableColumn<Rate, String>, TableCell<Rate, String>>() {
			public TableCell call(TableColumn p) {
				TableCell cell = new TableCell<Rate, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText(null);
							setStyle("");
						} else {
							setText(item);
							setStyle("-fx-background-color:"+item+";-fx-text-fill:"+item);

						}
					}
				};
				return cell;
			}
		};

		Callback<TableColumn<Rate, String>, TableCell<Rate, String>> priceFactory =
				new Callback<TableColumn<Rate, String>, TableCell<Rate, String>>() {
			public TableCell call(TableColumn p) {
				TableCell cell = new TableCell<Rate, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						if (item == null || empty) {
							setText(null);
							setStyle("");
						}else{
							setText(item);
						}
					}
				};

				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() > 1) {
							TableCell c = (TableCell) event.getSource();
							// Build the Dialog
							Dialog<String> dialog = new Dialog<>();
							dialog.setTitle("Set Price Data");
							ButtonType done = new ButtonType("Done", ButtonData.OK_DONE);
							dialog.getDialogPane().getButtonTypes().addAll(done, ButtonType.CANCEL);

							// Pane for holding all children in the dialog
							AnchorPane pane = new AnchorPane();
							pane.setPrefSize(300, 100);

							// Grid for radio buttons
							GridPane grid = new GridPane();
							grid.setHgap(10);
							grid.setVgap(10);
							grid.setPadding(new Insets(20, 90, 10, 10));

							// Radio buttons
							RadioButton single = new RadioButton();
							single.setText("Single Rate");
							RadioButton tiered = new RadioButton();
							tiered.setText("Tiered Rate");

							// Group for radio buttons
							ToggleGroup group = new ToggleGroup();
							single.setToggleGroup(group);
							tiered.setToggleGroup(group);
							single.setSelected(true);

							// Price grid for single price
							GridPane priceGrid = new GridPane();
							grid.setHgap(10);
							grid.setVgap(10);
							grid.setPadding(new Insets(20, 90, 10, 10));

							// Text field and label added to grid
							TextField price = new TextField(rateTable.getSelectionModel().getSelectedItem().getPrice(0).getValue());
							priceGrid.add(new Label("Price: "), 0, 0);
							priceGrid.add(price, 1, 0);

							// Table for tiered prices
							TableView<Price> tieredTable = new TableView<Price>();

							// Controlling the size of the table
							tieredTable.setEditable(true);
							tieredTable.setPrefSize(205, 180);

							// Set columns and ability to change value
							TableColumn priceCol = new TableColumn("Price");
							priceCol.setPrefWidth(100);
							priceCol.setCellValueFactory(new PropertyValueFactory<Price, String>("value"));
							priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
							priceCol.setOnEditCommit(
									new EventHandler<CellEditEvent<Price, String>>() {
										@Override
										public void handle(CellEditEvent<Price, String> t) {
											((Price) t.getTableView().getItems().get(
													t.getTablePosition().getRow())
													).setValue(t.getNewValue());
										}
									}
									);

							TableColumn threshCol = new TableColumn("Threshold");
							threshCol.setPrefWidth(100);
							threshCol.setCellValueFactory(new PropertyValueFactory<Price, String>("threshold"));
							threshCol.setCellFactory(TextFieldTableCell.forTableColumn());
							threshCol.setOnEditCommit(
									new EventHandler<CellEditEvent<Price, String>>() {
										@Override
										public void handle(CellEditEvent<Price, String> t) {
											((Price) t.getTableView().getItems().get(
													t.getTablePosition().getRow())
													).setThreshold(t.getNewValue());
										}
									}
									);

							// Add columns to table
							tieredTable.getColumns().addAll(priceCol, threshCol);

							Button add = new Button("Add");
							add.setOnAction(new EventHandler<ActionEvent>(){

								@Override
								public void handle(ActionEvent arg0) {
									rateTable.getSelectionModel().getSelectedItem().getPrices().add(new Price("0", "0"));
								}

							});
							Button remove = new Button("Remove");
							remove.setOnAction(new EventHandler<ActionEvent>(){
								@Override
								public void handle(ActionEvent arg0) {
									rateTable.getSelectionModel().getSelectedItem().getPrices()
									.remove(tieredTable.getSelectionModel().getSelectedIndex());
								}
							});

							// Listener for radio group that changes view
							group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
								public void changed(ObservableValue<? extends Toggle> ov,
										Toggle old_toggle, Toggle new_toggle) {
									if (group.getSelectedToggle() != null) {
										RadioButton rb = (RadioButton)group.getSelectedToggle();
										if(rb.getText().equals("Single Rate")){
											pane.getChildren().removeAll(tieredTable, add, remove);
											pane.getChildren().add(priceGrid);
											pane.setTopAnchor(priceGrid, 60.0);
											pane.setLeftAnchor(priceGrid, 40.0);
											pane.setMinHeight(50);
											dialog.setHeight(180);
										}else{
											pane.getChildren().remove(priceGrid);
											pane.getChildren().addAll(add, remove);
											pane.getChildren().add(tieredTable);
											pane.setTopAnchor(tieredTable, 60.0);
											pane.setLeftAnchor(tieredTable, 50.0);
											pane.setBottomAnchor(add, 10.0);
											pane.setLeftAnchor(add, 70.0);
											pane.setBottomAnchor(remove, 10.0);
											pane.setLeftAnchor(remove, 130.0);
											pane.setMinHeight(200);
											dialog.setHeight(375);
											ObservableList<Price> prices = rateTable.getSelectionModel().getSelectedItem().getPrices();
											tieredTable.setItems(prices);
										}
									}
								}
							});

							grid.add(single, 0, 0);
							grid.add(tiered, 1, 0);

							pane.setTopAnchor(grid, 0.0);
							pane.setLeftAnchor(grid, 50.0);
							pane.getChildren().add(grid);

							if(rateTable.getSelectionModel().getSelectedItem().isSinglePrice()){
								single.setSelected(true);
								pane.setTopAnchor(priceGrid, 60.0);
								pane.setLeftAnchor(priceGrid, 40.0);
								try{
									pane.getChildren().add(priceGrid);
								}catch(IllegalArgumentException e){}
								dialog.setHeight(200);
								price.setText(rateTable.getSelectionModel().getSelectedItem().getPrice(0).getValue());
							}else{
								tiered.setSelected(true);
								try{
									try{
										pane.getChildren().removeAll(tieredTable, add, remove, priceGrid);
									}catch(Exception e){
										e.printStackTrace();
									}
									pane.getChildren().addAll(add, remove);
									pane.getChildren().add(tieredTable);
								}catch(IllegalArgumentException e){
									e.printStackTrace();
								}
								pane.setTopAnchor(tieredTable, 60.0);
								pane.setLeftAnchor(tieredTable, 50.0);
								pane.setBottomAnchor(add, 10.0);
								pane.setLeftAnchor(add, 70.0);
								pane.setBottomAnchor(remove, 10.0);
								pane.setLeftAnchor(remove, 130.0);
								pane.setMinHeight(300);
								dialog.setHeight(400);
								ObservableList<Price> prices = rateTable.getSelectionModel().getSelectedItem().getPrices();
								tieredTable.setItems(prices);
							}

							dialog.getDialogPane().setContent(pane);

							dialog.setResultConverter(dialogButton -> {
								if (dialogButton == done) {
									if(single.isSelected()){
										rateTable.getSelectionModel().getSelectedItem().setSinglePrice(true);
										rateTable.getSelectionModel().getSelectedItem().setPrice(price.getText());
										return price.getText();
									}else{
										rateTable.getSelectionModel().getSelectedItem().setSinglePrice(false);
										return "Tiered..." + tieredTable.getItems().get(0).getValue();
									}
								}
								return null;
							});
							Optional<String> result = dialog.showAndWait();
							if (result != null){
								try{
									c.setText(result.get());
								}catch(NoSuchElementException e){
								}
							}

						}
					}
				});
				return cell;
			}
		};

		Callback<TableColumn<Rate, String>, TableCell<Rate, String>> feedinFactory =
				new Callback<TableColumn<Rate, String>, TableCell<Rate, String>>() {
			public TableCell call(TableColumn p) {
				TableCell cell = new TableCell<Rate, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						if (item == null || empty) {
							setText(null);
							setStyle("");
						}else{
							setText(item);
						}
					}
				};

				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if(rateSchedules.get(ratesScheduleIndex).getMeter() == 0){
							if (event.getClickCount() > 1) {
								TableCell c = (TableCell) event.getSource();
								// Build the Dialog
								Dialog<String> dialog = new Dialog<>();
								dialog.setTitle("Set Feedin Data");
								ButtonType done = new ButtonType("Done", ButtonData.OK_DONE);
								dialog.getDialogPane().getButtonTypes().addAll(done, ButtonType.CANCEL);

								// Pane for holding all children in the dialog
								AnchorPane pane = new AnchorPane();
								pane.setPrefSize(300, 100);

								// Grid for radio buttons
								GridPane grid = new GridPane();
								grid.setHgap(10);
								grid.setVgap(10);
								grid.setPadding(new Insets(20, 90, 10, 10));

								// Radio buttons
								RadioButton single = new RadioButton();
								single.setText("Single Rate");
								RadioButton tiered = new RadioButton();
								tiered.setText("Tiered Rate");

								// Group for radio buttons
								ToggleGroup group = new ToggleGroup();
								single.setToggleGroup(group);
								tiered.setToggleGroup(group);
								single.setSelected(true);

								// Feedin grid for single feedin
								GridPane feedinGrid = new GridPane();
								grid.setHgap(10);
								grid.setVgap(10);
								grid.setPadding(new Insets(20, 90, 10, 10));

								// Text field and label added to grid
								TextField feedin = new TextField(rateTable.getSelectionModel().getSelectedItem().getFeedin(0).getValue());
								feedinGrid.add(new Label("Feedin: "), 0, 0);
								feedinGrid.add(feedin, 1, 0);

								// Table for tiered feedins
								TableView<Feedin> tieredTable = new TableView<Feedin>();

								// Controlling the size of the table
								tieredTable.setEditable(true);
								tieredTable.setPrefSize(205, 180);

								// Set columns and ability to change value
								TableColumn feedinCol = new TableColumn("Feedin");
								feedinCol.setPrefWidth(100);
								feedinCol.setCellValueFactory(new PropertyValueFactory<Feedin, String>("value"));
								feedinCol.setCellFactory(TextFieldTableCell.forTableColumn());
								feedinCol.setOnEditCommit(
										new EventHandler<CellEditEvent<Feedin, String>>() {
											@Override
											public void handle(CellEditEvent<Feedin, String> t) {
												((Feedin) t.getTableView().getItems().get(
														t.getTablePosition().getRow())
														).setValue(t.getNewValue());
											}
										}
										);

								TableColumn threshCol = new TableColumn("Threshold");
								threshCol.setPrefWidth(100);
								threshCol.setCellValueFactory(new PropertyValueFactory<Feedin, String>("threshold"));
								threshCol.setCellFactory(TextFieldTableCell.forTableColumn());
								threshCol.setOnEditCommit(
										new EventHandler<CellEditEvent<Feedin, String>>() {
											@Override
											public void handle(CellEditEvent<Feedin, String> t) {
												((Feedin) t.getTableView().getItems().get(
														t.getTablePosition().getRow())
														).setThreshold(t.getNewValue());
											}
										}
										);

								// Add columns to table
								tieredTable.getColumns().addAll(feedinCol, threshCol);

								Button add = new Button("Add");
								add.setOnAction(new EventHandler<ActionEvent>(){

									@Override
									public void handle(ActionEvent arg0) {
										rateTable.getSelectionModel().getSelectedItem().getFeedins().add(new Feedin("0", "0"));
									}

								});
								Button remove = new Button("Remove");
								remove.setOnAction(new EventHandler<ActionEvent>(){
									@Override
									public void handle(ActionEvent arg0) {
										rateTable.getSelectionModel().getSelectedItem().getFeedins()
										.remove(tieredTable.getSelectionModel().getSelectedIndex());
									}
								});

								// Listener for radio group that changes view
								group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
									public void changed(ObservableValue<? extends Toggle> ov,
											Toggle old_toggle, Toggle new_toggle) {
										if (group.getSelectedToggle() != null) {
											RadioButton rb = (RadioButton)group.getSelectedToggle();
											if(rb.getText().equals("Single Rate")){
												pane.getChildren().removeAll(tieredTable, add, remove);
												pane.getChildren().add(feedinGrid);
												pane.setTopAnchor(feedinGrid, 60.0);
												pane.setLeftAnchor(feedinGrid, 40.0);
												pane.setMinHeight(50);
												dialog.setHeight(180);
											}else{
												pane.getChildren().remove(feedinGrid);
												pane.getChildren().addAll(add, remove);
												pane.getChildren().add(tieredTable);
												pane.setTopAnchor(tieredTable, 60.0);
												pane.setLeftAnchor(tieredTable, 50.0);
												pane.setBottomAnchor(add, 10.0);
												pane.setLeftAnchor(add, 70.0);
												pane.setBottomAnchor(remove, 10.0);
												pane.setLeftAnchor(remove, 130.0);
												pane.setMinHeight(200);
												dialog.setHeight(375);
												ObservableList<Feedin> feedins = rateTable.getSelectionModel().getSelectedItem().getFeedins();
												tieredTable.setItems(feedins);
											}
										}
									}
								});

								grid.add(single, 0, 0);
								grid.add(tiered, 1, 0);

								pane.setTopAnchor(grid, 0.0);
								pane.setLeftAnchor(grid, 50.0);
								pane.getChildren().add(grid);

								if(rateTable.getSelectionModel().getSelectedItem().isSingleFeedin()){
									single.setSelected(true);
									pane.setTopAnchor(feedinGrid, 60.0);
									pane.setLeftAnchor(feedinGrid, 40.0);
									try{
										pane.getChildren().add(feedinGrid);
									}catch(IllegalArgumentException e){}
									dialog.setHeight(200);
									feedin.setText(rateTable.getSelectionModel().getSelectedItem().getFeedin(0).getValue());
								}else{
									tiered.setSelected(true);
									try{
										try{
											pane.getChildren().removeAll(tieredTable, add, remove, feedinGrid);
										}catch(Exception e){
											e.printStackTrace();
										}
										pane.getChildren().addAll(add, remove);
										pane.getChildren().add(tieredTable);
									}catch(IllegalArgumentException e){
										e.printStackTrace();
									}
									pane.setTopAnchor(tieredTable, 60.0);
									pane.setLeftAnchor(tieredTable, 50.0);
									pane.setBottomAnchor(add, 10.0);
									pane.setLeftAnchor(add, 70.0);
									pane.setBottomAnchor(remove, 10.0);
									pane.setLeftAnchor(remove, 130.0);
									pane.setMinHeight(300);
									dialog.setHeight(400);
									ObservableList<Feedin> feedins = rateTable.getSelectionModel().getSelectedItem().getFeedins();
									tieredTable.setItems(feedins);
								}

								dialog.getDialogPane().setContent(pane);

								dialog.setResultConverter(dialogButton -> {
									if (dialogButton == done) {
										if(single.isSelected()){
											rateTable.getSelectionModel().getSelectedItem().setSingleFeedin(true);
											rateTable.getSelectionModel().getSelectedItem().setFeedin(feedin.getText());
											return feedin.getText();
										}else{
											rateTable.getSelectionModel().getSelectedItem().setSingleFeedin(false);
											return "Tiered..." + tieredTable.getItems().get(0).getValue();
										}
									}
									return null;
								});
								Optional<String> result = dialog.showAndWait();
								if (result != null){
									try{
										c.setText(result.get());
									}catch(NoSuchElementException e){

									}
								}

							}
						}
					}
				});
				return cell;
			}
		};

		Callback<TableColumn<Rate, String>, TableCell<Rate, String>> demandFactory =
				new Callback<TableColumn<Rate, String>, TableCell<Rate, String>>() {
			public TableCell call(TableColumn p) {
				TableCell cell = new TableCell<Rate, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						if (item == null || empty) {
							setText(null);
							setStyle("");
						}else{
							setText(item);
						}
					}
				};

				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() > 1) {
							TableCell c = (TableCell) event.getSource();
							// Build the Dialog
							Dialog<String> dialog = new Dialog<>();
							dialog.setTitle("Set Demand Data");
							ButtonType done = new ButtonType("Done", ButtonData.OK_DONE);
							dialog.getDialogPane().getButtonTypes().addAll(done, ButtonType.CANCEL);

							// Pane for holding all children in the dialog
							AnchorPane pane = new AnchorPane();
							pane.setPrefSize(300, 100);

							// Grid for radio buttons
							GridPane grid = new GridPane();
							grid.setHgap(10);
							grid.setVgap(10);
							grid.setPadding(new Insets(20, 90, 10, 10));

							// Radio buttons
							RadioButton single = new RadioButton();
							single.setText("Single Rate");
							RadioButton tiered = new RadioButton();
							tiered.setText("Tiered Rate");

							// Group for radio buttons
							ToggleGroup group = new ToggleGroup();
							single.setToggleGroup(group);
							tiered.setToggleGroup(group);
							single.setSelected(true);

							// Demand grid for single demand
							GridPane demandGrid = new GridPane();
							grid.setHgap(10);
							grid.setVgap(10);
							grid.setPadding(new Insets(20, 90, 10, 10));

							// Text field and label added to grid
							TextField demand = new TextField(rateTable.getSelectionModel().getSelectedItem().getDemand(0).getValue());
							demandGrid.add(new Label("Demand: "), 0, 0);
							demandGrid.add(demand, 1, 0);

							// Table for tiered demands
							TableView<Demand> tieredTable = new TableView<Demand>();

							// Controlling the size of the table
							tieredTable.setEditable(true);
							tieredTable.setPrefSize(205, 180);

							// Set columns and ability to change value
							TableColumn demandCol = new TableColumn("Demand");
							demandCol.setPrefWidth(100);
							demandCol.setCellValueFactory(new PropertyValueFactory<Demand, String>("value"));
							demandCol.setCellFactory(TextFieldTableCell.forTableColumn());
							demandCol.setOnEditCommit(
									new EventHandler<CellEditEvent<Demand, String>>() {
										@Override
										public void handle(CellEditEvent<Demand, String> t) {
											((Demand) t.getTableView().getItems().get(
													t.getTablePosition().getRow())
													).setValue(t.getNewValue());
										}
									}
									);

							TableColumn threshCol = new TableColumn("Threshold");
							threshCol.setPrefWidth(100);
							threshCol.setCellValueFactory(new PropertyValueFactory<Demand, String>("threshold"));
							threshCol.setCellFactory(TextFieldTableCell.forTableColumn());
							threshCol.setOnEditCommit(
									new EventHandler<CellEditEvent<Demand, String>>() {
										@Override
										public void handle(CellEditEvent<Demand, String> t) {
											((Demand) t.getTableView().getItems().get(
													t.getTablePosition().getRow())
													).setThreshold(t.getNewValue());
										}
									}
									);

							// Add columns to table
							tieredTable.getColumns().addAll(demandCol, threshCol);

							Button add = new Button("Add");
							add.setOnAction(new EventHandler<ActionEvent>(){

								@Override
								public void handle(ActionEvent arg0) {
									rateTable.getSelectionModel().getSelectedItem().getDemands().add(new Demand("0", "0"));
								}

							});
							Button remove = new Button("Remove");
							remove.setOnAction(new EventHandler<ActionEvent>(){
								@Override
								public void handle(ActionEvent arg0) {
									rateTable.getSelectionModel().getSelectedItem().getDemands()
									.remove(tieredTable.getSelectionModel().getSelectedIndex());
								}
							});

							// Listener for radio group that changes view
							group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
								public void changed(ObservableValue<? extends Toggle> ov,
										Toggle old_toggle, Toggle new_toggle) {
									if (group.getSelectedToggle() != null) {
										RadioButton rb = (RadioButton)group.getSelectedToggle();
										if(rb.getText().equals("Single Rate")){
											pane.getChildren().removeAll(tieredTable, add, remove);
											pane.getChildren().add(demandGrid);
											pane.setTopAnchor(demandGrid, 60.0);
											pane.setLeftAnchor(demandGrid, 40.0);
											pane.setMinHeight(50);
											dialog.setHeight(180);
										}else{
											pane.getChildren().remove(demandGrid);
											pane.getChildren().addAll(add, remove);
											pane.getChildren().add(tieredTable);
											pane.setTopAnchor(tieredTable, 60.0);
											pane.setLeftAnchor(tieredTable, 50.0);
											pane.setBottomAnchor(add, 10.0);
											pane.setLeftAnchor(add, 70.0);
											pane.setBottomAnchor(remove, 10.0);
											pane.setLeftAnchor(remove, 130.0);
											pane.setMinHeight(200);
											dialog.setHeight(375);
											ObservableList<Demand> demands = rateTable.getSelectionModel().getSelectedItem().getDemands();
											tieredTable.setItems(demands);
										}
									}
								}
							});

							grid.add(single, 0, 0);
							grid.add(tiered, 1, 0);

							pane.setTopAnchor(grid, 0.0);
							pane.setLeftAnchor(grid, 50.0);
							pane.getChildren().add(grid);

							if(rateTable.getSelectionModel().getSelectedItem().isSingleDemand()){
								single.setSelected(true);
								pane.setTopAnchor(demandGrid, 60.0);
								pane.setLeftAnchor(demandGrid, 40.0);
								try{
									pane.getChildren().add(demandGrid);
								}catch(IllegalArgumentException e){}
								dialog.setHeight(200);
								demand.setText(rateTable.getSelectionModel().getSelectedItem().getDemand(0).getValue());
							}else{
								tiered.setSelected(true);
								try{
									try{
										pane.getChildren().removeAll(tieredTable, add, remove, demandGrid);
									}catch(Exception e){
										e.printStackTrace();
									}
									pane.getChildren().addAll(add, remove);
									pane.getChildren().add(tieredTable);
								}catch(IllegalArgumentException e){
									e.printStackTrace();
								}
								pane.setTopAnchor(tieredTable, 60.0);
								pane.setLeftAnchor(tieredTable, 50.0);
								pane.setBottomAnchor(add, 10.0);
								pane.setLeftAnchor(add, 70.0);
								pane.setBottomAnchor(remove, 10.0);
								pane.setLeftAnchor(remove, 130.0);
								pane.setMinHeight(300);
								dialog.setHeight(400);
								ObservableList<Demand> demands = rateTable.getSelectionModel().getSelectedItem().getDemands();
								tieredTable.setItems(demands);
							}

							dialog.getDialogPane().setContent(pane);

							dialog.setResultConverter(dialogButton -> {
								if (dialogButton == done) {
									if(single.isSelected()){
										rateTable.getSelectionModel().getSelectedItem().setSingleDemand(true);
										rateTable.getSelectionModel().getSelectedItem().setDemand(demand.getText());
										return demand.getText();
									}else{
										rateTable.getSelectionModel().getSelectedItem().setSingleDemand(false);
										return "Tiered..." + tieredTable.getItems().get(0).getValue();
									}
								}
								return null;
							});
							Optional<String> result = dialog.showAndWait();
							if (result != null){
								try{
									c.setText(result.get());
								}catch(NoSuchElementException e){

								}
							}

						}
					}
				});
				return cell;
			}
		};

		rateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		rateColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<Rate, String>>() {
					@Override
					public void handle(CellEditEvent<Rate, String> t) {
						((Rate) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setRate(t.getNewValue());
					}
				}
				);
		priceColumn.setCellFactory(priceFactory);
		feedInColumn.setCellFactory(feedinFactory);
		demandColumn.setCellFactory(demandFactory);
		colorColumn.setCellFactory(cellFactory);
		resetPanes();
		/*
		String currentDir = System.getProperty("user.dir") + File.separator;
		File srp = new File(currentDir + "srp");
		if(srp.exists()){
			for(final File rs : srp.listFiles()){
				loadRate(new File(rs.getAbsolutePath() + File.separator + "rates.txt"));
			}
		}
		*/
	}

	private void resetPanes(){
		int count = 0;
		panes = new Pane[GRID_ROWS*GRID_COLUMNS][GRID_PANES];
		GridListener gl = new GridListener();
		for(int x = 0; x< GRID_ROWS; x++){
			for(int y = 0; y< GRID_COLUMNS; y++){
				Pane pane = new Pane();
				pane.setMinSize(40, 10);
				pane.setStyle("-fx-background-color:burlywood;-fx-border-color:black;");
				Pane pane1 = new Pane();
				pane1.setMinSize(29, 10);
				pane1.setMinSize(29, 10);
				pane1.setMaxWidth(29);
				pane1.setPrefWidth(29);
				pane1.setMinWidth(29);
				pane1.setStyle("-fx-background-color:burlywood;-fx-border-color:black;");
				pane1.setVisible(false);
				Pane pane2 = new Pane();
				pane2.setMinSize(11, 10);
				pane2.setMaxWidth(11);
				pane2.setPrefWidth(11);
				pane2.setMinWidth(11);
				pane2.setTranslateX(29);
				pane2.setStyle("-fx-background-color:burlywood;-fx-border-color:black;");
				pane2.setVisible(false);
				Pane pane3 = new Pane();
				pane3.setMinSize(40, 10);
				pane3.setMinSize(40, 10);
				pane3.setStyle("-fx-background-color:gray;-fx-border-color:black;");
				pane3.setVisible(false);
				panes[count][0] = pane;
				panes[count][1] = pane1;
				panes[count][2] = pane2;
				panes[count][3] = pane3;
				Button button = new Button();
				button.setId(Integer.toString(count));
				button.setMinSize(40, 10);
				button.setOpacity(0);
				button.setOnMouseReleased(gl);
				rateGrid.add(pane, y, x);
				rateGrid.add(pane1, y, x);
				rateGrid.add(pane2, y, x);
				rateGrid.add(pane3, y, x);
				rateGrid.add(button, y, x);
				count++;
			}
		}
	}
	private void initGrid(){
		ObservableList<String> timeSteps = FXCollections.observableArrayList();
		timeSteps.add("Hourly/Year (8760)");
		//timeSteps.add("30 Min/Year (17,520)");
		//timeSteps.add("15 Min/Year (35,040)");
		timeStepCB.setItems(timeSteps);
		timeStepCB.getSelectionModel().select(0);
	}

	@SuppressWarnings("unchecked")
	private void initSolar(){
		solarMonthColumn.setCellValueFactory(new PropertyValueFactory<SolarMonthly, String>("month"));
		solarGHIColumn.setCellValueFactory(new PropertyValueFactory<SolarMonthly, String>("GHI"));
		solarDNIColumn.setCellValueFactory(new PropertyValueFactory<SolarMonthly, String>("DNI"));
		solarClearnessColumn.setCellValueFactory(new PropertyValueFactory<SolarMonthly, String>("clearness"));

		solarGHIColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		solarGHIColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<SolarMonthly, String>>() {
					@Override
					public void handle(CellEditEvent<SolarMonthly, String> t) {
						((SolarMonthly) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setGHI(t.getNewValue());
					}
				}
				);
		solarDNIColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		solarDNIColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<SolarMonthly, String>>() {
					@Override
					public void handle(CellEditEvent<SolarMonthly, String> t) {
						((SolarMonthly) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setDNI(t.getNewValue());
					}
				}
				);
		solarClearnessColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		solarClearnessColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<SolarMonthly, String>>() {
					@Override
					public void handle(CellEditEvent<SolarMonthly, String> t) {
						((SolarMonthly) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setClearness(t.getNewValue());
					}
				}
				);

		ObservableList<SolarMonthly> m = FXCollections.observableArrayList();
		String[] arr1 = {"January", "February", "March", "April","May","June","July",
				"August","September","October","November","December"};
		String[] arr2 = {"3.35", "4.22", "5.57", "6.94","7.74","8.02","7.39",
				"6.74","6.04","4.84","3.76","3.07"};
		String[] arr3 = {"3.07", "3.35", "4.22", "5.57","6.94","7.74","6.74",
				"6.04","4.84","3.76","3.07","2.89"};
		String[] arr4 = {"0.618", "0.629", "0.660", "0.689","0.697","0.698","0.655",
				"0.647","0.673","0.671","0.660","0.612"};
		for(int x = 0; x < 12; x++){
			m.add(new SolarMonthly(arr1[x],arr2[x],arr3[x],arr4[x]));
		}
		session.setGHI(arr2);
		solarTable.setItems(m);
		solarTable.setFixedCellSize(39);

		ObservableList<XYChart.Series<String, Number>> s = FXCollections.observableArrayList();
		ObservableList<XYChart.Data<String, Number>> ss = FXCollections.observableArrayList();
		for(int x = 0; x < arr1.length; x++){
			ss.add(new XYChart.Data<String,Number>(arr1[x],Double.parseDouble(arr2[x])));
		}
		s.add(new XYChart.Series<>("GHI",ss));
		ss = FXCollections.observableArrayList();
		for(int x = 0; x < arr1.length; x++){
			ss.add(new XYChart.Data<String,Number>(arr1[x],Double.parseDouble(arr3[x])));
		}
		s.add(new XYChart.Series<>("DNI",ss));
		solarGraph.setData(s);
		solarGraph.getYAxis().setLabel("Radiation (kWh/m2/day)");
		solarGraph.getYAxis().setStyle("-fx-font-size:15;");
		solarGraph.getXAxis().setTickLabelRotation(45);
		solarGraph.getXAxis().setTickLabelFont(new Font("Arial", 11));

		ObservableList<String> timeZones = FXCollections.observableArrayList();
		timeZones.add("UTC-10:00 HAST");
		timeZones.add("UTC-9:00 AKST");
		timeZones.add("UTC-8:00 PST");
		timeZones.add("UTC-7:00 MST");
		timeZones.add("UTC-6:00 CST");
		timeZones.add("UTC-5:00 EST");
		solarTimeCB.setItems(timeZones);

		LocalDate date = LocalDate.of(2015, 1, 1);
		solarStartDP.setValue(date);
		date = LocalDate.of(2015, 12, 31);
		solarEndDP.setValue(date);
	}

	@FXML
	public void showRatepayersGraph() {
		try {
			FXMLLoader loader = new FXMLLoader();
			RatepayersController cont;
			InputStream in = Main.class.getResourceAsStream("ratepayers.fxml");
			loader.setBuilderFactory(new JavaFXBuilderFactory());
			loader.setLocation(Main.class.getResource("ratepayers.fxml"));
			AnchorPane page;
			try {
				page = (AnchorPane) loader.load(in);
				cont = (RatepayersController) loader.getController();

			}finally{
				in.close();
			}
			Stage stage = new Stage();
			stage.setTitle("Ratepayers");
			stage.setScene(new Scene(page, 1280, 720));
			cont.init(series);
			stage.show();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {

				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class GridListener implements EventHandler<Event>{

		@Override
		public void handle(Event event) {
			if(event.getEventType() == MouseEvent.MOUSE_RELEASED){
				Button button = (Button)event.getSource();
				if(gridToggle){
					Rate r = rateTable.getSelectionModel().getSelectedItem();
					if(r != null){
						int index = Integer.parseInt(button.getId());
						if(index < gridIndex){
							int temp = index;
							index = gridIndex;
							gridIndex = temp;
						}
						if (index%12 < gridIndex%12){
							int end = gridIndex%12;
							int start = index %12;
							index += end - start;
							gridIndex -= end - start;
						}
						int col1 = gridIndex%12;
						int col2 = index%12;
						panes[gridIndex][3].setVisible(false);
						for(int x = gridIndex; x<=index; x++){
							if(x%12 >= col1 && x%12 <= col2){
								if(ratesAllWeekBtn.isSelected()){
									rateSchedules.get(ratesScheduleIndex).setPaneColor(x,0,r.getColor());
									rateSchedules.get(ratesScheduleIndex).setSchedule(x, rateTable.getSelectionModel().getSelectedIndex(), rateTable.getSelectionModel().getSelectedIndex());
									panes[x][0].setStyle("-fx-background-color:"+r.getColor()+";-fx-border-color:black;");
									panes[x][1].setVisible(false);
									panes[x][2].setVisible(false);
								}
								if(ratesWeekdayBtn.isSelected()){
									rateSchedules.get(ratesScheduleIndex).setPaneColor(x,1,r.getColor());
									rateSchedules.get(ratesScheduleIndex).setSchedule(x, rateTable.getSelectionModel().getSelectedIndex(), -2);
									panes[x][1].setStyle("-fx-background-color:"+r.getColor()+";-fx-border-color:black;");
									panes[x][1].setVisible(true);
									panes[x][2].setVisible(true);
								}
								if(ratesWeekendBtn.isSelected()){
									rateSchedules.get(ratesScheduleIndex).setPaneColor(x,2,r.getColor());
									rateSchedules.get(ratesScheduleIndex).setSchedule(x, -2, rateTable.getSelectionModel().getSelectedIndex());
									panes[x][2].setStyle("-fx-background-color:"+r.getColor()+";-fx-border-color:black;");
									panes[x][1].setVisible(true);
									panes[x][2].setVisible(true);

								}
							}
						}
						gridToggle=false;
					}
				}else{
					Rate r = rateTable.getSelectionModel().getSelectedItem();
					if(r != null){
						int index = Integer.parseInt(button.getId());
						panes[index][3].setVisible(true);
						gridIndex = index;
						gridToggle = true;
					}
				}
			}

		}
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void aboutClicked(ActionEvent event) {
		// TODO: about stuff
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About SRP Rate Reviewer");
		alert.setHeaderText("ŠNathanJohnson2015");
		alert.setContentText("Build .4\nDeveloper: Derek Hamel");
		alert.show();
	}
	@FXML
	public void newClicked(ActionEvent event) {
		//TODO: save()? and clear()
		Alert dlg = new Alert(AlertType.CONFIRMATION);
		dlg.setTitle("Save?");
		dlg.setHeaderText("Do you want to save the current session?");
		dlg.setResizable(true);

		Optional<ButtonType> result = dlg.showAndWait();

		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			writeToFile();
		}else{
			clear();
		}
	}
	@FXML
	public void openClicked(ActionEvent event) {
		clear();
		FileChooser fc = new FileChooser();
		String currentDir = System.getProperty("user.dir") + File.separator;
		File file = new File(currentDir);
		fc.setInitialDirectory(file);
		file = fc.showOpenDialog(app.getStage());
		sessionFileName = file.getName();
		session = new Session();
		try{
			BufferedReader fr = new BufferedReader(new FileReader(file));
			String[] split;
			String input = fr.readLine();
			split = input.split(",");
			session.setAuthor(split[session.AUTHOR]);
			session.setNotes(split[session.NOTES]);
			session.setHourlyFile(split[session.INPUT_FILE]);
			if(Integer.parseInt(split[session.INPUT_FILE_CHECK])==1){
				session.setSolarFileChecked(true);
			}else{
				session.setSolarFileChecked(false);
			}
			String[] GHI = session.getGHI();
			for(int x = 0; x < 12; x++){
				GHI[x] = split[x+4];
			}
			session.setGHI(GHI);
			session.setLat(split[session.LAT]);
			session.setLon(split[session.LON]);
			if(Integer.parseInt(split[session.NORTH_SOUTH])==1){
				session.setNorth(true);
			}else{
				session.setNorth(false);
			}
			if(Integer.parseInt(split[session.EAST_WEST])==1){
				session.setEast(true);
			}else{
				session.setEast(false);
			}
			session.setTimezone(Integer.parseInt(split[session.TIMEZONE]));
			if(Integer.parseInt(split[session.DAYLIGHT_SAVINGS])==1){
				session.setDaySave(true);
			}else{
				session.setDaySave(false);
			}
			session.setSolarStart(Long.parseLong(split[session.SOLAR_START]));
			session.setSolarEnd(Long.parseLong(split[session.SOLAR_END]));
			while((input = fr.readLine()).charAt(0) != '|'){
				RateSchedule rs = new RateSchedule();
				if(input.matches("^:")){
					split = input.split(":");
					split = split[1].split(",");
				}else{
					split = input.split(",");
				}
				rs.setName(split[RateSchedule.NAME]);
				rs.setMeter(Integer.parseInt(split[RateSchedule.METER]));
				rs.setCredit(Double.parseDouble(split[RateSchedule.CREDIT]));
				rs.setCharge(Double.parseDouble(split[RateSchedule.CHARGE]));
				input = fr.readLine();
				split = input.split("\t");
				String[][] c = new String[GRID_ROWS * GRID_COLUMNS][GRID_PANES];
				for(int x = 0; x < c.length; x++){
					c[x] = split[x].split(",");
				}
				rs.setPaneColors(c);
				input = fr.readLine();
				split = input.split("\t");
				int[][] sch = new int[GRID_ROWS * GRID_COLUMNS][RateSchedule.GRID_INDICES];
				String[] vals;
				for(int x = 0; x < sch.length; x++){
					vals = split[x].split(",");
					for(int y = 0; y < sch[0].length; y++){
						sch[x][y] = Integer.parseInt(vals[y]);
					}
				}
				rs.setSchedule(sch);
				while((input = fr.readLine()).charAt(0) != '|'){
					Rate rate = new Rate();
					if(input.matches("^*")){
						split = input.split("*");
						split = split[1].split(",");
					}else{
						split = input.split(",");
					}
					rate.setRate(split[Rate.RATE]);
					rate.setColor(split[Rate.COLOR]);
					rate.setPrice(split[Rate.PRICE]);
					rate.setFeedin(split[Rate.FEEDIN]);
					rate.setDemand(split[Rate.DEMAND]);
					rate.setSinglePrice(Integer.parseInt(split[Rate.SINGLE_PRICE])==1);
					rate.setSingleFeedin(Integer.parseInt(split[Rate.SINGLE_FEEDIN])==1);
					rate.setSingleDemand(Integer.parseInt(split[Rate.SINGLE_DEMAND])==1);
					input = fr.readLine();
					split = input.split("\t");
					for(int x = 0; x < split.length; x++){
						vals = split[x].split(",");
						rate.setPrice(x, new Price(vals[0], vals[1]));
					}
					input = fr.readLine();
					split = input.split("\t");
					for(int x = 0; x < split.length; x++){
						vals = split[x].split(",");
						rate.setFeedin(x, new Feedin(vals[0], vals[1]));
					}
					input = fr.readLine();
					split = input.split("\t");
					for(int x = 0; x < split.length; x++){
						vals = split[x].split(",");
						rate.setDemand(x, new Demand(vals[0], vals[1]));
					}
					rs.addRate(rate);
				}
				session.getRateSchedules().add(rs);
			}
			while((input = fr.readLine()).charAt(0) != '|'){
				RatepayerGroup rpg = new RatepayerGroup();
				if(input.matches("^:")){
					split = input.split(":");
					split = split[1].split(",");
				}else{
					split = input.split(",");
				}
				rpg.setName(split[rpg.NAME]);
				rpg.setNum(split[rpg.NUMBER_CUST]);
				rpg.setRateSchedule(Integer.parseInt(split[rpg.RATE_SCHEDULE]));
				rpg.setLoadFile(split[rpg.LOAD_FILE]);
				rpg.setSolarChecked(split[rpg.SOLAR_CHECKED].charAt(0) == '1');
				rpg.setInverterChecked(split[rpg.INVERTER_CHECKED].charAt(0) == '1');
				rpg.setBatteryChecked(split[rpg.BATTERY_CHECKED].charAt(0) == '1');
				rpg.setEvChecked(split[rpg.EV_CHECKED].charAt(0) == '1');
				input = fr.readLine();
				split = input.split(",");
				for(int x = 0; x < split.length; x++){
					rpg.setSolarPVItem(x, Double.parseDouble(split[x]));
				}
				input = fr.readLine();
				split = input.split(",");
				for(int x = 0; x < split.length; x++){
					rpg.setInvertItem(x, Double.parseDouble(split[x]));
				}
				input = fr.readLine();
				split = input.split(",");
				for(int x = 0; x < split.length; x++){
					rpg.setBSItem(x, Double.parseDouble(split[x]));
				}
				input = fr.readLine();
				split = input.split(",");
				for(int x = 0; x < split.length; x++){
					rpg.setEVItem(x, Double.parseDouble(split[x]));
				}
				session.getRpGroups().add(rpg);
			}
			populate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@FXML
	public void saveClicked(ActionEvent event) {
		if(!(sessionFileName.length() > 0)){
			save();
		}else{
			writeToFile();
		}
	}
	@FXML
	public void saveAsClicked(ActionEvent event) {
		save();
	}
	@FXML
	public void closeClicked(ActionEvent event) {
		// TODO: save?
		System.exit(0);
	}
	// Event Listener on RadioButton[#enterMonthlyAveragesRadio].onAction
	@FXML
	public void monthlyAveragesSelected(ActionEvent event) {
		solarBrowseBtn.setDisable(true);
		hourlyDataFileTB.setDisable(true);
		solarTable.setDisable(false);
		session.setSolarFileChecked(true);
	}
	// Event Listener on RadioButton[#importHourlyDataRadio].onAction
	@FXML
	public void importHourlyDataSelected(ActionEvent event) {
		solarBrowseBtn.setDisable(false);
		hourlyDataFileTB.setDisable(false);
		solarTable.setDisable(true);
		session.setSolarFileChecked(true);
	}
	// Event Listener on Button.onAction
	@FXML
	public void hourlyDataFileButton(ActionEvent event) {
		FileChooser fc = new FileChooser();
		String currentDir = System.getProperty("user.dir") + File.separator;
		File file = new File(currentDir);
		fc.setInitialDirectory(file);
		file = fc.showOpenDialog(app.getStage());
		try{
			int multiplier = 0;
			switch(timeStepCB.getSelectionModel().getSelectedIndex()){
				case 0:{
					multiplier = 1;
					break;
				}
				case 1:{
					multiplier = 2;
					break;
				}
				case 2:{
					multiplier = 4;
					break;
				}
				default:{
					multiplier = 1;
					break;
				}
			}
			int lines = countFileLines(file);
			System.out.println(lines);
			if(lines/8760 == multiplier){
				hourlyDataFileTB.setText(file.getAbsolutePath());
				session.setHourlyFile(file.getAbsolutePath());
				session.setSolarLines(lines);
			}else{
				showErrorDialog("Solar Resource time step does not match time step on Grid tab");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void ratesNewClicked(ActionEvent event) {
		TextInputDialog dlg = new TextInputDialog();
		dlg.setTitle("New Schedule");
		dlg.setGraphic(null);
		dlg.setHeaderText("Enter the new rate schedule information.");
		dlg.setResizable(true);

		Label label1 = new Label("Name: ");
		TextField text1 = new TextField();
		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
		dlg.getDialogPane().setContent(grid);

		dlg.setResultConverter(new Callback<ButtonType, String>() {
			@Override
			public String call(ButtonType b) {
				if (b == ButtonType.OK) {
					rateTitles.add(text1.getText());
					RateSchedule rs = new RateSchedule();
					rs.setName(text1.getText());
					rateSchedules.add(rs);
				}
				return null;
			}
		});
		dlg.show();
	}
	// Event Listener on Button.onAction
	@FXML
	public void ratesCopyClicked(ActionEvent event) {
		TextInputDialog dlg = new TextInputDialog();
		dlg.setTitle("Copy Schedule");
		dlg.setGraphic(null);
		dlg.setHeaderText("Enter the new rate schedule information.");
		dlg.setResizable(true);

		Label label1 = new Label("Name: ");
		TextField text1 = new TextField();
		text1.setText(ratesScheduleCB.getSelectionModel().getSelectedItem().toString() + " - copy");
		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
		dlg.getDialogPane().setContent(grid);

		dlg.setResultConverter(new Callback<ButtonType, String>() {
			@Override
			public String call(ButtonType b) {
				if (b == ButtonType.OK) {
					rateTitles.add(text1.getText());
					// TODO : Rate Schedule Copy
					RateSchedule rs = new RateSchedule();
					rs.setName(text1.getText());
					rateSchedules.add(rs);
				}
				return null;
			}
		});
		dlg.show();
	}
	// Event Listener on Button.onAction
	@FXML
	public void ratesDeleteClicked(ActionEvent event) {
		String name = ratesScheduleCB.getSelectionModel().getSelectedItem().toString();
		Alert dlg = new Alert(AlertType.CONFIRMATION);
		dlg.setTitle("Copy Schedule");
		dlg.setHeaderText("Are you sure you want to delete: "+ name);
		dlg.setResizable(true);

		Optional<ButtonType> result = dlg.showAndWait();

		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			rateSchedules.remove(name);
			if(rateSchedules.size() > 0){
				ratesScheduleCB.getSelectionModel().select(0);
			}else{
				ratesScheduleCB.getSelectionModel().select(-1);
			}
		}
	}
	public void onRatesBrowseClicked(ActionEvent event){
		FileChooser fc = new FileChooser();
		String currentDir = System.getProperty("user.dir") + File.separator;
		File file = new File(currentDir);
		fc.setInitialDirectory(file);
		file = fc.showOpenDialog(app.getStage());
		ratesLoadFileTB.setText(file.getAbsolutePath());
	}
	public void onRatesLoadFileClicked(ActionEvent event){
		File file = new File(ratesLoadFileTB.getText());
		loadRate(file);
	}
	private void loadRate(File file){
		if(file.exists()){
			try{
				RateSchedule rs = new RateSchedule();
				file = new File(file.getParent() + File.separator + "monthly.txt");
				BufferedReader br = new BufferedReader(new FileReader(file));
				int[][] mon = new int[GRID_ROWS*GRID_COLUMNS][2];
				int count = 0;
				String input = "";
				String split[][] = new String[12][24];
				String[] vals;

				rs.setName(file.getParentFile().getName());
				try{
					while((input = br.readLine()) != null){
						split[count] = input.split(";");
						count++;
					}
					for(int x = 0; x < split.length; x++){
						for(int y = 0; y < split[0].length; y++){
							vals = split[x][y].split(",");
							int val1 = Integer.parseInt(vals[0]);
							int val2 = Integer.parseInt(vals[1]);
							rs.setSchedule(x+(y*12), val1, val2);
							if(val1 >= 0 && val2 >= 0){
								if(val1 == val2){
									rs.setPaneColor(x+(y*12), 0, colors.get(val1));
								}else{
									rs.setPaneColor(x+(y*12), 1, colors.get(val1));
									rs.setPaneColor(x+(y*12), 2, colors.get(val2));
								}
							}else if(val1 >=0){
								rs.setPaneColor(x+(y*12), 1, colors.get(val1));
							}else if(val2 >=0){
								rs.setPaneColor(x+(y*12), 2, colors.get(val2));
							}else{
								rs.setPaneColor(x+(y*12), 0, "burlywood");
							}
						}
					}
				}catch(NumberFormatException ex){
					ex.printStackTrace();
				}

				file = new File(file.getParent() + File.separator + "meter.txt");
				br = new BufferedReader(new FileReader(file));
				String[] met = new String[3];
				count = 0;
				input = "";

				while((input = br.readLine()) != null){
					met[count] = input;
					count++;
				}

				try{
					rs.setMeter(Integer.parseInt(met[0].split("\t")[1]));
					rs.setCredit(Double.parseDouble(met[1].split("\t")[1]));
					rs.setCharge(Double.parseDouble(met[2].split("\t")[1]));
				}catch(NumberFormatException ex){
					ex.printStackTrace();
				}

				file = new File(file.getParent() + File.separator + "rates.txt");
				br = new BufferedReader(new FileReader(file));
				ArrayList<String> rname = new ArrayList<String>();
				String[] rsplit;
				String[] tmpSplit;
				String[] tmpSplit1;
				ObservableList<Price> prices;
				ObservableList<Feedin> feedins;
				ObservableList<Demand> demands;
				input = "";
				Price tmpP;
				Feedin tmpF;
				Demand tmpD;
				Rate rate;

				while((input = br.readLine()) != null){
					rate = new Rate();
					prices = FXCollections.observableArrayList();
					feedins = FXCollections.observableArrayList();
					demands = FXCollections.observableArrayList();
					rsplit = input.split("\t");
					rate.setRate(rsplit[0]);
					rsplit = rsplit[1].split("%");
					tmpSplit = rsplit[0].split(";");
					for(int x = 0; x < tmpSplit.length; x++){
						tmpSplit1 = tmpSplit[x].split(",");
						tmpP = new Price(tmpSplit1[0],tmpSplit1[1]);
						prices.add(tmpP);
					}
					rate.setPrices(prices);
					tmpSplit = rsplit[1].split(";");
					for(int x = 0; x < tmpSplit.length; x++){
						tmpSplit1 = tmpSplit[x].split(",");
						tmpF = new Feedin(tmpSplit1[0],tmpSplit1[1]);
						feedins.add(tmpF);
					}
					rate.setFeedins(feedins);
					tmpSplit = rsplit[2].split(";");
					for(int x = 0; x < tmpSplit.length; x++){
						tmpSplit1 = tmpSplit[x].split(",");
						tmpD = new Demand(tmpSplit1[0],tmpSplit1[1]);
						demands.add(tmpD);
					}
					rate.setDemands(demands);
					try{
						rate.setColor(colors.get(rs.getRates().size()));
					}catch(Exception e){
						rate.setColor(colors.get(0));
					}					
					rate.setSinglePrice(prices.size() < 2);
					rate.setSingleFeedin(feedins.size() < 2);
					rate.setSingleDemand(demands.size() < 2);
					rs.addRate(rate);
				}
				rateSchedules.add(rs);
				rateTitles.add(file.getParentFile().getName());
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void ratesAddClicked(ActionEvent event) {
		RateSchedule rs = rateSchedules.get(ratesScheduleIndex);
		rs.addRate(new Rate("Enter Name","","","",colors.get(rs.getRates().size()), true, true,true));
	}
	// Event Listener on Button.onAction
	@FXML
	public void ratesRemoveClicked(ActionEvent event) {
		try{
			ObservableList<Rate> rateList = rateSchedules.get(ratesScheduleIndex).getRates();
			rateList.remove(rateTable.getSelectionModel().getSelectedItem());
			for(int x = 0; x < rateList.size(); x++){
				rateList.get(x).setColor(colors.get(x));
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	// Event Listener on Button.onAction
	@FXML
	public void rpNewClicked(ActionEvent event) {
		TextInputDialog dlg = new TextInputDialog();
		dlg.setTitle("Copy Schedule");
		dlg.setGraphic(null);
		dlg.setHeaderText("Enter the new ratepayer group information.");
		dlg.setResizable(true);

		Label label1 = new Label("Name: ");
		TextField text1 = new TextField();
		Label label2 = new Label("No. of Customers: ");
		TextField text2 = new TextField();
		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
		grid.add(label2, 1, 2);
		grid.add(text2, 2, 2);
		dlg.getDialogPane().setContent(grid);

		dlg.setResultConverter(new Callback<ButtonType, String>() {
			@Override
			public String call(ButtonType b) {
				if (b == ButtonType.OK) {
					RatepayerGroup rpg = new RatepayerGroup();
					rpg.setName(text1.getText());
					rpg.setNum(text2.getText());
					rpg.setRateSchedule(0);
					rpGroups.add(rpg);
					rpGroupList.add(text1.getText());
				}
				return null;
			}
		});
		dlg.show();
	}
	// Event Listener on Button.onAction
	@FXML
	public void rpCopyClicked(ActionEvent event) {
		TextInputDialog dlg = new TextInputDialog();
		dlg.setTitle("Copy Schedule");
		dlg.setGraphic(null);
		dlg.setHeaderText("Enter the new ratepayer group information.");
		dlg.setResizable(true);

		Label label1 = new Label("Name: ");
		TextField text1 = new TextField();
		Label label2 = new Label("No. of Customers: ");
		TextField text2 = new TextField();
		text1.setText(rpStrataCB.getSelectionModel().getSelectedItem().toString() + " - copy");
		text2.setText(rpGroups.get(rpStrataCB.getSelectionModel().getSelectedIndex()).getNum());
		GridPane grid = new GridPane();
		grid.add(label1, 1, 1);
		grid.add(text1, 2, 1);
		grid.add(label2, 1, 2);
		grid.add(text2, 2, 2);
		dlg.getDialogPane().setContent(grid);

		dlg.setResultConverter(new Callback<ButtonType, String>() {
			@Override
			public String call(ButtonType b) {
				if (b == ButtonType.OK) {
					RatepayerGroup rpg = new RatepayerGroup();
					rpg.setName(text1.getText());
					rpg.setNum(text2.getText());
					rpg.setRateSchedule(0);
					rpGroups.add(rpg);
					rpGroupList.add(text1.getText());
				}
				return null;
			}
		});
		dlg.show();
	}
	// Event Listener on Button.onAction
	@FXML
	public void rpDeleteClicked(ActionEvent event) {
		String name = rpStrataCB.getSelectionModel().getSelectedItem().toString();
		int index = rpStrataCB.getSelectionModel().getSelectedIndex();
		Alert dlg = new Alert(AlertType.CONFIRMATION);
		dlg.setTitle("Copy Group");
		dlg.setHeaderText("Are you sure you want to delete: "+ name);
		dlg.setResizable(true);

		Optional<ButtonType> result = dlg.showAndWait();

		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			rpGroups.remove(index);
			rpGroupList.remove(index);
			if(rpGroups.size() > 0){
				rpStrataCB.getSelectionModel().select(0);
			}else{
				rpStrataCB.getSelectionModel().select(-1);
			}
		}
	}
	public void onRPRateChange(ActionEvent event) {
		if(rpGroupIndex >= 0){
			rpGroups.get(rpGroupIndex).setRateSchedule(rpRateSchedule.getSelectionModel().getSelectedIndex());
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void rpLoadDataClicked(ActionEvent event) {
		FileChooser fc = new FileChooser();
		String currentDir = System.getProperty("user.dir") + File.separator;
		File file = new File(currentDir);
		fc.setInitialDirectory(file);
		file = fc.showOpenDialog(app.getStage());
		try{
			int multiplier = 0;
			switch(timeStepCB.getSelectionModel().getSelectedIndex()){
				case 0:{
					multiplier = 1;
					break;
				}
				case 1:{
					multiplier = 2;
					break;
				}
				case 2:{
					multiplier = 4;
					break;
				}
				default:{
					multiplier = 1;
					break;
				}
			}
			int lines = countFileLines(file);
			if(lines/8760 == multiplier){
				rpLoadDataTB.setText(file.getAbsolutePath());
				rpGroups.get(rpGroupIndex).setLoadFile(file.getAbsolutePath());
				rpGroups.get(rpGroupIndex).setLoadLines(lines);
			}else{
				showErrorDialog("Load time step does not match time step on Grid tab");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	// Event Listener on CheckBox.onAction
	@FXML
	public void rpBatteryStorageClicked(ActionEvent event) {
		if(rpGroups.get(rpGroupIndex).isBatteryChecked()){
			rpBatteryStoragePane.setDisable(true);
			RatepayerGroup rpg = rpGroups.get(rpGroupIndex);
			rpg.setBatteryChecked(false);
			rpg.setBSItem(0, -1);
			rpg.setBSItem(1, -1);
			rpg.setBSItem(2, -1);
			rpg.setBSItem(3, -1);
			rpBSCapTB.setText("");
			rpBSRoundEffTB.setText("");
			rpBSMinSOCTB.setText("");
			rpBSMaxCTB.setText("");
		}else{
			rpBatteryStoragePane.setDisable(false);
			rpGroups.get(rpGroupIndex).setBatteryChecked(true);
		}
	}
	// Event Listener on CheckBox.onAction
	@FXML
	public void rpElectricVehicleClicked(ActionEvent event) {
		if(rpGroups.get(rpGroupIndex).isEvChecked()){
			rpElecVehiclePane.setDisable(true);
			RatepayerGroup rpg = rpGroups.get(rpGroupIndex);
			rpg.setEvChecked(false);
			rpg.setEVItem(0, -1);
			rpg.setEVItem(1, 0);
			rpg.setEVItem(2, -1);
			rpg.setEVItem(3, -1);
			rpg.setEVItem(4, -1);
			rpg.setEVItem(5, -1);
			rpg.setEVItem(6, -1);
			rpg.setEVItem(7, 0);
			rpEVBatteryCapTB.setText("");
			rpEVChargerCB.getSelectionModel().select(0);
			rpEVChargeEffTB.setText("");
			rpEVStartSOCTB.setText("");
			rpEVEndSOCTB.setText("");
			rpEVStartTimeTB.setText("");
			rpEVEndTimeTB.setText("");
			rpEVChargeStratCB.getSelectionModel().select(0);
		}else{
			rpElecVehiclePane.setDisable(false);
			rpGroups.get(rpGroupIndex).setEvChecked(true);
		}
	}
	// Event Listener on CheckBox.onAction
	@FXML
	public void rpInvertCBClicked(ActionEvent event) {
		if(rpGroups.get(rpGroupIndex).isInverterChecked()){
			rpInverterPane.setDisable(true);
			rpGroups.get(rpGroupIndex).setInverterChecked(false);
			RatepayerGroup rpg = rpGroups.get(rpGroupIndex);
			rpg.setInverterChecked(false);
			rpg.setInvertItem(0, -1);
			rpg.setInvertItem(1, -1);
			rpInvertCapTB.setText("");
			rpInvertEfficiencyTB.setText("");
		}else{
			rpInverterPane.setDisable(false);
			rpGroups.get(rpGroupIndex).setInverterChecked(true);
		}
	}
	// Event Listener on CheckBox.onAction
	@FXML
	public void rpSolarCBClicked(ActionEvent event) {
		if(rpGroups.get(rpGroupIndex).isSolarChecked()){
			rpSolarPane.setDisable(true);
			rpGroups.get(rpGroupIndex).setSolarChecked(false);
			rpGroups.get(rpGroupIndex).setInverterChecked(false);
			RatepayerGroup rpg = rpGroups.get(rpGroupIndex);
			rpg.setInverterChecked(false);
			rpg.setSolarChecked(false);
			rpg.setInvertItem(0, -1);
			rpg.setInvertItem(1, -1);
			rpg.setSolarPVItem(0, -1);
			rpg.setSolarPVItem(1, -181);
			rpg.setSolarPVItem(2, -1);
			rpSolarCapTB.setText("");
			rpSolarAzTB.setText("");
			rpSolarSlopeTB.setText("");
			rpInvertCapTB.setText("");
			rpInvertEfficiencyTB.setText("");
		}else{
			rpSolarPane.setDisable(false);
			rpGroups.get(rpGroupIndex).setSolarChecked(true);
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void onRunClicked(ActionEvent event) {
		try {
			File file = new File("." + File.separator + "data");
			if(file.exists()){
				try{
					deleteFilesInDirectory(file);
				}catch(FileNotFoundException e){
					e.printStackTrace();
				}
			}
			file = new File("." + File.separator + "data" + File.separator + "Grid" + File.separator + "output");
			file.mkdirs();
			file = new File("." + File.separator + "data" + File.separator + "logger");
			file.mkdirs();
			String nl = System.lineSeparator();
			file = new File("." + File.separator + "data" + File.separator + "Location.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fr = new FileWriter(file);
			fr.write("Lat\t" + session.getLat());
			fr.write(nl);
			fr.write("Long\t" + session.getLon());
			fr.write(nl);
			fr.write("TZ\t" + (session.getTimezone()-10));
			fr.close();

			ObservableList<RateSchedule> rateSchedules = session.getRateSchedules();

			for(int x = 0; x < rateSchedules.size(); x++){
				RateSchedule rs = rateSchedules.get(x);
				file = new File("." + File.separator + "data" + File.separator 
							+ "Rates" + File.separator + rs.getName());
				if(!file.exists()){
					file.mkdirs();
				}
				file = new File("." + File.separator + "data" + File.separator 
							+ "Rates" + File.separator + rs.getName() + File.separator + "rates.txt");
				if(!file.exists()){
					file.createNewFile();
				}

				fr = new FileWriter(file);

				ObservableList<Rate> rateList = rs.getRates();
				for(int y = 0; y < rateList.size(); y++){
					fr.write(rateList.get(y).getRate() + "\t");
					ObservableList<Price> prices = rateList.get(y).getPrices();
					for(int z = 0; z < prices.size(); z++){
						fr.write(prices.get(z).getValue() + ","+ prices.get(z).getThreshold());
						if(z+1 < prices.size()){
							fr.write(";");
						}
					}
					fr.write("%");
					if(rs.getMeter() == 0){
						ObservableList<Feedin> feedins = rateList.get(y).getFeedins();
						for(int z = 0; z < feedins.size(); z++){
							fr.write(feedins.get(z).getValue() + ","+ feedins.get(z).getThreshold());
							if(z+1 < feedins.size()){
								fr.write(";");
							}
						}
					}else{
						fr.write("0,0");
					}
					fr.write("%");
					ObservableList<Demand> demands = rateList.get(y).getDemands();
					for(int z = 0; z < demands.size(); z++){
						fr.write(demands.get(z).getValue() + ","+ demands.get(z).getThreshold());
						if(z+1 < demands.size()){
							fr.write(";");
						}
					}
					if(y+1 != rateList.size()){
						fr.write("\n");
					}
				}
				fr.close();

				file = new File("." + File.separator + "data" + File.separator + "Rates" 
						+ File.separator + rs.getName() + File.separator + "monthly.txt");
				if(!file.exists()){
					file.createNewFile();
				}

				fr = new FileWriter(file);
				int[][] schedule = rs.getSchedule();

				for(int y = 0; y < 12; y++){
					for(int z = 0; z < 24; z++){
						fr.write(schedule[(z*12) + y][0] + "," + schedule[(z*12) + y][1]);
						if(z+1 != 24){
							fr.write(";");
						}
					}
					if(y+1 != 12){
						fr.write("\n");
					}
				}

				fr.close();

				file = new File("." + File.separator + "data" + File.separator + "Rates" 
						+ File.separator + rs.getName() + File.separator + "meter.txt");
				if(!file.exists()){
					file.createNewFile();
				}

				fr = new FileWriter(file);

				fr.write("Net Metering:\t"+rs.getMeter()+"\n");
				fr.write("Production Credit:\t"+rs.getCredit()+"\n");
				fr.write("Interconnection Charge:\t"+rs.getCharge()+"\n");

				fr.close();
			}
			numStrataCalc = 0;
			numStrataCalcMax = 0;

			ObservableList<RatepayerGroup> rpgs = session.getRpGroups();
			for(int x = 0; x < rpgs.size(); x++){
				RatepayerGroup rpg = rpgs.get(x);
				if(!(rpg.getRateSchedule() < 0 || rpg.getLoadFile().length() <= 0)){
					numStrataCalcMax++;
				}
			}
			for(int x = 0; x < rpgs.size(); x++){
				RatepayerGroup rpg = rpgs.get(x);

				if(!(rpg.getRateSchedule() < 0 || rpg.getLoadFile().length() <= 0)){

					file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
							+ File.separator + rpg.getName() + File.separator + "input");
					if(!file.exists()){
						file.mkdirs();
					}
					file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
							+ File.separator + rpg.getName() + File.separator + "output");
					if(!file.exists()){
						file.mkdirs();
					}

					file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
							+ File.separator + rpg.getName() + File.separator + "input" + File.separator + "rpg.txt");
					if(!file.exists()){
						file.createNewFile();
					}
					fr = new FileWriter(file);
					fr.write("Ratepayer Group:\t" + rpg.getName());
					fr.write(nl);
					fr.write("Rate Schedule:\t" + rateSchedules.get(rpg.getRateSchedule()).getName());
					fr.write(nl);
					fr.write("Number of Customers:\t" + rpg.getNum());
					fr.close();

					if(rpg.isSolarChecked()){
						file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
								+ File.separator + rpg.getName() + File.separator + "input" + File.separator + "SolarPV.txt");
						if(!file.exists()){
							file.createNewFile();
						}
						fr = new FileWriter(file);
						fr.write("Capacity\t" + rpg.getSolarPVItem(0));
						fr.write(nl);
						fr.write("Slope\t" + rpg.getSolarPVItem(2));
						fr.write(nl);
						fr.write("Azimuth\t" + rpg.getSolarPVItem(1));
						fr.close();
					}

					if(rpg.isInverterChecked()){
						file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
								+ File.separator + rpg.getName() + File.separator + "input" + File.separator + "Inverter.txt");
						if(!file.exists()){
							file.createNewFile();
						}
						fr = new FileWriter(file);
						fr.write("Capacity\t" + rpg.getInvertItem(0));
						fr.write(nl);
						fr.write("Efficiency\t" + rpg.getInvertItem(1));
						fr.close();
					}

					if(rpg.isBatteryChecked()){
						file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
								+ File.separator + rpg.getName() + File.separator + "input" + File.separator + "Battery.txt");
						if(!file.exists()){
							file.createNewFile();
						}
						fr = new FileWriter(file);
						fr.write("Capacity\t" + rpg.getBSItem(0));
						fr.write(nl);
						fr.write("Efficiency\t" + rpg.getBSItem(1));
						fr.write(nl);
						fr.write("MinSOC\t" + rpg.getBSItem(2));
						fr.write(nl);
						fr.write("MaxCRate\t" + rpg.getBSItem(3));
						fr.close();
					}

					//String[] start = Double.toString(rpg.getEVItem(5)).split(".");
					//String[] end = Double.toString(rpg.getEVItem(6)).split(".");

					if(rpg.isEvChecked()){
						file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
								+ File.separator + rpg.getName() + File.separator + "input" + File.separator + "ElectricVehicle.txt");
						if(!file.exists()){
							file.createNewFile();
						}
						fr = new FileWriter(file);
						fr.write("ChargerType\t" + ((int)rpg.getEVItem(1)));
						fr.write(nl);
						fr.write("ChargingStrategy\t" + ((int)rpg.getEVItem(7)));
						fr.write(nl);
						fr.write("Capacity\t" + rpg.getEVItem(0));
						fr.write(nl);
						fr.write("Efficiency\t" + rpg.getEVItem(2));
						fr.write(nl);
						fr.write("StartingSOC\t" + rpg.getEVItem(3));
						fr.write(nl);
						fr.write("EndingSOC\t" + rpg.getEVItem(4));
						fr.write(nl);
						fr.write("StartTime\t" + (int)rpg.getEVItem(5));
						fr.write(nl);
						fr.write("EndTime \t" + (int)rpg.getEVItem(6));
						fr.close();
					}

					File lfile = new File(rpg.getLoadFile());

					if(file.exists()){
						try{
							file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
									+ File.separator + rpg.getName() + File.separator + "input" + File.separator + "Load.txt");
							copyFile(lfile, file);
						}catch(IOException e){
							e.printStackTrace();
						}
					}
					switch(timeStepCB.getSelectionModel().getSelectedIndex()){
						case 0:{
							timestepMultiplier = 1;
							break;
						}
						case 1:{
							timestepMultiplier = 2;
							break;
						}
						case 2:{
							timestepMultiplier = 4;
							break;
						}
						default:{
							timestepMultiplier = 1;
							break;
						}
					}
					CalcProcess cp = new CalcProcess(File.separator + "Ratepayers" + File.separator + rpg.getName() + "," 
							+ rpLoadDataTB.getText() + "," 
							+ hourlyDataFileTB.getText() 
							+ ",101," 
							+ (rpg.isSolarChecked() ? 1 : 0) + "," 
							+ (rpg.isInverterChecked() ? 1 : 0) + "," 
							+ (rpg.isBatteryChecked() ? 1 : 0) + "," 
							+ (rpg.isEvChecked() ? 1 : 0) + "," 
							+ (TIMESTEP * timestepMultiplier)
							, this);
					cp.run();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	// Event Listener on Button.onAction
	@FXML
	public void onExportClicked(ActionEvent event) {
		//TODO
	}
	// Event Listener on ComboBox[#ratesScheduleCB].onAction
	@FXML
	public void onRateCBChange(ActionEvent event) {
		ratesNameTB.setText(ratesScheduleCB.getSelectionModel().getSelectedItem().toString());
		rateScheduleLabel.setText(ratesScheduleCB.getSelectionModel().getSelectedItem().toString());
		ratesScheduleIndex = ratesScheduleCB.getSelectionModel().getSelectedIndex();
		RateSchedule rs = rateSchedules.get(ratesScheduleIndex);
		switch(rs.getMeter()){
		case 0:{
			ratesNoNetRadio.setSelected(true);
			break;
		}
		case 1:{
			ratesMonthlyRadio.setSelected(true);
			break;
		}
		case 2:{
			ratesAnnualRadio.setSelected(true);
			break;
		}
		}
		rateTable.setItems(rs.getRates());
		/*
		for(int x = 0; x < GRID_COLUMNS*GRID_ROWS; x++){
			panes[x].setStyle("-fx-background-color:"+rs.getPaneColor(x)+";-fx-border-color:black;");
			switch(rs.getPanePlacement(x)){
			case 0:{
				panes[x].setMaxWidth(50);
				panes[x].setPrefWidth(40);
				panes[x].setMinWidth(40);
				panes[x].setTranslateX(0);
				break;
			}
			case 1:{
				panes[x].setMaxWidth(29);
				panes[x].setPrefWidth(29);
				panes[x].setMinWidth(29);
				panes[x].setTranslateX(0);
			}
			case 2:{
				panes[x].setMaxWidth(11);
				panes[x].setPrefWidth(11);
				panes[x].setMinWidth(11);
				panes[x].setTranslateX(30);
			}
			}
		}
		 */
		ratesOverprodTB.setText(Double.toString(rs.getCredit()));
		ratesInterconTB.setText(Double.toString(rs.getCharge()));
		if(rs.getMeter() == 0){
			ratesOverprodTB.setDisable(true);
			rateTable.getColumns().get(2).setEditable(true);
			rateTable.getColumns().get(2).getStyleClass().remove("celldisable");
		}else{
			ratesOverprodTB.setDisable(false);
			rateTable.getColumns().get(2).setEditable(false);
			rateTable.getColumns().get(2).getStyleClass().add("celldisable");
		}

		String[][] colors = rs.getPaneColors();
		int[][] schedule = rs.getSchedule();
		for(int x = 0; x < panes.length; x++){
			if(schedule[x][0] >= 0 && schedule[x][1] >= 0){
				if(schedule[x][0] == schedule[x][1]){
					panes[x][0].setStyle("-fx-background-color:"+colors[x][0]+";-fx-border-color:black;");
					panes[x][1].setVisible(false);
					panes[x][2].setVisible(false);
				}else{
					panes[x][1].setStyle("-fx-background-color:"+colors[x][1]+";-fx-border-color:black;");
					panes[x][2].setStyle("-fx-background-color:"+colors[x][2]+";-fx-border-color:black;");
					panes[x][1].setVisible(true);
					panes[x][2].setVisible(true);
				}
			}else if(schedule[x][0] >=0){
				panes[x][1].setStyle("-fx-background-color:"+colors[x][1]+";-fx-border-color:black;");
				panes[x][2].setStyle("-fx-background-color:burlywood;-fx-border-color:black;");
				panes[x][1].setVisible(true);
				panes[x][2].setVisible(true);
			}else if(schedule[x][1] >=0){
				panes[x][2].setStyle("-fx-background-color:"+colors[x][2]+";-fx-border-color:black;");
				panes[x][1].setStyle("-fx-background-color:burlywood;-fx-border-color:black;");
				panes[x][1].setVisible(true);
				panes[x][2].setVisible(true);
			}else{
				panes[x][0].setStyle("-fx-background-color:burlywood;-fx-border-color:black;");
				panes[x][1].setVisible(false);
				panes[x][2].setVisible(false);
			}
		}		
	}
	// Event Listener on TextField[#ratesNameTB].onKeyReleased
	@FXML
	public void onRateNameChanged(KeyEvent event) {
		if(ratesScheduleIndex >= 0){
			int pos = ratesNameTB.getCaretPosition();
			int tmp = ratesScheduleIndex;
			rateTitles.set(ratesScheduleIndex, ratesNameTB.getText());
			ratesScheduleIndex = tmp;
			ratesScheduleCB.getSelectionModel().select(ratesScheduleIndex);
			ratesNameTB.positionCaret(pos);
		}
	}
	// Event Listener on ComboBox[#rpStrataCB].onAction
	@FXML
	public void onRPCBChange(ActionEvent event) {
		rpGroupIndex = rpStrataCB.getSelectionModel().getSelectedIndex();
		RatepayerGroup rpg = rpGroups.get(rpStrataCB.getSelectionModel().getSelectedIndex());
		rpNameTB.setText(rpg.getName());
		rpNoCustTB.setText(rpg.getNum());
		rpRateSchedule.getSelectionModel().select(rpg.getRateSchedule());
		rpLoadDataTB.setText(rpg.getLoadFile());
		RatepayerGroup rg = session.getRpGroups().get(rpGroupIndex);

		if(rg.isSolarChecked()){
			if(rg.getSolarPVItem(0) < 0){
				rpSolarCapTB.setText("");
			}else{
				rpSolarCapTB.setText(Double.toString(rg.getSolarPVItem(0)));
			}
			if(rg.getSolarPVItem(1) < -180){
				rpSolarAzTB.setText("");
			}else{
				rpSolarAzTB.setText(Double.toString(rg.getSolarPVItem(1)));
			}
			if(rg.getSolarPVItem(2) < 0){
				rpSolarSlopeTB.setText("");
			}else{
				rpSolarSlopeTB.setText(Double.toString(rg.getSolarPVItem(2)));
			}
		}else{
			rpSolarCB.setSelected(false);
			rpSolarCapTB.setText("");
			rpSolarAzTB.setText("");
			rpSolarSlopeTB.setText("");
			rpInvertCapTB.setText("");
			rpInvertEfficiencyTB.setText("");
		}

		if(rg.isInverterChecked()){
			if(rg.getInvertItem(0) < 0){
				rpInvertCapTB.setText("");
			}else{
				rpInvertCapTB.setText(Double.toString(rg.getInvertItem(0)));
			}
			if(rg.getInvertItem(1) < 0){
				rpInvertEfficiencyTB.setText("");
			}else{
				rpInvertEfficiencyTB.setText(Double.toString(rg.getInvertItem(1)));
			}
		}else{
			rpInvertCB.setSelected(false);
			rpInvertCapTB.setText("");
			rpInvertEfficiencyTB.setText("");
		}

		if(rg.isBatteryChecked()){
			if(rg.getBSItem(0) < 0){
				rpBSCapTB.setText("");
			}else{
				rpBSCapTB.setText(Double.toString(rg.getBSItem(0)));
			}
			if(rg.getBSItem(1) < 0){
				rpBSRoundEffTB.setText("");
			}else{
				rpBSRoundEffTB.setText(Double.toString(rg.getBSItem(1)));
			}
			if(rg.getBSItem(2) < 0){
				rpBSMinSOCTB.setText("");
			}else{
				rpBSMinSOCTB.setText(Double.toString(rg.getBSItem(2)));
			}
			if(rg.getBSItem(3) < 0){
				rpBSMaxCTB.setText("");
			}else{
				rpBSMaxCTB.setText(Double.toString(rg.getBSItem(3)));
			}
		}else{
			rpBSCB.setSelected(false);
			rpBSCapTB.setText("");
			rpBSRoundEffTB.setText("");
			rpBSMinSOCTB.setText("");
			rpBSMaxCTB.setText("");
		}

		if(rg.isBatteryChecked()){
			if(rg.getEVItem(0) < 0){
				rpEVBatteryCapTB.setText("");
			}else{
				rpEVBatteryCapTB.setText(Double.toString(rg.getEVItem(0)));
			}
			if(rg.getEVItem(1) < 0){
				rpEVChargerCB.getSelectionModel().select(0);
			}else{
				rpEVChargerCB.getSelectionModel().select((int)rg.getEVItem(1));
			}
			if(rg.getEVItem(2) < 0){
				rpEVChargeEffTB.setText("");
			}else{
				rpEVChargeEffTB.setText(Double.toString(rg.getEVItem(2)));
			}
			if(rg.getEVItem(3) < 0){
				rpEVStartSOCTB.setText("");
			}else{
				rpEVStartSOCTB.setText(Double.toString(rg.getEVItem(3)));
			}
			if(rg.getEVItem(4) < 0){
				rpEVEndSOCTB.setText("");
			}else{
				rpEVEndSOCTB.setText(Double.toString(rg.getEVItem(4)));
			}
			if(rg.getEVItem(5)>0){
				String[] s = Double.toString(rg.getEVItem(5)).split("\\.");
				if(s[0].length() == 1){
					s[0] = "0" + s[0];
				}
				if(s[1].length() == 1){
					s[1] += "0";
				}
				rpEVStartTimeTB.setText(s[0] + ":" + s[1]);
			}else{
				rpEVStartTimeTB.setText("");
			}
			if(rg.getEVItem(6)>0){
				String[] e = Double.toString(rg.getEVItem(6)).split("\\.");
				if(e[0].length() == 1){
					e[0] = "0" + e[0];
				}
				if(e[1].length() == 1){
					e[1] += "0";
				}
				rpEVEndTimeTB.setText(e[0] + ":" + e[1]);
			}else{
				rpEVEndTimeTB.setText("");
			}
			if(rg.getEVItem(7) < 0){
				rpEVChargeStratCB.getSelectionModel().select(0);
			}else{
				rpEVChargeStratCB.getSelectionModel().select((int)rg.getEVItem(7));
			}
		}else{
			rpEVCB.setSelected(false);
			rpEVBatteryCapTB.setText("");
			rpEVChargerCB.getSelectionModel().select(0);
			rpEVChargeEffTB.setText("");
			rpEVStartSOCTB.setText("");
			rpEVEndSOCTB.setText("");
			rpEVStartTimeTB.setText("");
			rpEVEndTimeTB.setText("");
			rpEVChargeStratCB.getSelectionModel().select(0);
		}

		// Outputs
		double[] loadOut = rpg.getLoadOut();
		rpPeakLoadTB.setText(Double.toString(loadOut[0]));
		rpAverageLoadTB.setText(Double.toString(loadOut[1]));
		rpEUDayTB.setText(Double.toString(loadOut[2]));
		rpEUYearTB.setText(Double.toString(loadOut[3]));
		rpLoadFactorTB.setText(Double.toString(loadOut[4]));
		double[] solarOut = rpg.getSolarOut();
		rpSolarEOTB.setText(Double.toString(solarOut[0]));
		rpSolarEODayTB.setText(Double.toString(solarOut[1]));
		rpSolarEOYearTB.setText(Double.toString(solarOut[2]));
		rpCapFactorTB.setText(Double.toString(solarOut[3]));
		rpSolarPVPenTB.setText(Double.toString(solarOut[4]));
		double[] invertOut = rpg.getInvertOut();
		rpInvertEnergyInTB.setText(Double.toString(invertOut[0]));
		rpInvertEnergyOutTB.setText(Double.toString(invertOut[1]));
		rpInvertLossesTB.setText(Double.toString(invertOut[2]));
		rpInvertCapFactorTB.setText(Double.toString(invertOut[3]));
		double[] bsOut = rpg.getBsOut();
		rpBSEnergyInTB.setText(Double.toString(bsOut[0]));
		rpBSEnergyOutTB.setText(Double.toString(bsOut[1]));
		rpBSLossesTB.setText(Double.toString(bsOut[2]));
		rpBSAutonomyTB.setText(Double.toString(bsOut[3]));
		double[] evOut = rpg.getEvOut();
		rpEVEInDayTB.setText(Double.toString(evOut[0]));
		rpEVEInYearTB.setText(Double.toString(evOut[1]));
		rpEVLossesTB.setText(Double.toString(evOut[2]));
		rpEVLoadPercentTB.setText(Double.toString(evOut[3]));
		double[] interconOut = rpg.getInterconOut();
		rpEnergyPurchasedTB.setText(Double.toString(interconOut[0]));
		rpEnergySoldTB.setText(Double.toString(interconOut[1]));
		rpNetPurchasesTB.setText(Double.toString(interconOut[2]));
		rpPeakGridTB.setText(Double.toString(interconOut[3]));
		rpAvgGridTB.setText(Double.toString(interconOut[4]));
		rpEUDGridTB.setText(Double.toString(interconOut[5]));
		rpEUYGridTB.setText(Double.toString(interconOut[6]));
		rpLoadGridTB.setText(Double.toString(interconOut[7]));
		rpTotalChargesTB.setText(Double.toString(interconOut[8]));
		rpEnergyChargesTB.setText(Double.toString(interconOut[9]));
		rpDemandChargesTB.setText(Double.toString(interconOut[10]));
		rpInterconChargesTB.setText(Double.toString(interconOut[11]));		
	}
	// Event Listener on TextField[#rpNameTB].onKeyReleased
	@FXML
	public void onRPNameChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			int pos = rpNameTB.getCaretPosition();
			int tmp = rpGroupIndex;
			RatepayerGroup rpg = rpGroups.get(rpGroupIndex);
			rpg.setName(rpNameTB.getText());
			rpGroupList.set(rpGroupIndex, rpNameTB.getText());
			rpGroupIndex = tmp;
			rpStrataCB.getSelectionModel().select(rpGroupIndex);
			rpNameTB.positionCaret(pos);
		}
	}
	@FXML
	public void onRPNumChanged(KeyEvent event) {
		RatepayerGroup rpg = rpGroups.get(rpGroupIndex);
		rpg.setNum(rpNoCustTB.getText());
	}
	@FXML
	public void onNoNetChange(ActionEvent event) {
		rateSchedules.get(ratesScheduleIndex).setMeter(0);
		ratesOverprodTB.setText("0.0");
		ratesOverprodTB.setDisable(true);
		rateTable.getColumns().get(2).setEditable(true);
		rateTable.getColumns().get(2).getStyleClass().remove("celldisable");
	}
	@FXML
	public void onMonthlyChange(ActionEvent event) {
		rateSchedules.get(ratesScheduleIndex).setMeter(1);
		ratesOverprodTB.setText(Double.toString(rateSchedules.get(ratesScheduleIndex).getCredit()));
		ratesOverprodTB.setDisable(false);
		rateTable.getColumns().get(2).setEditable(false);
		rateTable.getColumns().get(2).getStyleClass().add("celldisable");
	}
	@FXML
	public void onAnnualChange(ActionEvent event) {
		rateSchedules.get(ratesScheduleIndex).setMeter(2);
		ratesOverprodTB.setText(Double.toString(rateSchedules.get(ratesScheduleIndex).getCredit()));
		ratesOverprodTB.setDisable(false);
		rateTable.getColumns().get(2).setEditable(false);
		rateTable.getColumns().get(2).getStyleClass().add("celldisable");
	}
	// Event Listener on ComboBox[#gridStrataCB].onAction
	@FXML
	public void onGridSummaryCBChange(ActionEvent event) {
		int index = gridStrataCB.getSelectionModel().getSelectedIndex();
		if(index > 0){
			checkBoxGrid.getChildren().clear();
			index--;
			RatepayerGroup rpg = rpGroups.get(index);
			noCustLabel.setText(rpg.getNum());
			double[] gridOut = rpg.getGridOut();
			gridEPurchasedTB.setText(Double.toString(gridOut[0]));
			gridESoldTB.setText(Double.toString(gridOut[1]));
			gridNetPurchasesTB.setText(Double.toString(gridOut[2]));
			gridPeakLoadTB.setText(Double.toString(gridOut[3]));
			gridAvgLoadTB.setText(Double.toString(gridOut[4]));
			gridEUseDayTB.setText(Double.toString(gridOut[5]));
			gridEUseYearTB.setText(Double.toString(gridOut[6]));
			gridLoadFactorTB.setText(Double.toString(gridOut[7]));
			gridTotalChargesTB.setText(Double.toString(gridOut[8]));
			gridEChargeTB.setText(Double.toString(gridOut[9]));
			gridDChargeTB.setText(Double.toString(gridOut[10]));
			gridInterconTB.setText(Double.toString(gridOut[11]));
			loadTimeSeries(index+1);
		}else{
			checkBoxGrid.getChildren().clear();
			Grid grid = session.getGrid();
			gridEPurchasedTB.setText(Double.toString(grid.getEnergyPurchased()));
			gridESoldTB.setText(Double.toString(grid.getEnergySold()));
			gridNetPurchasesTB.setText(Double.toString(grid.getNetPurchases()));
			gridPeakLoadTB.setText(Double.toString(grid.getPeakLoad()));
			gridAvgLoadTB.setText(Double.toString(grid.getAverageLoad()));
			gridEUseDayTB.setText(Double.toString(grid.getEnergyUseDay()));
			gridEUseYearTB.setText(Double.toString(grid.getEnergyUseYear()));
			gridLoadFactorTB.setText(Double.toString(grid.getLoadFactor()));
			gridTotalChargesTB.setText(Double.toString(grid.getTotalCharges()));
			gridEChargeTB.setText(Double.toString(grid.getEnergyCharges()));
			gridDChargeTB.setText(Double.toString(grid.getDemandCharges()));
			gridInterconTB.setText(Double.toString(grid.getInterconCharges()));
			loadTimeSeries(index);
		}
	}
	@FXML
	public void onCreditChanged(KeyEvent event) {
		String text = ratesOverprodTB.getText();
		if(ratesScheduleIndex >=0 ){
			try{
				rateSchedules.get(ratesScheduleIndex).setCredit(Double.parseDouble(text));
				ratesOverprodTB.setStyle("-fx-text-fill:black;");
			}catch(Exception e){
				if(text.length()>0){
					ratesOverprodTB.setStyle("-fx-text-fill:red;");
				}
			}
		}
	}
	@FXML
	public void onChargeChanged(KeyEvent event) {
		String text = ratesInterconTB.getText();
		if(ratesScheduleIndex >=0 || !(text.length()>0)){
			try{
				rateSchedules.get(ratesScheduleIndex).setCharge(Double.parseDouble(text));
				ratesInterconTB.setStyle("-fx-text-fill:black;");
			}catch(Exception e){
				if(text.length()>0){
					ratesInterconTB.setStyle("-fx-text-fill:red;");
				}
			}
		}
	}
	private void showInvalidInputDialog(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText("Your input was invalid.");
		alert.show();
	}
	private void showInvalidInputDialog(String msg){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText("Your input was invalid.");
		alert.setContentText(msg);
		alert.show();
	}
	private void showErrorDialog(String msg){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An Error Occurred");
		alert.setContentText(msg);
		alert.show();
	}
	private void save(){
		FileChooser fc = new FileChooser();
		String currentDir = System.getProperty("user.dir") + File.separator;
		File file = new File(currentDir);
		fc.setInitialDirectory(file);
		fc.setInitialFileName(sessionFileName);
		file = fc.showSaveDialog(app.getStage());
		sessionFileName = file.getName();
		writeToFile();
	}
	private void clear(){
		authorTB.setText("");
		notesTB.setText("");
		enterMonthlyAveragesRadio.setSelected(false);
		importHourlyDataRadio.setSelected(false);
		solarBrowseBtn.setDisable(true);
		hourlyDataFileTB.setDisable(true);
		hourlyDataFileTB.setText("");
		ObservableList<SolarMonthly> sl = solarTable.getItems();
		ObservableList<SolarMonthly> nl = FXCollections.observableArrayList();
		for(int x = 0; x < sl.size(); x++){
			sl.get(x).setGHI("0.0");
			sl.get(x).setDNI("0.0");
			sl.get(x).setClearness("0.0");
			nl.add(sl.get(x));
		}
		sl.removeAll(sl);
		solarTable.setItems(nl);
		solarLatTB.setText("");
		solarLonTB.setText("");
		solarTimeCB.getSelectionModel().select(-1);
		solarStartDP.setValue(null);
		solarEndDP.setValue(null);
		ObservableList<String> rs = ratesScheduleCB.getItems();
		rs.removeAll(rs);
		ratesNameTB.setText("");
		ratesOverprodTB.setText("");
		ratesInterconTB.setText("");
		ObservableList<Rate> r = rateTable.getItems();
		r.removeAll(r);
		ObservableList<String> rpgs = rpStrataCB.getItems();
		rpgs.removeAll(rpgs);
		rpNameTB.setText("");
		rpNoCustTB.setText("");
		rpLoadDataTB.setText("");
		rpSolarCapTB.setText("");
		rpSolarAzTB.setText("");
		rpSolarSlopeTB.setText("");
		rpPeakLoadTB.setText("");
		rpAverageLoadTB.setText("");
		rpEUDayTB.setText("");
		rpEUYearTB.setText("");
		rpLoadFactorTB.setText("");
		rpDemandChargesTB.setText("");
		rpEnergyChargesTB.setText("");
		rpInterconChargesTB.setText("");
		rpTotalChargesTB.setText("");
		rpNetPurchasesTB.setText("");
		rpEnergySoldTB.setText("");
		rpEnergyPurchasedTB.setText("");
		rpInvertEnergyInTB.setText("");
		rpInvertEnergyOutTB.setText("");
		rpInvertLossesTB.setText("");
		rpInvertCapFactorTB.setText("");
		rpInvertCapTB.setText("");
		rpInvertEfficiencyTB.setText("");
		rpBSEnergyInTB.setText("");
		rpBSEnergyOutTB.setText("");
		rpBSLossesTB.setText("");
		rpBSAutonomyTB.setText("");
		rpBSCapTB.setText("");
		rpBSRoundEffTB.setText("");
		rpBSMaxCTB.setText("");
		rpBSMinSOCTB.setText("");
		rpEVEInDayTB.setText("");
		rpEVEInYearTB.setText("");
		rpEVLossesTB.setText("");
		rpEVLoadPercentTB.setText("");
		rpEVBatteryCapTB.setText("");
		rpEVStartSOCTB.setText("");
		rpEVChargeEffTB.setText("");
		rpEVEndTimeTB.setText("");
		rpEVStartTimeTB.setText("");
		rpEVEndSOCTB.setText("");
		gridPeakLoadTB.setText("");
		gridAvgLoadTB.setText("");
		noCustLabel.setText("");
		gridEUseYearTB.setText("");
		gridEUseDayTB.setText("");
		gridLoadFactorTB.setText("");
	}
	private void populate(){
		authorTB.setText(session.getAuthor());
		notesTB.setText(session.getNotes());
		if(session.isSolarFileChecked()){
			importHourlyDataRadio.setSelected(true);
		}else{
			enterMonthlyAveragesRadio.setSelected(true);
		}
		hourlyDataFileTB.setText(session.getHourlyFile());
		ObservableList<SolarMonthly> sl = solarTable.getItems();
		ObservableList<SolarMonthly> nl = FXCollections.observableArrayList();
		String[] ghi = session.getGHI();
		for(int x = 0; x < sl.size(); x++){
			sl.get(x).setGHI(ghi[x]);
			nl.add(sl.get(x));
		}
		sl.removeAll(sl);
		solarTable.setItems(nl);
		solarLatTB.setText(session.getLat());
		solarLonTB.setText(session.getLon());
		if(session.isNorth()){
			solarNorthRadio.setSelected(true);
		}else{
			solarSouthRadio.setSelected(true);
		}
		if(session.isEast()){
			solarEastRadio.setSelected(true);
		}else{
			solarWestRadio.setSelected(true);
		}
		solarTimeCB.getSelectionModel().select(session.getTimezone());
		if(session.isDaySave()){
			solarDaylightSavingsCB.setSelected(true);
		}else{
			solarDaylightSavingsCB.setSelected(false);
		}
		ObservableList<RateSchedule> rs = session.getRateSchedules();
		rateSchedules = rs;
		ObservableList<String> rsList = ratesScheduleCB.getItems();
		for(int x = 0; x < rs.size(); x++){
			rsList.add(rs.get(x).getName());
		}
		ObservableList<RatepayerGroup> rpgs = session.getRpGroups();
		rpGroups = session.getRpGroups();
		ObservableList<String> rpgList = rpStrataCB.getItems();
		for(int x = 0; x < rpgs.size(); x++){
			rpgList.add(rpgs.get(x).getName());
		}
	}
	private void writeToFile(){
		File file = new File(System.getProperty("user.dir")+File.separator+sessionFileName);
		ObservableList<RateSchedule> rs = session.getRateSchedules();
		ObservableList<RatepayerGroup> rp = session.getRpGroups();
		try {
			FileWriter fr = new FileWriter(file);
			fr.write(session.toString());
			fr.write("\n");
			for(int x = 0; x < rs.size(); x++){
				fr.write(rs.get(x).toString());
				fr.write("\n|\n");
			}
			fr.write("|\n");
			for(int x = 0; x < rp.size(); x++){
				fr.write(rp.get(x).toString());
				fr.write("\n");
			}
			fr.write("|");
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void onSolarCapChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpSolarCapTB.getText().length() > 0){
					double value = Double.parseDouble(rpSolarCapTB.getText());
					if(value < 0){
						rpSolarCapTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setSolarPVItem(0, value);
						rpSolarCapTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpSolarCapTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onSolarAziChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpSolarAzTB.getText().length() > 0){
					double value = Double.parseDouble(rpSolarAzTB.getText());
					if(value < -180 || value > 180){
						rpSolarAzTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setSolarPVItem(1, value);
						rpSolarAzTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpSolarAzTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onSolarSlopeChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpSolarSlopeTB.getText().length() > 0){
					double value = Double.parseDouble(rpSolarSlopeTB.getText());
					if(value < 0 || value > 90){
						rpSolarAzTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setSolarPVItem(2, value);
						rpSolarAzTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpSolarAzTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onInvertCapChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpInvertCapTB.getText().length() > 0){
					double value = Double.parseDouble(rpInvertCapTB.getText());
					if(value < 0){
						rpInvertCapTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setInvertItem(0, value);
						rpInvertCapTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpInvertCapTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onInvertEffChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpInvertEfficiencyTB.getText().length() > 0){
					double value = Double.parseDouble(rpInvertEfficiencyTB.getText());
					if(value < 0 || value > 100){
						rpInvertEfficiencyTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setInvertItem(1, value);
						rpInvertEfficiencyTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpInvertEfficiencyTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onBSCapChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpBSCapTB.getText().length() > 0){
					double value = Double.parseDouble(rpBSCapTB.getText());
					if(value < 0){
						rpBSCapTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setBSItem(0, value);
						rpBSCapTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpBSCapTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onBSRoundChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpBSRoundEffTB.getText().length() > 0){
					double value = Double.parseDouble(rpBSRoundEffTB.getText());
					if(value < 0 || value > 100){
						rpBSRoundEffTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setBSItem(1, value);
						rpBSRoundEffTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpBSRoundEffTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onBSMinSOCChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpBSMinSOCTB.getText().length() > 0){
					double value = Double.parseDouble(rpBSMinSOCTB.getText());
					if(value < 0 || value > 100){
						rpBSMinSOCTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setBSItem(2, value);
						rpBSMinSOCTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpBSMinSOCTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onBSMaxCChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpBSMaxCTB.getText().length() > 0){
					double value = Double.parseDouble(rpBSMaxCTB.getText());
					if(value <= 0){
						rpBSMaxCTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setBSItem(3, value);
						rpBSMaxCTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpBSMaxCTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onEVBattChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpEVBatteryCapTB.getText().length() > 0){
					double value = Double.parseDouble(rpEVBatteryCapTB.getText());
					if(value < 0){
						rpEVBatteryCapTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setEVItem(0, value);
						rpEVBatteryCapTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpEVBatteryCapTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onEVChargerCBChanged(ActionEvent event){
		if(rpGroupIndex >= 0){
			session.getRpGroups().get(rpGroupIndex).setEVItem(1, rpEVChargerCB.getSelectionModel().getSelectedIndex());
		}
	}
	@FXML
	public void onEVChargeEffChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpEVChargeEffTB.getText().length() > 0){
					double value = Double.parseDouble(rpEVChargeEffTB.getText());
					if(value < 0 || value > 100){
						rpEVChargeEffTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setEVItem(2, value);
						rpEVChargeEffTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpEVChargeEffTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onEVStartSOCChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpEVStartSOCTB.getText().length() > 0){
					double value = Double.parseDouble(rpEVStartSOCTB.getText());
					if(value < 0 || value > 100){
						rpEVStartSOCTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setEVItem(3, value);
						rpEVStartSOCTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpEVStartSOCTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onEVEndSOCChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpEVEndSOCTB.getText().length() > 0){
					double value = Double.parseDouble(rpEVEndSOCTB.getText());
					if(value < 0 || value > 100){
						rpEVEndSOCTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setEVItem(4, value);
						rpEVEndSOCTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpEVEndSOCTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onEVStartTimeChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				String in = rpEVStartTimeTB.getText();
				if(in.length() > 0){
					if(in.contains(":")){
						String[] value = in.split(":");
						if(value.length != 2){
							if(!(event.getCode() == KeyCode.ENTER)){
								rpEVStartTimeTB.setStyle("-fx-text-fill:red;");
							}
						}else{
							int hrs = Integer.parseInt(value[0]);
							int min = Integer.parseInt(value[1]);
							if(hrs < 0 || hrs > 23 || min < 0 || min > 59){
								rpEVStartTimeTB.setStyle("-fx-text-fill:red;");
							}else{
								String v = hrs+"."+min;
								session.getRpGroups().get(rpGroupIndex).setEVItem(5, Double.parseDouble(v));
								rpEVStartTimeTB.setStyle("-fx-text-fill:black;");
							}
						}
					}
				}
			}catch(Exception e){
				rpEVStartTimeTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onEVEndTimeChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				String in = rpEVEndTimeTB.getText();
				if(in.length() > 0){
					if(in.contains(":")){
						String[] value = rpEVEndTimeTB.getText().split(":");
						if(value.length != 2){
							if(!(event.getCode() == KeyCode.ENTER)){
								rpEVEndTimeTB.setStyle("-fx-text-fill:red;");
							}
						}else{
							int hrs = Integer.parseInt(value[0]);
							int min = Integer.parseInt(value[1]);
							if(hrs < 0 || hrs > 23 || min < 0 || min > 59){
								rpEVEndTimeTB.setStyle("-fx-text-fill:red;");
							}else{
								String v = hrs+"."+min;
								session.getRpGroups().get(rpGroupIndex).setEVItem(6, Double.parseDouble(v));
								rpEVEndTimeTB.setStyle("-fx-text-fill:black;");
							}
						}
					}
				}
			}catch(Exception e){
				rpEVEndTimeTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void onEVChargeStratChanged(ActionEvent event) {
		if(rpGroupIndex >= 0){
			session.getRpGroups().get(rpGroupIndex).setEVItem(7, rpEVChargeStratCB.getSelectionModel().getSelectedIndex());
		}
	}
	@FXML
	public void onDRPercentChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpDRPercentContTB.getText().length() > 0){
					double value = Double.parseDouble(rpDRPercentContTB.getText());
					if(value < 0 || value > 100){
						rpDRPercentContTB.setStyle("-fx-text-fill:red;");
					}else{
						session.getRpGroups().get(rpGroupIndex).setDRItem(0, value);
						rpDRPercentContTB.setStyle("-fx-text-fill:black;");
					}
				}
			}catch(Exception e){
				rpDRPercentContTB.setStyle("-fx-text-fill:red;");
			}
		}
	}
	@FXML
	public void solarLatChanged(KeyEvent event) {
		try{
			String in = solarLatTB.getText();
			if(in.length() > 0){
				double value = Double.parseDouble(in);
				if(value < -90 || value > 90){
					solarLatTB.setStyle("-fx-text-fill:red;");
				}else{
					session.setLat(in);
					solarLatTB.setStyle("-fx-text-fill:black;");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@FXML
	public void solarLonChanged(KeyEvent event) {
		try{
			String in = solarLonTB.getText();
			if(in.length() > 0){
				double value = Double.parseDouble(in);
				if(value < -180 || value > 180){
					solarLonTB.setStyle("-fx-text-fill:red;");
				}else{
					session.setLon(in);
					solarLonTB.setStyle("-fx-text-fill:black;");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@FXML
	public void solarNorthChanged(ActionEvent event) {
		if(solarNorthRadio.isSelected()){
			session.setNorth(true);
		}
	}
	@FXML
	public void solarSouthChanged(ActionEvent event) {
		if(solarSouthRadio.isSelected()){
			session.setNorth(false);
		}
	}
	@FXML
	public void solarEastChanged(ActionEvent event) {
		if(solarEastRadio.isSelected()){
			session.setEast(true);
		}
	}
	@FXML
	public void solarWestChanged(ActionEvent event) {
		if(solarWestRadio.isSelected()){
			session.setEast(false);
		}
	}
	@FXML
	public void solarTimezoneChanged(ActionEvent event) {
		session.setTimezone(solarTimeCB.getSelectionModel().getSelectedIndex());
	}
	@FXML
	public void solarDaySaveChanged(ActionEvent event) {
		if(solarDaylightSavingsCB.isSelected()){
			session.setDaySave(true);
		}else{
			session.setDaySave(false);
		}
	}
	@FXML
	public void solarStartChanged(ActionEvent event) {
		LocalDate localDate = solarStartDP.getValue();
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		session.setSolarStart(date.getTime());
	}
	@FXML
	public void solarEndChanged(ActionEvent event) {
		LocalDate localDate = solarEndDP.getValue();
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		session.setSolarEnd(date.getTime());
	}
	@FXML
	public void onBeginDrag(MouseEvent event) {
		try{
			int begin = (int)gridBeginSlider.getValue();
			int end = (int)gridEndSlider.getValue();
			if(begin>end){
				begin = end -3;
				gridBeginSlider.setValue(begin);
			}
			fillGridLineChart(begin, end);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@FXML
	public void onEndDrag(MouseEvent event){

		try{
			int begin = (int)gridBeginSlider.getValue();
			int end = (int)gridEndSlider.getValue();
			if(begin>end){
				end = begin + 3;
				gridEndSlider.setValue(end);
			}
			fillGridLineChart(begin, end);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@FXML
	public void onAuthorChanged(KeyEvent event){
		session.setAuthor(authorTB.getText());
	}
	@FXML
	public void onNotesChanged(KeyEvent event){
		session.setNotes(notesTB.getText());
	}
	public void fillGridLineChart(int begin, int end){
		long jan1 = 1451631600000l;
		long dec31 = 1483253999000l;
		long oneHour = 3600000;
		long beginTime = jan1 + (begin*oneHour);
		long endTime = jan1 + (end*oneHour);
		Date b = new Date(beginTime);
		Date e = new Date(endTime);
		SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
		gridChartLabel.setText(df.format(b)+ " - " + df.format(e));
		gridGraphPane.getChildren().removeAll(gridGraphPane.getChildren());
		ObservableList<XYChart.Series<Date, Number>> s = FXCollections.observableArrayList();
		for(int x = 1; x < series[0].length; x++){
			if(gridChartCBBool[x-1]){
				ObservableList<XYChart.Data<Date, Number>> ss = FXCollections.observableArrayList();
				Date date = null;
				int count = 0;
				for(int y = begin + 1; y < end; y++){

					date = new Date(beginTime + (count * oneHour));
					if(series[y][x] != null && date != null){
						ss.add(new XYChart.Data<Date,Number>(date,Double.parseDouble(series[y][x])));
					}else{
						break;
					}
					count++;
				}
				s.add(new XYChart.Series<>("",ss));
			}
		}
		DateAxis dateAxis = new DateAxis();
		NumberAxis numberAxis = new NumberAxis();
		gridChart = new LineChart<Date,Number>(dateAxis, numberAxis, s);
		gridChart.getYAxis().setLabel("Load (kW)");
		gridChart.getYAxis().setStyle("-fx-font-size:15;");
		gridChart.getXAxis().setTickLabelRotation(45);
		gridChart.getXAxis().setTickLabelFont(new Font("Arial", 14));
		gridChart.setLegendVisible(false);
		gridChart.setCreateSymbols(false);
		gridChart.setMinHeight(392);
		gridChart.setMinWidth(908);
		gridGraphPane.getChildren().add(gridChart);
		int count = 0;
		for(int x = 1; x<series[0].length;x++){
			if(gridChartCBBool[x-1]){
				s.get(count).nodeProperty().get().setStyle("-fx-stroke: "+colors.get(x-1)+";");
				count++;
			}
		}
	}
	public void populateOutput(String ret){
		try {
			String[] type = ret.split(",");
			if(type[3].equals("101")){
				numStrataCalc++;
				String[] split = ret.split(",")[0].split("\\" + File.separator);
				String fileName = split[split.length-1];
				ObservableList<RatepayerGroup> rpgs = session.getRpGroups();
				RatepayerGroup rpg = null;
				int index = 0;
				for(int x = 0; x < rpgs.size(); x++){
					if(rpgs.get(x).getName().equals(fileName)){
						rpg = rpgs.get(x);
						index = x;
					}
				}
				File file;
				BufferedReader br;
				String input;
				int count = 0;
				String[] tmp;
				String[] values;
				double[] dValues;

				if(rpg.isBatteryChecked()){
					file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
							+ File.separator + rpg.getName() + File.separator + "output" + File.separator + "Battery.txt");
					if(!file.exists()){
						System.out.println("Output error");
					}
					br = new BufferedReader(new FileReader(file));


					values = new String[4];
					while((input = br.readLine()) != null){
						if(count > 4 && count < 9){
							tmp = input.split(" ");
							try{
								values[count - 5] = tmp[tmp.length-1];
							}catch (Exception e){
								e.printStackTrace();
								values[count - 5] = "0.0";
							}
						}
						count++;
					}

					dValues = new double[4];

					for(int x = 0; x < dValues.length; x++){
						try{
							dValues[x] = Double.parseDouble(values[x]);
						}catch(NumberFormatException e){

						}
					}

					rpg.setBsOut(dValues);
				}else{
					rpg.setBsOut(zeros(new double[4]));
				}

				if(rpg.isInverterChecked()){
					file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
							+ File.separator + rpg.getName() + File.separator + "output" + File.separator + "Converter.txt");
					br = new BufferedReader(new FileReader(file));
					count = 0;
					values = new String[4];
					while((input = br.readLine()) != null){
						if(count > 4 && count < 9){
							tmp = input.split(" ");
							try{
								values[count - 5] = tmp[tmp.length-1];
							}catch (Exception e){
								values[count - 5] = "0.0";
							}
						}
						count++;
					}

					dValues = new double[4];

					for(int x = 0; x < dValues.length; x++){
						try{
							dValues[x] = Double.parseDouble(values[x]);
						}catch(NumberFormatException e){

						}
					}

					rpg.setInvertOut(dValues);
				}else{
					rpg.setInvertOut(zeros(new double[4]));
				}

				if(rpg.isEvChecked()){
					file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
							+ File.separator + rpg.getName() + File.separator + "output" + File.separator + "ElectricVehicle.txt");
					br = new BufferedReader(new FileReader(file));
					count = 0;
					values = new String[4];
					while((input = br.readLine()) != null){
						if(count > 4 && count < 9){
							tmp = input.split(" ");
							try{
								values[count - 5] = tmp[tmp.length-1];
							}catch (Exception e){
								values[count - 5] = "0.0";
							}
						}
						count++;
					}

					dValues = new double[4];

					for(int x = 0; x < dValues.length; x++){
						try{
							dValues[x] = Double.parseDouble(values[x]);
						}catch(NumberFormatException e){

						}
					}

					rpg.setEvOut(dValues);
				}else{
					rpg.setEvOut(zeros(new double[4]));
				}

				values = new String[6];
				file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
						+ File.separator + rpg.getName() + File.separator + "output" + File.separator + "Load.txt");
				br = new BufferedReader(new FileReader(file));
				count = 0;
				while((input = br.readLine()) != null){
					if(count > 3 && count < 10){
						tmp = input.split(" ");
						try{
							values[count - 4] = tmp[tmp.length-1];
						}catch (Exception e){
							values[count - 4] = "0.0";
						}
					}
					count++;
				}

				dValues = new double[5];
				try{
					dValues[0] = Double.parseDouble(values[0]);
					dValues[1] = Double.parseDouble(values[2]);
					dValues[2] = Double.parseDouble(values[3]);
					dValues[3] = Double.parseDouble(values[4]);
					dValues[4] = Double.parseDouble(values[5]);
				}catch(NumberFormatException e){

				}
				rpg.setLoadOut(dValues);

				if(rpg.isSolarChecked()){
					values = new String[8];
					file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
							+ File.separator + rpg.getName() + File.separator + "output" + File.separator + "SolarPV.txt");
					br = new BufferedReader(new FileReader(file));
					count = 0;
					while((input = br.readLine()) != null){
						if(count > 4 && count < 13){
							tmp = input.split(" ");
							try{
								values[count - 5] = tmp[tmp.length-1];
							}catch (Exception e){
								values[count - 5] = "0.0";
							}
						}
						count++;
					}

					dValues = new double[5];
					try{
						dValues[0] = Double.parseDouble(values[2]);
						dValues[1] = Double.parseDouble(values[3]);
						dValues[2] = Double.parseDouble(values[4]);
						dValues[3] = Double.parseDouble(values[5]);
						dValues[4] = Double.parseDouble(values[7]);
					}catch(NumberFormatException e){

					}

					rpg.setSolarOut(dValues);
				}else{
					rpg.setSolarOut(zeros(new double[5]));
				}

				ObservableList<SolarMonthly> sm = solarTable.getItems();
				values = new String[12];
				file = new File("." + File.separator + "data" + File.separator + "Ratepayers" 
						+ File.separator + rpg.getName() + File.separator + "output" + File.separator + "SolarResource.txt");
				br = new BufferedReader(new FileReader(file));
				count = 0;
				while((input = br.readLine()) != null){
					if(count > 5 && count < 18){
						tmp = input.split("\t");
						sm.get(count-6).setGHI(tmp[1]);
						values[count-6] = tmp[1];
						sm.get(count-6).setDNI(tmp[2]);
						sm.get(count-6).setClearness(tmp[3]);
					}
					count++;
				}
				session.setGHI(values);
				ObservableList<SolarMonthly> copy = FXCollections.observableArrayList();
				copy.addAll(sm);
				sm.removeAll(sm);
				sm.addAll(copy);
				solarGraph.getData().clear();
				ObservableList<XYChart.Series<String, Number>> s = FXCollections.observableArrayList();
				ObservableList<XYChart.Data<String, Number>> ss = FXCollections.observableArrayList();
				for(int x = 0; x < sm.size(); x++){
					ss.add(new XYChart.Data<String,Number>(sm.get(x).getMonth(),Double.parseDouble(sm.get(x).getGHI())));
				}
				s.add(new XYChart.Series<>("GHI",ss));
				ss = FXCollections.observableArrayList();
				for(int x = 0; x < sm.size(); x++){
					ss.add(new XYChart.Data<String,Number>(sm.get(x).getMonth(),Double.parseDouble(sm.get(x).getDNI())));
				}
				s.add(new XYChart.Series<>("DNI",ss));
				solarGraph.setData(s);

				if(numStrataCalc == numStrataCalcMax){
					CalcProcess cp = new CalcProcess(File.separator + "Ratepayers" 
																	+ "," + rpLoadDataTB.getText() 
																	+ "," + hourlyDataFileTB.getText() 
																	+ ",102,0,0,0,0," 
																	+ (TIMESTEP*timestepMultiplier), this);
					cp.run();
				}
			}else{
				String[] values = new String[14];
				File file = new File("." + File.separator + "data" + File.separator + "Grid" + File.separator + "output" + File.separator + "GridInterconnection.txt");
				BufferedReader br = new BufferedReader(new FileReader(file));
				String input;
				String[] tmp;
				int count = 0;
				while((input = br.readLine()) != null){
					if(count < 14){
						tmp = input.split(" ");
						try{
							values[count] = tmp[tmp.length-1];
						}catch (Exception e){
							values[count] = "0.0";
						}
					}
					count++;
				}
				gridEPurchasedTB.setText(values[0]);
				gridESoldTB.setText(values[1]);
				gridNetPurchasesTB.setText(values[2]);
				gridPeakLoadTB.setText(values[4]);
				gridAvgLoadTB.setText(values[5]);
				gridEUseDayTB.setText(values[6]);
				gridEUseYearTB.setText(values[7]);
				gridLoadFactorTB.setText(values[8]);
				gridTotalChargesTB.setText(values[10]);
				gridEChargeTB.setText(values[11]);
				gridDChargeTB.setText(values[12]);
				gridInterconTB.setText(values[13]);

				double[] dValues = new double[12];
				try{
					dValues[0] = Double.parseDouble(values[0]);
					dValues[1] = Double.parseDouble(values[1]);
					dValues[2] = Double.parseDouble(values[2]);
					dValues[3] = Double.parseDouble(values[4]);
					dValues[4] = Double.parseDouble(values[5]);
					dValues[5] = Double.parseDouble(values[6]);
					dValues[6] = Double.parseDouble(values[7]);
					dValues[7] = Double.parseDouble(values[8]);
					dValues[8] = Double.parseDouble(values[10]);
					dValues[9] = Double.parseDouble(values[11]);
					dValues[10] = Double.parseDouble(values[12]);
					dValues[11] = Double.parseDouble(values[13]);
				}catch(NumberFormatException e){
					showErrorDialog("Error loading grid data");
				}

				session.setGrid(new Grid("Grid", dValues[8], dValues[11], dValues[9], dValues[10], dValues[0], dValues[1], dValues[2], dValues[3], dValues[4], dValues[5], dValues[6], dValues[7]));
				// TODO: populate stuff
				ObservableList<String> list = FXCollections.observableArrayList();
				list.add(session.getGrid().getName());
				for(int x = 0; x < rpGroupList.size(); x++){
					list.add(rpGroupList.get(x));
					ObservableList<RatepayerGroup> rpgs = session.getRpGroups();
					RatepayerGroup rpg = rpgs.get(x);
					values = new String[14];
					file = new File("." + File.separator + "data" + File.separator + "Ratepayers" + File.separator + rpg.getName() + File.separator + "output" + File.separator + "GridInterconnection.txt");
					if(file.exists()){
						br = new BufferedReader(new FileReader(file));
						count = 0;
						while((input = br.readLine()) != null){
							if(count < 14){
								tmp = input.split(" ");
								try{
									values[count] = tmp[tmp.length-1];
								}catch (Exception e){
									values[count] = "0.0";
								}
							}
							count++;
						}

						dValues = new double[12];
						try{
							dValues[0] = Double.parseDouble(values[0]);
							dValues[1] = Double.parseDouble(values[1]);
							dValues[2] = Double.parseDouble(values[2]);
							dValues[3] = Double.parseDouble(values[4]);
							dValues[4] = Double.parseDouble(values[5]);
							dValues[5] = Double.parseDouble(values[6]);
							dValues[6] = Double.parseDouble(values[7]);
							dValues[7] = Double.parseDouble(values[8]);
							dValues[8] = Double.parseDouble(values[10]);
							dValues[9] = Double.parseDouble(values[11]);
							dValues[10] = Double.parseDouble(values[12]);
							dValues[11] = Double.parseDouble(values[13]);
						}catch(NumberFormatException e){
							e.printStackTrace();
						}
						rpg.setInterconOut(dValues);
					}

					values = new String[14];
					file = new File("." + File.separator + "data" + File.separator + "Ratepayers" + File.separator + rpg.getName() + File.separator + "output" + File.separator + "GridInterconnectionTotal.txt");
					if(file.exists()){
						br = new BufferedReader(new FileReader(file));
						count = 0;
						while((input = br.readLine()) != null){
							if(count < 14){
								tmp = input.split(" ");
								try{
									values[count] = tmp[tmp.length-1];
								}catch (Exception e){
									values[count] = "0.0";
								}
							}
							count++;
						}

						dValues = new double[12];
						try{
							dValues[0] = Double.parseDouble(values[0]);
							dValues[1] = Double.parseDouble(values[1]);
							dValues[2] = Double.parseDouble(values[2]);
							dValues[3] = Double.parseDouble(values[4]);
							dValues[4] = Double.parseDouble(values[5]);
							dValues[5] = Double.parseDouble(values[6]);
							dValues[6] = Double.parseDouble(values[7]);
							dValues[7] = Double.parseDouble(values[8]);
							dValues[8] = Double.parseDouble(values[10]);
							dValues[9] = Double.parseDouble(values[11]);
							dValues[10] = Double.parseDouble(values[12]);
							dValues[11] = Double.parseDouble(values[13]);
						}catch(NumberFormatException e){
							e.printStackTrace();
						}

						rpg.setGridOut(dValues);
					}
				}
				gridStrataCB.setItems(list);
				gridStrataCB.getSelectionModel().select(0);
				try{
					rpStrataCB.getSelectionModel().select(-1);
				}catch (Exception ex){}
				rpStrataCB.getSelectionModel().select(0);
			}

		} catch (Exception e) {
			gridStrataCB.getSelectionModel().select(0);
			e.printStackTrace();
		}

	}

	private void loadTimeSeries(int index){
		try{
			if(index > 0){
				index--;
				RatepayerGroup rpg = session.getRpGroups().get(index);

				File file = new File("." + File.separator + "data" + File.separator + "Ratepayers" + File.separator + rpg.getName() + File.separator + "output" + File.separator + "time_series_simple.csv");
				if(file.exists()){
					BufferedReader br = new BufferedReader(new FileReader(file));
					int count = 0;
					ArrayList<String[]> ts = new ArrayList();
					String input = "";

					while((input = br.readLine()) != null){
						ts.add(input.split(","));
						count++;
					}

					series = new String[ts.size()][ts.get(0).length];
					gridChartCBBool = new boolean[series[0].length-1];

					for(int x =0; x< gridChartCBBool.length; x++){
						gridChartCBBool[x] = true;
					}

					for(int x = 1; x < ts.size(); x++ ){
						series[x] = ts.get(x);
					}
					int may1_0 = 2904 * timestepMultiplier;
					int may7_24 = 3070 * timestepMultiplier;

					fillGridLineChart(may1_0, may7_24);
					gridBeginSlider.setMax(TIMESTEP * timestepMultiplier);
					gridEndSlider.setMax(TIMESTEP * timestepMultiplier);
					gridBeginSlider.setValue(may1_0);
					gridEndSlider.setValue(may7_24);

					for(int x = 1; x < series[0].length; x++){
						CheckBox cb = new CheckBox();
						cb.setId(Integer.toString(x-1));
						cb.setText(ts.get(0)[x]);
						cb.setStyle("-fx-text-fill:"+colors.get(x-1)+";");
						cb.setSelected(true);
						cb.setOnAction(new OnGridCBChangeListener(){
							@Override
							public void handle(ActionEvent arg0) {
								CheckBox cbNew = (CheckBox)arg0.getSource();
								for(int x = 0; x < gridChartCBBool.length; x++){
									if(x == Integer.parseInt(cbNew.getId())){
										gridChartCBBool[x] = gridChartCBBool[x] ? false : true;
										int begin = (int)gridBeginSlider.getValue();
										int end = (int)gridEndSlider.getValue();
										fillGridLineChart(begin, end);
									}
								}
							}
						});
						checkBoxGrid.add(cb, 0, x-1);
					}
				}else{
					showErrorDialog("No Time Series Data");
				}
			}else{
				File file = new File("." + File.separator + "data" + File.separator + "Grid" + File.separator + "output" + File.separator + "time_series_simple.csv");
				if(file.exists()){
					BufferedReader br = new BufferedReader(new FileReader(file));
					int count = 0;
					ArrayList<String[]> ts = new ArrayList();
					String input = "";

					while((input = br.readLine()) != null){
						ts.add(input.split(","));
						count++;
					}

					series = new String[ts.size()][ts.get(0).length];
					gridChartCBBool = new boolean[series[0].length-1];

					for(int x =0; x< gridChartCBBool.length; x++){
						gridChartCBBool[x] = true;
					}

					for(int x = 1; x < ts.size(); x++ ){
						series[x] = ts.get(x);
					}
					int may1_0 = 2904;
					int may7_24 = 3070;

					fillGridLineChart(may1_0, may7_24);
					gridBeginSlider.setValue(may1_0);
					gridEndSlider.setValue(may7_24);

					for(int x = 1; x < series[0].length; x++){
						CheckBox cb = new CheckBox();
						cb.setId(Integer.toString(x-1));
						cb.setText(ts.get(0)[x]);
						cb.setStyle("-fx-text-fill:"+colors.get(x-1)+";");
						cb.setSelected(true);
						cb.setOnAction(new OnGridCBChangeListener(){
							@Override
							public void handle(ActionEvent arg0) {
								CheckBox cbNew = (CheckBox)arg0.getSource();
								for(int x = 0; x < gridChartCBBool.length; x++){
									if(x == Integer.parseInt(cbNew.getId())){
										gridChartCBBool[x] = gridChartCBBool[x] ? false : true;
										int begin = (int)gridBeginSlider.getValue();
										int end = (int)gridEndSlider.getValue();
										fillGridLineChart(begin, end);
									}
								}
							}
						});
						checkBoxGrid.add(cb, 0, x-1);
					}
				}else{
					showErrorDialog("No Time Series Data");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}
	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if(!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		}
		finally {
			if(source != null) {
				source.close();
			}
			if(destination != null) {
				destination.close();
			}
		}
	}
	public void deleteFilesInDirectory(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				deleteFilesInDirectory(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}
	public double[] zeros(double[] array){
		for(int x = 0; x < array.length; x++){
			array[x] = 0.0d;
		}
		return array;
	}
	
	public int countFileLines(File file) throws IOException{
		LineNumberReader  lnr = new LineNumberReader(new FileReader(file));
		lnr.skip(Long.MAX_VALUE);
		return(lnr.getLineNumber() + 1); 
	}
}

