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

/**
 * The Main controller used to interact with the general settings and the main menu.
 */
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

    /**
     * Instantiates a new Main controller.
     * Create an advanced settings controller to save all of the advanced settings values between scenes.
     */
    public MainController(){
        this.advancedSettingsController = new AdvancedSettingsController(this);
    }

    /**
     * Initialize advanced settings controller into a scene.
     * Scene is then stored for future use.
     *
     * @throws IOException the io exception
     */
    @FXML
    public void initialize() throws IOException {
        FXMLLoader settingsLoad = new FXMLLoader(MainApplication.class.getResource("fxml/advancedSettings.fxml"));
        settingsLoad.setController(this.advancedSettingsController);
        VBox vBox = settingsLoad.load();
        this.settings = new Scene(vBox);
    }

    /**
     * Past results button action.
     * Past results button action navigates to the window where the simulator runs are stored.
     * This button also loads a new instance of simulation statistics
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void pastResultsButton(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) menuBar.getScene().getWindow();
        FXMLLoader statsLoad = new FXMLLoader(MainApplication.class.getResource("fxml/simulationStats.fxml"));
        statsLoad.setController(new SimulatorStatisticsController(this));
        VBox vBox = statsLoad.load();
        primaryStage.setScene(new Scene(vBox));
    }

    /**
     * Faq button action used to access some faq questions from the main menu.
     * Creates a new instance of faq page window.
     *
     * @throws IOException the io exception
     */
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

    /**
     * Advanced settings button action opens the advanced settings scene that is stored in mainController.
     * MainMenu scene is also saved before opening advanced setting to preserve the values in manuMenu
     *
     * @param actionEvent the action event
     */
    @FXML
    public void advancedSettings(ActionEvent actionEvent){
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        this.mainMenu = primaryStage.getScene();
        primaryStage.setScene(this.settings);
    }

    /**
     * Run simulation button action.
     * This verifies the mainmenu settings fields to ensure that the base settings are correct.
     * After that it passes advancedsettings and main menu to the simulator controller.
     * This also generates a new instance of the simulator view.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
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

    /**
     * Gets main menu scene.
     *
     * @return the main menu
     */
    public Scene getMainMenu() {
        return mainMenu;
    }

    /**
     * Gets sample size setting.
     *
     * @return the sample size
     */
    public double getSampleSize() {
        return sampleSize.getValue();
    }

    /**
     * Gets simulation time time setting.
     *
     * @return the simulation time
     */
    public int getSimulationTime() {
        return Integer.parseInt(simulationTime.getText());
    }

    /**
     * Gets virus probability setting.
     *
     * @return the virus probability
     */
    public int getVirusProbability() {
        return Integer.parseInt(virusProbability.getText());
    }
}
