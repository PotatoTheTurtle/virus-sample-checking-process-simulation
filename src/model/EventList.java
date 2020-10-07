package model;

import java.util.PriorityQueue;

public class EventList {
	private PriorityQueue<Event> lista = new PriorityQueue<Event>();
	
	public EventList(){
	 
	}
	
	public Event poista(){
		Trace.out(Trace.Level.INFO,"Poisto " + lista.peek());
		return lista.remove();
	}
	
	public void lisaa(Event t){
		lista.add(t);
	}
	
	public double getSeuraavanAika(){
		return lista.peek().getAika();
	}
	
	
}
