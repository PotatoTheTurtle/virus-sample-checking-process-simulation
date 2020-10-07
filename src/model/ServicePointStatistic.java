package model;

public class ServicePointStatistic {

    private String name;
    private double busyTime;
    private int serviceTimes;
    private double utilization;
    private double avgServiceTime;

    public ServicePointStatistic(String name, double busyTime, int serviceTimes, double utilization, double avgServiceTime){
        this.name = name;
        this.busyTime = busyTime;
        this.serviceTimes = serviceTimes;
        this.utilization = utilization;
        this.avgServiceTime = avgServiceTime;
    }

    public String getName() {
        return name;
    }

    public double getBusyTime() {
        return busyTime;
    }

    public int getServiceTimes() {
        return serviceTimes;
    }

    public double getUtilization() {
        return utilization;
    }

    public double getAvgServiceTime() {
        return avgServiceTime;
    }
}
