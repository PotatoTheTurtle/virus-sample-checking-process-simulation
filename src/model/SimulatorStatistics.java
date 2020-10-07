package model;

public class SimulatorStatistics {

    private int runID;
    private int simulationTime;
    private long runTime;
    private ServicePointStatistic[] servicePointStatistics;

    public SimulatorStatistics(int runID, int simulationTime, long runTime, ServicePointStatistic[] servicePointStatistics){
        this.runID = runID;
        this.simulationTime = simulationTime;
        this.runTime = runTime;
        this.servicePointStatistics = servicePointStatistics;
    }

    public ServicePointStatistic[] getServicePointStatistics(){
        return this.servicePointStatistics;
    }

    public int getRunID() {
        return runID;
    }

    public int getSimulationTime() {
        return simulationTime;
    }

    public long getRunTime() {
        return runTime;
    }
}
