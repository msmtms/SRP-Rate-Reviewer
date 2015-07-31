package application;

public class SolarMonthly {
	private String month;
	private String GHI;
	private String DNI;
	private String clearness;
	
	public SolarMonthly(){
		month = "";
		GHI = "";
		DNI = "";
		clearness = "";
	}
	

	public SolarMonthly(String month, String gHI, String dNI, String clearness) {
		super();
		this.month = month;
		GHI = gHI;
		DNI = dNI;
		this.clearness = clearness;
	}


	public String getGHI() {
		return GHI;
	}


	public void setGHI(String gHI) {
		GHI = gHI;
	}


	public String getDNI() {
		return DNI;
	}


	public void setDNI(String dNI) {
		DNI = dNI;
	}


	public String getClearness() {
		return clearness;
	}


	public void setClearness(String clearness) {
		this.clearness = clearness;
	}


	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}

}
