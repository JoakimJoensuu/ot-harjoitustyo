/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package investmentsimulator.dao;

import investmentsimulator.domain.Simulation;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author afkaaja
 */
public class SimulationDao {

    private String database;

    public SimulationDao(String databaseName) throws SQLException {
        this.database = databaseName;
        initDatabase();
    }

    public void initDatabase() throws SQLException {
        Connection connection = connect();
        PreparedStatement createSimulationTable = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS simulation (id INTEGER PRIMARY KEY,name VARCHAR(100),sum INTEGER,startingDate DATE,periodType VARCHAR(10),amountOfPeriods INTEGER);"
        );
        createSimulationTable.execute();
        createSimulationTable.close();

        PreparedStatement createPriceTable = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS price (simulation_id INTEGER,i INTEGER,price INTEGER,FOREIGN KEY(simulation_id) REFERENCES simulation(id));"
        );
        createPriceTable.execute();
        createPriceTable.close();

        connection.close();

    }

    private Connection connect() throws SQLException {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + database;
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void saveSimulation(String name, int sum, LocalDate startingDate, String periodType, int amountOfPeriods, int[] prices) throws SQLException {
        Connection connection = connect();

        saveSimulationDetails(connection, name, sum, startingDate, periodType, amountOfPeriods);

        int id = getLastId(connection);
        System.out.println(id);

        saveSimulationPrices(id, prices, connection);
        connection.close();

    }

    private void saveSimulationDetails(Connection connection, String name, int sum, LocalDate startingDate, String periodType, int amountOfPeriods) throws SQLException {
        PreparedStatement saveDetailsStatement = connection.prepareStatement(
                "INSERT INTO simulation"
                + "(name,"
                + "sum,"
                + "startingDate,"
                + "periodType,"
                + "amountOfPeriods)"
                + "VALUES (?, ?, ?, ?, ?);"
        );

        saveDetailsStatement.setString(1, name);
        saveDetailsStatement.setInt(2, sum);
        saveDetailsStatement.setDate(3, Date.valueOf(startingDate));
        saveDetailsStatement.setString(4, periodType);
        saveDetailsStatement.setInt(5, amountOfPeriods);

        saveDetailsStatement.executeUpdate();
        saveDetailsStatement.close();
    }

    private int getLastId(Connection connection) throws SQLException {
        PreparedStatement getLastId = connection.prepareStatement("SELECT last_insert_rowid() AS id;");
        ResultSet resultSet = getLastId.executeQuery();

        int id = -1;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }

        return id;
    }

    private void saveSimulationPrices(int simulationId, int[] prices, Connection connection) throws SQLException {
        for (int i = 0; i < prices.length; i++) {
            int price = prices[i];

            PreparedStatement savePricesStatement = connection.prepareStatement(
                    "INSERT INTO price"
                    + "(simulation_id,"
                    + "i,"
                    + "price)"
                    + "VALUES (?, ?, ?);"
            );

            savePricesStatement.setInt(1, simulationId);
            savePricesStatement.setInt(2, i);
            savePricesStatement.setInt(3, price);
            savePricesStatement.executeUpdate();
            savePricesStatement.close();
        }
    }

    public List<Simulation> getAllSimulations() {
        List<Simulation> savedSimulations = new ArrayList<>();
        try {
            Connection connection = connect();
            PreparedStatement getSimulationsQuery = connection.prepareStatement("SELECT * FROM simulation;");
            ResultSet resultSet = getSimulationsQuery.executeQuery();

            while (resultSet.next()) {
                Simulation s = new Simulation();
                s.setSimulationDetails(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("sum"), resultSet.getDate("startingDate").toLocalDate(), resultSet.getString("periodType"), resultSet.getInt("amountOfPeriods"));
                savedSimulations.add(s);

            }

            getSimulationsQuery.close();
            resultSet.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SimulationDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return savedSimulations;
    }

    public List<Integer> getSimulationPrices(int id) {
        List<Integer> prices = new ArrayList<>();
        try {
            Connection connection = connect();
            PreparedStatement getPricesQuery = connection.prepareStatement("SELECT price FROM price WHERE simulation_id == ? ORDER BY i ASC;");
            getPricesQuery.setInt(1, id);
            ResultSet resultSet = getPricesQuery.executeQuery();

            while (resultSet.next()) {
                prices.add(resultSet.getInt("price"));
            }

            getPricesQuery.close();
            resultSet.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SimulationDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prices;

    }

}
