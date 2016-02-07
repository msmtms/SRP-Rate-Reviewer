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
		colors.add("forestgreen");
		colors.add("aquamarine");
		colors.add("grey");
		colors.add("lime");
		colors.add("yellow");
		colors.add("maroon");
	}

	@SuppressWarnings("unchecked")
	private void initRatepayers(){
		rpGroups = FXCollections.observableArrayList();
		session.setRpGroups(rpGroups);
		rpGroupList = FXCollections.observableArrayList();
		RatepayerGroup rpg1 = new RatepayerGroup();
		rpg1.setName("Strata 1");
		rpg1.setNum("15000");
		RatepayerGroup rpg2 = new RatepayerGroup();
		rpg2.setName("Strata 2");
		rpg2.setNum("20000");
		rpGroups.add(rpg1);
		rpGroups.add(rpg2);
		rpGroupList.addAll("Strata 1", "Strata 2");
		rpGroupIndex = -1;
		rpStrataCB.setItems(rpGroupList);
		gridStrataCB.setItems(rpGroupList);
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
									System.out.println(rateSchedules.get(ratesScheduleIndex).getRates().get(rateTable.getSelectionModel().getSelectedIndex()).getPrice());
									c.setText(result.get());
									System.out.println(rateSchedules.get(ratesScheduleIndex).getRates().get(rateTable.getSelectionModel().getSelectedIndex()).getPrice());
								}catch(NoSuchElementException e){
									e.printStackTrace();
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
		String currentDir = System.getProperty("user.dir") + File.separator;
		File srp = new File(currentDir + "srp");
		if(srp.exists()){
			for(final File rs : srp.listFiles()){
				loadRate(new File(rs.getAbsolutePath() + File.separator + "rates.txt"));
			}
		}
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
		gridStrataCB.setItems(rateTitles);
		try {

			/*
			for(int x = 1; x < sheet.getPhysicalNumberOfRows(); x++){
				XSSFRow row = sheet.getRow(x);

				for(int y = 0; y < row.getPhysicalNumberOfCells(); y++){
					if(row.getCell(y).getCellType() == XSSFCell.CELL_TYPE_FORMULA){
						series[x][y] = Double.toString(row.getCell(y).getNumericCellValue());
					}else{
						Date date = row.getCell(0).getDateCellValue();
						if(date!= null){
							series[x][y] = Long.toString(date.getTime());
						}
					}
				}
			}

			gridBeginSlider.setMax(series.length);
			gridEndSlider.setMax(series.length-1);
			gridEndSlider.setValue(series.length-1);
			fillGridLineChart(0, series.length-1);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
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
		alert.setHeaderText("©NathanJohnson2015");
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
			session.setAuthor(input);
			input = fr.readLine();
			session.setNotes(input);
			input = fr.readLine();
			split = input.split(",");
			session.setGHI(split);
			input = fr.readLine();
			split = input.split(",");
			session.setLat(split[0]);
			session.setLon(split[1]);
			if(Integer.parseInt(split[2])==0){
				session.setNorth(true);
			}else{
				session.setNorth(false);
			}
			if(Integer.parseInt(split[3])==0){
				session.setEast(true);
			}else{
				session.setEast(false);
			}
			session.setTimezone(Integer.parseInt(split[4]));
			if(Integer.parseInt(split[5])==0){
				session.setDaySave(true);
			}else{
				session.setDaySave(false);
			}
			session.setSolarStart(Long.parseLong(split[6]));
			session.setSolarEnd(Long.parseLong(split[7]));
			input = fr.readLine();
			split = input.split(",");
			while(split.length == 4){
				RateSchedule rs = new RateSchedule();
				rs.setName(split[0]);
				rs.setMeter(Integer.parseInt(split[1]));
				rs.setCredit(Double.parseDouble(split[2]));
				rs.setCharge(Double.parseDouble(split[2]));
				input = fr.readLine();
				split = input.split(",");
				String[][] c = new String[GRID_ROWS * GRID_COLUMNS][GRID_PANES];
				int count = 0;
				for(int x = 0; x < split.length; x+=4){
					c[count][0] = split[x];
					c[count][1] = split[x+1];
					c[count][2] = split[x+2];
					c[count][2] = split[x+3];
					count++;
				}
				rs.setPaneColors(c);
				input = fr.readLine();
				split = input.split(",");
				input = fr.readLine();
				split = input.split(",");
				while(split.length == 5){
					Rate rate = new Rate();
					rate.setRate(split[0]);
					rate.setPrice(split[1]);
					rate.setFeedin(split[2]);
					rate.setDemand(split[3]);
					rate.setColor(split[4]);
					rs.getRates().add(rate);
					input = fr.readLine();
					split = input.split(",");
				}
				session.getRateSchedules().add(rs);
			}
			while(split.length == 2){
				RatepayerGroup rpg = new RatepayerGroup();
				rpg.setName(split[0]);
				rpg.setNum(split[1]);
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
				input = fr.readLine();
				split = input.split(",");
				for(int x = 0; x < split.length; x++){
					rpg.setDRItem(x, Double.parseDouble(split[x]));
				}
				session.getRpGroups().add(rpg);
				input = fr.readLine();
				if(input == null){
					break;
				}
			}
			clear();
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
	}
	// Event Listener on RadioButton[#importHourlyDataRadio].onAction
	@FXML
	public void importHourlyDataSelected(ActionEvent event) {
		solarBrowseBtn.setDisable(false);
		hourlyDataFileTB.setDisable(false);
		solarTable.setDisable(true);
	}
	// Event Listener on Button.onAction
	@FXML
	public void hourlyDataFileButton(ActionEvent event) {
		FileChooser fc = new FileChooser();
		String currentDir = System.getProperty("user.dir") + File.separator;
		File file = new File(currentDir);
		fc.setInitialDirectory(file);
		file = fc.showOpenDialog(app.getStage());
		hourlyDataFileTB.setText(file.getAbsolutePath());
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
					rpGroups.add(new RatepayerGroup(text1.getText(),text2.getText(),rpRateSchedule.getSelectionModel().getSelectedIndex(), "",new double[3],new double[2],new double[4],new double[8],new double[2]));
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
					rpGroups.add(new RatepayerGroup(text1.getText(),text2.getText(),rpRateSchedule.getSelectionModel().getSelectedIndex(),"",new double[3],new double[2],new double[4],new double[8],new double[2]));
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
		rpLoadDataTB.setText(file.getAbsolutePath());
		rpGroups.get(rpGroupIndex).setLoadFile(file.getAbsolutePath());
	}
	// Event Listener on CheckBox.onAction
	@FXML
	public void rpDRDeferClicked(ActionEvent event) {
		if(rpDRDeferCB.isSelected()){
			session.getRpGroups().get(rpGroupIndex).setDRItem(0, 0);
		}else{
			session.getRpGroups().get(rpGroupIndex).setDRItem(0, 1);
		}
	}
	// Event Listener on CheckBox.onAction
	@FXML
	public void rpDemandResponseClicked(ActionEvent event) {
		if(rpDRToggle){
			rpDemandRespPane.setDisable(true);
			rpDRToggle = false;
		}else{
			rpDemandRespPane.setDisable(false);
			rpDRToggle = true;
		}
	}
	// Event Listener on CheckBox.onAction
	@FXML
	public void rpBatteryStorageClicked(ActionEvent event) {
		if(rpBSToggle){
			rpBatteryStoragePane.setDisable(true);
			rpBSToggle = false;
		}else{
			rpBatteryStoragePane.setDisable(false);
			rpBSToggle = true;
		}
	}
	// Event Listener on CheckBox.onAction
	@FXML
	public void rpElectricVehicleClicked(ActionEvent event) {
		if(rpEVToggle){
			rpElecVehiclePane.setDisable(true);
			rpEVToggle = false;
		}else{
			rpElecVehiclePane.setDisable(false);
			rpEVToggle = true;
		}
	}
	// Event Listener on CheckBox.onAction
	@FXML
	public void rpInvertCBClicked(ActionEvent event) {
		if(rpInvertToggle){
			rpInverterPane.setDisable(true);
			rpInvertToggle = false;
		}else{
			rpInverterPane.setDisable(false);
			rpInvertToggle = true;
		}
	}
	// Event Listener on CheckBox.onAction
	@FXML
	public void rpSolarCBClicked(ActionEvent event) {
		if(rpSolarToggle){
			rpSolarPane.setDisable(true);
			rpSolarToggle = false;
		}else{
			rpSolarPane.setDisable(false);
			rpSolarToggle = true;
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void onRunClicked(ActionEvent event) {
		try {
			File file = new File("." + File.separator + "data");
			if(file.exists()){
				deleteFilesInDirectory(file);
			}
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
				file = new File("." + File.separator + "data" + File.separator + "rates" + File.separator + rs.getName());
				if(!file.exists()){
					file.mkdirs();
				}
				file = new File("." + File.separator + "data" + File.separator + "rates" + File.separator + rs.getName() + File.separator + "rates.txt");
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

				file = new File("." + File.separator + "data" + File.separator + "rates" + File.separator + rs.getName() + File.separator + "monthly.txt");
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

				file = new File("." + File.separator + "data" + File.separator + "rates" + File.separator + rs.getName() + File.separator + "meter.txt");
				if(!file.exists()){
					file.createNewFile();
				}

				fr = new FileWriter(file);

				fr.write("Net Metering:\t"+rs.getMeter()+"\n");
				fr.write("Production Credit:\t"+rs.getCredit()+"\n");
				fr.write("Interconnection Charge:\t"+rs.getCharge()+"\n");

				fr.close();
			}

			ObservableList<RatepayerGroup> rpgs = session.getRpGroups();
			for(int x = 0; x < rpgs.size(); x++){
				RatepayerGroup rpg = rpgs.get(x);

				if(!(rpg.getRateSchedule() < 0 || rpg.getLoadFile().length() <= 0)){

					file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "input");
					if(!file.exists()){
						file.mkdirs();
					}
					file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "output");
					if(!file.exists()){
						file.mkdirs();
					}

					file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "input" + File.separator + "rpg.txt");
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

					file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "input" + File.separator + "SolarPV.txt");
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

					file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "input" + File.separator + "Inverter.txt");
					if(!file.exists()){
						file.createNewFile();
					}
					fr = new FileWriter(file);
					fr.write("Capacity\t" + rpg.getInvertItem(0));
					fr.write(nl);
					fr.write("Efficiency\t" + rpg.getInvertItem(1));
					fr.close();

					file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "input" + File.separator + "Battery.txt");
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

					//String[] start = Double.toString(rpg.getEVItem(5)).split(".");
					//String[] end = Double.toString(rpg.getEVItem(6)).split(".");

					file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "input" + File.separator + "ElectricVehicle.txt");
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

					File lfile = new File(rpg.getLoadFile());

					if(file.exists()){
						try{
							file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "input" + File.separator + "load.txt");
							copyFile(lfile, file);
						}catch(IOException e){
							e.printStackTrace();
						}
					}

					// TODO ============================================= FILE STRUCTURE STUFF
					CalcProcess cp = new CalcProcess(File.separator + "ratepayers" + File.separator + rpg.getName() + "," + rpLoadDataTB.getText() + "," + hourlyDataFileTB.getText() , this);
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
		rpSolarCapTB.setText(Double.toString(rg.getSolarPVItem(0)));
		rpSolarAzTB.setText(Double.toString(rg.getSolarPVItem(1)));
		rpSolarSlopeTB.setText(Double.toString(rg.getSolarPVItem(2)));
		rpInvertCapTB.setText(Double.toString(rg.getInvertItem(0)));
		rpInvertEfficiencyTB.setText(Double.toString(rg.getInvertItem(1)));
		rpBSCapTB.setText(Double.toString(rg.getBSItem(0)));
		rpBSRoundEffTB.setText(Double.toString(rg.getBSItem(1)));
		rpBSMinSOCTB.setText(Double.toString(rg.getBSItem(2)));
		rpBSMaxCTB.setText(Double.toString(rg.getBSItem(3)));
		rpEVBatteryCapTB.setText(Double.toString(rg.getEVItem(0)));
		rpEVChargerCB.getSelectionModel().select((int)rg.getEVItem(1));
		rpEVChargeEffTB.setText(Double.toString(rg.getEVItem(2)));
		rpEVStartSOCTB.setText(Double.toString(rg.getEVItem(3)));
		rpEVEndSOCTB.setText(Double.toString(rg.getEVItem(4)));
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
			rpEVStartTimeTB.setText("00:00");
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
			rpEVEndTimeTB.setText("00:00");
		}
		rpEVChargeStratCB.getSelectionModel().select((int)rg.getEVItem(7));
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
		double[] summaryOut = rpg.getSummaryOut();
		rpInterconChargesTB.setText(Double.toString(summaryOut[0]));
		rpEnergyChargesTB.setText(Double.toString(summaryOut[1]));
		rpDemandChargesTB.setText(Double.toString(summaryOut[2]));
		rpTotalChargesTB.setText(Double.toString(summaryOut[3]));
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
		RatepayerGroup rpg = rpGroups.get(index);
		noCustLabel.setText(rpg.getNum());
		double[] loadOut = rpg.getLoadOut();
		gridPeakLoadTB.setText(Double.toString(loadOut[0]));
		gridAvgLoadTB.setText(Double.toString(loadOut[1]));
		gridEUseDayTB.setText(Double.toString(loadOut[2]));
		gridEUseYearTB.setText(Double.toString(loadOut[3]));
		gridLoadFactorTB.setText(Double.toString(loadOut[4]));
		loadTimeSeries(index);
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
		rpDRDemandContTB.setText("");
		rpDRCapFactorTB.setText("");
		rpDRPercentContTB.setText("");
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

	}
	private void writeToFile(){
		File file = new File(System.getProperty("user.dir")+File.separator+sessionFileName);
		String d = ",";
		String n = "\n";
		ObservableList<RateSchedule> rs = session.getRateSchedules();
		ObservableList<RatepayerGroup> rp = session.getRpGroups();
		try {
			FileWriter fr = new FileWriter(file);
			fr.write(authorTB.getText()+n+notesTB.getText()+n);
			ObservableList<SolarMonthly> l = solarTable.getItems();
			for(int x = 0; x < (l.size()); x++){
				fr.write(l.get(x).getGHI());
				if(!(x+1==l.size())){
					fr.write(d);
				}
			}
			fr.write(n);
			fr.write(session.getLat()+d);
			fr.write(session.getLon()+d);
			if(session.isNorth()){
				fr.write("0");
			}else{
				fr.write("1");
			}
			fr.write(d);
			if(session.isEast()){
				fr.write("0");
			}else{
				fr.write("1");
			}
			fr.write(d);
			fr.write(Integer.toString(session.getTimezone()));
			fr.write(d);
			if(session.isDaySave()){
				fr.write("0");
			}else{
				fr.write("1");
			}
			fr.write(d);
			fr.write(session.getSolarStart() + d + session.getSolarEnd());
			fr.write(n);
			RateSchedule r;
			String[][] p;
			ObservableList<Rate> ra;
			Rate rl;
			for(int x = 0; x < rs.size(); x++){
				r = rs.get(x);
				p = r.getPaneColors();
				ra = r.getRates();
				String c = new String();
				fr.write(r.getName() + d + r.getMeter() + d + r.getCredit() + d + r.getCharge() + n);
				for(int y = 0; y < p.length; y++){
					for(int z = 0; z < p[0].length; z++){
						c +=p[y][z]+d;
					}
				}
				fr.write(c.substring(0,c.length()-1));
				fr.write(n);
				for(int y = 0; y < ra.size(); y++){
					rl = ra.get(y);
					fr.write(rl.getRate() + d + rl.getPrice() + d +rl.getFeedin() + d + rl.getDemand() + d + rl.getColor());
					fr.write(n);
				}
			}
			ObservableList<RatepayerGroup> rpgs = session.getRpGroups();
			RatepayerGroup rpg;
			double[] solarPV;
			double[] inverter;
			double[] batteryStorage;
			double[] electricVehicle;
			double[] demandResponse;
			for(int x = 0; x < rpgs.size(); x++){
				rpg = rpgs.get(x);
				solarPV = rpg.getSolarPV();
				inverter = rpg.getInverter();
				batteryStorage = rpg.getBatteryStorage();
				electricVehicle = rpg.getElectricVehicle();
				demandResponse = rpg.getDemandResponse();
				fr.write(rpg.getName() + d + rpg.getNum() + n);
				for(int y = 0; y < solarPV.length; y++){
					fr.write(Double.toString(solarPV[y]));
					if(!(y+1==solarPV.length)){
						fr.write(d);
					}
				}
				fr.write(n);
				for(int y = 0; y < inverter.length; y++){
					fr.write(Double.toString(inverter[y]));
					if(!(y+1==inverter.length)){
						fr.write(d);
					}
				}
				fr.write(n);
				for(int y = 0; y < batteryStorage.length; y++){
					fr.write(Double.toString(batteryStorage[y]));
					if(!(y+1==batteryStorage.length)){
						fr.write(d);
					}
				}
				fr.write(n);
				for(int y = 0; y < electricVehicle.length; y++){
					fr.write(Double.toString(electricVehicle[y]));
					if(!(y+1==electricVehicle.length)){
						fr.write(d);
					}
				}
				fr.write(n);
				for(int y = 0; y < demandResponse.length; y++){
					fr.write(Double.toString(demandResponse[y]));
					if(!(y+1==demandResponse.length)){
						fr.write(d);
					}
				}
				fr.write(n);
			}
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
			File file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "output" + File.separator + "Battery.txt");
			if(!file.exists()){
				System.out.println("Output error");
			}
			BufferedReader br = new BufferedReader(new FileReader(file));
			String input;
			int count = 0;
			String[] tmp;
			String[] values = new String[4];
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
			if(rpg.getName().equals(gridStrataCB.getItems().get(0).toString())){
				rpBSEnergyInTB.setText(values[0]);
				rpBSEnergyOutTB.setText(values[1]);
				rpBSLossesTB.setText(values[2]);
				rpBSAutonomyTB.setText(values[3]);
			}

			double[] dValues = new double[4];

			for(int x = 0; x < dValues.length; x++){
				try{
					dValues[x] = Double.parseDouble(values[x]);
				}catch(NumberFormatException e){

				}
			}

			rpg.setBsOut(dValues);

			file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "output" + File.separator + "Converter.txt");
			br = new BufferedReader(new FileReader(file));
			count = 0;
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
			if(rpg.getName().equals(gridStrataCB.getItems().get(0).toString())){
				rpInvertEnergyInTB.setText(values[0]);
				rpInvertEnergyOutTB.setText(values[1]);
				rpInvertLossesTB.setText(values[2]);
				rpInvertCapFactorTB.setText(values[3]);
			}

			dValues = new double[4];

			for(int x = 0; x < dValues.length; x++){
				try{
					dValues[x] = Double.parseDouble(values[x]);
				}catch(NumberFormatException e){

				}
			}

			rpg.setInvertOut(dValues);

			file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "output" + File.separator + "ElectricVehicle.txt");
			br = new BufferedReader(new FileReader(file));
			count = 0;
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
			if(rpg.getName().equals(gridStrataCB.getItems().get(0).toString())){
				rpEVEInDayTB.setText(values[0]);
				rpEVEInYearTB.setText(values[1]);
				rpEVLossesTB.setText(values[2]);
				rpEVLoadPercentTB.setText(values[3]);
			}

			dValues = new double[4];

			for(int x = 0; x < dValues.length; x++){
				try{
					dValues[x] = Double.parseDouble(values[x]);
				}catch(NumberFormatException e){

				}
			}

			rpg.setEvOut(dValues);

			values = new String[6];
			file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "output" + File.separator + "Load.txt");
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
			if(rpg.getName().equals(gridStrataCB.getItems().get(0).toString())){
				rpPeakLoadTB.setText(values[0]);
				rpAverageLoadTB.setText(values[2]);
				rpEUDayTB.setText(values[3]);
				rpEUYearTB.setText(values[4]);
				rpLoadFactorTB.setText(values[5]);
				gridPeakLoadTB.setText(values[0]);
				gridAvgLoadTB.setText(values[2]);
				gridEUseDayTB.setText(values[3]);
				gridEUseYearTB.setText(values[4]);
				gridLoadFactorTB.setText(values[5]);
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


			values = new String[8];
			file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "output" + File.separator + "SolarPV.txt");
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
			if(rpg.getName().equals(gridStrataCB.getItems().get(0).toString())){
				rpSolarEOTB.setText(values[2]);
				rpSolarEOYearTB.setText(values[3]);
				rpSolarEODayTB.setText(values[4]);
				rpCapFactorTB.setText(values[5]);
				rpSolarPVPenTB.setText(values[7]);
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

			ObservableList<SolarMonthly> sm = solarTable.getItems();

			file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "output" + File.separator + "SolarResource.txt");
			br = new BufferedReader(new FileReader(file));
			count = 0;
			while((input = br.readLine()) != null){
				if(count > 5 && count < 18){
					tmp = input.split("\t");
					sm.get(count-6).setGHI(tmp[1]);
					sm.get(count-6).setDNI(tmp[2]);
					sm.get(count-6).setClearness(tmp[3]);
				}
				count++;
			}

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

			file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "output" + File.separator + "GridInterconnection.txt");
			br = new BufferedReader(new FileReader(file));
			count = 0;
			while((input = br.readLine()) != null){
				if(count < 3){
					tmp = input.split(" ");
					try{
						values[count] = tmp[tmp.length-1];
					}catch (Exception e){
						values[count] = "0.0";
					}
				}
				count++;
			}
			if(rpg.getName().equals(gridStrataCB.getItems().get(0).toString())){
				rpEnergyPurchasedTB.setText(values[0]);
				rpEnergySoldTB.setText(values[1]);
				rpNetPurchasesTB.setText(values[2]);
			}

			dValues = new double[3];
			try{
				dValues[0] = Double.parseDouble(values[0]);
				dValues[1] = Double.parseDouble(values[1]);
				dValues[2] = Double.parseDouble(values[2]);
			}catch(NumberFormatException e){

			}

			rpg.setInterconOut(dValues);

			gridStrataCB.getSelectionModel().select(0);

		} catch (Exception e) {
			gridStrataCB.getSelectionModel().select(0);
			e.printStackTrace();
		}
	}

	private void loadTimeSeries(int index){
		try{
			RatepayerGroup rpg = session.getRpGroups().get(index);

			File file = new File("." + File.separator + "data" + File.separator + "ratepayers" + File.separator + rpg.getName() + File.separator + "output" + File.separator + "time_series_simple.csv");
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
}