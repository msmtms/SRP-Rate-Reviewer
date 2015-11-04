package application;

public class SummaryStatistics {

	private double mean;
    private double min;
    private double max;
    private double total;
    
    public SummaryStatistics(){
		mean=-1; min=-1; max=-1; total=-1; operatingTime=0;
	}
    
    public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public long getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(long operatingTime) {
		this.operatingTime = operatingTime;
	}

	private long operatingTime; // non-zero values in array denote operating time
    
    
}
