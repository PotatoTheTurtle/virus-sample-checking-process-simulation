package controller;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Negexp;
import eduni.distributions.Uniform;
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

    public ContinuousGenerator getVirusSampleSubmissionGenerator(){
        if(!virusSampleSubmissionMin.getText().isEmpty() && !virusSampleSubmissionMax.getText().isEmpty()){
            if(virusSampleSubmissionMin.getText().matches("\\d*") && virusSampleSubmissionMax.getText().matches("\\d*")){
                return new Uniform(Integer.parseInt(virusSampleSubmissionMin.getText()), Integer.parseInt(virusSampleSubmissionMax.getText()));
            }
        }
        return new Uniform(this.virusSampleSubmissionMinDefault, this.virusSampleSubmissionMaxDefault);
    }

    public ContinuousGenerator getBackendScanGenerator(){
        if(!backendScanMin.getText().isEmpty() && !backendScanMax.getText().isEmpty()){
            if(backendScanMin.getText().matches("\\d*") && backendScanMax.getText().matches("\\d*")){
                return new Uniform(Integer.parseInt(backendScanMin.getText()), Integer.parseInt(backendScanMax.getText()));
            }
        }
        return new Uniform(this.backendScanMinDefault, this.backendScanMaxDefault);
    }

    public ContinuousGenerator getRobotVerify1Generator(){
        if(!robotVerify1Min.getText().isEmpty() && !robotVerify1Max.getText().isEmpty()){
            if(robotVerify1Min.getText().matches("\\d*") && robotVerify1Max.getText().matches("\\d*")){
                return new Uniform(Integer.parseInt(robotVerify1Min.getText()), Integer.parseInt(robotVerify1Max.getText()));
            }
        }
        return new Uniform(this.robotVerify1MinDefault, this.robotVerify1MaxDefault);
    }

    public ContinuousGenerator getRobotVerify2Generator(){
        if(!robotVerify2Min.getText().isEmpty() && !robotVerify2Max.getText().isEmpty()){
            if(robotVerify2Min.getText().matches("\\d*") && robotVerify2Max.getText().matches("\\d*")){
                return new Uniform(Integer.parseInt(robotVerify2Min.getText()), Integer.parseInt(robotVerify2Max.getText()));
            }
        }
        return new Uniform(this.robotVerify2MinDefault, this.robotVerify2MaxDefault);
    }

    public ContinuousGenerator getHumanVerificationGenerator(){
        if(!humanVerifyMin.getText().isEmpty() && !humanVerifyMax.getText().isEmpty()){
            if(humanVerifyMin.getText().matches("\\d*") && humanVerifyMax.getText().matches("\\d*")){
                return new Uniform(Integer.parseInt(humanVerifyMin.getText()), Integer.parseInt(humanVerifyMax.getText()));
            }
        }
        return new Uniform(this.humanVerifyMinDefault, this.humanVerifyMaxDefault);
    }


    //TODO: this is seed, redo this please
    public ContinuousGenerator getSubmissionGenerator(){
        if(!submissionMin.getText().isEmpty() && !submissionMax.getText().isEmpty()){
            if(submissionMin.getText().matches("\\d*") && submissionMax.getText().matches("\\d*")){
                return new Negexp(Integer.parseInt(submissionMin.getText()), Integer.parseInt(submissionMax.getText()));
            }
        }
        return new Negexp(this.submissionMinDefault, this.submissionMaxDefualt);
    }
}
