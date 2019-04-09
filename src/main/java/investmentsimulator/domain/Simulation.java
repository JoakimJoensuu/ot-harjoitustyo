package investmentsimulator.domain;

import java.time.LocalDate;
import java.util.*;

public class Simulation {

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

    }

    public void initializeSimulation(String name, int sum, LocalDate startingDate, String periodType, int amountOfPeriods, double variation) {
        this.id = -1;
        this.name = name;
        this.amountOfPeriods = amountOfPeriods;
        this.sum = sum;
        this.periodType = periodType;
        this.startingDate = startingDate;
        this.prices = generatePrices(variation / 100, amountOfPeriods);
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
        this.dates = datesCreator(startingDate, amountOfPeriods, periodType);
        this.costAverageShares = generateCostAverageShares(prices, sum, amountOfPeriods);
        this.valueAverageShares = generateValueAverageShares(prices, sum, amountOfPeriods);
        this.costAverageValues = generateCostAverageValues(prices, costAverageShares);
        this.valueAverageValues = generateValueAverageValues(prices, valueAverageShares);
        this.valueAveragePurchases = generateValueAveragePurchases(valueAverageValues, sum);
        this.costAveragePurchases = generateCostAveragePurchases(costAverageValues, sum);
        this.costAverageProfit = generateCostAverageProfit(costAveragePurchases, costAverageValues);
        this.valueAverageProfit = generateValueAverageProfit(valueAveragePurchases, sum);
        this.costAverageROI = generateROI(costAverageProfit, costAveragePurchases);
        this.valueAverageROI = generateROI(valueAverageProfit, valueAveragePurchases);
        this.valueAverageInvested = generateInvested(valueAveragePurchases);
        this.costAverageInvested = generateInvested(costAveragePurchases);
    }

    public LocalDate[] datesCreator(LocalDate startingDate, int amountOfPeriods, String periodType) {
        if (periodType.equals("Päivä")) {
            return createDays(startingDate, amountOfPeriods);
        } else if (periodType.equals("Viikko")) {
            return createWeeks(startingDate, amountOfPeriods);
        } else if (periodType.equals("Kuukausi")) {
            return createMonths(startingDate, amountOfPeriods);
        } else if (periodType.equals("Vuosi")) {
            return createYears(startingDate, amountOfPeriods);
        } else {
            return new LocalDate[1];
        }
    }

    public LocalDate[] createDays(LocalDate startingDate, int amountOfPeriods) {
        LocalDate[] generatedDates = new LocalDate[amountOfPeriods + 1];
        generatedDates[0] = startingDate;
        for (int i = 1; i < generatedDates.length; i++) {
            generatedDates[i] = generatedDates[i - 1].plusDays(1);
        }
        return generatedDates;
    }

    public LocalDate[] createWeeks(LocalDate startingDate, int amountOfPeriods) {
        LocalDate[] generatedDates = new LocalDate[amountOfPeriods + 1];
        generatedDates[0] = startingDate;
        for (int i = 1; i < generatedDates.length; i++) {
            generatedDates[i] = generatedDates[i - 1].plusWeeks(1);
        }
        return generatedDates;
    }

    public LocalDate[] createMonths(LocalDate startingDate, int amountOfPeriods) {
        LocalDate[] generatedDates = new LocalDate[amountOfPeriods + 1];
        generatedDates[0] = startingDate;
        for (int i = 1; i < generatedDates.length; i++) {
            generatedDates[i] = generatedDates[i - 1].plusMonths(1);
        }
        return generatedDates;
    }

    public LocalDate[] createYears(LocalDate startingDate, int amountOfPeriods) {
        LocalDate[] generatedDates = new LocalDate[amountOfPeriods + 1];
        generatedDates[0] = startingDate;
        for (int i = 1; i < generatedDates.length; i++) {
            generatedDates[i] = generatedDates[i - 1].plusYears(1);
        }
        return generatedDates;
    }

    public int[] generatePrices(double variation, int amountOfPeriods) {
        int[] generatedPrices = new int[amountOfPeriods + 1];
        Random r = new Random();
        generatedPrices[0] = 10000;

        for (int i = 1; i < generatedPrices.length; i++) {
            double multiplier = 1 + (r.nextDouble() * 2 - 1) * variation;
            generatedPrices[i] = (int) ((double) generatedPrices[i - 1] * multiplier);

        }
        return generatedPrices;
    }

    public double[] generateCostAverageShares(int[] prices, int sum, int amountOfPeriods) {
        double[] generatedCostAverageShares = new double[amountOfPeriods + 1];
        double sharesToBuy;
        generatedCostAverageShares[0] = 0;
        for (int i = 1; i < generatedCostAverageShares.length; i++) {
            sharesToBuy = (double) sum / prices[i - 1];
            generatedCostAverageShares[i] = sharesToBuy + generatedCostAverageShares[i - 1];
        }
        return generatedCostAverageShares;
    }

    public double[] generateValueAverageShares(int[] prices, int sum, int amountOfPeriods) {
        double[] generatedValueAverageShares = new double[amountOfPeriods + 1];
        int periodTotalValueTarget;

        generatedValueAverageShares[0] = 0;
        for (int i = 1; i < generatedValueAverageShares.length; i++) {
            periodTotalValueTarget = sum * i;
            generatedValueAverageShares[i] = (double) periodTotalValueTarget / prices[i - 1];
        }
        return generatedValueAverageShares;
    }

    public int[] generateCostAverageValues(int[] prices, double[] costAverageShares) {
        int[] generatedCostAverageValues = new int[costAverageShares.length];
        for (int i = 1; i < generatedCostAverageValues.length; i++) {
            generatedCostAverageValues[i] = roundToInt(prices[i] * costAverageShares[i]);

        }
        return generatedCostAverageValues;
    }

    public int[] generateValueAverageValues(int[] prices, double[] valueAverageShares) {
        int[] generatedValueAverageValues = new int[valueAverageShares.length];
        for (int i = 1; i < generatedValueAverageValues.length; i++) {
            generatedValueAverageValues[i] = roundToInt(prices[i] * valueAverageShares[i]);

        }
        return generatedValueAverageValues;
    }

    public int[] generateValueAveragePurchases(int[] valueAverageValues, int sum) {
        int[] generatedValueAveragePurchases = new int[valueAverageValues.length];
        int periodsTargetValue = 0;
        for (int i = 0; i < generatedValueAveragePurchases.length; i++) {
            periodsTargetValue += sum;
            generatedValueAveragePurchases[i] = periodsTargetValue - valueAverageValues[i];
        }
        return generatedValueAveragePurchases;
    }

    public int[] generateCostAveragePurchases(int[] costAverageValues, int sum) {
        int[] generatedCostAveragePurchases = new int[costAverageValues.length];

        for (int i = 0; i < generatedCostAveragePurchases.length; i++) {
            generatedCostAveragePurchases[i] = sum;
        }
        return generatedCostAveragePurchases;
    }

    public int[] generateCostAverageProfit(int[] costAveragePurchases, int[] costAverageValues) {
        int[] generatedCostAverageProfit = new int[costAveragePurchases.length];
        int totalPurchases = 0;

        for (int i = 0; i < generatedCostAverageProfit.length; i++) {
            generatedCostAverageProfit[i] = costAverageValues[i] - totalPurchases;
            totalPurchases += costAveragePurchases[i];
        }

        return generatedCostAverageProfit;
    }

    public int[] generateValueAverageProfit(int[] valueAveragePurchases, int sum) {
        int[] generatedValueAverageProfit = new int[valueAveragePurchases.length];
        int accumulatedProfit = 0;
        for (int i = 0; i < generatedValueAverageProfit.length; i++) {
            accumulatedProfit += sum - valueAveragePurchases[i];
            generatedValueAverageProfit[i] += accumulatedProfit;
        }

        return generatedValueAverageProfit;
    }

    public int roundToInt(double d) {
        return (int) Math.round(d);
    }

    public double roundToNDecimals(double d, int decimals) {
        for (int i = 0; i < decimals; i++) {
            d *= 10;
        }
        d = (double) roundToInt(d);
        for (int i = 0; i < decimals; i++) {
            d /= 10;
        }

        for (int i = 0; i < decimals; i++) {
            d *= 10;
        }
        d = (double) roundToInt(d);
        for (int i = 0; i < decimals; i++) {
            d /= 10;
        }
        return d;
    }

    public double[] generateROI(int[] profit, int[] purchases) {
        double[] rois = new double[profit.length];

        int totalMoneyInvested = 0;

        for (int i = 1; i < rois.length; i++) {
            if (purchases[i - 1] >= 0) {
                totalMoneyInvested += purchases[i - 1];
            }

            double roi = (double) profit[i] / totalMoneyInvested * 100;
            double roundedROI = roundToNDecimals(roi, 2);

            rois[i] = roundedROI;

        }

        return rois;
    }

    private int[] generateInvested(int[] purchases) {
        int[] generatedInvested = new int[purchases.length];

        for (int i = 1; i < generatedInvested.length; i++) {
            generatedInvested[i] = purchases[i - 1] + generatedInvested[i - 1];

        }

        return generatedInvested;
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
    
}
