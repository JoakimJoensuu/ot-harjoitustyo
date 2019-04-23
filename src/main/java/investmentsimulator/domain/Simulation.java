package investmentsimulator.domain;

import java.time.LocalDate;
import java.util.*;

public class Simulation {

    private Generator generator;
    private LocalDate[] dates;
    private int[] prices;
    private int[] costAverageValues;
    private double[] costAverageShares;
    private int[] costAveragePurchases;
    private int[] costAverageProfit;
    private int[] valueAverageValues;
    private double[] valueAverageShares;
    private int[] valueAveragePurchases;
    private int[] valueAverageProfit;
    private double[] valueAverageROI;
    private double[] costAverageROI;
    private int[] costAverageInvested;
    private int[] valueAverageInvested;
    private String name;
    private int amountOfPeriods;
    private int sum;
    private LocalDate startingDate;
    private String periodType;
    private int id;

    public Simulation() {
        this.generator = new Generator();
    }

    public void initializeSimulation(String name, int sum, LocalDate startingDate, String periodType, int amountOfPeriods, double variation) {
        this.id = -1;
        this.name = name;
        this.amountOfPeriods = amountOfPeriods;
        this.sum = sum;
        this.periodType = periodType;
        this.startingDate = startingDate;
        this.prices = generator.generatePrices(variation / 100, amountOfPeriods);
        initializeArrays();
    }

    public void setSimulationDetails(int id, String name, int sum, LocalDate startingDate, String periodType, int amountOfPeriods) {
        this.id = id;
        this.name = name;
        this.amountOfPeriods = amountOfPeriods;
        this.sum = sum;
        this.periodType = periodType;
        this.startingDate = startingDate;
    }

    public void initializeArrays() {
        this.dates = generator.datesCreator(startingDate, amountOfPeriods, periodType);
        this.costAverageShares = generator.generateCostAverageShares(prices, sum, amountOfPeriods);
        this.valueAverageShares = generator.generateValueAverageShares(prices, sum, amountOfPeriods);
        this.costAverageValues = generator.generateCostAverageValues(prices, costAverageShares);
        this.valueAverageValues = generator.generateValueAverageValues(prices, valueAverageShares);
        this.valueAveragePurchases = generator.generateValueAveragePurchases(valueAverageValues, sum);
        this.costAveragePurchases = generator.generateCostAveragePurchases(costAverageValues, sum);
        this.costAverageProfit = generator.generateCostAverageProfit(costAveragePurchases, costAverageValues);
        this.valueAverageProfit = generator.generateValueAverageProfit(valueAveragePurchases, sum);
        this.valueAverageInvested = generator.generateInvested(valueAveragePurchases);
        this.costAverageInvested = generator.generateInvested(costAveragePurchases);
        this.costAverageROI = generator.generateROI(costAverageProfit, costAverageInvested);
        this.valueAverageROI = generator.generateROI(valueAverageProfit, valueAverageInvested);
    }


    public int roundToInt(double d) {
        return (int) Math.round(d);
    }

    public int getIndexOfTheDate(LocalDate date) {
        return dateIndexBinarySearch(dates, date);
    }

    public int dateIndexBinarySearch(LocalDate arr[], LocalDate x) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            if (arr[m].equals(x)) {
                return m;
            }

            if (arr[m].compareTo(x) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        return -1;
    }

    public String centsToEuroString(int money) {
        String accidental = "";
        if (money < 0) {
            accidental = "-";
            money *= -1;
        }
        int euros = money / 100;

        String cents = String.format("%02d", money % 100);

        return accidental + euros + "," + cents + " €";
    }

    public LocalDate[] getDates() {
        return dates;
    }

    public int[] getCostAverageValues() {
        return costAverageValues;
    }

    public int[] getValueAverageValues() {
        return valueAverageValues;
    }

    public double[] getValueAverageROI() {
        return valueAverageROI;
    }

    public double[] getCostAverageROI() {
        return costAverageROI;
    }

    public int getSum() {
        return sum;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int[] getPrices() {
        return prices;
    }

    public int getAmountOfPeriods() {
        return amountOfPeriods;
    }

    public int getId() {
        return id;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }

    public int[] getCostAveragePurchases() {
        return costAveragePurchases;
    }

    public int[] getCostAverageProfit() {
        return costAverageProfit;
    }

    public int[] getValueAveragePurchases() {
        return valueAveragePurchases;
    }

    public int[] getValueAverageProfit() {
        return valueAverageProfit;
    }

    public int[] getCostAverageInvested() {
        return costAverageInvested;
    }

    public int[] getValueAverageInvested() {
        return valueAverageInvested;
    }

    public double[] getCostAverageShares() {
        return costAverageShares;
    }

    public double[] getValueAverageShares() {
        return valueAverageShares;
    }
}
