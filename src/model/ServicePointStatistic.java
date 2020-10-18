package model;

/**
 * The type Service point statistic.
 */
public class ServicePointStatistic {

    private String name;
    private double busyTime;
    private int serviceTimes;
    private double utilization;
    private double avgServiceTime;

    /**
     * Instantiates a new Servicepoint statistic.
     * Servicepoint statistic stores any statistic/data related to servicepoints.
     *
     * @param name           the name
     * @param busyTime       the busy time
     * @param serviceTimes   the service times
     * @param utilization    the utilization
     * @param avgServiceTime the avg service time
     */
    public ServicePointStatistic(String name, double busyTime, int serviceTimes, double utilization, double avgServiceTime){
        this.name = name;
        this.busyTime = busyTime;
        this.serviceTimes = serviceTimes;
        this.utilization = utilization;
        this.avgServiceTime = avgServiceTime;
    }

    /**
     * Gets servicepoint name.
     *
     * @return the servicepoint name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets servicepoint total busy time.
     *
     * @return the total busy time.
     */
    public double getBusyTime() {
        return busyTime;
    }

    /**
     * Gets the times that servicepoint was used.
     *
     * @return the service times int
     */
    public int getServiceTimes() {
        return serviceTimes;
    }

    /**
     * Gets utilization.
     *
     * @return the total utilization
     */
    public double getUtilization() {
        return utilization;
    }

    /**
     * Gets avg total service time.
     *
     * @return the avg total service time
     */
    public double getAvgServiceTime() {
        return avgServiceTime;
    }
}
