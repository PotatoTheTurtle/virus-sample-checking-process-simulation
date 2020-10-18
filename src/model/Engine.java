package model;

import controller.AdvancedSettingsController;
import controller.MainController;
import controller.SimulatorController;
import controller.Tracker;

import java.util.Random;

/**
 * The Engine
 * This is the center point of the simulator
 * The engine creates service points and moves events in order
 *
 * @author Ivan Turbin
 */
public class Engine extends Thread {

	//Entries
	private double simulationTime;
	private int probability;
	private double size;
	private long delay = 500L;
	
	private Servicepoint[] servicepoints = new Servicepoint[5];
	private Clock clock;

	private ArrivalProcess arrivalProcess;
	private EventList eventList;
	private SimulatorController simulatorController;
	private AdvancedSettingsController advancedSettingsController;
	private MainController mainController;


	/**
	 * Instantiates a new Engine.
	 * Creates 5 service points
	 * Sets clock
	 * Generates first arrival
	 *
	 * @param simulatorController the simulator controller
	 */
	public Engine(SimulatorController simulatorController){
		Trace.setTraceLevel(Trace.Level.ERR);
		this.simulatorController = simulatorController;
		this.mainController = simulatorController.getMainController();
		this.advancedSettingsController = simulatorController.getAdvancedSettingsController();
		this.simulationTime = this.mainController.getSimulationTime();
		this.probability = this.mainController.getVirusProbability();
		this.size = this.mainController.getSampleSize();

		servicepoints[0] = new Servicepoint(0, "Virus sample submission", this.advancedSettingsController.getVirusSampleSubmissionGenerator(), this, EventType.DEP1, false);
		servicepoints[1] = new Servicepoint(1, "Backend scan", this.advancedSettingsController.getBackendScanGenerator(), this, EventType.DEP2, false);
		//In backend scan we want to detect if the testsample comes up as a virus (lets assume that it always will be).
		//In virus submission we dont scan anything. Thus both of them are marked as "non skippable"

		servicepoints[2] = new Servicepoint(2, "Robot verification 1", this.advancedSettingsController.getRobotVerify1Generator(), this, EventType.DEP3, true);
		servicepoints[3] = new Servicepoint(3, "Robot verification 2", this.advancedSettingsController.getRobotVerify2Generator(), this, EventType.DEP3, true);
		servicepoints[4] = new Servicepoint(4, "Human verification", this.advancedSettingsController.getHumanVerificationGenerator(), this, EventType.DEP4, true);

		clock = Clock.getInstance();
		
		arrivalProcess = new ArrivalProcess(this, this.advancedSettingsController.getSubmissionGenerator(), EventType.ARR1);
		eventList = new EventList();
		arrivalProcess.generateNext(); // Ensimmäinen saapuminen!!
		
	}

	/**
	 * Get tracker object from simulator controller.
	 *
	 * @return the tracker
	 */
	public Tracker getTracker(){
		return this.simulatorController.getTracker();
	}

	/**
	 * Slowdown time.
	 *
	 * @param time the time to slowdown by
	 */
	public void slowdownTime(int time){
		this.delay += time;
	}

	/**
	 * Speedup time.
	 *
	 * @param time the time to speedup by
	 */
	public void speedupTime(int time){
		if(this.delay - time > 0){
			this.delay -= time;
		}
	}

	private void delay(){
		try {
			sleep(this.delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run(){
		while (simulating()){
			this.delay();
			clock.setTime(currentTime());
			completeBEvents();
			tryCEvents();
			this.simulatorController.updateSimulationTime(clock.getTime());
		}
		results();
		clock.setTime(0); //Reset to 0 incase of second run.
		this.simulatorController.showNextButton();
	}

	private void completeBEvents(){
		while (eventList.getNextTime() == clock.getTime()){
			completeEvent(eventList.deleteEvent());
		}
	}

	private void tryCEvents(){
		for (Servicepoint p: servicepoints){
			if (!p.isInUse() && p.isInQueue()){
				p.startService();
			}
		}
	}

	private void completeEvent(Event event){

		VirusSample virusSample;
		switch (event.getType()){
			
			case ARR1:
				//Enter submission
				Random random = new Random();
				VirusSample sample = new VirusSample(this.size, random.nextInt(this.probability) + 1);
				sample.setArrivalTime(this.clock.getTime());
				servicepoints[0].addToQueue(sample);
				arrivalProcess.generateNext();
				break;
			case DEP1:
				//Backend check
				virusSample = servicepoints[0].takeFromQueue();
				servicepoints[1].addToQueue(virusSample);
				break;
			case DEP2:
				//Robot verification
				virusSample = servicepoints[1].takeFromQueue();

				//Mennään aina pienimpään jonoon
				//Check jos palvelu on vapaana ja jos on niin mene sinne jonoon
				if(servicepoints[2].getQueueSize() > servicepoints[3].getQueueSize()){
					servicepoints[3].addToQueue(virusSample);
				}else{
					servicepoints[2].addToQueue(virusSample);
				}

				break;
			case DEP3:
				//Human verification
				if(servicepoints[2].getQueueSize() > servicepoints[3].getQueueSize()){
					virusSample = servicepoints[2].takeFromQueue();
				}else{
					virusSample = servicepoints[3].takeFromQueue();
				}

				servicepoints[4].addToQueue(virusSample);
				break;
			case DEP4:
				//Stats
				servicepoints[4].takeFromQueue();
				break;
			case SKIP:
				System.out.println("SKIP EVENT");
				break;
		}	
	}

	/**
	 * Add new event to the event list.
	 *
	 * @param event the event to add.
	 */
	public void newEvent(Event event){
		eventList.add(event);
	}

	private double currentTime(){
		return eventList.getNextTime();
	}
	
	private boolean simulating(){
		Trace.out(Trace.Level.INFO, "Time is: " + clock.getTime());
		return clock.getTime() < simulationTime;
	}
	
	private void results(){
		System.out.println(clock.getTime());
		ServicePointStatistic[] servicePointStatistics = new ServicePointStatistic[this.servicepoints.length];
		for(int i = 0; i < this.servicepoints.length; i++){
			Servicepoint servicepoint = this.servicepoints[i];
			servicePointStatistics[i] = new ServicePointStatistic(servicepoint.getName(), servicepoint.getBusyTime(), servicepoint.getCompletedServices(),
					servicepoint.getBusyTime() / this.simulationTime, servicepoint.getBusyTime() / servicepoint.getCompletedServices());
		}
		this.simulatorController.saveStatistics((int) this.simulationTime, servicePointStatistics);
	}
}