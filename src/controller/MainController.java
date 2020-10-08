package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.MainApplication;

import javafx.event.ActionEvent;
import java.io.IOException;

public class MainController {

    private AdvancedSettingsController advancedSettingsController;

    private Scene mainMenu = null;
    private Scene settings;

    @FXML
    private Slider sampleSize;
    @FXML
    private TextField simulationTime, virusProbability;



    public MainController(){
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
        if(!this.verifyFields()){
            return;
        }
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader simulationRun = new FXMLLoader(MainApplication.class.getResource("fxml/runningSimulation.fxml"));
        simulationRun.setController(new SimulatorController(this.advancedSettingsController, this));
        primaryStage.setScene(new Scene(simulationRun.load()));
    }

    private boolean verifyFields(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid settings");
        StringBuilder stringBuilder = new StringBuilder();
        boolean flag = true;
        if(simulationTime.getText().isEmpty()){
            stringBuilder.append("Please enter simulation time.\n");
            flag = false;
        }
        if(!simulationTime.getText().matches("\\d*")){
            stringBuilder.append("Please enter simulation time as a number.\n");
            flag = false;
        }
        if(simulationTime.getText().matches("\\d*") && !simulationTime.getText().isEmpty()) {
            if (Integer.parseInt(simulationTime.getText()) < 1) {
                stringBuilder.append("Simulation time must be 1 as minimum, however you cant simulate anything at that time. It is recommended to keep it at 300\n");
                flag = false;
            }
        }

        if(virusProbability.getText().isEmpty()){
            stringBuilder.append("Please enter virus probability.\n");
            flag = false;
        }
        if(!virusProbability.getText().matches("\\d*")){
            stringBuilder.append("Please enter virus probability chance as a number.\n");
            flag = false;
        }
        if(virusProbability.getText().matches("\\d*") && !virusProbability.getText().isEmpty()) {
            if (Integer.parseInt(virusProbability.getText()) > 100 || Integer.parseInt(virusProbability.getText()) < 0) {
                stringBuilder.append("Virus probability must be max 100 or min 0, ie. 100% or 0% chance.\n");
                flag = false;
            }
        }
        if(!flag){
            alert.setHeaderText(stringBuilder.toString());
            alert.show();
        }
        return flag;
    }

    public Scene getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(Scene mainMenu) {
        this.mainMenu = mainMenu;
    }

    public double getSampleSize() {
        return sampleSize.getValue();
    }

    public int getSimulationTime() {
        return Integer.parseInt(simulationTime.getText());
    }

    public int getVirusProbability() {
        return Integer.parseInt(virusProbability.getText());
    }
}
