package simulator;

public class Clock {

	private double aika;
	private static Clock instance;
	
	private Clock(){
		aika = 0;
	}
	
	public static Clock getInstance(){
		if (instance == null){
			instance = new Clock();
		}
		return instance;
	}
	
	public void setTime(double aika){
		this.aika = aika;
	}

	public double getTime(){
		return aika;
	}
}
