package application;

public class CalcProcess implements Runnable{
	private String fileName;
	private SceneController app;
	
	public CalcProcess(String fileName, SceneController app){
		this.fileName = fileName;
		this.app = app;
	}

	@Override
	public void run() {
		RateReviewerProxy rrp = new RateReviewerProxy(fileName, app);
	}

}
