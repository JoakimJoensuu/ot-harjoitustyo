package investmentsimulator.domain;

import java.time.LocalDate;
import java.util.*;

public class Simulation {

    private LocalDate[] dates;
    private int[] cAValues;
    private double[] cAShares;
    private int[] cAPurchases;
    private int[] vAValues;
    private double[] vAShares;
    private int[] vAPurchases;
    private int[] prices;

    Simulation(int sum, LocalDate startingDate, String periodType, int amountOfPeriods, double variation) {
        datesCreator(startingDate, amountOfPeriods, periodType);
        generatePrices(variation / 100, amountOfPeriods);
        createCAValuesAndShares(amountOfPeriods, sum);
        createVAValuesAndShares(amountOfPeriods, sum);
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

    private void createCAValuesAndShares(int amountOfPeriods, int sum) {
        cAShares = new double[amountOfPeriods + 1];
        cAValues = new int[amountOfPeriods + 1];
        cAPurchases = new int[amountOfPeriods + 1];
        cAShares[0] = ((double) sum / prices[0]);
        cAValues[0] = roundToInt(prices[0] * cAShares[0]);
        cAPurchases[0] = sum;
        for (int i = 1; i < cAValues.length; i++) {
            cAShares[i] = cAShares[i - 1] + ((double) sum / prices[i]);
            cAValues[i] = roundToInt(prices[i] * cAShares[i]);
            cAPurchases[i] = sum;
        }
    }

    private void createVAValuesAndShares(int amountOfPeriods, int sum) {
        vAShares = new double[amountOfPeriods + 1];
        vAValues = new int[amountOfPeriods + 1];
        vAPurchases = new int[amountOfPeriods + 1];
        vAShares[0] = (double) sum / prices[0];
        vAValues[0] = roundToInt(prices[0] * vAShares[0]);
        vAPurchases[0] = sum;
        for (int i = 1; i < vAShares.length; i++) {
            int buyingSum = roundToInt(((i + 1) * sum - ((double) vAShares[i - 1] * prices[i])));
            vAShares[i] = vAShares[i - 1] + ((double) buyingSum / prices[i]);
            vAValues[i] = roundToInt(prices[i] * vAShares[i]);
            vAPurchases[i] = buyingSum;
        }
    }

    private int roundToInt(double d) {
        return (int) Math.round(d);
    }

    public LocalDate[] getDates() {
        return dates;
    }

    public void setDates(LocalDate[] dates) {
        this.dates = dates;
    }

    public int[] getcAValues() {
        return cAValues;
    }

    public void setcAValues(int[] cAValues) {
        this.cAValues = cAValues;
    }

    public double[] getcAShares() {
        return cAShares;
    }

    public void setcAShares(double[] cAShares) {
        this.cAShares = cAShares;
    }

    public int[] getcAPurchases() {
        return cAPurchases;
    }

    public void setcAPurchases(int[] cAPurchases) {
        this.cAPurchases = cAPurchases;
    }

    public int[] getvAValues() {
        return vAValues;
    }

    public void setvAValues(int[] vAValues) {
        this.vAValues = vAValues;
    }

    public double[] getvAShares() {
        return vAShares;
    }

    public void setvAShares(double[] vAShares) {
        this.vAShares = vAShares;
    }

    public int[] getvAPurchases() {
        return vAPurchases;
    }

    public void setvAPurchases(int[] vAPurchases) {
        this.vAPurchases = vAPurchases;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }
    
    
    
}


