/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InvestmentSimulator;

import investmentsimulator.domain.Generator;
import investmentsimulator.domain.Simulation;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author afkaaja
 */
public class GeneratorTest {

    Generator s;

    @Before
    public void setUp() {
        this.s = new Generator();
    }

    @Test
    public void generateCostAverageSharesWorksCorrectly() {
        int[] prices1 = {10000, 20000, 30000};
        int[] prices2 = {10000, 10, 1};
        int[] prices3 = {10000, 18000, 300};

        assertTrue(s.generateCostAverageShares(prices1, 10000, 3)[3] == 1.8333333333333333);
        assertTrue(s.generateCostAverageShares(prices2, 10000, 3)[3] == 11001.0);
        assertTrue(s.generateCostAverageShares(prices3, 10000, 3)[3] == 34.88888888888889);
    }

    @Test
    public void generateValueAverageSharesWorksCorrectly() {
        int[] prices1 = {10000, 20000, 30000};
        int[] prices2 = {10000, 10, 1};
        int[] prices3 = {10000, 18000, 300};
        int[] prices4 = {10000, 20000, 50000};

        assertTrue(s.generateValueAverageShares(prices1, 10000, 3)[3] == 1);
        assertTrue(s.generateValueAverageShares(prices2, 10000, 3)[3] == 30000.0);
        assertTrue(s.generateValueAverageShares(prices3, 10000, 3)[3] == 100.0);
        assertTrue(s.generateValueAverageShares(prices4, 10000, 3)[3] == 0.6);
    }

    @Test
    public void generateValueAverageValuesWorksCorrectly() {
        int[] prices1 = {10000, 20000, 30000, 40000};
        double[] shares1 = {0.0, 1.0, 1.0, 1.0};

        int[] prices2 = {10000, 10, 1, 20};
        double[] shares2 = {0.0, 1.0, 2000.0, 30000.0};

        int[] prices3 = {10000, 18000, 300, 40};
        double[] shares3 = {0.0, 1.0, 1.1111111111111112, 100.0};

        int[] prices4 = {10000, 20000, 50000, 70000};
        double[] shares4 = {0.0, 1.0, 1.0, 0.6};

        assertTrue(s.generateValueAverageValues(prices1, shares1)[3] == 40000);
        assertTrue(s.generateValueAverageValues(prices2, shares2)[3] == 600000);
        assertTrue(s.generateValueAverageValues(prices3, shares3)[3] == 4000);
        assertTrue(s.generateValueAverageValues(prices4, shares4)[3] == 42000);
    }

    @Test
    public void generateCostAverageValuesWorksCorrectly() {
        int[] prices1 = {10000, 20000, 30000, 40000};
        double[] shares1 = s.generateCostAverageShares(prices1, 10000, 3);

        int[] prices2 = {10000, 10, 1, 20};
        double[] shares2 = {0.0, 1.0, 1001.0, 11001.0};

        int[] prices3 = {10000, 18000, 300, 40};
        double[] shares3 = {0.0, 1.0, 1.5555555555555556, 34.88888888888889};

        int[] prices4 = {10000, 20000, 50000, 70000};
        double[] shares4 = {0.0, 1.0, 1.5, 1.7};

        assertTrue(s.generateCostAverageValues(prices1, shares1)[3] == 73333);
        assertTrue(s.generateCostAverageValues(prices2, shares2)[3] == 220020);
        assertTrue(s.generateCostAverageValues(prices3, shares3)[3] == 1396);
        assertTrue(s.generateCostAverageValues(prices4, shares4)[3] == 119000);
    }

    @Test
    public void generateValueAveragePurchasesWorksCorrectly() {
        int[] values1 = {0, 20000, 30000, 40000};
        int[] values2 = {0, 10, 2000, 600000};
        int[] values3 = {0, 18000, 333, 4000};
        int[] values4 = {0, 20000, 50000, 42000};
        int sum = 10000;

        assertTrue(s.generateValueAveragePurchases(values1, sum)[3] == 0);
        assertTrue(s.generateValueAveragePurchases(values2, sum)[3] == -560000);
        assertTrue(s.generateValueAveragePurchases(values3, sum)[3] == 36000);
        assertTrue(s.generateValueAveragePurchases(values4, sum)[3] == -2000);
    }

    @Test
    public void generateCostAveragePurchasesWorksCorrectly() {
        int[] values1 = {0, 20000, 45000, 73333};
        int[] values2 = {0, 10, 1001, 220020};
        int[] values3 = {0, 18000, 467, 1396};
        int[] values4 = {0, 20000, 75000, 119000};
        int sum = 10000;

        assertTrue(s.generateCostAveragePurchases(values1, sum)[3] == 10000);
        assertTrue(s.generateCostAveragePurchases(values2, sum)[3] == 10000);
        assertTrue(s.generateCostAveragePurchases(values3, sum)[3] == 10000);
        assertTrue(s.generateCostAveragePurchases(values4, sum)[3] == 10000);
    }

    @Test
    public void generateCostAverageProfitWorksCorrectly() {
        int[] values1 = {0, 20000, 45000, 73333};
        int[] purchases1 = {10000, 10000, 10000, 10000};
        int[] values2 = {0, 10, 1001, 220020};
        int[] purchases2 = {10000, 10000, 10000, 10000};
        int[] values3 = {0, 18000, 467, 1396};
        int[] purchases3 = {10000, 10000, 10000, 10000};
        int[] values4 = {0, 20000, 75000, 119000};
        int[] purchases4 = {10000, 10000, 10000, 10000};

        assertTrue(s.generateCostAverageProfit(purchases1, values1)[3] == 43333);
        assertTrue(s.generateCostAverageProfit(purchases2, values2)[3] == 190020);
        assertTrue(s.generateCostAverageProfit(purchases3, values3)[3] == -28604);
        assertTrue(s.generateCostAverageProfit(purchases4, values4)[3] == 89000);
    }

    @Test
    public void generateValueAverageProfitWotksCorrectly() {
        int[] purchases1 = {10000, 0, 0, 0};
        int[] purchases2 = {10000, 19990, 28000, -560000};
        int[] purchases3 = {10000, 2000, 29667, 36000};
        int[] purchases4 = {10000, 0, -20000, -2000};
        int sum = 10000;

        assertTrue(s.generateValueAverageProfit(purchases1, sum)[3] == 30000);
        assertTrue(s.generateValueAverageProfit(purchases2, sum)[3] == 542010);
        assertTrue(s.generateValueAverageProfit(purchases3, sum)[3] == -37667);
        assertTrue(s.generateValueAverageProfit(purchases4, sum)[3] == 52000);

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
    public void roundToNDecimalsRoundsCorrectly() {
        double decimal1 = 123.456234;
        int n1 = 2;
        int n2 = 1;

        assertTrue(s.roundToNDecimals(decimal1, n1) == 123.46);
        assertTrue(s.roundToNDecimals(decimal1, n2) == 123.5);

    }

    @Test
    public void generateROICorrectly() {
        int[] purchases1 = {10000, 10000, 10000, 10000};
        int[] purchases2 = {10000, 29990, 57990, -502010};
        int[] purchases3 = {10000, 12000, 41667, 77667};
        int[] purchases4 = {10000, 10000, -10000, -8000};
        int[] profit1 = {0, 10000, 20000, 30000};
        int[] profit2 = {0, -9990, -27990, 542010};
        int[] profit3 = {0, 8000, -11667, -37667};
        int[] profit4 = {0, 10000, 40000, 52000};

        assertTrue(s.generateROI(profit1, purchases1)[3] == 300.0);
        assertTrue(s.generateROI(profit2, purchases2)[3] == -107.97);
        assertTrue(s.generateROI(profit3, purchases3)[3] == -48.5);
        assertTrue(s.generateROI(profit4, purchases4)[3] == -650.0);

    }

}
