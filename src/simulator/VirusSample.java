package simulator;

import controller.Controller;
import controller.Tracker;

public class VirusSample {
	private Tracker tracker;

	private double arrivalTime;
	private double departureTime;

	private int id;
	private double size;
	private int virusProbability;
	private int servicedAt;

	private static int i = 1;
	private static long sum = 0;
	
	public VirusSample(double size, int virusProbability){
		this.size = size;
		this.virusProbability = virusProbability;
		this.tracker = tracker;
	    id = i++;
	    
		arrivalTime = Clock.getInstance().getTime();
		Trace.out(Trace.Level.INFO, "Uusi asiakas:" + id + ":"+ arrivalTime);
	}

	public double getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(double departureTime) {
		this.departureTime = departureTime;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getVirusProbability(){
		return this.virusProbability;
	}

	public double getSize(){
		return this.size;
	}

	/*public int getServicedAt() {
		return this.servicedAt;
	}

	public void setServicedAt(int servicedAt) {
		System.out.println("serviced at " + this.servicedAt);
		this.tracker.setCircleStatus(servicedAt);
		this.servicedAt = servicedAt;
	}*/

	public void raportti(){
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui:" + arrivalTime);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui:" + departureTime);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi:" +(departureTime - arrivalTime));
		sum += (departureTime - arrivalTime);
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo "+ keskiarvo);
	}

}
