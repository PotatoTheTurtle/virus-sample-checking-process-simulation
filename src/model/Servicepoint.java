package model;

import java.util.LinkedList;
import java.util.Random;

import controller.Tracker;
import eduni.distributions.ContinuousGenerator;


public class Servicepoint {

	private Engine engine;

	private LinkedList<VirusSample> jono = new LinkedList<>();
	private ContinuousGenerator generator;
	private EventType scheduledEventType;

	private String name;
	private double busyTime = 0;
	private int completedServices = 0;
	private int serviceID;
	private Tracker tracker;
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false;
	private boolean skippable;


	public Servicepoint(int serviceID ,String name, ContinuousGenerator generator, Engine engine, EventType tyyppi, boolean skippable){
		System.out.println("Service point");
		this.engine = engine;
		this.generator = generator;
		this.scheduledEventType = tyyppi;
		this.name = name;
		this.skippable = skippable;
		this.serviceID = serviceID;
	}


	public void lisaaJonoon(VirusSample a){
		this.engine.getTracker().setCircleStatus(this.serviceID, true);
		jono.add(a);
		this.engine.getTracker().setServicepointLabel(this.serviceID, jono.size());
	}

	public VirusSample otaJonosta(){
		if(this.jono.size() == 1){
			this.engine.getTracker().setCircleStatus(this.serviceID, false);
		}
		this.engine.getTracker().setServicepointLabel(this.serviceID, jono.size() - 1);
		varattu = false;
		this.completedServices += 1;
		return this.jono.poll();
	}

	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		if(jono.size() == 0){
			Trace.out(Trace.Level.ERR, "CANT START SERVICE EMPTY QUEUE");
			return;
		}

		varattu = true;
		double palveluaika = generator.sample();
		double size = jono.peek().getSize();
		this.busyTime += palveluaika * size;

		//If scan detected test sample as real virus then skip all other verification methods
		Random random = new Random();
		if((jono.peek().getVirusProbability() > random.nextInt(100) + 1) && this.skippable){
			System.out.println("Skipped " + this.name);
			jono.poll();
			varattu = false;
			this.completedServices += 1;
			engine.uusiTapahtuma(new Event(EventType.SKIP, Clock.getInstance().getTime() + (palveluaika * size)));
		}else{
			engine.uusiTapahtuma(new Event(this.scheduledEventType, Clock.getInstance().getTime() + (palveluaika * size)));
		}
	}


	public boolean onVarattu(){
		return varattu;
	}

	public double getBusyTime(){
		return this.busyTime;
	}

	public int getCompletedServices(){
		return this.completedServices;
	}

	public boolean onJonossa(){
		return jono.size() != 0;
	}

	public int getJonoSize(){
		return jono.size();
	}

	public String getName(){
		return this.name;
	}

	public int getServiceID() {
		return this.serviceID;
	}

	public void setServiceID(int serviceID) {
		System.out.println("Service id set to: " + serviceID);
		this.serviceID = serviceID;
	}

}
