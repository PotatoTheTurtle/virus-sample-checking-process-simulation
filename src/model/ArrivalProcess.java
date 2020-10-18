package model;
import eduni.distributions.*;

/**
 * The type Arrival process.
 */
public class ArrivalProcess {
	private Engine engine;
	private ContinuousGenerator generator;
	private EventType eventType;

	/**
	 * Instantiates a new Arrival process.
	 *
	 * @param engine    the engine
	 * @param generator the generator do be used in event generation
	 * @param eventType the eventType used in even generation
	 */
	public ArrivalProcess(Engine engine, ContinuousGenerator generator, EventType eventType){
		this.engine = engine;
		this.generator = generator;
		this.eventType = eventType;
	}

	/**
	 * Generate next event.
	 */
	public void generateNext(){
		Event t = new Event(eventType, Clock.getInstance().getTime()+ generator.sample());
		engine.newEvent(t);
	}
}
