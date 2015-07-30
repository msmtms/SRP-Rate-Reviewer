package application;

import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SceneController extends AnchorPane{
	@FXML
	private AnchorPane AnchorPane;
	@FXML
	private ToggleGroup solarone;
	@FXML
	private ToggleGroup solartwo;
	@FXML
	private ToggleGroup solarthree;
	@FXML
	private ToggleGroup netmetering;
	@FXML
	private ToggleGroup ratesweek;
	@FXML
	private GridPane rateGrid;
	@FXML
	private Pane gridGraphPane;
	@FXML
	private CheckBox tieredRateCB;
	@FXML
	private CheckBox timeOfUseCB;
	@FXML
	private CheckBox criticalPeakCB;
	@FXML
	private CheckBox netGridNonSolarCB;
	@FXML
	private CheckBox netGridSolarCB;

	private static final int GRID_ROWS = 24;
	private static final int GRID_COLUMNS = 12;
	private boolean gridToggle;
	private int gridIndex;
	private Pane[] panes;
	private String[][] series;
	private String[] colors = {"pink","green","blue","purple", "red"};
	
	public void init(){
		gridIndex = 0;
		gridToggle = false;
		int count = 0;
		panes = new Pane[GRID_ROWS*GRID_COLUMNS];
		GridListener gl = new GridListener();
		for(int x = 0; x< GRID_ROWS; x++){
			for(int y = 0; y< GRID_COLUMNS; y++){
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
			gridChart.getXAxis().setTickLabelRotation(80);
			gridChart.getXAxis().setTickLabelFont(new Font("Arial", 14));
			gridChart.setLegendVisible(false);
			gridChart.setCreateSymbols(false);
			gridChart.setMinHeight(392);
			gridChart.setMinWidth(908);
			gridGraphPane.getChildren().add(gridChart);
			tieredRateCB.setStyle("-fx-text-fill:"+colors[0]+";");
			timeOfUseCB.setStyle("-fx-text-fill:"+colors[1]+";");
			criticalPeakCB.setStyle("-fx-text-fill:"+colors[2]+";");
			netGridNonSolarCB.setStyle("-fx-text-fill:"+colors[3]+";");
			netGridSolarCB.setStyle("-fx-text-fill:"+colors[4]+";");
			
			for(int x = 0; x<s.size();x++){
				s.get(x).nodeProperty().get().setStyle("-fx-stroke: "+colors[x]+";");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
					int index = Integer.parseInt(button.getId());
					if(index < gridIndex || (index%12 < gridIndex%12)){
						panes[gridIndex].setStyle("-fx-background-color:burlywood;-fx-border-color:black;");
					}else{
						int col1 = gridIndex%12;
						int col2 = index%12;
						for(int x = gridIndex; x<=index; x++){
							if(x%12 >= col1 && x%12 <= col2){
								panes[x].setStyle("-fx-background-color:green;-fx-border-color:black;");
							}
						}
					}
					gridToggle=false;
				}else{
					int index = Integer.parseInt(button.getId());
					panes[index].setStyle("-fx-background-color:gray;-fx-border-color:black;");
					gridIndex = index;
					gridToggle = true;
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
}

