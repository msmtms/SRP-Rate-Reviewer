package application;

public class Rate {
	private String rate;
	private String price;
	private String feedin;
	private String demand;
	private String color;
	
	public Rate() {
		this.rate = "";
		this.price = "";
		this.feedin = "";
		this.demand = "";
		this.color = "";
	}
	
	public Rate(String rate, String price, String feedin, String demand,
			String color) {
		this.rate = rate;
		this.price = price;
		this.feedin = feedin;
		this.demand = demand;
		this.color = color;
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
