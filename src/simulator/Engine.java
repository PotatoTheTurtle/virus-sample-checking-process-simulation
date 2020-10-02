package simulator;

import controller.Controller;
import controller.Tracker;
import eduni.distributions.*;

import java.util.ArrayList;
import java.util.Random;

public class Engine extends Thread {

	//Entries
	private double simulointiaika = 300;
	private int probability = 10;
	private int size = 2;
	private long delay = 500L;
	//TODO: Make size min and max, we want random sample sizes not static.
	//TODO: Parametrize random generators
	//Service point time variations
	//Arrival differences
	
	private Servicepoint[] servicepoints = new Servicepoint[5];
	private Clock clock;

	private ArrivalProcess arrivalProcess;
	private EventList eventList;
	private Controller controller;

	//Stats
	private ArrayList<VirusSample> simualtedSamples = new ArrayList<>();
	private int enteredSamples = 0;
	

	public Engine(Controller controller){
		System.out.println("Engine ctor");
		Trace.setTraceLevel(Trace.Level.ERR);
		this.controller = controller;

		servicepoints[0] = new Servicepoint(0, "Virus sample submission", new Uniform(1, 5), this, EventType.DEP1, false);
		servicepoints[1] = new Servicepoint(1, "Backend scan", new Uniform(4, 8), this, EventType.DEP2, false);
		//In backend scan we want to detect if the testsample comes up as a virus (lets assume that it always will be).
		//In virus submission we dont scan anything. Thus both of them are marked as "non skippable"

		servicepoints[2] = new Servicepoint(2, "Robot verification 1", new Uniform(6, 10), this, EventType.DEP3, true);
		servicepoints[3] = new Servicepoint(3, "Robot verification 2", new Uniform(6, 10), this, EventType.DEP3, true);

		servicepoints[4] = new Servicepoint(4, "Human verification", new Uniform(5, 13), this, EventType.DEP4, true);

		clock = Clock.getInstance();
		
		arrivalProcess = new ArrivalProcess(this, new Negexp(15,5), EventType.ARR1);
		eventList = new EventList();
		arrivalProcess.generoiSeuraava(); // Ensimmäinen saapuminen!!
		
	}

	public Tracker getTracker(){
		return this.controller.getTracker();
	}

	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}

	public void slowdownTime(int time){
		this.delay += time;
	}

	public void speedupTime(int time){
		if(this.delay - time > 0){
			this.delay -= time;
		}
	}

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

		}
		tulokset();
	}
	
	void suoritaBTapahtumat(){
		while (eventList.getSeuraavanAika() == clock.getTime()){
			suoritaTapahtuma(eventList.poista());
		}
	}

	void yritaCTapahtumat(){
		for (Servicepoint p: servicepoints){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}

	
	void suoritaTapahtuma(Event event){  // Keskitetty algoritmi tapahtumien käsittelyyn ja asiakkaiden siirtymisten hallintaan

		VirusSample a;
		switch (event.getTyyppi()){
			
			case ARR1:
				//Enter submission
				Random random = new Random();
				VirusSample sample = new VirusSample(size, random.nextInt(this.probability) + 1);
				sample.setArrivalTime(this.clock.getTime());
				servicepoints[0].lisaaJonoon(sample);
				arrivalProcess.generoiSeuraava();
				this.enteredSamples += 1;
				System.out.println("asd");
				break;
			case DEP1:
				//Backend check
				a = servicepoints[0].otaJonosta();
				servicepoints[1].lisaaJonoon(a);
				break;
			case DEP2:
				//Robot verification
				a = servicepoints[1].otaJonosta();

				//Mennään aina pienimpään jonoon
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
				a.raportti();
				this.simualtedSamples.add(a);
				break;
			case SKIP:
				System.out.println("SKIP EVENT");
				break;
		}	
	}

	public void uusiTapahtuma(Event t){
		eventList.lisaa(t);
	}


	public double nykyaika(){
		return eventList.getSeuraavanAika();
	}
	
	private boolean simuloidaan(){
		Trace.out(Trace.Level.INFO, "Kello on: " + clock.getTime());
		return clock.getTime() < simulointiaika;
	}
	
	private void tulokset(){
		for(Servicepoint servicepoint : this.servicepoints){
			try {
				System.out.format("Statistics for %s: \n", servicepoint.getName());
				System.out.format("Busy time: %f \n", servicepoint.getBusyTime());
				System.out.format("Service times: %d \n", servicepoint.getCompletedServices());
				System.out.format("Utilization: %f\n", servicepoint.getBusyTime() / this.simulointiaika);
				System.out.format("Average service time: %f\n\n", servicepoint.getBusyTime() / servicepoint.getCompletedServices());
			}catch (ArithmeticException e){
				System.out.println("Cannot devide by 0.");
			}
		}

		System.out.println("Whole simulation: ");
		System.out.format("Throughput: %f\n", this.simualtedSamples.size() / this.simulointiaika);
		System.out.format("Simulation ended at: %f\n", this.clock.getTime());
		
	}
	
	
}