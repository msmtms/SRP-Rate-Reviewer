package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class RateSchedule {

	private static final int GRID_ROWS = 24;
	private static final int GRID_COLUMNS = 12;
	private static final int GRID_PANES = 4;
	private static final int GRID_INDICES = 2;
	private String name;
	private int meter;
	private double credit;
	private double charge;
	private String[][] paneColors;
	private int[][] schedule;
	private ObservableList<Rate> rates;

	public RateSchedule(){
		this.meter = 0;
		this.credit = 0;
		this.charge = 0;
		paneColors = new String[GRID_ROWS*GRID_COLUMNS][GRID_PANES];
		schedule = new int[GRID_ROWS*GRID_COLUMNS][GRID_INDICES];
		for(int x = 0; x < paneColors.length; x++){
			for(int y = 0; y < paneColors[0].length; y++){
				paneColors[x][y] = "burlywood";
				if(y == 3){
					paneColors[x][y] = "gray";
				}
			}
			for(int y = 0; y < schedule[0].length; y++){
				schedule[x][y] = -1;
			}
		}
		rates = FXCollections.observableArrayList();
	}

	public RateSchedule(String name, int meter, double credit, double charge, String[][] colors, int[][] schedule,
			ObservableList<Rate> rates) {
		super();
		this.name = name;
		this.meter = meter;
		this.credit = credit;
		this.charge = charge;
		this.paneColors = colors;
		this.rates = rates;
		this.schedule = schedule;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMeter() {
		return meter;
	}
	public void setMeter(int meter) {
		this.meter = meter;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public double getCharge() {
		return charge;
	}
	public void setCharge(double charge) {
		this.charge = charge;
	}
	public String[][] getPaneColors() {
		return paneColors;
	}
	public void setPaneColors(String[][] paneColors) {
		this.paneColors = paneColors;
	}
	public ObservableList<Rate> getRates() {
		return rates;
	}
	public void setRates(ObservableList<Rate> rates) {
		this.rates = rates;
	}
	public void addRate(Rate rate){
		rates.add(rate);
	}
	public void removeRate(Rate rate){
		rates.remove(rate);
	}
	public void setPaneColor(int paneIndex, int pane, String color){
		paneColors[paneIndex][pane] = color;
	}
	public String getPaneColor(int paneIndex, int pane){
		return paneColors[paneIndex][pane];
	}

	public int[][] getSchedule() {
		return schedule;
	}
	public int[] getSchedule(int index) {
		return schedule[index];
	}

	public void setSchedule(int[][] schedule) {
		this.schedule = schedule;
	}
	
	public void setSchedule(int index, int val1, int val2){
		if(val1 >= -1){
			schedule[index][0] = val1;
		}
		if(val2 >= -1){
			schedule[index][1] = val2;
		}
	}
	
}
