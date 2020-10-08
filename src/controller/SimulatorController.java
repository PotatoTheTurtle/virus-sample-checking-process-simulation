package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import model.Engine;
import model.ServicePointStatistic;
import model.StatsDAO;
import view.MainApplication;

import java.io.IOException;
import java.util.Arrays;

public class SimulatorController {

    private AdvancedSettingsController advancedSettingsController;
    private MainController mainController;

    private StatsDAO statsDAO;
    private Engine engine;
    private Tracker tracker;

    private int speed = 500;
    private int speedChange = 100;

    @FXML
    private Label timeLabel, simulationTimeLabel;
    @FXML
    private Button nextButton;
    @FXML
    private Circle circle0, circle1, circle2, circle3, circle4;
    @FXML
    private Circle circle0_1, circle1_2, circle1_3, circle2_4, circle3_4, circle4_out;
    @FXML
    private Circle skipCircle1, skipCircle2, skipCircle3;
    @FXML
    private Polyline polyline0_1;
    @FXML
    private Label label0, label1, label2, label3, label4;
    @FXML
    private Button speedupTimeButton, slowdownTimeButton;

    // Get settings for the simulator from the controllers
    public SimulatorController(AdvancedSettingsController advancedSettingsController, MainController mainController){
        this.advancedSettingsController = advancedSettingsController;
        this.mainController = mainController;
        this.engine = new Engine(this);
        this.statsDAO = new StatsDAO();

    }

    public void saveStatistics(int simulationTime, ServicePointStatistic[] servicePointStatistics){
        int run_id = this.statsDAO.setupRunTable(simulationTime);
        if(run_id != 0){
            for(ServicePointStatistic servicePointStatistic : servicePointStatistics){
                this.statsDAO.saveServicePoint(servicePointStatistic, run_id);
            }
        }
    }

    @FXML
    public void initialize() throws InterruptedException {
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

    @FXML
    public void speedupTime(){
        if(this.speed - speedChange < 0){
            return;
        }
        this.speed -= this.speedChange;
        this.timeLabel.setText((double) this.speed / 1000 + "/second(s) per cycle");
        this.engine.speedupTime(this.speedChange);
    }

    public void showNextButton(){
        this.speedupTimeButton.setVisible(false);
        this.slowdownTimeButton.setVisible(false);
        this.timeLabel.setVisible(false);
        this.nextButton.setVisible(true);
    }

    public void updateSimulationTime(double time){
        Platform.runLater(() -> this.simulationTimeLabel.setText(String.format("Time passed: %.2f", time)));
    }

    @FXML
    public void nextPage(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader simulationRun = new FXMLLoader(MainApplication.class.getResource("fxml/simulationStats.fxml"));
        simulationRun.setController(new SimulatorStatisticsController(this.mainController));
        primaryStage.setScene(new Scene(simulationRun.load()));
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

    public Circle getSkipCircle1() {
        return skipCircle1;
    }

    public Circle getSkipCircle2() {
        return skipCircle2;
    }

    public Circle getSkipCircle3() {
        return skipCircle3;
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
