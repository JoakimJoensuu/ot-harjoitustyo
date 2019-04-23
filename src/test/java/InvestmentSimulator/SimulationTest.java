package InvestmentSimulator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import investmentsimulator.domain.Simulation;
import java.time.LocalDate;
import java.util.Arrays;
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

    Simulation s;

    @Before
    public void setUp() {
        this.s = new Simulation();
    }

    @Test
    public void simulationPricesWithMaximumVariationAreCorrect() {

        for (int i = 0; i < 1000000; i++) {
            int generatedPrice = s.generatePrices(1, 1)[1];

            assertTrue(generatedPrice >= 0 && generatedPrice <= 20000);
        }
    }

    @Test
    public void simulationPricesWithMediumVariationAreCorrect() {
        for (int i = 0; i < 1000000; i++) {
            int generatedPrice = s.generatePrices(0.5, 1)[1];

            assertTrue(generatedPrice >= 5000 && generatedPrice <= 15000);
        }
    }

    @Test
    public void simulationPricesWithMinimumVariationAreCorrect() {
        for (int i = 0; i < 1000000; i++) {
            int generatedPrice = s.generatePrices(0, 1)[1];

            assertTrue(generatedPrice == 10000);
        }
    }

    @Test
    public void roundToIntRoundsCorrectly() {
        assertTrue(s.roundToInt(1.99) == 2);
        assertTrue(s.roundToInt(1.5) == 2);
        assertTrue(s.roundToInt(1.499) == 1);
    }

    @Test
    public void centsToEuroStringWorksCorrectly() {

        int a = 123456;
        int b = -12346;
        int c = 0;
        int d = 2;
        int e = 11;
        int f = 111;

        assertTrue(s.centsToEuroString(a).equals("1234,56 €"));
        assertTrue(s.centsToEuroString(b).equals("-123,46 €"));
        assertTrue(s.centsToEuroString(c).equals("0,00 €"));
        assertTrue(s.centsToEuroString(d).equals("0,02 €"));
        assertTrue(s.centsToEuroString(e).equals("0,11 €"));
        assertTrue(s.centsToEuroString(f).equals("1,11 €"));
    }

    @Test
    public void initializeArraysInitializesAllArrays() {
        assertTrue(s.getDates() == null);
        assertTrue(s.getCostAverageShares() == null);
        assertTrue(s.getValueAverageShares() == null);
        assertTrue(s.getCostAverageValues() == null);
        assertTrue(s.getValueAverageValues() == null);
        assertTrue(s.getValueAveragePurchases() == null);
        assertTrue(s.getCostAveragePurchases() == null);
        assertTrue(s.getCostAverageProfit() == null);
        assertTrue(s.getValueAverageProfit() == null);
        assertTrue(s.getCostAverageROI() == null);
        assertTrue(s.getValueAverageROI() == null);
        assertTrue(s.getValueAverageInvested() == null);
        assertTrue(s.getCostAverageInvested() == null);
        assertTrue(s.getPrices() == null);

        s.initializeSimulation("name", 100, LocalDate.now(), "Viikko", 1, 30);

        assertTrue(s.getDates() != null);
        assertTrue(s.getCostAverageShares() != null);
        assertTrue(s.getValueAverageShares() != null);
        assertTrue(s.getCostAverageValues() != null);
        assertTrue(s.getValueAverageValues() != null);
        assertTrue(s.getValueAveragePurchases() != null);
        assertTrue(s.getCostAveragePurchases() != null);
        assertTrue(s.getCostAverageProfit() != null);
        assertTrue(s.getValueAverageProfit() != null);
        assertTrue(s.getCostAverageROI() != null);
        assertTrue(s.getValueAverageROI() != null);
        assertTrue(s.getValueAverageInvested() != null);
        assertTrue(s.getCostAverageInvested() != null);
        assertTrue(s.getPrices() != null);
    }

}
