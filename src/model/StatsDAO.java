package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class StatsDAO {

    final String URL = "jdbc:mysql://localhost/SIMULATOR";
    final String USERNAME = "simu";
    final String PASSWORD = "simu";

    private class RunItem
    {
        public int run_id;
        public long run_time;
        public int simulator_time;
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

    public void saveServicePoint(ServicePointStatistic servicePointStatistic, int run_id){
        try(Connection connection = this.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO SERVICEPOINT (run_id, name, busy_time, service_times, utilization, avg_servicetime) VALUES (?, ?, ?, ?, ?, ?);");
            //String query = String.format("INSERT INTO SERVICEPOINT (run_id, name, busy_time, service_times, utilization, avg_servicetime) VALUES (%d, '%s', %s, %d, %s, %s);",
             //       run_id, sPs.getName(), sPs.getBusyTime(), sPs.getServiceTimes(), sPs.getUtilization(), sPs.getAvgServiceTime());

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

    private ResultSet getRunResultSet(){

        ResultSet run_results = null;
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            run_results = statement.executeQuery("SELECT * FROM RUN;");
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to initialize database");
        }

        return run_results;
    }

    private ArrayList<RunItem> getRunResultSet2(){

        ArrayList<RunItem> items = new ArrayList<>();

        ResultSet run_results = null;
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            run_results = statement.executeQuery("SELECT * FROM RUN");
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



    public ArrayList<SimulatorStatistics> getAllServicepoints(){
        ArrayList<SimulatorStatistics> simulatorStatistics = new ArrayList<>();
        //ResultSet run_results = this.getRunResultSet();
        ArrayList<RunItem> runItems =  getRunResultSet2();

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
                }
                simulatorStatistics.add(new SimulatorStatistics(item.run_id,item.simulator_time, item.run_time, servicePointStatistics));
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to initialize database");
            return null;
        }
        return simulatorStatistics;
    }
    /*public SimulatorStatistics[] getAllServicepoints(){
        ArrayList<SimulatorStatistics> simulatorStatistics = new ArrayList<>();
        ResultSet run_results = this.getRunResultSet();
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();

            ServicePointStatistic[] servicePointStatistics = new ServicePointStatistic[5];
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SERVICEPOINT JOIN RUN ON RUN.run_id = SERVICEPOINT.run_id;");

            int i = 0;
            while(resultSet.next()){
                servicePointStatistics[i] = new ServicePointStatistic(resultSet.getString(3), resultSet.getDouble(4),
                        resultSet.getInt(5), resultSet.getDouble(6), resultSet.getDouble(7));
            }
            simulatorStatistics.add(new SimulatorStatistics(run_results.getInt(1), run_results.getInt(2), run_results.getInt(3), servicePointStatistics));

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to initialize database");
            return new SimulatorStatistics[0];
        }
        return (SimulatorStatistics[]) simulatorStatistics.toArray();
    }*/

}
