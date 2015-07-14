package application;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
	private Pane[] panes;
	public void init(){
		int count = 0;
		panes = new Pane[GRID_ROWS*GRID_COLUMNS];
		GridListener gl = new GridListener();
		for(int x = 0; x< GRID_ROWS; x++){
			for(int y = 0; y< GRID_COLUMNS; y++){
				Pane pane = new Pane();
				pane.setMinSize(40, 10);
				pane.setStyle("-fx-background-color:pink;-fx-border-color:black;");
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
	
	class GridListener implements EventHandler<Event>{

		@Override
		public void handle(Event event) {
			if(event.getEventType() == MouseEvent.MOUSE_RELEASED){
				Button button = (Button)event.getSource();
				int index = Integer.parseInt(button.getId());
				panes[index].setStyle("-fx-background-color:gray;-fx-border-color:black;");
			}
			
		}


		
	}
}

