package model;

import java.util.Arrays;
import java.util.Calendar;

public class SimulatorStatistics implements Comparable<SimulatorStatistics>  {

    private int runID;
    private int simulationTime;
    private long runTime;
    private ServicePointStatistic[] servicePointStatistics;
    private Calendar calendar;

    public SimulatorStatistics(int runID, int simulationTime, long runTime, ServicePointStatistic[] servicePointStatistics){
        this.runID = runID;
        this.simulationTime = simulationTime;
        this.runTime = runTime;
        this.servicePointStatistics = servicePointStatistics;
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(runTime);
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

    public ServicePointStatistic getServicepointByIndex(int index){
        return this.servicePointStatistics[index];
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override
    public String toString() {
        return String.format("%d/%d/%d %d:%d", this.calendar.get(Calendar.DATE), this.calendar.get(Calendar.MONTH),
                this.calendar.get(Calendar.YEAR), this.calendar.get(Calendar.HOUR_OF_DAY), this.calendar.get(Calendar.MINUTE));
    }

    @Override
    public int compareTo(SimulatorStatistics simulatorStatistics) {
        return simulatorStatistics.getCalendar().compareTo(this.calendar);
    }

}
