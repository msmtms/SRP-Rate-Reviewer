package application;

public class RatepayerGroup {

	private String name;
	private String num;
	private int rateSchedule;
	private String loadFile;
	private double[] solarPV;
	private double[] inverter;
	private double[] batteryStorage;
	private double[] electricVehicle;
	private double[] demandResponse;
	private double[] loadOut;
	private double[] solarOut;
	private double[] invertOut;
	private double[] bsOut;
	private double[] evOut;
	private double[] drOut;
	private double[] interconOut;
	private double[] gridOut;
	
	public RatepayerGroup(){
		name = "";
		num = "";
		rateSchedule = -1;
		loadFile = "";
		solarPV = new double[3];
		for(int x = 0; x < solarPV.length; x++){
			solarPV[x] = 0;
		}
		inverter = new double[2];
		for(int x = 0; x < inverter.length; x++){
			inverter[x] = 0;
		}
		batteryStorage = new double[4];
		for(int x = 0; x < batteryStorage.length; x++){
			batteryStorage[x] = 0;
		}
		electricVehicle = new double[8];
		for(int x = 0; x < electricVehicle.length; x++){
			electricVehicle[x] = 0;
		}
		demandResponse = new double[2];
		for(int x = 0; x < demandResponse.length; x++){
			demandResponse[x] = 0;
		}
		loadOut = new double[5];
		for(int x = 0; x < loadOut.length; x++){
			loadOut[x] = 0;
		}
		solarOut = new double[5];
		for(int x = 0; x < solarOut.length; x++){
			solarOut[x] = 0;
		}
		invertOut = new double[4];
		for(int x = 0; x < invertOut.length; x++){
			invertOut[x] = 0;
		}
		bsOut = new double[4];
		for(int x = 0; x < bsOut.length; x++){
			bsOut[x] = 0;
		}
		evOut = new double[4];
		for(int x = 0; x < evOut.length; x++){
			evOut[x] = 0;
		}
		drOut = new double[2];
		for(int x = 0; x < drOut.length; x++){
			drOut[x] = 0;
		}
		interconOut = new double[12];
		for(int x = 0; x < interconOut.length; x++){
			interconOut[x] = 0;
		}
		gridOut = new double[12];
		for(int x = 0; x < gridOut.length; x++){
			gridOut[x] = 0;
		}
	}
	
	public RatepayerGroup(String name, String num, int rateSchedule, String loadFile, double[] solarPV,
			double[] inverter, double[] batteryStorage,
			double[] electricVehicle, double[] demandResponse) {
		this.name = name;
		this.num = num;
		this.rateSchedule = rateSchedule;
		this.loadFile = loadFile;
		this.solarPV = solarPV;
		this.inverter = inverter;
		this.batteryStorage = batteryStorage;
		this.electricVehicle = electricVehicle;
		this.demandResponse = demandResponse;
		loadOut = new double[5];
		for(int x = 0; x < loadOut.length; x++){
			loadOut[x] = 0;
		}
		solarOut = new double[5];
		for(int x = 0; x < solarOut.length; x++){
			solarOut[x] = 0;
		}
		invertOut = new double[4];
		for(int x = 0; x < invertOut.length; x++){
			invertOut[x] = 0;
		}
		bsOut = new double[4];
		for(int x = 0; x < bsOut.length; x++){
			bsOut[x] = 0;
		}
		evOut = new double[4];
		for(int x = 0; x < evOut.length; x++){
			evOut[x] = 0;
		}
		drOut = new double[2];
		for(int x = 0; x < drOut.length; x++){
			drOut[x] = 0;
		}
		interconOut = new double[12];
		for(int x = 0; x < interconOut.length; x++){
			interconOut[x] = 0;
		}
		gridOut = new double[12];
		for(int x = 0; x < gridOut.length; x++){
			gridOut[x] = 0;
		}
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

	public double[] getLoadOut() {
		return loadOut;
	}

	public void setLoadOut(double[] loadOut) {
		this.loadOut = loadOut;
	}

	public double[] getSolarOut() {
		return solarOut;
	}

	public void setSolarOut(double[] solarOut) {
		this.solarOut = solarOut;
	}

	public double[] getInvertOut() {
		return invertOut;
	}

	public void setInvertOut(double[] invertOut) {
		this.invertOut = invertOut;
	}

	public double[] getBsOut() {
		return bsOut;
	}

	public void setBsOut(double[] bsOut) {
		this.bsOut = bsOut;
	}

	public double[] getEvOut() {
		return evOut;
	}

	public void setEvOut(double[] evOut) {
		this.evOut = evOut;
	}

	public double[] getDrOut() {
		return drOut;
	}

	public void setDrOut(double[] drOut) {
		this.drOut = drOut;
	}

	public double[] getInterconOut() {
		return interconOut;
	}

	public void setInterconOut(double[] interconOut) {
		this.interconOut = interconOut;
	}

	public int getRateSchedule() {
		return rateSchedule;
	}

	public void setRateSchedule(int rateSchedule) {
		this.rateSchedule = rateSchedule;
	}

	public String getLoadFile() {
		return loadFile;
	}

	public void setLoadFile(String loadFile) {
		this.loadFile = loadFile;
	}

	public double[] getGridOut() {
		return gridOut;
	}

	public void setGridOut(double[] gridOut) {
		this.gridOut = gridOut;
	}
	

}
