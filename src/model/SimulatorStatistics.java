package model;

import java.util.Calendar;

/**
 * The type Simulator statistics.
 */
public class SimulatorStatistics implements Comparable<SimulatorStatistics>  {

    private int simulationTime;
    private ServicePointStatistic[] servicePointStatistics;
    private Calendar calendar;

    /**
     * Instantiates a new object for simulator statistics.
     * Simulator statics stores the whole simulation statics including servicepoints.
     *
     * @param simulationTime         the total simulation time
     * @param runTime                the run time in milliseconds
     * @param servicePointStatistics the service point statistics
     */
    public SimulatorStatistics(int simulationTime, long runTime, ServicePointStatistic[] servicePointStatistics){
        this.simulationTime = simulationTime;
        this.servicePointStatistics = servicePointStatistics;
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(runTime);
    }

    /**
     * Gets total simulation time.
     *
     * @return the total simulation time
     */
    public int getSimulationTime() {
        return simulationTime;
    }

    /**
     * Get servicepoint by index (0-4)
     *
     * @param index the index of the service point (servicepoint_id - 1)
     * @return the servicepoint statistic
     */
    public ServicePointStatistic getServicepointByIndex(int index){
        return this.servicePointStatistics[index];
    }

    private Calendar getCalendar() {
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
