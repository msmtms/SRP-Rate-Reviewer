package application;
	
import java.io.InputStream;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private final double MINIMUM_WINDOW_WIDTH = 1280;
    private final double MINIMUM_WINDOW_HEIGHT = 840;
    private Scene scene;
    private Stage stage;
    
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setMinWidth(MINIMUM_WINDOW_WIDTH);
			primaryStage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                	
                }
            });
			stage = primaryStage;
			stage.setTitle("Rate Reviewer");
			SceneController cont = (SceneController) replaceSceneContent("scene.fxml", null);
			cont.init(this);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public Scene getScene(){
		return scene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Node replaceSceneContent(String fxml, Class<? extends AnchorPane> cls) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Node cont;
        if (cls == null) {
            //System.out.println("controller was set by FXML");
        } else {
            loader.setController(cls.getConstructor().newInstance()); //set controller manually
        }
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
            cont = (Node) loader.getController();
        }finally{
        	in.close();
        }

        // Store the stage width and height in case the user has resized the window
        double stageWidth = stage.getWidth();
        if (!Double.isNaN(stageWidth)) {
            stageWidth -= (stage.getWidth() - stage.getScene().getWidth());
        }

        double stageHeight = stage.getHeight();
        if (!Double.isNaN(stageHeight)) {
            stageHeight -= (stage.getHeight() - stage.getScene().getHeight());
        }

        Scene scene = new Scene(page);
        if (!Double.isNaN(stageWidth)) {
            page.setPrefWidth(stageWidth);
        }
        if (!Double.isNaN(stageHeight)) {
            page.setPrefHeight(stageHeight);
        }

        stage.setScene(scene);
        stage.sizeToScene();
        
        return cont;
    }
}
