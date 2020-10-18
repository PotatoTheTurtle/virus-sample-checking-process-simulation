package model;

import java.util.PriorityQueue;

/**
 * The EventList to store events
 */
public class EventList {
	private PriorityQueue<Event> eventList = new PriorityQueue<Event>();

	/**
	 * Delete event from eventList.
	 *
	 * @return the removed event
	 */
	public Event deleteEvent(){
		Trace.out(Trace.Level.INFO,"Delete " + eventList.peek());
		return eventList.remove();
	}

	/**
	 * Add to event list
	 *
	 * @param event the event to be added
	 */
	public void add(Event event){
		eventList.add(event);
	}

	/**
	 * Get next event time
	 *
	 * @return the next event time as double
	 */
	public double getNextTime(){
		return eventList.peek().getTime();
	}
	
	
}
