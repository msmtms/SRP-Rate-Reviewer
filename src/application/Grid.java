package application;

public class Grid {
	private String name;
	private double totalCharges;
	private double interconCharges;
	private double energyCharges;
	private double demandCharges;
	private double energyPurchased;
	private double energySold;
	private double netPurchases;
	private double peakLoad;
	private double averageLoad;
	private double energyUseDay;
	private double energyUseYear;
	private double loadFactor;
	
	public Grid() {
		this.name = "";
		this.totalCharges = 0;
		this.interconCharges = 0;
		this.energyCharges = 0;
		this.demandCharges = 0;
		this.energyPurchased = 0;
		this.energySold = 0;
		this.netPurchases = 0;
		this.peakLoad = 0;
		this.averageLoad = 0;
		this.energyUseDay = 0;
		this.energyUseYear = 0;
		this.loadFactor = 0;
	}

	public Grid(String name, double totalCharges, double interconCharges,
			double energyCharges, double demandCharges, double energyPurchased,
			double energySold, double netPurchases, double peakLoad,
			double averageLoad, double energyUseDay, double energyUseYear,
			double loadFactor) {
		super();
		this.name = name;
		this.totalCharges = totalCharges;
		this.interconCharges = interconCharges;
		this.energyCharges = energyCharges;
		this.demandCharges = demandCharges;
		this.energyPurchased = energyPurchased;
		this.energySold = energySold;
		this.netPurchases = netPurchases;
		this.peakLoad = peakLoad;
		this.averageLoad = averageLoad;
		this.energyUseDay = energyUseDay;
		this.energyUseYear = energyUseYear;
		this.loadFactor = loadFactor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(double totalCharges) {
		this.totalCharges = totalCharges;
	}

	public double getInterconCharges() {
		return interconCharges;
	}

	public void setInterconCharges(double interconCharges) {
		this.interconCharges = interconCharges;
	}

	public double getEnergyCharges() {
		return energyCharges;
	}

	public void setEnergyCharges(double energyCharges) {
		this.energyCharges = energyCharges;
	}

	public double getDemandCharges() {
		return demandCharges;
	}

	public void setDemandCharges(double demandCharges) {
		this.demandCharges = demandCharges;
	}

	public double getEnergyPurchased() {
		return energyPurchased;
	}

	public void setEnergyPurchased(double energyPurchased) {
		this.energyPurchased = energyPurchased;
	}

	public double getEnergySold() {
		return energySold;
	}

	public void setEnergySold(double energySold) {
		this.energySold = energySold;
	}

	public double getNetPurchases() {
		return netPurchases;
	}

	public void setNetPurchases(double netPurchases) {
		this.netPurchases = netPurchases;
	}

	public double getPeakLoad() {
		return peakLoad;
	}

	public void setPeakLoad(double peakLoad) {
		this.peakLoad = peakLoad;
	}

	public double getAverageLoad() {
		return averageLoad;
	}

	public void setAverageLoad(double averageLoad) {
		this.averageLoad = averageLoad;
	}

	public double getEnergyUseDay() {
		return energyUseDay;
	}

	public void setEnergyUseDay(double energyUseDay) {
		this.energyUseDay = energyUseDay;
	}

	public double getEnergyUseYear() {
		return energyUseYear;
	}

	public void setEnergyUseYear(double energyUseYear) {
		this.energyUseYear = energyUseYear;
	}

	public double getLoadFactor() {
		return loadFactor;
	}

	public void setLoadFactor(double loadFactor) {
		this.loadFactor = loadFactor;
	}
}
