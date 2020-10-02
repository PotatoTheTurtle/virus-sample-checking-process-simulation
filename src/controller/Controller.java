package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import simulator.Engine;
import view.MainApplication;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    private MainApplication mainApplication;
    private Engine engine;
    private Tracker tracker;

    private Scene mainMenu;
    private Scene settings;

    @FXML
    private Label timeLabel;
    private int speed = 500;
    private int speedChange = 100;

    @FXML
    private Slider sampleSize;
    @FXML
    private TextField simulationTime, virusProbability;
    @FXML
    private TextField virusSampleSubmissionMin, virusSampleSubmissionMax;
    @FXML
    private TextField backendScanMin, backendScanMax;
    @FXML
    private TextField robotVerify1Min, robotVerify1Max;
    @FXML
    private TextField robotVerify2Min, robotVerify2Max;
    @FXML
    private TextField humanVerifyMin, humanVerifyMax;
    @FXML
    private TextField submissionMin, submissionMax;
    @FXML
    private Circle circle0, circle1, circle2, circle3, circle4;
    @FXML
    private Circle circle0_1, circle1_2, circle1_3, circle2_4, circle3_4, circle4_out;
    @FXML
    private Line line0_1;




    public Controller(MainApplication mainApplication){
        this.mainApplication = mainApplication;
        this.engine = new Engine(this);
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
            settingsLoad.setController(this);
            VBox vBox = settingsLoad.load();
            primaryStage.setScene(new Scene(vBox));
        }else{
            primaryStage.setScene(this.settings);
        }
    }

    @FXML
    public void backSettings(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        this.settings = primaryStage.getScene();

        if(this.mainMenu == null){
            FXMLLoader mainMenu = new FXMLLoader(MainApplication.class.getResource("fxml/mainMenu.fxml"));
            mainMenu.setController(this);
            VBox vBox = mainMenu.load();
            primaryStage.setScene(new Scene(vBox));
        }else{
            primaryStage.setScene(this.mainMenu);
        }
    }

    @FXML
    public void runSimulation(ActionEvent actionEvent) throws IOException, InterruptedException {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader simulationRun = new FXMLLoader(MainApplication.class.getResource("fxml/runningSimulation.fxml"));
        simulationRun.setController(this);
        VBox vBox = simulationRun.load();

        Label label = new Label();
        label.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
        vBox.getChildren().add(label);

        this.tracker = new Tracker(vBox, this);

        this.timeLabel.setText((double) this.speed / 1000 + "/second per cycle");
        primaryStage.setScene(new Scene(vBox));
        this.engine.start();
        this.tracker.moveSample(0);
        label.setText("TEST");
        label.setTranslateX(50);
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

    public Line getLine0_1() {
        return line0_1;
    }
}
