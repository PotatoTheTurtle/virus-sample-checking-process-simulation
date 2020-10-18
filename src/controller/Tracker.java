package controller;

/**
 * The tracker is used to set the correct circle visible during the simulation.
 */
public class Tracker {


    private SimulatorController simulatorController;

    /**
     * Instantiates a new Tracker.
     * Simulator controller is used the get the circle fxml
     *
     * @param simulatorController the simulator controller
     */
    public Tracker(SimulatorController simulatorController){
        this.simulatorController = simulatorController;
    }

    /**
     * Set circle status.
     *
     * @param id      the id of the servicepoint
     * @param visible is visible bool
     */
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

    /**
     * Set skip circle during simulation.
     * Can only set the skip circle if servicepoint is skippable
     *
     * @param id      the id of the servicepoint
     * @param visible is visible bool
     */
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

    /**
     * Set servicepoint label.
     * Used to indicate how many are samples are in the queue.
     *
     * @param id          the id of the servicepoint
     * @param queueAmount the queue amount
     */
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

}
