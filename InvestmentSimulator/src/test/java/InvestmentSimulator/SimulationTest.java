package InvestmentSimulator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import investmentsimulator.domain.Simulation;
import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author afkaaja
 */
public class SimulationTest {

    Simulation simulation;

    public SimulationTest() {
        simulation = new Simulation(1, LocalDate.of(2000, 1, 1), "Päivä", 1, 100);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Test
    public void simulationPricesWithMaximumVariationAreCorrect() {
        for (int i = 0; i < 10000000; i++) {
            Simulation s = new Simulation(1, LocalDate.of(2000, 1, 1), "Päivä", 1, 100);
            int generatedPrice = s.getPrices()[1];
            assertTrue(generatedPrice >= 0 && generatedPrice <= 20000);
        }
    }
    
    @Test
    public void simulationPricesWithMediumVariationAreCorrect() {
        for (int i = 0; i < 10000000; i++) {
            Simulation s = new Simulation(1, LocalDate.of(2000, 1, 1), "Päivä", 1, 50);
            int generatedPrice = s.getPrices()[1];
            assertTrue(generatedPrice >= 5000 && generatedPrice <= 15000);
        }
    }
    @Test
    public void simulationPricesWithMinimumVariationAreCorrect() {
        for (int i = 0; i < 10000000; i++) {
            Simulation s = new Simulation(1, LocalDate.of(2000, 1, 1), "Päivä", 1, 0);
            int generatedPrice = s.getPrices()[1];
            assertTrue(generatedPrice == 10000);
        }
    }
    
    @Test
    public void roundToIntRoundsCorrectly() {
        assertTrue(simulation.roundToInt(1.99) == 2);
        System.out.println(simulation.roundToInt(1.99));
        assertTrue(simulation.roundToInt(1.5) == 2);
        System.out.println(simulation.roundToInt(1.5));
        assertTrue(simulation.roundToInt(1.499) == 1);
        System.out.println(simulation.roundToInt(1.499));
    }
    
    
}
