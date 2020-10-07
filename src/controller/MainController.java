package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.MainApplication;

import javafx.event.ActionEvent;
import java.io.IOException;

public class MainController {

    private MainApplication mainApplication;
    private AdvancedSettingsController advancedSettingsController;

    private Scene mainMenu;
    private Scene settings;

    @FXML
    private Slider sampleSize;
    @FXML
    private TextField simulationTime, virusProbability;



    public MainController(MainApplication mainApplication){
        this.mainApplication = mainApplication;
        this.advancedSettingsController = new AdvancedSettingsController(this);
    }

    @FXML
    public void initialize() throws IOException {
        FXMLLoader settingsLoad = new FXMLLoader(MainApplication.class.getResource("fxml/advancedSettings.fxml"));
        settingsLoad.setController(this.advancedSettingsController);
        VBox vBox = settingsLoad.load();
        this.settings = new Scene(vBox);
    }

    @FXML
    public void advancedSettings(ActionEvent actionEvent){
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        this.mainMenu = primaryStage.getScene();
        primaryStage.setScene(this.settings);
    }

    @FXML
    public void runSimulation(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader simulationRun = new FXMLLoader(MainApplication.class.getResource("fxml/runningSimulation.fxml"));
        simulationRun.setController(new SimulatorController(this.advancedSettingsController, this));
        primaryStage.setScene(new Scene(simulationRun.load()));
    }

    public Scene getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(Scene mainMenu) {
        this.mainMenu = mainMenu;
    }

    public Slider getSampleSize() {
        return sampleSize;
    }

    public TextField getSimulationTime() {
        return simulationTime;
    }

    public TextField getVirusProbability() {
        return virusProbability;
    }
}
