package model;

import java.util.LinkedList;
import java.util.Random;

import eduni.distributions.ContinuousGenerator;


/**
 * The service point where an event is processed.
 * Service point also controls some of the UI elements when simulation is in progress.
 */
public class Servicepoint {

	private Engine engine;

	private LinkedList<VirusSample> queue = new LinkedList<>();
	private ContinuousGenerator generator;
	private EventType scheduledEventType;

	private String name;
	private double busyTime = 0;
	private int completedServices = 0;
	private int serviceID = 0;

	private boolean inUse = false;
	private boolean skippable = false;


	/**
	 * Instantiates a new Servicepoint.
	 *
	 * @param serviceID the service id
	 * @param name      the service name
	 * @param generator the generator to be used
	 * @param engine    the engine
	 * @param eventType the service eventType
	 * @param skippable is service skippable
	 */
	public Servicepoint(int serviceID, String name, ContinuousGenerator generator, Engine engine, EventType eventType, boolean skippable){
		this.engine = engine;
		this.generator = generator;
		this.scheduledEventType = eventType;
		this.name = name;
		this.skippable = skippable;
		this.serviceID = serviceID;
	}

	/**
	 * Add virus sample to queue.
	 * Update simulation UI queue size and circles
	 *
	 * @param virusSample the virus sample to be added.
	 */
	public void addToQueue(VirusSample virusSample){
		this.engine.getTracker().setCircleStatus(this.serviceID, true);
		queue.add(virusSample);
		this.engine.getTracker().setServicepointLabel(this.serviceID, queue.size());
	}

	/**
	 * Take virus sample from queue.
	 * Update simulation UI queue size and circles
	 *
	 * @return the taken virus sample from the queue.
	 */
	public VirusSample takeFromQueue(){
		if(this.queue.size() == 1){
			this.engine.getTracker().setCircleStatus(this.serviceID, false);
		}
		this.engine.getTracker().setServicepointLabel(this.serviceID, queue.size() - 1);
		inUse = false;
		this.completedServices += 1;
		return this.queue.poll();
	}

	/**
	 * Start service
	 * Process current event and virus sample
	 * Check if virus sample is virus, works only if service can be skipped
	 * Busy time depends on virus size and generator
	 *
	 * Sets the circle in UI when virus sample is being processed
	 */
	public void startService(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		if(queue.size() == 0){
			Trace.out(Trace.Level.ERR, "CANT START SERVICE EMPTY QUEUE");
			return;
		}

		inUse = true;
		double palveluaika = generator.sample();
		double size = queue.peek().getSize();
		this.busyTime += palveluaika * size;

		//If scan detected test sample as real virus then skip all other verification methods
		Random random = new Random();
		if((queue.peek().getVirusProbability() >= random.nextInt(100) + 1) && this.skippable){
			System.out.println("Skipped " + this.name);
			queue.poll();
			inUse = false;
			this.completedServices += 1;
			this.engine.getTracker().setSkipCircle(this.serviceID, true);
			engine.newEvent(new Event(EventType.SKIP, Clock.getInstance().getTime() + (palveluaika * size)));
		}else{
			this.engine.getTracker().setSkipCircle(this.serviceID, false);
			engine.newEvent(new Event(this.scheduledEventType, Clock.getInstance().getTime() + (palveluaika * size)));
		}
	}


	/**
	 * Check if service is in use.
	 *
	 * @return the boolean
	 */
	public boolean isInUse(){
		return inUse;
	}

	/**
	 * Get service total busy time.
	 *
	 * @return busy time double
	 */
	public double getBusyTime(){
		return this.busyTime;
	}

	/**
	 * Get completed services
	 *
	 * @return amount of completed services as int
	 */
	public int getCompletedServices(){
		return this.completedServices;
	}

	/**
	 * Check if any virus sample is in queue
	 *
	 * @return the boolean
	 */
	public boolean isInQueue(){
		return queue.size() != 0;
	}

	/**
	 * Get queue size int.
	 *
	 * @return size as int
	 */
	public int getQueueSize(){
		return queue.size();
	}

	/**
	 * Get service name
	 *
	 * @return the name as string
	 */
	public String getName(){
		return this.name;
	}

}
