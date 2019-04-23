/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InvestmentSimulator;

import investmentsimulator.dao.SimulationDao;
import investmentsimulator.domain.Simulation;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author afkaaja
 */
public class SimulationDaoTest {

    SimulationDao s;

    @Before
    public void setUp() {
        try {
            s = new SimulationDao("testbase.db");
        } catch (SQLException ex) {
            Logger.getLogger(SimulationDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
        File file = new File("testbase.db");
        file.delete();
    }

    @Test
    public void SavingSimulationAndGettingSameSimulationWorks() {
        int[] prices = {100, 90, 80};
        LocalDate date = LocalDate.now();
        s.saveSimulation("name", 100, date, "Viikko", 2, prices);

        Simulation loadedSimulation = s.getAllSimulations().get(0);

        List<Integer> loadedPrices = s.getSimulationPrices(loadedSimulation.getId());

        assertTrue(loadedSimulation.getName().equals("name"));
        assertTrue(loadedSimulation.getSum() == 100);
        assertTrue(loadedSimulation.getStartingDate().isEqual(date));
        assertTrue(loadedSimulation.getPeriodType().equals("Viikko"));
        assertTrue(loadedSimulation.getAmountOfPeriods() == 2);

    }
    
     @Test
    public void SavingSimulationAndGettingSimulationPricecsWorks() {
        int[] prices = {100, 90, 80};
        LocalDate date = LocalDate.now();
        s.saveSimulation("name", 100, date, "Viikko", 2, prices);

        Simulation loadedSimulation = s.getAllSimulations().get(0);

        List<Integer> loadedPrices = s.getSimulationPrices(loadedSimulation.getId());

        assertTrue(loadedPrices.get(0) == prices[0]);
        assertTrue(loadedPrices.get(1) == prices[1]);
        assertTrue(loadedPrices.get(2) == prices[2]);
    }

}
