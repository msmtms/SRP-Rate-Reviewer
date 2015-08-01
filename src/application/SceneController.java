package application;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
	private TextField rpEVChargStratTB;
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

	private static final int GRID_ROWS = 24;
	private static final int GRID_COLUMNS = 12;
	private boolean gridToggle,rpSolarToggle,rpInvertToggle,rpBSToggle,rpEVToggle,rpDRToggle;
	private int gridIndex,rateCount;
	private Pane[] panes;
	private boolean[] pp;
	private String[][] series;
	private ArrayList<String> colors = new ArrayList();
	private ObservableList<Rate> rateList;

	public void init(){
		gridIndex = 0;
		gridToggle = false;
		rpSolarToggle = true;
		rpInvertToggle = true;
		rpBSToggle = true;
		rpEVToggle = true;
		rpDRToggle = true;
		populateColors();
		initGrid();
		initSolar();
		initRates();
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
	private void initRates(){
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
		rateList = FXCollections.observableArrayList();
		String[] arr1 = {"Non-summer", "Summer off-Peak", "Summer on-peak"};
		String[] arr2 = {"0.12", "0.16", "0.16"};
		String[] arr3 = {"0.3", "0.3", "0.3"};
		String[] arr4 = {"0.0", "0.0", "0.0"};
		for(int x = 0; x < arr1.length; x++){
			rateList.add(new Rate(arr1[x],arr2[x],arr3[x],arr4[x],colors.get(x)));
		}
		rateTable.setItems(rateList);
	}

	private void initGrid(){
		int count = 0;
		panes = new Pane[GRID_ROWS*GRID_COLUMNS];
		pp = new boolean[GRID_ROWS*GRID_COLUMNS];
		GridListener gl = new GridListener();
		for(int x = 0; x< GRID_ROWS; x++){
			for(int y = 0; y< GRID_COLUMNS; y++){
				pp[count] = false;
				Pane pane = new Pane();
				pane.setMinSize(40, 10);
				pane.setStyle("-fx-background-color:burlywood;-fx-border-color:black;");
				Button button = new Button();
				button.setId(Integer.toString(count));
				button.setMinSize(40, 10);
				button.setOpacity(0);
				button.setOnMouseReleased(gl);
				panes[count] = pane;
				rateGrid.add(pane, y, x);
				rateGrid.add(button, y, x);
				count++;
			}
		}
		InputStream is;
		try {
			LineChart<Date,Number> gridChart;
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

			ObservableList<XYChart.Series<Date, Number>> s = FXCollections.observableArrayList();
			for(int x = 1; x < 6; x++){
				ObservableList<XYChart.Data<Date, Number>> ss = FXCollections.observableArrayList();
				Date date = null; 
				for(int y = 1; y < series.length; y++){
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

			for(int x = 0; x<s.size();x++){
				s.get(x).nodeProperty().get().setStyle("-fx-stroke: "+colors.get(x)+";");
			}
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
						if(index < gridIndex || (index%12 < gridIndex%12)){
							panes[gridIndex].setStyle("-fx-background-color:burlywood;-fx-border-color:black;");
						}else{

							int col1 = gridIndex%12;
							int col2 = index%12;
							for(int x = gridIndex; x<=index; x++){
								if(x%12 >= col1 && x%12 <= col2){
									panes[x].setStyle("-fx-background-color:"+r.getColor()+";-fx-border-color:black;");
									if(ratesAllWeekBtn.isSelected()){
										panes[x].setMaxWidth(50);
										panes[x].setPrefWidth(40);
										panes[x].setMinWidth(40);
										if(pp[x]){
											panes[x].setTranslateX(0);
											pp[x] = false;
										}
									}
									if(ratesWeekdayBtn.isSelected()){
										panes[x].setMaxWidth(29);
										panes[x].setPrefWidth(29);
										panes[x].setMinWidth(29);
										if(pp[x]){
											panes[x].setTranslateX(0);
											pp[x] = false;
										}
									}
									if(ratesWeekendBtn.isSelected()){
										panes[x].setMaxWidth(11);
										panes[x].setPrefWidth(11);
										panes[x].setMinWidth(11);
										if(!pp[x]){
											panes[x].setTranslateX(30);
											pp[x] = true;
										}
									}
								}
							}
						}
						gridToggle=false;
					}
				}else{
					Rate r = rateTable.getSelectionModel().getSelectedItem();
					if(r != null){
						int index = Integer.parseInt(button.getId());
						panes[index].setStyle("-fx-background-color:gray;-fx-border-color:black;");
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
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About SRP Rate Reviewer");
		alert.setHeaderText("©NathanJohnson2015");
		alert.setContentText("Build .17\nDeveloper: Derek Hamel");
		alert.show();
	}
	@FXML
	public void newClicked(ActionEvent event) {
		//TODO: save()? and clear()
	}
	@FXML
	public void openClicked(ActionEvent event) {
		FileChooser fc = new FileChooser();
		String currentDir = System.getProperty("user.dir") + File.separator;
		File file = new File(currentDir);
		fc.setInitialDirectory(file);
		fc.showOpenDialog(null);
	}
	@FXML
	public void saveClicked(ActionEvent event) {
		FileChooser fc = new FileChooser();
		String currentDir = System.getProperty("user.dir") + File.separator;
		File file = new File(currentDir);
		fc.setInitialDirectory(file);
		fc.showSaveDialog(null);
	}
	@FXML
	public void saveAsClicked(ActionEvent event) {
		FileChooser fc = new FileChooser();
		String currentDir = System.getProperty("user.dir") + File.separator;
		File file = new File(currentDir);
		fc.setInitialDirectory(file);
		fc.showSaveDialog(null);
	}
	@FXML
	public void closeClicked(ActionEvent event) {
		System.exit(0);
	}
	// Event Listener on RadioButton[#enterMonthlyAveragesRadio].onAction
	@FXML
	public void monthlyAveragesSelected(ActionEvent event) {
		solarBrowseBtn.setDisable(true);
		hourlyDataFileTB.setDisable(true);
	}
	// Event Listener on RadioButton[#importHourlyDataRadio].onAction
	@FXML
	public void importHourlyDataSelected(ActionEvent event) {
		solarBrowseBtn.setDisable(false);
		hourlyDataFileTB.setDisable(false);
	}
	// Event Listener on Button.onAction
	@FXML
	public void hourlyDataFileButton(ActionEvent event) {

	}
	// Event Listener on Button.onAction
	@FXML
	public void ratesNewClicked(ActionEvent event) {

	}
	// Event Listener on Button.onAction
	@FXML
	public void ratesCopyClicked(ActionEvent event) {

	}
	// Event Listener on Button.onAction
	@FXML
	public void ratesDeleteClicked(ActionEvent event) {

	}
	// Event Listener on Button.onAction
	@FXML
	public void ratesAddClicked(ActionEvent event) {

	}
	// Event Listener on Button.onAction
	@FXML
	public void ratesRemoveClicked(ActionEvent event) {

	}
	// Event Listener on Button.onAction
	@FXML
	public void rpNewClicked(ActionEvent event) {

	}
	// Event Listener on Button.onAction
	@FXML
	public void rpCopyClicked(ActionEvent event) {

	}
	// Event Listener on Button.onAction
	@FXML
	public void rpDeleteClicked(ActionEvent event) {

	}
	// Event Listener on Button.onAction
	@FXML
	public void rpLoadDataClicked(ActionEvent event) {

	}
	// Event Listener on CheckBox.onAction
	@FXML
	public void rpDRDeferClicked(ActionEvent event) {

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

	}
	// Event Listener on Button.onAction
	@FXML
	public void onExportClicked(ActionEvent event) {

	}


}

