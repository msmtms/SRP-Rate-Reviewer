package application;

public class RatepayerGroup {

	private String name;
	private String num;
	private double[] solarPV;
	private double[] inverter;
	private double[] batteryStorage;
	private double[] electricVehicle;
	private double[] demandResponse;
	
	public RatepayerGroup(){
		name = "";
		num = "";
		solarPV = new double[3];
		inverter = new double[2];
		batteryStorage = new double[4];
		electricVehicle = new double[8];
		demandResponse = new double[2];
	}
	
	public RatepayerGroup(String name, String num, double[] solarPV,
			double[] inverter, double[] batteryStorage,
			double[] electricVehicle, double[] demandResponse) {
		this.name = name;
		this.num = num;
		this.solarPV = solarPV;
		this.inverter = inverter;
		this.batteryStorage = batteryStorage;
		this.electricVehicle = electricVehicle;
		this.demandResponse = demandResponse;
	}
	public double[] getSolarPV() {
		return solarPV;
	}
	public void setSolarPV(double[] solarPV) {
		this.solarPV = solarPV;
	}
	public double[] getInverter() {
		return inverter;
	}
	public void setInverter(double[] inverter) {
		this.inverter = inverter;
	}
	public double[] getBatteryStorage() {
		return batteryStorage;
	}
	public void setBatteryStorage(double[] batteryStorage) {
		this.batteryStorage = batteryStorage;
	}
	public double[] getElectricVehicle() {
		return electricVehicle;
	}
	public void setElectricVehicle(double[] electricVehicle) {
		this.electricVehicle = electricVehicle;
	}
	public double[] getDemandResponse() {
		return demandResponse;
	}
	public void setDemandResponse(double[] demandResponse) {
		this.demandResponse = demandResponse;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public void setSolarPVItem(int index, double value){
		solarPV[index] = value;
	}
	public double getSolarPVItem(int index){
		return solarPV[index];
	}
	public void setInvertItem(int index, double value){
		inverter[index] = value;
	}
	public double getInvertItem(int index){
		return inverter[index];
	}
	public void setBSItem(int index, double value){
		batteryStorage[index] = value;
	}
	public double getBSItem(int index){
		return batteryStorage[index];
	}
	public void setEVItem(int index, double value){
		electricVehicle[index] = value;
	}
	public double getEVItem(int index){
		return electricVehicle[index];
	}
	public void setDRItem(int index, double value){
		demandResponse[index] = value;
	}
	public double getDRItem(int index){
		return demandResponse[index];
	}
}
