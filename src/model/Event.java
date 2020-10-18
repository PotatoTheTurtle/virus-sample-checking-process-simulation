package model;


/**
 * The type Event.
 */
public class Event implements Comparable<Event> {
	
		
	private EventType eventType;
	private double time;

	/**
	 * Instantiates a new Event.
	 *
	 * @param eventType assign the event type
	 * @param time the time to be assigned
	 */
	public Event(EventType eventType, double time){
		this.eventType = eventType;
		this.time = time;
	}

	/**
	 * Get event type.
	 *
	 * @return the event type
	 */
	public EventType getType() {
		return eventType;
	}

	/**
	 * Gets event time.
	 *
	 * @return the event time
	 */
	public double getTime() {
		return time;
	}

	@Override
	public int compareTo(Event arg) {
		if (this.time < arg.time) return -1;
		else if (this.time > arg.time) return 1;
		return 0;
	}
	
	
	

}
