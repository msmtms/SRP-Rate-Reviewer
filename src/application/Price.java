package application;

public class Price {
	private String value;
	private String threshold;
	
	public Price(){
		value = "0";
		threshold = "0";
	}

	public Price(String value, String threshold) {
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
