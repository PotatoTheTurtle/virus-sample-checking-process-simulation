package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.MainApplication;

import javafx.event.ActionEvent;
import java.io.IOException;

public class MainController {

    private AdvancedSettingsController advancedSettingsController;

    private Scene mainMenu;
    private Scene settings;

    @FXML
    private Slider sampleSize;
    @FXML
    private TextField simulationTime, virusProbability;
    @FXML
    private MenuBar menuBar;

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
    public void pastResultsButton(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) menuBar.getScene().getWindow();
        FXMLLoader statsLoad = new FXMLLoader(MainApplication.class.getResource("fxml/simulationStats.fxml"));
        statsLoad.setController(new SimulatorStatisticsController(this));
        VBox vBox = statsLoad.load();
        primaryStage.setScene(new Scene(vBox));
    }

    @FXML
    public void faqButton() throws IOException {
        FXMLLoader faqLoad = new FXMLLoader(MainApplication.class.getResource("fxml/faqPage.fxml"));
        faqLoad.setController(this);
        VBox vBox = faqLoad.load();
        Stage stage = new Stage();
        stage.setTitle("FAQ");
        stage.setScene(new Scene(vBox));
        stage.show();
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
        boolean result = true;
        if(simulationTime.getText().isEmpty()){
            stringBuilder.append("Please enter simulation time.\n");
            result = false;
        }
        if(!simulationTime.getText().matches("\\d*")){
            stringBuilder.append("Please enter simulation time as a number.\n");
            result = false;
        }
        if(simulationTime.getText().matches("\\d*") && !simulationTime.getText().isEmpty()) {
            if (Integer.parseInt(simulationTime.getText()) < 1) {
                stringBuilder.append("Simulation time must be 1 as minimum, however you cant simulate anything at that time. It is recommended to keep it at 300\n");
                result = false;
            }
        }
        if(virusProbability.getText().isEmpty()){
            stringBuilder.append("Please enter virus probability.\n");
            result = false;
        }
        if(!virusProbability.getText().matches("\\d*")){
            stringBuilder.append("Please enter virus probability chance as a number.\n");
            result = false;
        }
        if(virusProbability.getText().matches("\\d*") && !virusProbability.getText().isEmpty()) {
            int probability = Integer.parseInt(virusProbability.getText());
            if (!(0 < probability && probability < 100)) {
                stringBuilder.append("Virus probability must be between 0 and 100, ie. 100% or 0% chance.\n");
                result = false;
            }
        }
        if(!result){
            alert.setHeaderText(stringBuilder.toString());
            alert.show();
        }
        return result;
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
