package investmentsimulator.domain;

import java.time.LocalDate;
import java.util.*;

public class Simulation {

    private LocalDate[] dates;
    private int[] cAValues;
    private double[] cAShares;
    private int[] vAValues;
    private double[] vASahares;
    private int[] prices;

    Simulation(int sum, LocalDate startingDate, String periodType, int amountOfPeriods, double variation) {
        datesCreator(startingDate, amountOfPeriods, periodType);
        generatePrices(variation / 100, amountOfPeriods);
        createCAValuesAndShares(amountOfPeriods, sum);
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

        cAShares[0] = ((double) sum / prices[0]);
        cAValues[0] = roundToInt(prices[0] * cAShares[0]);

        for (int i = 1; i < cAValues.length; i++) {
            cAShares[i] = cAShares[i - 1] + ((double) sum / prices[i]);
            cAValues[i] = roundToInt(prices[i] * cAShares[i]);
        }

        for (int i = 0; i < cAShares.length; i++) {
            System.out.println("Price: " + prices[i]);
            System.out.println("Arvo: " + cAValues[i]);
            System.out.println("Osat: " + cAShares[i]);
        }
    }

    private int roundToInt(double d) {
        return (int) Math.round(d);
    }
}
