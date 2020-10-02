package controller;

import javafx.animation.PathTransition;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class Tracker {


    private VBox vBox;
    private Controller controller;

    public Tracker(VBox vBox, Controller controller){
        this.vBox = vBox;
        this.controller = controller;
    }

    public void setCircleStatus(int id){
        switch (id){
            case 0:
                this.controller.getCircle0().setVisible(true);
                break;
            case 1:
                this.controller.getCircle1().setVisible(true);
                break;
            case 2:
                this.controller.getCircle2().setVisible(true);
                break;
            case 3:
                this.controller.getCircle3().setVisible(true);
                break;
            case 4:
                this.controller.getCircle4().setVisible(true);
                break;
        }
    }

    public void removeCircleStatus(int id){
        switch (id){
            case 0:
                this.controller.getCircle0().setVisible(false);
                break;
            case 1:
                this.controller.getCircle1().setVisible(false);
                break;
            case 2:
                this.controller.getCircle2().setVisible(false);
                break;
            case 3:
                this.controller.getCircle3().setVisible(false);
                break;
            case 4:
                this.controller.getCircle4().setVisible(false);
                break;
        }
    }

    public void moveSample(int id){
        PathTransition transition = new PathTransition();
        switch (id){
            case 0:
                this.controller.getCircle0_1().setVisible(true);
                transition.setNode(this.controller.getCircle0_1());
                transition.setDuration(Duration.seconds(2));
                transition.setPath(this.controller.getLine0_1());
                transition.setCycleCount(1);
                transition.play();
                break;
            case 1:
                this.controller.getCircle1().setVisible(false);
                break;
            case 2:
                this.controller.getCircle2().setVisible(false);
                break;
            case 3:
                this.controller.getCircle3().setVisible(false);
                break;
            case 4:
                this.controller.getCircle4().setVisible(false);
                break;
        }
    }

}
