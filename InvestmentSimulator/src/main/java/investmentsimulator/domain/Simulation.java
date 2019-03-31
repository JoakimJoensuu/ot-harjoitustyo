package investmentsimulator.domain;

import java.time.LocalDate;
import java.util.*;

public class Simulation {

    private LocalDate[] dates;
    private int[] costAverageValues;
    private double[] costAverageShares;
    private int[] costAveragePurchases;
    private int[] valueAverageValues;
    private double[] valueAverageShares;
    private int[] valueAveragePurchases;
    private int[] prices;

    public Simulation(int sum, LocalDate startingDate, String periodType, int amountOfPeriods, double variation) {
        datesCreator(startingDate, amountOfPeriods, periodType);
        generatePrices(variation / 100, amountOfPeriods);
        createCostAverageValuesAndShares(amountOfPeriods, sum);
        createValueAverageValuesAndShares(amountOfPeriods, sum);
    }

    private void datesCreator(LocalDate startingDate, int amountOfPeriods, String periodType) {
        dates = new LocalDate[amountOfPeriods + 1];
        if (periodType.equals("Päivä")) {
            createDays(startingDate);
        } else if (periodType.equals("Viikko")) {
            createWeeks(startingDate);
        } else if (periodType.equals("Kuukausi")) {
            createMonths(startingDate);
        } else if (periodType.equals("Vuosi")) {
            createYears(startingDate);
        }
    }

    private void createDays(LocalDate startingDate) {
        dates[0] = startingDate;
        for (int i = 1; i < dates.length; i++) {
            dates[i] = dates[i - 1].plusDays(1);
        }
    }

    private void createWeeks(LocalDate startingDate) {
        dates[0] = startingDate;
        for (int i = 1; i < dates.length; i++) {
            dates[i] = dates[i - 1].plusWeeks(1);

        }
    }

    private void createMonths(LocalDate startingDate) {
        dates[0] = startingDate;
        for (int i = 1; i < dates.length; i++) {
            dates[i] = dates[i - 1].plusMonths(1);

        }
    }

    private void createYears(LocalDate startingDate) {
        dates[0] = startingDate;
        for (int i = 1; i < dates.length; i++) {
            dates[i] = dates[i - 1].plusYears(1);
        }
    }

    private void generatePrices(double variation, int amountOfPeriods) {
        Random r = new Random();
        prices = new int[amountOfPeriods + 1];
        prices[0] = 10000;
        for (int i = 1; i < prices.length; i++) {
            double multiplier = 1 + (r.nextDouble() * 2 - 1) * variation;
            prices[i] = (int) ((double) prices[i - 1] * multiplier);

        }
    }

    private void createCostAverageValuesAndShares(int amountOfPeriods, int sum) {
        costAverageShares = new double[amountOfPeriods + 1];
        costAverageValues = new int[amountOfPeriods + 1];
        costAveragePurchases = new int[amountOfPeriods + 1];
        costAverageShares[0] = ((double) sum / prices[0]);
        costAverageValues[0] = roundToInt(prices[0] * costAverageShares[0]);
        costAveragePurchases[0] = sum;
        for (int i = 1; i < costAverageValues.length; i++) {
            costAverageShares[i] = costAverageShares[i - 1] + ((double) sum / prices[i]);
            costAverageValues[i] = roundToInt(prices[i] * costAverageShares[i]);
            costAveragePurchases[i] = sum;
        }
    }

    private void createValueAverageValuesAndShares(int amountOfPeriods, int sum) {
        valueAverageShares = new double[amountOfPeriods + 1];
        valueAverageValues = new int[amountOfPeriods + 1];
        valueAveragePurchases = new int[amountOfPeriods + 1];
        valueAverageShares[0] = (double) sum / prices[0];
        valueAverageValues[0] = roundToInt(prices[0] * valueAverageShares[0]);
        valueAveragePurchases[0] = sum;
        for (int i = 1; i < valueAverageShares.length; i++) {
            int buyingSum = roundToInt(((i + 1) * sum - ((double) valueAverageShares[i - 1] * prices[i])));
            valueAverageShares[i] = valueAverageShares[i - 1] + ((double) buyingSum / prices[i]);
            valueAverageValues[i] = roundToInt(prices[i] * valueAverageShares[i]);
            valueAveragePurchases[i] = buyingSum;
        }
    }

    public int roundToInt(double d) {
        return (int) Math.round(d);
    }

    public LocalDate[] getDates() {
        return dates;
    }

    public void setDates(LocalDate[] dates) {
        this.dates = dates;
    }

    public int[] getCostAverageValues() {
        return costAverageValues;
    }

    public void setCostAverageValues(int[] costAverageValues) {
        this.costAverageValues = costAverageValues;
    }

    public double[] getCostAverageShares() {
        return costAverageShares;
    }

    public void setCostAverageShares(double[] costAverageShares) {
        this.costAverageShares = costAverageShares;
    }

    public int[] getCostAveragePurchases() {
        return costAveragePurchases;
    }

    public void setCostAveragePurchases(int[] costAveragePurchases) {
        this.costAveragePurchases = costAveragePurchases;
    }

    public int[] getValueAverageValues() {
        return valueAverageValues;
    }

    public void setValueAverageValues(int[] valueAverageValues) {
        this.valueAverageValues = valueAverageValues;
    }

    public double[] getValueAverageShares() {
        return valueAverageShares;
    }

    public void setValueAverageShares(double[] valueAverageShares) {
        this.valueAverageShares = valueAverageShares;
    }

    public int[] getValueAveragePurchases() {
        return valueAveragePurchases;
    }

    public void setValueAveragePurchases(int[] valueAveragePurchases) {
        this.valueAveragePurchases = valueAveragePurchases;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }
}
