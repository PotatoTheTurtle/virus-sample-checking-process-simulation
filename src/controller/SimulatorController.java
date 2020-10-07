package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import simulator.Engine;

public class SimulatorController {

    private AdvancedSettingsController advancedSettingsController;
    private MainController mainController;

    private Engine engine;
    private Tracker tracker;

    private int speed = 500;
    private int speedChange = 100;

    @FXML
    private Label timeLabel;

    @FXML
    private Circle circle0, circle1, circle2, circle3, circle4;
    @FXML
    private Circle circle0_1, circle1_2, circle1_3, circle2_4, circle3_4, circle4_out;
    @FXML
    private Polyline polyline0_1;
    @FXML
    private Label label0, label1, label2, label3, label4;

    // Get settings for the simulator from the controllers
    public SimulatorController(AdvancedSettingsController advancedSettingsController, MainController mainController){
        this.advancedSettingsController = advancedSettingsController;
        this.mainController = mainController;
        this.engine = new Engine(this);
    }

    @FXML
    public void initialize(){
        this.tracker = new Tracker(this);
        this.timeLabel.setText((double) this.speed / 1000 + "/second per cycle");
        this.engine.start();

    }

    @FXML
    public void slowdownTime(){
        this.speed += this.speedChange;
        this.timeLabel.setText((double) this.speed / 1000 + "/second(s) per cycle");
        this.engine.slowdownTime(this.speedChange);
    }

    @FXML void speedupTime(){
        this.speed -= this.speedChange;
        this.timeLabel.setText((double) this.speed / 1000 + "/second(s) per cycle");
        this.engine.speedupTime(this.speedChange);
    }

    public AdvancedSettingsController getAdvancedSettingsController(){
        return this.advancedSettingsController;
    }

    public MainController getMainController(){
        return this.mainController;
    }

    public Tracker getTracker() {
        return this.tracker;
    }

    public Circle getCircle0() {
        return circle0;
    }

    public Circle getCircle1() {
        return circle1;
    }

    public Circle getCircle2() {
        return circle2;
    }

    public Circle getCircle3() {
        return circle3;
    }

    public Circle getCircle4() {
        return circle4;
    }

    public Circle getCircle0_1() {
        return circle0_1;
    }

    public Circle getCircle1_2() {
        return circle1_2;
    }

    public Circle getCircle1_3() {
        return circle1_3;
    }

    public Circle getCircle2_4() {
        return circle2_4;
    }

    public Circle getCircle3_4() {
        return circle3_4;
    }

    public Circle getCircle4_out() {
        return circle4_out;
    }

    public void setLabel0(String text) {
        Platform.runLater(()-> {
            this.label0.setText(text);
        });
    }

    public void setLabel1(String text) {
        Platform.runLater(()-> {
            this.label1.setText(text);
        });
    }

    public void setLabel2(String text) {
        Platform.runLater(()-> {
            this.label2.setText(text);
        });
    }

    public void setLabel3(String text) {
        Platform.runLater(()-> {
            this.label3.setText(text);
        });
    }

    public void setLabel4(String text) {
        Platform.runLater(()-> {
            this.label4.setText(text);
        });
    }

    public Polyline getPolyline0_1() {
        return polyline0_1;
    }

}
