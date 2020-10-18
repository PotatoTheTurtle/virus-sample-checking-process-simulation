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

/**
 * The Simulator controller.
 * Used to control varios elements on the simulaton run window.
 */
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

    /**
     * Instantiates a new Simulator controller.
     * Gets settings for the simulator from the controllers.
     * Initialize engine and stats data access object.
     *
     * @param advancedSettingsController the advanced settings controller
     * @param mainController             the main controller
     */

    public SimulatorController(AdvancedSettingsController advancedSettingsController, MainController mainController){
        this.advancedSettingsController = advancedSettingsController;
        this.mainController = mainController;
        this.engine = new Engine(this);
        this.statsDAO = new StatsDAO();

    }

    /**
     * Save statistics using the stats data access object.
     *
     * @param simulationTime         the total simulation time
     * @param servicePointStatistics the service point statistics list
     */
    public void saveStatistics(int simulationTime, ServicePointStatistic[] servicePointStatistics){
        int run_id = this.statsDAO.setupRunTable(simulationTime);
        if(run_id != 0){
            for(ServicePointStatistic servicePointStatistic : servicePointStatistics){
                this.statsDAO.saveServicePoint(servicePointStatistic, run_id);
            }
        }
    }

    /**
     * Initialize Tracker that is used to control the circles in the UI.
     * Start simulation.
     */
    @FXML
    public void initialize() {
        this.tracker = new Tracker(this);
        this.timeLabel.setText((double) this.speed / 1000 + "/second per cycle");
        this.engine.start();
    }

    /**
     * Slowdown time.
     */
    @FXML
    public void slowdownTime(){
        this.speed += this.speedChange;
        this.timeLabel.setText((double) this.speed / 1000 + "/second(s) per cycle");
        this.engine.slowdownTime(this.speedChange);
    }

    /**
     * Speedup time.
     */
    @FXML
    public void speedupTime(){
        if(this.speed - speedChange < 0){
            return;
        }
        this.speed -= this.speedChange;
        this.timeLabel.setText((double) this.speed / 1000 + "/second(s) per cycle");
        this.engine.speedupTime(this.speedChange);
    }

    /**
     * Show button to the results.
     */
    public void showNextButton(){
        this.speedupTimeButton.setVisible(false);
        this.slowdownTimeButton.setVisible(false);
        this.timeLabel.setVisible(false);
        this.nextButton.setVisible(true);
    }

    /**
     * Update simulation time in the ui.
     *
     * @param time the time to be set
     */
    public void updateSimulationTime(double time){
        Platform.runLater(() -> this.simulationTimeLabel.setText(String.format("Time passed: %.2f", time)));
    }

    /**
     * Next page button action.
     * Opens simulation stats.
     * Create simulation stats controller where maincontroller is passed.
     * Main controller is used to load the mainmenu scene with the saved settings.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void nextPage(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader simulationRun = new FXMLLoader(MainApplication.class.getResource("fxml/simulationStats.fxml"));
        simulationRun.setController(new SimulatorStatisticsController(this.mainController));
        primaryStage.setScene(new Scene(simulationRun.load()));
    }

    /**
     * Get advanced settings controller.
     *
     * @return the advanced settings controller
     */
    public AdvancedSettingsController getAdvancedSettingsController(){
        return this.advancedSettingsController;
    }

    /**
     * Get main controller.
     *
     * @return the main controller
     */
    public MainController getMainController(){
        return this.mainController;
    }

    /**
     * Gets tracker.
     *
     * @return the tracker
     */
    public Tracker getTracker() {
        return this.tracker;
    }

    /**
     * Gets circle 0.
     *
     * @return the circle 0
     */
    public Circle getCircle0() {
        return circle0;
    }

    /**
     * Gets circle 1.
     *
     * @return the circle 1
     */
    public Circle getCircle1() {
        return circle1;
    }

    /**
     * Gets circle 2.
     *
     * @return the circle 2
     */
    public Circle getCircle2() {
        return circle2;
    }

    /**
     * Gets circle 3.
     *
     * @return the circle 3
     */
    public Circle getCircle3() {
        return circle3;
    }

    /**
     * Gets circle 4.
     *
     * @return the circle 4
     */
    public Circle getCircle4() {
        return circle4;
    }

    /**
     * Gets circle 0 1.
     *
     * @return the circle 0 1
     */
    public Circle getCircle0_1() {
        return circle0_1;
    }

    /**
     * Gets skip circle 1.
     *
     * @return the skip circle 1
     */
    public Circle getSkipCircle1() {
        return skipCircle1;
    }

    /**
     * Gets skip circle 2.
     *
     * @return the skip circle 2
     */
    public Circle getSkipCircle2() {
        return skipCircle2;
    }

    /**
     * Gets skip circle 3.
     *
     * @return the skip circle 3
     */
    public Circle getSkipCircle3() {
        return skipCircle3;
    }

    /**
     * Sets label 0.
     *
     * @param text the text
     */
    public void setLabel0(String text) {
        Platform.runLater(()-> {
            this.label0.setText(text);
        });
    }

    /**
     * Sets label 1.
     *
     * @param text the text
     */
    public void setLabel1(String text) {
        Platform.runLater(()-> {
            this.label1.setText(text);
        });
    }

    /**
     * Sets label 2.
     *
     * @param text the text
     */
    public void setLabel2(String text) {
        Platform.runLater(()-> {
            this.label2.setText(text);
        });
    }

    /**
     * Sets label 3.
     *
     * @param text the text
     */
    public void setLabel3(String text) {
        Platform.runLater(()-> {
            this.label3.setText(text);
        });
    }

    /**
     * Sets label 4.
     *
     * @param text the text
     */
    public void setLabel4(String text) {
        Platform.runLater(()-> {
            this.label4.setText(text);
        });
    }

}
