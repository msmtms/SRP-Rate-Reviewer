package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class RateSchedule {

	private static final int GRID_ROWS = 24;
	private static final int GRID_COLUMNS = 12;
	private String name;
	private int meter;
	private double credit;
	private double charge;
	private String[] paneColors;
	private int[] panePlacement;
	private ObservableList<Rate> rates;
	
	public RateSchedule(){
		this.meter = 0;
		this.credit = 0;
		this.charge = 0;
		paneColors = new String[GRID_ROWS*GRID_COLUMNS];
		panePlacement = new int[GRID_ROWS*GRID_COLUMNS];
		for(int x = 0; x < paneColors.length; x++){
			paneColors[x] = "burlywood";
			panePlacement[x] = 0;
		}
		rates = FXCollections.observableArrayList();
	}
	
	public RateSchedule(String name, int meter, double credit, double charge, String[] colors,
			ObservableList<Rate> rates) {
		super();
		this.name = name;
		this.meter = meter;
		this.credit = credit;
		this.charge = charge;
		this.paneColors = colors;
		this.rates = rates;
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
	public String[] getPaneColors() {
		return paneColors;
	}
	public void setPaneColors(String[] paneColors) {
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

	public int getPanePlacement(int paneIndex) {
		return panePlacement[paneIndex];
	}

	public void setPanePlacement(int paneIndex, int pos) {
		panePlacement[paneIndex] = pos;
	}
	public void setPaneColor(int paneIndex, String color){
		paneColors[paneIndex] = color;
	}
	public String getPaneColor(int paneIndex){
		return paneColors[paneIndex];
	}
	public int[] getPanePlacementList(){
		return panePlacement;
	}
}
