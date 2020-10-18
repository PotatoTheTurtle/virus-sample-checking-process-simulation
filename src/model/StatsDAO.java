package model;

import java.sql.*;
import java.util.ArrayList;

/**
 * Statistics Data Access Object , used to store data on SQL server.
 * Please have SIMULATOR database made with username: simu and password: simu.
 *
 * You can also find "createTable.sql" in sql folder of this project. Use that to initialize that database.
 */
public class StatsDAO {

    private final String URL = "jdbc:mysql://localhost/SIMULATOR";
    private final String USERNAME = "simu";
    private final String PASSWORD = "simu";

    private class RunItem
    {
        private int run_id;
        private long run_time;
        private int simulator_time;
    }

    private Connection getConnection(){
        try{
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Unable to connect to database.");
        }
        return null;
    }

    /**
     * Setup RUN table.
     * This is the "base" for the whole simulation statistics.
     *
     * @param simulatorTime the simulation time length
     * @return amount of rows affected
     */
    public int setupRunTable(int simulatorTime){
        try(Connection connection = this.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO RUN (run_time, simulator_time) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, System.currentTimeMillis());
            statement.setLong(2, simulatorTime);
            int affectedRows = statement.executeUpdate();

            if(affectedRows == 0){
                throw new SQLException("Creating run failed, no rows affected.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }else{
                return 0;
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to initialize database #1");
        }
        return 0;
    }

    /**
     * Save service point to database.
     * SERVICEPOINT and RUN tables are linked with RUN_ID
     *
     * @param servicePointStatistic the servicepoint statistic object
     * @param run_id                the run_id used to link the servicepoint to the correct simulation run
     */
    public void saveServicePoint(ServicePointStatistic servicePointStatistic, int run_id){
        try(Connection connection = this.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO SERVICEPOINT (run_id, name, busy_time, service_times, utilization, avg_servicetime) VALUES (?, ?, ?, ?, ?, ?);");

            statement.setInt(1, run_id);
            statement.setString(2, servicePointStatistic.getName());
            statement.setDouble(3, servicePointStatistic.getBusyTime());
            statement.setInt(4, servicePointStatistic.getServiceTimes());
            statement.setDouble(5, servicePointStatistic.getUtilization());
            statement.setDouble(6, servicePointStatistic.getAvgServiceTime());

            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to initialize database");
        }
    }

    private ArrayList<RunItem> getRunResultSet(){

        ArrayList<RunItem> items = new ArrayList<>();

        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet run_results = statement.executeQuery("SELECT * FROM RUN");
            while(run_results.next()) {
                RunItem item = new RunItem();
                item.run_id = run_results.getInt(1);
                item.run_time = run_results.getLong(2);
                item.simulator_time = run_results.getInt(3);
                items.add(item);
            }
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("failed to call SQL");
        }

        return items;
    }

    /**
     * Get all simulator statistics in an arraylist form.
     *
     * @return the array list with simulator statistics
     */
    public ArrayList<SimulatorStatistics> getAllSimulatorStatistics(){
        ArrayList<SimulatorStatistics> simulatorStatistics = new ArrayList<>();
        ArrayList<RunItem> runItems =  getRunResultSet();

        try(Connection connection = this.getConnection()){
            for(RunItem item : runItems){

                ServicePointStatistic[] servicePointStatistics = new ServicePointStatistic[5];
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM SERVICEPOINT where run_id = ?");
                statement.setInt(1, item.run_id);
                ResultSet resultSet = statement.executeQuery();

                int i = 0;
                while(resultSet.next()){
                    servicePointStatistics[i] = new ServicePointStatistic(resultSet.getString(3), resultSet.getDouble(4),
                            resultSet.getInt(5), resultSet.getDouble(6), resultSet.getDouble(7));
                    i++;
                }
                simulatorStatistics.add(new SimulatorStatistics(item.simulator_time, item.run_time, servicePointStatistics));
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to initialize database");
            return null;
        }
        return simulatorStatistics;
    }

}
