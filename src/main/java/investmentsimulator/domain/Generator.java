
package investmentsimulator.domain;

import java.time.LocalDate;


public class Generator {

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

    public int[] generateInvested(int[] purchases) {
        int[] generatedInvested = new int[purchases.length];

        for (int i = 1; i < generatedInvested.length; i++) {
            generatedInvested[i] = purchases[i - 1] + generatedInvested[i - 1];

        }

        return generatedInvested;
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

}
