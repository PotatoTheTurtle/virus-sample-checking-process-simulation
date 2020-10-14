package model;

import controller.AdvancedSettingsController;
import controller.MainController;
import controller.SimulatorController;
import controller.Tracker;

import java.util.ArrayList;
import java.util.Random;

/**
 * The type Engine.
 *
 * @author Ivan Turbin
 */
public class Engine extends Thread {

	//Entries
	private double simulointiaika;
	private int probability;
	private double size;
	private long delay = 500L;
	//Service point time variations
	//Arrival differences
	
	private Servicepoint[] servicepoints = new Servicepoint[5];
	private Clock clock;

	private ArrivalProcess arrivalProcess;
	private EventList eventList;
	private SimulatorController simulatorController;
	private AdvancedSettingsController advancedSettingsController;
	private MainController mainController;

	//Stats
	private ArrayList<VirusSample> simualtedSamples = new ArrayList<>();
	private int enteredSamples = 0;


	/**
	 * Instantiates a new Engine.
	 *
	 * @param simulatorController the simulator controller
	 */
	public Engine(SimulatorController simulatorController){
		System.out.println("Engine ctor");
		Trace.setTraceLevel(Trace.Level.ERR);
		this.simulatorController = simulatorController;
		this.mainController = simulatorController.getMainController();
		this.advancedSettingsController = simulatorController.getAdvancedSettingsController();
		this.simulointiaika = this.mainController.getSimulationTime();
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
		arrivalProcess.generoiSeuraava(); // Ensimm�inen saapuminen!!
		
	}

	/**
	 * Get tracker tracker.
	 *
	 * @return the tracker
	 */
	public Tracker getTracker(){
		return this.simulatorController.getTracker();
	}

	/**
	 * Sets simulointiaika.
	 *
	 * @param aika the aika
	 */
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}

	/**
	 * Slowdown time.
	 *
	 * @param time the time
	 */
	public void slowdownTime(int time){
		this.delay += time;
	}

	/**
	 * Speedup time.
	 *
	 * @param time the time
	 */
	public void speedupTime(int time){
		if(this.delay - time > 0){
			this.delay -= time;
		}
	}

	/**
	 * Delay.
	 */
	public void delay(){
		try {
			sleep(this.delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while (simuloidaan()){
			this.delay();
			clock.setTime(nykyaika());
			suoritaBTapahtumat();
			yritaCTapahtumat();
			this.simulatorController.updateSimulationTime(clock.getTime());
		}
		tulokset();
		clock.setTime(0); //Reset to 0 incase of second run.
		this.simulatorController.showNextButton();
	}

	/**
	 * Suorita b tapahtumat.
	 */
	void suoritaBTapahtumat(){
		while (eventList.getSeuraavanAika() == clock.getTime()){
			suoritaTapahtuma(eventList.poista());
		}
	}

	/**
	 * Yrita c tapahtumat.
	 */
	void yritaCTapahtumat(){
		for (Servicepoint p: servicepoints){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}


	/**
	 * Suorita tapahtuma.
	 *
	 * @param event the event
	 */
	void suoritaTapahtuma(Event event){

		VirusSample a;
		switch (event.getTyyppi()){
			
			case ARR1:
				//Enter submission
				Random random = new Random();
				VirusSample sample = new VirusSample(this.size, random.nextInt(this.probability) + 1);
				sample.setArrivalTime(this.clock.getTime());
				servicepoints[0].lisaaJonoon(sample);
				arrivalProcess.generoiSeuraava();
				this.enteredSamples += 1;
				break;
			case DEP1:
				//Backend check
				a = servicepoints[0].otaJonosta();
				servicepoints[1].lisaaJonoon(a);
				break;
			case DEP2:
				//Robot verification
				a = servicepoints[1].otaJonosta();

				//Menn��n aina pienimp��n jonoon
				//Check jos palvelu on vapaana ja jos on niin mene sinne jonoon
				if(servicepoints[2].getJonoSize() > servicepoints[3].getJonoSize()){
					servicepoints[3].lisaaJonoon(a);
				}else{
					servicepoints[2].lisaaJonoon(a);
				}

				break;
			case DEP3:
				//Human verification
				if(servicepoints[2].getJonoSize() > servicepoints[3].getJonoSize()){
					a = servicepoints[2].otaJonosta();
				}else{
					a = servicepoints[3].otaJonosta();
				}

				servicepoints[4].lisaaJonoon(a);
				break;
			case DEP4:
				//Stats
				a = servicepoints[4].otaJonosta();
				a.setDepartureTime(clock.getTime());
				this.simualtedSamples.add(a);
				break;
			case SKIP:
				System.out.println("SKIP EVENT");
				break;
		}	
	}

	/**
	 * Uusi tapahtuma.
	 *
	 * @param t the t
	 */
	public void uusiTapahtuma(Event t){
		eventList.lisaa(t);
	}

	/**
	 * Nykyaika double.
	 *
	 * @return the double
	 */
	public double nykyaika(){
		return eventList.getSeuraavanAika();
	}
	
	private boolean simuloidaan(){
		Trace.out(Trace.Level.INFO, "Kello on: " + clock.getTime());
		return clock.getTime() < simulointiaika;
	}
	
	private void tulokset(){
		System.out.println(clock.getTime());
		ServicePointStatistic[] servicePointStatistics = new ServicePointStatistic[this.servicepoints.length];
		for(int i = 0; i < this.servicepoints.length; i++){
			Servicepoint servicepoint = this.servicepoints[i];
			servicePointStatistics[i] = new ServicePointStatistic(servicepoint.getName(), servicepoint.getBusyTime(), servicepoint.getCompletedServices(),
					servicepoint.getBusyTime() / this.simulointiaika, servicepoint.getBusyTime() / servicepoint.getCompletedServices());
		}
		this.simulatorController.saveStatistics((int) this.simulointiaika, servicePointStatistics);
	}
}