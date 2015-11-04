package application;

import java.io.File;

public class RateReviewerProxy {
	static {
		if(is64BitVM()){
			System.loadLibrary("rrp64");
		}else{
			System.loadLibrary("rrp");
		}
	}
	
	RateReviewerProxy(String fileName, SceneController app){
		System.out.println(initInterface(fileName));
		app.populateOutput();
	}
	private native String initInterface(String fileName);
	
	public static boolean is64BitVM() { 
        String bits = System.getProperty("sun.arch.data.model", "?");
        if (bits.equals("64")) {
            return true;
        }
        if (bits.equals("?")) {
            return System.getProperty("java.vm.name")
                    .toLowerCase().indexOf("64") >= 0;

        }
        return false;
    }
}
