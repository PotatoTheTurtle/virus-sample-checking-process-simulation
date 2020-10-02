package view;
	

import java.text.DecimalFormat;

import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simulator.Trace;
import simulator.Trace.Level;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;



public class Main extends Application implements GuiIf{

	//Kontrollerin esittely (tarvitaan käyttöliittymässä)
	private KontrolleriIf kontrolleri;

	// Käyttöliittymäkomponentit:
	private TextField aika;
	private TextField viive;
	private TextField tulos;
	private Button kaynnistaButton;
	private Button hidastaButton;
	private Button nopeutaButton;

	private Visualisointi naytto;


	@Override
	public void init(){
		
		Trace.setTraceLevel(Level.INFO);
		
		kontrolleri = new Kontrolleri(this);
	}

	@Override
	public void start(Stage primaryStage) {
		// Käyttöliittymän rakentaminen
		try {
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});
						
			
			primaryStage.setTitle("Simulaattori");

			kaynnistaButton = new Button();
			kaynnistaButton.setText("Käynnistä simulointi");
			kaynnistaButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                kontrolleri.kaynnistaSimulointi();
	            }
	        });

			hidastaButton = new Button();
			hidastaButton.setText("Hidasta");
			hidastaButton.setOnAction(e -> kontrolleri.hidasta());

			nopeutaButton = new Button();
			nopeutaButton.setText("Nopeuta");
			nopeutaButton.setOnAction(e -> kontrolleri.nopeuta());
	        	      
	        aika = new TextField("Simulointiaika");
	        aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        aika.setPrefWidth(150);

	        viive = new TextField("Viive");
	        viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        viive.setPrefWidth(150);
	                	        
	        tulos = new TextField();
	        tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        tulos.setPrefWidth(150);

	        HBox hBox = new HBox();
	        hBox.setPadding(new Insets(15, 12, 15, 12)); // marginaalit ylä, oikea, ala, vasen
	        hBox.setSpacing(10);   // noodien välimatka 10 pikseliä
	        
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setVgap(20);
	        grid.setHgap(10);

	        grid.add(aika, 0, 0);               // sarake, rivi
	        grid.add(viive, 1, 0);               // sarake, rivi
	        grid.add(kaynnistaButton,0, 1);     // sarake, rivi
	        grid.add(tulos, 1, 1);            // sarake, rivi
	        grid.add(nopeutaButton, 0, 2);               // sarake, rivi
	        grid.add(hidastaButton, 1, 2);               // sarake, rivi
	        
	        naytto = new Visualisointi(400,200);

	        // Täytetään boxi:
	        hBox.getChildren().addAll(grid, naytto);
	        
	        Scene scene = new Scene(hBox);
	        primaryStage.setScene(scene);
	        primaryStage.show();



		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	//Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

	@Override
	public double getAika(){
		return Double.parseDouble(aika.getText());
	}

	@Override
	public long getViive(){
		return Long.parseLong(viive.getText());
	}

	@Override
	public void setLoppuaika(double aika){
		 DecimalFormat formatter = new DecimalFormat("#0.00");
		 this.tulos.setText(formatter.format(aika));
	}


	@Override
	public Visualisointi getVisualisointi() {
		 return naytto;
	}
	
	
	// JavaFX-sovelluksen (käyttöliittymän) käynnistäminen

	public static void main(String[] args) {
		launch(args);
	}

	
}
