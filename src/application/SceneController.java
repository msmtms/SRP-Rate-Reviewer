package application;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList; 
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
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
	private CheckBox tieredRateCB;
	@FXML
	private CheckBox timeOfUseCB;
	@FXML
	private CheckBox criticalPeakCB;
	@FXML
	private CheckBox pvPowerCB;
	@FXML
	private CheckBox netGridNonSolarCB;
	@FXML
	private CheckBox netGridSolarCB;
	@FXML
	private CheckBox invertInputCB;
	@FXML
	private CheckBox gridSalesCB;
	@FXML
	private CheckBox gridPurchasesCB;
	@FXML
	private CheckBox invertOutputCB;
	@FXML
	private CheckBox rectInputCB;
	@FXML
	private CheckBox rectOutputCB;
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

	private static final int GRID_ROWS = 24;
	private static final int GRID_COLUMNS = 12;
	private static final int GRID_PANES = 4;
	private boolean gridToggle,rpSolarToggle,rpInvertToggle,rpBSToggle,rpEVToggle,rpDRToggle;
	private int gridIndex,rateCount,ratesScheduleIndex,rpGroupIndex;
	private Pattern p;
	private Pane[][] panes;
	private String[][] series;
	private ArrayList<String> colors = new ArrayList();
	private ObservableList<RateSchedule> rateSchedules;
	private ObservableList<String> rateTitles,rpGroupList;
	private ObservableList<RatepayerGroup> rpGroups;
	private String sessionFileName;
	private Main app;
	private Session session;
	private LineChart<Date,Number> gridChart;

	public void init(Main app){
		new RateReviewerProxy("blah");
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
		colors.add("aquamarine");
		colors.add("beige");
		colors.add("brown");
		colors.add("forestgreen");
		colors.add("gold");
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
		RateSchedule rs1 = new RateSchedule();
		RateSchedule rs2 = new RateSchedule();
		rs1.setName("TOU 3-6");
		rateTitles.add("TOU 3-6");
		rs2.setName("TOU 4-7");
		rateTitles.add("TOU 4-7");
		rateSchedules.addAll(rs1,rs2);

		ratesScheduleIndex = -1;
		ratesScheduleCB.setItems(rateTitles);
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
		priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		priceColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<Rate, String>>() {
					@Override
					public void handle(CellEditEvent<Rate, String> t) {
						((Rate) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setPrice(t.getNewValue());
					}
				}
				);
		feedInColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		feedInColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<Rate, String>>() {
					@Override
					public void handle(CellEditEvent<Rate, String> t) {
						((Rate) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setFeedin(t.getNewValue());
					}
				}
				);

		demandColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		demandColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<Rate, String>>() {
					@Override
					public void handle(CellEditEvent<Rate, String> t) {
						((Rate) t.getTableView().getItems().get(
								t.getTablePosition().getRow())
								).setDemand(t.getNewValue());
					}
				}
				);
		colorColumn.setCellFactory(cellFactory);
		ObservableList<Rate> rateList = FXCollections.observableArrayList();
		String[] arr1 = {"Non-summer", "Summer off-Peak", "Summer on-peak"};
		String[] arr2 = {"0.12", "0.16", "0.16"};
		String[] arr3 = {"0.3", "0.3", "0.3"};
		String[] arr4 = {"0.0", "0.0", "0.0"};
		for(int x = 0; x < arr1.length; x++){
			Rate rate = new Rate(arr1[x],arr2[x],arr3[x],arr4[x],colors.get(x));
			rateList.add(rate);
		}
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
		rateSchedules.get(0).setRates(rateList);
	}
	private void initGrid(){
		gridStrataCB.setItems(rateTitles);
		InputStream is;
		try {

			is = this.getClass().getResourceAsStream("/srpweek.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(is);

			XSSFSheet sheet = wb.getSheetAt(0);
			series = new String[sheet.getPhysicalNumberOfRows()][sheet.getRow(0).getPhysicalNumberOfCells()];

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
			tieredRateCB.setStyle("-fx-text-fill:"+colors.get(0)+";");
			timeOfUseCB.setStyle("-fx-text-fill:"+colors.get(1)+";");
			criticalPeakCB.setStyle("-fx-text-fill:"+colors.get(2)+";");
			netGridNonSolarCB.setStyle("-fx-text-fill:"+colors.get(3)+";");
			netGridSolarCB.setStyle("-fx-text-fill:"+colors.get(4)+";");
			pvPowerCB.setStyle("-fx-text-fill:"+colors.get(5)+";");
			gridPurchasesCB.setStyle("-fx-text-fill:"+colors.get(6)+";");
			gridSalesCB.setStyle("-fx-text-fill:"+colors.get(7)+";");
			invertInputCB.setStyle("-fx-text-fill:"+colors.get(8)+";");
			invertOutputCB.setStyle("-fx-text-fill:"+colors.get(9)+";");
			rectInputCB.setStyle("-fx-text-fill:"+colors.get(10)+";");
			rectOutputCB.setStyle("-fx-text-fill:"+colors.get(11)+";");
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
									panes[x][0].setStyle("-fx-background-color:"+r.getColor()+";-fx-border-color:black;");
									panes[x][1].setVisible(false);
									panes[x][2].setVisible(false);
								}
								if(ratesWeekdayBtn.isSelected()){
									rateSchedules.get(ratesScheduleIndex).setPaneColor(x,1,r.getColor());
									panes[x][1].setStyle("-fx-background-color:"+r.getColor()+";-fx-border-color:black;");
									panes[x][1].setVisible(true);
									panes[x][2].setVisible(true);
								}
								if(ratesWeekendBtn.isSelected()){
									rateSchedules.get(ratesScheduleIndex).setPaneColor(x,2,r.getColor());
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
		// TODO
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
	// Event Listener on Button.onAction
	@FXML
	public void ratesAddClicked(ActionEvent event) {
		RateSchedule rs = rateSchedules.get(ratesScheduleIndex);
		rs.addRate(new Rate("Enter Name","","","",colors.get(rs.getRates().size())));
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
					rpGroups.add(new RatepayerGroup(text1.getText(),text2.getText(),new double[3],new double[2],new double[4],new double[8],new double[2]));
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
					rpGroups.add(new RatepayerGroup(text1.getText(),text2.getText(),new double[3],new double[2],new double[4],new double[8],new double[2]));
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
	// Event Listener on Button.onAction
	@FXML
	public void rpLoadDataClicked(ActionEvent event) {
		//TODO
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
		//TODO
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
	}
	// Event Listener on TextField[#ratesNameTB].onKeyReleased
	@FXML
	public void onRateNameChanged(KeyEvent event) {
		int tmp = ratesScheduleIndex;
		rateTitles.set(ratesScheduleIndex, ratesNameTB.getText());
		ratesScheduleIndex = tmp;
		ratesScheduleCB.getSelectionModel().select(ratesScheduleIndex);
		ratesNameTB.positionCaret(ratesNameTB.getLength());
	}
	// Event Listener on ComboBox[#rpStrataCB].onAction
	@FXML
	public void onRPCBChange(ActionEvent event) {
		rpGroupIndex = rpStrataCB.getSelectionModel().getSelectedIndex();
		RatepayerGroup rpg = rpGroups.get(rpStrataCB.getSelectionModel().getSelectedIndex());
		rpNameTB.setText(rpg.getName());
		rpNoCustTB.setText(rpg.getNum());
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
			String[] s = Double.toString(rg.getEVItem(5)).split(".");
			rpEVStartTimeTB.setText(s[0] + ":" + s[1]);
		}else{
			rpEVStartTimeTB.setText("00:00");
		}
		if(rg.getEVItem(6)>0){
			String[] e = Double.toString(rg.getEVItem(6)).split(".");
			rpEVEndTimeTB.setText(e[0] + ":" + e[1]);
		}else{
			rpEVEndTimeTB.setText("00:00");
		}
		rpEVChargeStratCB.getSelectionModel().select((int)rg.getEVItem(7));
		rpDRPercentContTB.setText(Double.toString(rg.getDRItem(0)));
		int d = (int) rg.getDRItem(1);
		if(d == 0){
			rpDRDeferCB.setSelected(false);
		}else{
			rpDRDeferCB.setSelected(true);
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
		double[] drOut = rpg.getDrOut();
		rpDRDemandContTB.setText(Double.toString(drOut[0]));
		rpDRCapFactorTB.setText(Double.toString(drOut[1]));
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
		int tmp = rpGroupIndex;
		RatepayerGroup rpg = rpGroups.get(rpGroupIndex);
		rpg.setName(rpNameTB.getText());
		rpGroupList.set(rpGroupIndex, rpNameTB.getText());
		rpGroupIndex = tmp;
		rpStrataCB.getSelectionModel().select(rpGroupIndex);
		rpNameTB.positionCaret(rpNameTB.getLength());
	}
	@FXML
	public void onRPNumChanged(KeyEvent event) {
		RatepayerGroup rpg = rpGroups.get(rpGroupIndex);
		System.out.println(rpGroupIndex);
		rpg.setNum(rpNoCustTB.getText());
	}
	@FXML
	public void onNoNetChange(ActionEvent event) {
		rateSchedules.get(ratesScheduleIndex).setMeter(0);
	}
	@FXML
	public void onMonthlyChange(ActionEvent event) {
		rateSchedules.get(ratesScheduleIndex).setMeter(1);
	}
	@FXML
	public void onAnnualChange(ActionEvent event) {
		rateSchedules.get(ratesScheduleIndex).setMeter(2);

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
	}
	@FXML
	public void onCreditChanged(KeyEvent event) {
		String text = ratesOverprodTB.getText();
		if(ratesScheduleIndex >=0 ){
			try{
				rateSchedules.get(ratesScheduleIndex).setCredit(Double.parseDouble(text));
			}catch(Exception e){
				if(text.length()>0){
					showInvalidInputDialog();
				}
			}
		}
	}
	@FXML
	public void onChargeChanged(KeyEvent event) {
		String text = ratesInterconTB.getText();
		if(ratesScheduleIndex >=0 || !(text.length()>0)){
			try{
				rateSchedules.get(ratesScheduleIndex).setCredit(Double.parseDouble(text));
			}catch(Exception e){
				if(text.length()>0){
					showInvalidInputDialog();
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
		ratesOverprodTB.setText("0.0");
		ratesInterconTB.setText("0.0");
		ObservableList<Rate> r = rateTable.getItems();
		r.removeAll(r);
		ObservableList<String> rpgs = rpStrataCB.getItems();
		rpgs.removeAll(rpgs);
		rpNameTB.setText("");
		rpNoCustTB.setText("");
		rpLoadDataTB.setText("");
		rpSolarCapTB.setText("0.0");
		rpSolarAzTB.setText("0.0");
		rpSolarSlopeTB.setText("0.0");
		rpPeakLoadTB.setText("0.0");
		rpAverageLoadTB.setText("0.0");
		rpEUDayTB.setText("0.0");
		rpEUYearTB.setText("0.0");
		rpLoadFactorTB.setText("0.0");
		rpDemandChargesTB.setText("0.0");
		rpEnergyChargesTB.setText("0.0");
		rpInterconChargesTB.setText("0.0");
		rpTotalChargesTB.setText("0.0");
		rpNetPurchasesTB.setText("0.0");
		rpEnergySoldTB.setText("0.0");
		rpEnergyPurchasedTB.setText("0.0");
		rpInvertEnergyInTB.setText("0.0");
		rpInvertEnergyOutTB.setText("0.0");
		rpInvertLossesTB.setText("0.0");
		rpInvertCapFactorTB.setText("0.0");
		rpInvertCapTB.setText("0.0");
		rpInvertEfficiencyTB.setText("0.0");
		rpBSEnergyInTB.setText("0.0");
		rpBSEnergyOutTB.setText("0.0");
		rpBSLossesTB.setText("0.0");
		rpBSAutonomyTB.setText("0.0");
		rpBSCapTB.setText("0.0");
		rpBSRoundEffTB.setText("0.0");
		rpBSMaxCTB.setText("0.0");
		rpBSMinSOCTB.setText("0.0");
		rpDRDemandContTB.setText("0.0");
		rpDRCapFactorTB.setText("0.0");
		rpDRPercentContTB.setText("0.0");
		rpEVEInDayTB.setText("0.0");
		rpEVEInYearTB.setText("0.0");
		rpEVLossesTB.setText("0.0");
		rpEVLoadPercentTB.setText("0.0");
		rpEVBatteryCapTB.setText("0.0");
		rpEVStartSOCTB.setText("0.0");
		rpEVChargeEffTB.setText("0.0");
		rpEVEndTimeTB.setText("00:00");
		rpEVStartTimeTB.setText("00:00");
		rpEVEndSOCTB.setText("0.0");
		gridPeakLoadTB.setText("0.0");
		gridAvgLoadTB.setText("0.0");
		noCustLabel.setText("0");
		gridEUseYearTB.setText("0.0");
		gridEUseDayTB.setText("0.0");
		gridLoadFactorTB.setText("0.0");
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
						showInvalidInputDialog("Value must be 0 or positive.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setSolarPVItem(0, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be between -180 and 180.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setSolarPVItem(1, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be between 0 and 90.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setSolarPVItem(2, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be 0 or positive.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setInvertItem(0, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be between 0 and 100.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setInvertItem(1, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be 0 or positive.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setBSItem(0, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be between 0 and 100.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setBSItem(1, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be between 0 and 100.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setBSItem(2, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be greater than 0.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setBSItem(3, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be 0 or positive.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setBSItem(0, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
			}
		}
	}
	@FXML
	public void onEVChargerCBChanged(ActionEvent event){
		if(rpGroupIndex >= 0){
			session.getRpGroups().get(rpGroupIndex).setBSItem(1, rpEVChargerCB.getSelectionModel().getSelectedIndex());
		}
	}
	@FXML
	public void onEVChargeEffChanged(KeyEvent event) {
		if(rpGroupIndex >= 0){
			try{
				if(rpEVChargeEffTB.getText().length() > 0){
					double value = Double.parseDouble(rpEVChargeEffTB.getText());
					if(value < 0 || value > 100){
						showInvalidInputDialog("Value must be between 0 and 100.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setBSItem(2, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be between 0 and 100.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setBSItem(3, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be between 0 and 100.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setEVItem(4, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
								showInvalidInputDialog("Value must be in the format HH:MM.\nHours: 0-23. Min: 0-59.");
							}
						}else{
							int hrs = Integer.parseInt(value[0]);
							int min = Integer.parseInt(value[1]);
							if(hrs < 0 || hrs > 23 || min < 0 || min > 59){
								showInvalidInputDialog("Value must be in the format HH:MM.\nHours: 0-23. Min: 0-59.");
							}else{
								String v = hrs+"."+min;
								session.getRpGroups().get(rpGroupIndex).setEVItem(5, Double.parseDouble(v));
							}
						}
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
								showInvalidInputDialog("Value must be in the format HH:MM.\nHours: 0-23. Min: 0-59.");
							}
						}else{
							int hrs = Integer.parseInt(value[0]);
							int min = Integer.parseInt(value[1]);
							if(hrs < 0 || hrs > 23 || min < 0 || min > 59){
								showInvalidInputDialog("Value must be in the format HH:MM.\nHours: 0-23. Min: 0-59.");
							}else{
								String v = hrs+"."+min;
								session.getRpGroups().get(rpGroupIndex).setEVItem(6, Double.parseDouble(v));
							}
						}
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
						showInvalidInputDialog("Value must be between 0 and 100.");
					}else{
						session.getRpGroups().get(rpGroupIndex).setDRItem(0, value);
					}
				}
			}catch(Exception e){
				showInvalidInputDialog();
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
					showInvalidInputDialog("Value must be between -90 and 90.");
				}else{
					session.setLat(in);
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
					showInvalidInputDialog("Value must be between -180 and 180.");
				}else{
					session.setLon(in);
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
		Date b = new Date(Long.parseLong(series[begin+1][0]));
		Date e = new Date(Long.parseLong(series[end-1][0]));
		SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
		gridChartLabel.setText(df.format(b)+ " - " + df.format(e));
		gridGraphPane.getChildren().removeAll(gridGraphPane.getChildren());
		ObservableList<XYChart.Series<Date, Number>> s = FXCollections.observableArrayList();
		for(int x = 1; x < 6; x++){
			ObservableList<XYChart.Data<Date, Number>> ss = FXCollections.observableArrayList();
			Date date = null; 
			for(int y = begin + 1; y < end; y++){
				if(series[y][0] != null){
					date = new Date(Long.parseLong(series[y][0]));
				}
				if(series[y][x] != null && date != null){
					ss.add(new XYChart.Data<Date,Number>(date,Double.parseDouble(series[y][x])));
				}else{
					break;
				}
			}
			s.add(new XYChart.Series<>("",ss));
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
		for(int x = 0; x<s.size();x++){
			s.get(x).nodeProperty().get().setStyle("-fx-stroke: "+colors.get(x)+";");
		}
	}

}

