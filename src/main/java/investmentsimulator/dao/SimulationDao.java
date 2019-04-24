package investmentsimulator.dao;

import investmentsimulator.domain.Simulation;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Luokka tarjoaa sovelluksen muille luokille metodeja tietokannan käyttöä
 * varten.
 * 
 * @author Joakim Joensuu
 */
public class SimulationDao {

    /**
     * Sovelluksen tietokannan nimi, joka määritellään konstruktorissa.
     */
    private final String database;

    /**
     * SimulationDao-luokan konstruktori, joka määrittelee luokkamuuttujan
     * database ja alustaa tietokannan.
     *
     * @see investmentsimulator.dao.SimulationDao#initDatabase()
     *
     * @param databaseName tietokannan tiedostonimi
     *
     * @throws SQLException mikäli tietokannan luonnissa tapahtuu virhe
     */
    public SimulationDao(String databaseName) throws SQLException {
        this.database = databaseName;
        initDatabase();
    }

    /**
     * Luo kaksitauluisen tietokannan, mikäli tietokantaa ei ole jo luotu.
     *
     */
    public void initDatabase() {
        try {
            Connection connection = connect();
            PreparedStatement createSimulationTable = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS simulation (id INTEGER PRIMARY KEY, name VARCHAR(100), sum INTEGER, startingDate DATE, periodType VARCHAR(10), amountOfPeriods INTEGER);"
            );
            createSimulationTable.execute();
            createSimulationTable.close();

            PreparedStatement createPriceTable = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS price (simulation_id INTEGER, i INTEGER, price INTEGER, FOREIGN KEY(simulation_id) REFERENCES simulation(id));"
            );
            createPriceTable.execute();
            createPriceTable.close();

            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SimulationDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodin avulla saadaan luotua yhteys tietokantaan.
     *
     * @return connection-olio, joka on yhdistetty tietokantaan
     */
    private Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + database;
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     *
     * Tallentaa simulaation ne tiedot tietokantaan, jotka tarvitaan simulaation
     * uudelleen generoimista varten. Hinnat tallennetaan "price"-tauluun loput
     * tiedot "simulation"-tauluun.
     *
     * @see
     * investmentsimulator.dao.SimulationDao#saveSimulationDetails(java.sql.Connection,
     * java.lang.String, int, java.time.LocalDate, java.lang.String, int)
     * @see investmentsimulator.dao.SimulationDao#saveSimulationPrices(int,
     * int[], java.sql.Connection)
     *
     * @param name simulaation nimi
     * @param sum periodeittain sijoitettava summa
     * @param startingDate päivämäärä, josta simulaatio alkaa
     * @param periodType periodin tyyppi: "Päivä" "Viikko" "Kuukausi" tai
     * "Vuosi"
     * @param amountOfPeriods periodien määrä
     * @param prices lista simulaation kohteen hinnoista
     */
    public void saveSimulation(String name, int sum, LocalDate startingDate, String periodType, int amountOfPeriods, int[] prices) {
        try {
            Connection connection = connect();

            saveSimulationDetails(connection, name, sum, startingDate, periodType, amountOfPeriods);

            int id = getLastId(connection);

            saveSimulationPrices(id, prices, connection);
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SimulationDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Tallentaa annetut tiedot "simulation"-tietokantaan.
     *
     * @param connection connection-olio, joka on yhdistetty tietokantaan
     * @param name simulaation nimi
     * @param sum periodeittain sijoitettava summa
     * @param startingDate päivämäärä, josta simulaatio alkaa
     * @param periodType periodin tyyppi: "Päivä" "Viikko" "Kuukausi" tai
     * "Vuosi"
     * @param amountOfPeriods periodien määrä
     */
    private void saveSimulationDetails(Connection connection, String name, int sum, LocalDate startingDate, String periodType, int amountOfPeriods) {
        try {
            PreparedStatement saveDetailsStatement = connection.prepareStatement(
                    "INSERT INTO simulation (name, sum, startingDate, periodType, amountOfPeriods) VALUES (?, ?, ?, ?, ?);"
            );

            saveDetailsStatement.setString(1, name);
            saveDetailsStatement.setInt(2, sum);
            saveDetailsStatement.setDate(3, Date.valueOf(startingDate));
            saveDetailsStatement.setString(4, periodType);
            saveDetailsStatement.setInt(5, amountOfPeriods);

            saveDetailsStatement.executeUpdate();
            saveDetailsStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SimulationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Kertoo viimeisimmän tietokantaan tallennetun rivin id-numeron.
     *
     *
     * @param connection connection-olio, joka on yhdistetty tietokantaan
     * @return viimeisimmän rivin id numero
     */
    private int getLastId(Connection connection) {
        int id = -1;
        try {
            PreparedStatement getLastId = connection.prepareStatement("SELECT last_insert_rowid() AS id;");
            ResultSet resultSet = getLastId.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }

            return id;
        } catch (SQLException ex) {
            Logger.getLogger(SimulationDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     * Tallentaa simulaation hinnat järjestyksessä tietokantaan.
     *
     * @param simulationId simulaation id-numero, johon hinnat liittyvät
     * @param prices hinnat taulukkona
     * @param connection connection-olio, joka on yhdistetty tietokantaan
     */
    private void saveSimulationPrices(int simulationId, int[] prices, Connection connection) {
        for (int i = 0; i < prices.length; i++) {
            try {
                int price = prices[i];

                PreparedStatement savePricesStatement = connection.prepareStatement(
                        "INSERT INTO price(simulation_id, i, price) VALUES (?, ?, ?);"
                );

                savePricesStatement.setInt(1, simulationId);
                savePricesStatement.setInt(2, i);
                savePricesStatement.setInt(3, price);
                savePricesStatement.executeUpdate();
                savePricesStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(SimulationDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Palauttaa tietokantaan tallennetut simulaatiot Simulation-olioita
     * sisältävänä listana.
     *
     * @return Simulation-olioita sisältävä lista
     */
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

    /**
     * Palauttaa tietyn simulaation hinnat listana.
     *
     * @param id Simulaation id-numero
     * @return kokonaislukuja sisältävä lista hinnoista järjestyksessä
     * ensimmäisestä viimeiseen
     */
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
