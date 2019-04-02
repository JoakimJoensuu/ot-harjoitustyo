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

    public Simulation() {
        
    }

    public void initializeSimulation(int sum, LocalDate startingDate, String periodType, int amountOfPeriods, double variation) {
        this.dates = datesCreator(startingDate, amountOfPeriods, periodType);
        this.prices = generatePrices(variation / 100, amountOfPeriods);
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
        double[] ROIs = new double[profit.length];

        int totalMoneyInvested = 0;

        for (int i = 1; i < ROIs.length; i++) {
            if (purchases[i - 1] >= 0) {
                totalMoneyInvested += purchases[i - 1];
            }

            double ROI = (double) profit[i] / totalMoneyInvested * 100;
            double RoundedROI = roundToNDecimals(ROI, 2);

            ROIs[i] = RoundedROI;

        }

        return ROIs;
    }

    private int[] generateInvested(int[] purchases) {
        int[] generatedInvested = new int[purchases.length];

        for (int i = 1; i < generatedInvested.length; i++) {
            generatedInvested[i] = purchases[i] + generatedInvested[i - 1];

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
        String moneyString = "" + money;

        String accidental = "";
        if (moneyString.substring(0, 1).equals("-")) {
            moneyString = moneyString.substring(1, moneyString.length());
            accidental = "-";
        }

        String euros = "0";

        if (moneyString.length() > 2) {
            euros = moneyString.substring(0, moneyString.length() - 2);
        }
        String cents = "";
        if (moneyString.length() > 1) {
            cents = moneyString.substring(moneyString.length() - 2, moneyString.length());
        } else if (moneyString.length() == 1) {
            cents = "0" + moneyString;
        } else {
            cents = "00";
        }

        return accidental + euros + "," + cents + " €";

    }

    public LocalDate[] getDates() {
        return dates;
    }

    public void setDates(LocalDate[] dates) {
        this.dates = dates;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
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

    public int[] getCostAverageProfit() {
        return costAverageProfit;
    }

    public void setCostAverageProfit(int[] costAverageProfit) {
        this.costAverageProfit = costAverageProfit;
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

    public int[] getValueAverageProfit() {
        return valueAverageProfit;
    }

    public void setValueAverageProfit(int[] valueAverageProfit) {
        this.valueAverageProfit = valueAverageProfit;
    }

    public double[] getValueAverageROI() {
        return valueAverageROI;
    }

    public void setValueAverageROI(double[] valueAverageROI) {
        this.valueAverageROI = valueAverageROI;
    }

    public double[] getCostAverageROI() {
        return costAverageROI;
    }

    public void setCostAverageROI(double[] costAverageROI) {
        this.costAverageROI = costAverageROI;
    }

    public int[] getCostAverageInvested() {
        return costAverageInvested;
    }

    public void setCostAverageInvested(int[] costAverageInvested) {
        this.costAverageInvested = costAverageInvested;
    }

    public int[] getValueAverageInvested() {
        return valueAverageInvested;
    }

    public void setValueAverageInvested(int[] valueAverageInvested) {
        this.valueAverageInvested = valueAverageInvested;
    }

}
