package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ServicePointStatistic;
import model.SimulatorStatistics;
import model.StatsDAO;
import view.MainApplication;

import java.io.IOException;
import java.util.Collections;

/**
 * The Simulator statistics controller is used to go over the current and past results.
 * You can also navigate back to the main menu from here.
 */
public class SimulatorStatisticsController {

    private StatsDAO statsDAO;
    private MainController mainController;
    private int selected = 0;

    @FXML
    private ListView<SimulatorStatistics> runsList;
    @FXML
    private Label nameLabel, busyTimeLabel, serviceTimesLabel, utilizationLabel, avgServiceTimesLabel, simulationLengthLabel;

    /**
     * Instantiates a new Simulator statistics controller.
     *
     * @param mainController the main controller
     */
    public SimulatorStatisticsController(MainController mainController){
        this.statsDAO = new StatsDAO();
        this.mainController = mainController;
    }

    /**
     * Initialize the sidebar menu with all of the previsour runs ordered by date.
     */
    @FXML
    public void initialize(){
        ObservableList<SimulatorStatistics> statisticsObservableList = FXCollections.observableArrayList(this.statsDAO.getAllSimulatorStatistics());
        Collections.sort(statisticsObservableList);
        this.runsList.setItems(statisticsObservableList);
        this.runsList.getSelectionModel().selectFirst();
        this.infoHelper(this.selected);
        this.runsList.getSelectionModel().getSelectedItems().addListener((ListChangeListener<SimulatorStatistics>) change -> {
            this.infoHelper(this.selected);
        });
    }

    /**
     * Main menu button action.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void mainMenuButton(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader mainMenuLoad = new FXMLLoader(MainApplication.class.getResource("fxml/mainMenu.fxml"));
        mainMenuLoad.setController(new MainController());
        VBox vbox = mainMenuLoad.load();
        primaryStage.setScene(new Scene(vbox));
    }

    private void infoHelper(int index){
        ServicePointStatistic servicePointStatistic = this.runsList.getSelectionModel().getSelectedItem().getServicepointByIndex(index);
        this.nameLabel.setText(servicePointStatistic.getName());
        this.busyTimeLabel.setText(String.format("%.2f", servicePointStatistic.getBusyTime()));
        this.serviceTimesLabel.setText(String.valueOf(servicePointStatistic.getServiceTimes()));
        this.utilizationLabel.setText(String.format("%d%%", (int) (servicePointStatistic.getUtilization() * 100)));
        this.avgServiceTimesLabel.setText(String.format("%.2f", servicePointStatistic.getAvgServiceTime()));
        this.simulationLengthLabel.setText(String.valueOf(this.runsList.getSelectionModel().getSelectedItem().getSimulationTime()));
    }

    /**
     * Sample submission button action.
     * Selects the samplesubmission servicepoint data.
     */
    @FXML
    public void sampleSubmission(){
        this.infoHelper(0);
        this.selected = 0;
    }

    /**
     * Backend scan button action.
     * Selects the Backend scan servicepoint data.
     */
    @FXML
    public void backendScan(){
        this.infoHelper(1);
        this.selected = 1;
    }

    /**
     * Robot verification 1 button action.
     * Selects the Robot verification 1 servicepoint data.
     */
    @FXML
    public void robotVerification1(){
        this.infoHelper(2);
        this.selected = 2;
    }

    /**
     * Robot verification 2 button action.
     * Selects the Robot verification 2 servicepoint data.
     */
    @FXML
    public void robotVerification2(){
        this.infoHelper(3);
        this.selected = 3;
    }

    /**
     * Human verification button action.
     * Selects the Human verification servicepoint data.
     */
    @FXML
    public void humanVerification(){
        this.infoHelper(4);
        this.selected = 4;
    }
}
