package simulator;
import eduni.distributions.*;

public class ArrivalProcess {
	private Engine engine;
	private ContinuousGenerator generaattori;
	private EventType tyyppi;

	/* Testejä varten,  ks. alla oleva kommentoitu osuus  
	int i = 0;
	double sum = 0;
	double max = 0;
	double min = 100;
	*/
	
	public ArrivalProcess(Engine m, ContinuousGenerator g, EventType tyyppi){
		this.engine = m;
		this.generaattori = g;
		this.tyyppi = tyyppi;
	}

	public void generoiSeuraava(){
		Event t = new Event(tyyppi, Clock.getInstance().getTime()+generaattori.sample());
		engine.uusiTapahtuma(t);
		
		/* Generaattorin tuottamien lukujen tutkimista (keskiarvo, peinin, suurin) 		
		double aika = generaattori.sample();
		sum = sum + aika;
		i++;
		System.out.println(sum/i);
		System.out.println(min);
		System.out.println(max);
		System.out.println();
		if (max < aika) max = aika;
		if (min > aika) min = aika;
		*/
	}

}
