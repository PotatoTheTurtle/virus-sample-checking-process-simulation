package model;

/**
 * The type Virus sample.
 */
public class VirusSample {

	private double arrivalTime;

	private int id;
	private double size;
	private int virusProbability;

	private static int i = 1;

	/**
	 * Instantiates a new Virus sample.
	 *
	 * @param size             the size of the virus sample.
	 * @param virusProbability the chanse that the sample is an actual virus, 0-100
	 */
	public VirusSample(double size, int virusProbability){
		this.size = size;
		this.virusProbability = virusProbability;
	    id = i++;

		arrivalTime = Clock.getInstance().getTime();
		Trace.out(Trace.Level.INFO, "New virus sample:" + id + ":"+ arrivalTime);
	}

	/**
	 * Sets arrival time of the virus sample.
	 *
	 * @param arrivalTime the arrival time
	 */
	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Get virus probability int.
	 *
	 * @return the virus probability
	 */
	public int getVirusProbability(){
		return this.virusProbability;
	}

	/**
	 * Get virus size
	 *
	 * @return virus size as double
	 */
	public double getSize(){
		return this.size;
	}
}
