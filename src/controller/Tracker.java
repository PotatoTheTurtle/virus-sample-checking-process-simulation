package controller;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;


public class Tracker {


    private SimulatorController simulatorController;

    public Tracker(SimulatorController simulatorController){
        this.simulatorController = simulatorController;
    }

    public void setCircleStatus(int id, boolean visible){
        switch (id){
            case 0:
                this.simulatorController.getCircle0().setVisible(visible);
                break;
            case 1:
                this.simulatorController.getCircle1().setVisible(visible);
                break;
            case 2:
                this.simulatorController.getCircle2().setVisible(visible);
                break;
            case 3:
                this.simulatorController.getCircle3().setVisible(visible);
                break;
            case 4:
                this.simulatorController.getCircle4().setVisible(visible);
                break;
        }
    }

    public void setSkipCircle(int id, boolean visible){
        switch (id){
            case 2:
                this.simulatorController.getSkipCircle1().setVisible(visible);
                break;
            case 3:
                this.simulatorController.getSkipCircle2().setVisible(visible);
                break;
            case 4:
                this.simulatorController.getSkipCircle3().setVisible(visible);
                break;
        }
    }

    public void setServicepointLabel(int id, int queueAmount){
        switch (id){
            case 0:
                    this.simulatorController.setLabel0("Sample submission\nsamples: " + queueAmount);
                break;
            case 1:
                    this.simulatorController.setLabel1("Backend scan\nsamples: " + queueAmount);
                break;
            case 2:
                    this.simulatorController.setLabel2("Robot verification #1\nsamples: " + queueAmount);
                break;
            case 3:
                    this.simulatorController.setLabel3("Robot verification #2\nsamples: " + queueAmount);
                break;
            case 4:
                    this.simulatorController.setLabel4("Human verification\nsamples: " + queueAmount);
                break;
        }
    }

    // TODO: Animations
    public void moveSample(int id){
        PathTransition transition = new PathTransition();
        switch (id){
            case 0:
                this.simulatorController.getCircle0_1().setVisible(true);
                System.out.println(this.simulatorController.getCircle1().getBoundsInParent());
                System.out.println(this.simulatorController.getCircle0_1().getBoundsInParent());
                System.out.println(this.simulatorController.getPolyline0_1());

                Polyline polyline = new Polyline();
                polyline.getPoints().addAll(new Double[]{
                        63.0, 0.0,
                        215.0, 0.0
                });

                transition.setNode(this.simulatorController.getCircle0_1());
                transition.setDuration(Duration.seconds(2));
                transition.setPath(polyline);
                transition.setCycleCount(Animation.INDEFINITE);
                transition.play();
                break;
            case 1:
                this.simulatorController.getCircle1().setVisible(false);
                break;
            case 2:
                this.simulatorController.getCircle2().setVisible(false);
                break;
            case 3:
                this.simulatorController.getCircle3().setVisible(false);
                break;
            case 4:
                this.simulatorController.getCircle4().setVisible(false);
                break;
        }
    }

}
