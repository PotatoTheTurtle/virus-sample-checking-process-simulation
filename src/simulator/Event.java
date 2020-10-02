package simulator;


public class Event implements Comparable<Event> {
	
		
	private EventType tyyppi;
	private double aika;
	
	public Event(EventType tyyppi, double aika){
		this.tyyppi = tyyppi;
		this.aika = aika;
	}
	
	public void setTyyppi(EventType tyyppi) {
		this.tyyppi = tyyppi;
	}
	public EventType getTyyppi() {
		return tyyppi;
	}
	public void setAika(double aika) {
		this.aika = aika;
	}
	public double getAika() {
		return aika;
	}

	@Override
	public int compareTo(Event arg) {
		if (this.aika < arg.aika) return -1;
		else if (this.aika > arg.aika) return 1;
		return 0;
	}
	
	
	

}
