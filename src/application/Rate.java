package application;

public class Rate {
	private String rate;
	private String price;
	private String feedin;
	private String demand;
	private String color;
	private int meter;
	private double credit;
	private double charge;
	private int[][][] cells;
	
	public Rate() {
		this.rate = "";
		this.price = "";
		this.feedin = "";
		this.demand = "";
		this.color = "";
		this.meter = 0;
		this.credit = 0;
		this.charge = 0;
		this.cells = new int[12][24][2];
	}
	
	public Rate(String rate, String price, String feedin, String demand,
			String color, int meter, double credit, double charge,
			int[][][] cells) {
		this.rate = rate;
		this.price = price;
		this.feedin = feedin;
		this.demand = demand;
		this.color = color;
		this.meter = meter;
		this.credit = credit;
		this.charge = charge;
		this.cells = cells;
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
	public int[][][] getCells() {
		return cells;
	}
	public void setCells(int[][][] cells) {
		this.cells = cells;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getFeedin() {
		return feedin;
	}
	public void setFeedin(String feedin) {
		this.feedin = feedin;
	}
	public String getDemand() {
		return demand;
	}
	public void setDemand(String demand) {
		this.demand = demand;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
