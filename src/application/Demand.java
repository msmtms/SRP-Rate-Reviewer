package application;

public class Demand {
	
	private String value;
	private String threshold;
	
	public Demand(){
		value = "0";
		threshold = "0";
	}

	public Demand(String value, String threshold) {
		super();
		this.value = value;
		this.threshold = threshold;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
}
