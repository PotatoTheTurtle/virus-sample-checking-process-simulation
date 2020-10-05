package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import simulator.Engine;
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
    }

    /*public void initialize(URL location, ResourceBundle resources) {
        simulationTime.textProperty().addListener((obs, oldText, newText) -> {
            simulationTime.setText(newText);
        });
    }*/

    @FXML
    public void advancedSettings(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        this.mainMenu = primaryStage.getScene();

        if(this.settings == null){
            FXMLLoader settingsLoad = new FXMLLoader(MainApplication.class.getResource("fxml/advancedSettings.fxml"));
            this.advancedSettingsController = new AdvancedSettingsController(this);
            settingsLoad.setController(this.advancedSettingsController);
            VBox vBox = settingsLoad.load();
            this.settings = new Scene(vBox);
        }
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
