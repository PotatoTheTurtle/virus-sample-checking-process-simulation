package controller;

import javafx.application.Platform;
import simulator.Engine;

import view.*;

public class Kontrolleri implements KontrolleriIf {

	
	private Engine engine;
	private GuiIf gui;
	
	public Kontrolleri(GuiIf gui) {
		this.gui = gui;
	}

	
	// Moottorin ohjausta:
		
	@Override
	public void kaynnistaSimulointi() {
		/*engine = new Engine(new Controller(gui)); // luodaan uusi moottorisäie jokaista simulointia varten
		engine.setSimulointiaika(gui.getAika());
		//moottori.setViive(gui.getViive());
		gui.getVisualisointi().tyhjennaNaytto();
		//((Thread)moottori).start();
		engine.start();*/
	}

	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		//moottori.setViive((long)(moottori.getViive()*1.10));
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		//moottori.setViive((long)(moottori.getViive()*0.9));
	}
	
	
	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska gui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen
		
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(()->gui.setLoppuaika(aika)); 
	}

	
	@Override
	public void visualisoiAsiakas() {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().uusiAsiakas();
			}
		});
	}


}
