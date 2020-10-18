package model;

/**
 * Singleton clock class used for keeping track of simulation time.
 */
public class Clock {

	private double time;
	private static Clock instance;
	
	private Clock(){
		time = 0;
	}

	/**
	 * Get instance clock.
	 *
	 * @return the clock
	 */
	public static Clock getInstance(){
		if (instance == null){
			instance = new Clock();
		}
		return instance;
	}

	/**
	 * Set time.
	 *
	 * @param time the time
	 */
	public void setTime(double time){
		this.time = time;
	}

	/**
	 * Get time.
	 *
	 * @return get time as double.
	 */
	public double getTime(){
		return time;
	}
}
