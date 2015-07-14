package application;

import java.io.IOException;
import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

	private static final int GRID_ROWS = 24;
	private static final int GRID_COLUMNS = 12;
	private static boolean gridToggle;
	private static int gridIndex;
	private Pane[] panes;
	
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
	}
	
	@FXML
    public void showRatepayersGraph() {
		try {
			FXMLLoader loader = new FXMLLoader();
	        Node cont;
	        InputStream in = Main.class.getResourceAsStream("ratepayers.fxml");
	        loader.setBuilderFactory(new JavaFXBuilderFactory());
	        loader.setLocation(Main.class.getResource("ratepayers.fxml"));
	        AnchorPane page;
	        try {
	            page = (AnchorPane) loader.load(in);
	            cont = (Node) loader.getController();
	        }finally{
	        	in.close();
	        }
            Stage stage = new Stage();
            stage.setTitle("Ratepayers");
            stage.setScene(new Scene(page, 1280, 720));
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
}

