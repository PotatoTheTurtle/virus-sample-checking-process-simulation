package controller;

import eduni.distributions.ContinuousGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdvancedSettingsController {

    private MainController mainController;

    // Defualt values
    private int virusSampleSubmissionMinDefault = 1;
    private int virusSampleSubmissionMaxDefault = 5;
    private int backendScanMinDefault = 4;
    private int backendScanMaxDefault = 8;
    private int robotVerify1MinDefault = 6;
    private int robotVerify1MaxDefault = 10;
    private int robotVerify2MinDefault = 6;
    private int robotVerify2MaxDefault = 10;
    private int humanVerifyMinDefault = 5;
    private int humanVerifyMaxDefault = 13;
    private int submissionMinDefault = 15;
    private int submissionMaxDefualt = 5;


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

    public AdvancedSettingsController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    public void backSettings(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        // Load saved main menu.
        primaryStage.setScene(this.mainController.getMainMenu());
    }

    @FXML
    public void resetSettings(){
        this.virusSampleSubmissionMin.setText(String.valueOf(this.virusSampleSubmissionMinDefault));
        this.virusSampleSubmissionMax.setText(String.valueOf(this.virusSampleSubmissionMaxDefault));
        this.backendScanMin.setText(String.valueOf(this.backendScanMinDefault));
        this.backendScanMax.setText(String.valueOf(this.backendScanMaxDefault));
        this.robotVerify1Min.setText(String.valueOf(this.robotVerify1MinDefault));
        this.robotVerify1Max.setText(String.valueOf(this.robotVerify1MaxDefault));
        this.robotVerify2Min.setText(String.valueOf(this.robotVerify2MinDefault));
        this.robotVerify2Max.setText(String.valueOf(this.robotVerify2MaxDefault));
        this.humanVerifyMin.setText(String.valueOf(this.humanVerifyMinDefault));
        this.humanVerifyMax.setText(String.valueOf(this.humanVerifyMaxDefault));
        this.submissionMin.setText(String.valueOf(this.submissionMinDefault));
        this.submissionMax.setText(String.valueOf(this.submissionMaxDefualt));
    }

    // Methods to verify that the field contains numbers, if not then use default value.

    public void verify(){

    }

    public ContinuousGenerator getVirusSampleSubmission(){
        if(this.getVirusSampleSubmissionMin() > this.getVirusSampleSubmissionMax()){
            this.virusSampleSubmissionMin
        }
    }

    public int getVirusSampleSubmissionMin() {
        if(virusSampleSubmissionMin.getText().matches("\\d*") && !virusSampleSubmissionMin.getText().isEmpty()){
            return Integer.parseInt(virusSampleSubmissionMin.getText());
        }
        return this.virusSampleSubmissionMinDefault;
    }

    public int getVirusSampleSubmissionMax() {
        if(virusSampleSubmissionMax.getText().matches("\\d*") && !virusSampleSubmissionMax.getText().isEmpty()){
            return Integer.parseInt(virusSampleSubmissionMax.getText());
        }
        return this.virusSampleSubmissionMaxDefault;
    }

    public int getBackendScanMin() {
        if(backendScanMin.getText().matches("\\d*") && !backendScanMin.getText().isEmpty()){
            return Integer.parseInt(backendScanMin.getText());
        }
        return this.backendScanMinDefault;
    }

    public int getBackendScanMax() {
        if(backendScanMax.getText().matches("\\d*") && !backendScanMax.getText().isEmpty() ){
            return Integer.parseInt(backendScanMax.getText());
        }
        return this.backendScanMaxDefault;
    }

    public int getRobotVerify1Min() {
        if(robotVerify1Min.getText().matches("\\d*") && !robotVerify1Min.getText().isEmpty()){
            return Integer.parseInt(robotVerify1Min.getText());
        }
        return this.robotVerify1MinDefault;
    }

    public int getRobotVerify1Max() {
        if(robotVerify1Max.getText().matches("\\d*") && !robotVerify1Max.getText().isEmpty()){
            return Integer.parseInt(robotVerify1Max.getText());
        }
        return this.robotVerify1MaxDefault;
    }

    public int getRobotVerify2Min() {
        if(robotVerify2Min.getText().matches("\\d*") && !robotVerify2Min.getText().isEmpty()){
            return Integer.parseInt(robotVerify2Min.getText());
        }
        return this.robotVerify2MinDefault;
    }

    public int getRobotVerify2Max() {
        if(robotVerify2Max.getText().matches("\\d*") && !robotVerify2Max.getText().isEmpty()){
            return Integer.parseInt(robotVerify2Max.getText());
        }
        return this.robotVerify2MaxDefault;
    }

    public int getHumanVerifyMin() {
        if(humanVerifyMin.getText().matches("\\d*") && !humanVerifyMin.getText().isEmpty()){
            return Integer.parseInt(humanVerifyMin.getText());
        }
        return this.humanVerifyMinDefault;
    }

    public int getHumanVerifyMax() {
        if(humanVerifyMax.getText().matches("\\d*") && !humanVerifyMax.getText().isEmpty()){
            return Integer.parseInt(humanVerifyMax.getText());
        }
        return this.humanVerifyMaxDefault;
    }

    public int getSubmissionMin() {
        if(virusSampleSubmissionMin.getText().matches("\\d*") && !virusSampleSubmissionMin.getText().isEmpty()){
            return Integer.parseInt(virusSampleSubmissionMin.getText());
        }
        return this.virusSampleSubmissionMinDefault;
    }

    public int getSubmissionMax() {
        if(virusSampleSubmissionMax.getText().matches("\\d*") && !virusSampleSubmissionMax.getText().isEmpty()){
            return Integer.parseInt(virusSampleSubmissionMax.getText());
        }
        return this.virusSampleSubmissionMaxDefault;
    }
}
