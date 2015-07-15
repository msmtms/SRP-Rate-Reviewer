package application;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

public class RatepayersController extends AnchorPane {
	@FXML
	private LineChart ratepayersGraph;

	public void init(String[][] series){
		try{
			ratepayersGraph.getData().clear();
			final NumberAxis xAxis = new NumberAxis();
			final NumberAxis yAxis = new NumberAxis();
			yAxis.setLabel("Load (kW)");

			for(int x = 1; x < 6; x++){
				XYChart.Series s = new XYChart.Series();
				switch(x){
				case 1:{
					s.setName("Tiered Rate");
					break;
				}
				case 2:{
					s.setName("Time-of-Use");
					break;
				}
				case 3:{
					s.setName("Critical Peak");
					break;
				}
				case 4:{
					s.setName("Net Grid (with solar)");
					break;
				}
				case 5:{
					s.setName("Net Grid (without solar)");
					break;
				}
				}
				String oldDate = "";
				for(int y = 1; y < series.length; y++){
					if(series[y][x] != null){
						String date = series[y][0];
						s.getData().add(new XYChart.Data(date,Double.parseDouble(series[y][x])));
					}else{
						break;
					}
				}
				ratepayersGraph.getData().add(s);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
