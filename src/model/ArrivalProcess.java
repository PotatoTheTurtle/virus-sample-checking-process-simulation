package model;
import eduni.distributions.*;

public class ArrivalProcess {
	private Engine engine;
	private ContinuousGenerator generaattori;
	private EventType tyyppi;

	public ArrivalProcess(Engine m, ContinuousGenerator g, EventType tyyppi){
		this.engine = m;
		this.generaattori = g;
		this.tyyppi = tyyppi;
	}

	public void generoiSeuraava(){
		Event t = new Event(tyyppi, Clock.getInstance().getTime()+generaattori.sample());
		engine.uusiTapahtuma(t);
	}
}
