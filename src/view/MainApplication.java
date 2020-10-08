package view;

import controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader mainMenuLoad = new FXMLLoader(MainApplication.class.getResource("fxml/mainMenu.fxml"));
        mainMenuLoad.setController(new MainController());
        VBox vbox = mainMenuLoad.load();
        Scene mainMenu = new Scene(vbox);

        primaryStage.setOnCloseRequest((WindowEvent windowEvent) -> {
                Platform.exit();
                System.exit(0);
        });


        primaryStage.setTitle("Simulointi");
        primaryStage.setScene(mainMenu);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
