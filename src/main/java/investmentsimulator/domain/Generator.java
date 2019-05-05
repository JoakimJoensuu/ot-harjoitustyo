package investmentsimulator.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Random;

/**
 *
 * Tarjoaa metodeita simulaation eri arvojen laskemiselle.
 *
 * @author Joakim Joensuu
 */
public class Generator {

    /**
     * Generoi periodien määrän verran satunnaisia hintoja annetun vaihtelutason
     * mukaan. Suurempi vaihtelutaso aiheuttaa hintojen suuremman heittelyn.
     * Ensimmäinen hinta-arvo on 100.
     *
     * @param variation vaihtelutaso
     * @param amountOfPeriods periodien määrä
     * @return taulukko hinnoista sentteinä
     */
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

    /**
     * Luo taulukon, jossa periodien määrän verran päiviä annetun periodityypin
     * välein. Mikäli annettu periodin tyyppi on vihreellinen palautetaan tyhjä
     * taulukko, jonka koko on 1.
     *
     * @param startingDate aloituspäivä
     * @param amountOfPeriods periodien määrä
     * @param periodType periodin tyyppi "Päivä" "Viikko" "Kuukausi" tai "Vuosi"
     * @return päivämäärät taulukkona
     */
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

    /**
     * Luo taulukon jossa periodien määrän verran päiviä vuorokauden välein.
     *
     * @param startingDate aloituspäivä
     * @param amountOfPeriods periodien määrä
     * @return luodut päivämäärät taulukkona
     */
    public LocalDate[] createDays(LocalDate startingDate, int amountOfPeriods) {
        LocalDate[] generatedDates = new LocalDate[amountOfPeriods + 1];
        generatedDates[0] = startingDate;
        for (int i = 1; i < generatedDates.length; i++) {
            generatedDates[i] = generatedDates[i - 1].plusDays(1);
        }
        return generatedDates;
    }

    /**
     * Luo taulukon jossa periodien määrän verran päiviä viikon välein.
     *
     * @param startingDate aloituspäivä
     * @param amountOfPeriods periodien määrä
     * @return luodut päivämäärät taulukkona
     */
    public LocalDate[] createWeeks(LocalDate startingDate, int amountOfPeriods) {
        LocalDate[] generatedDates = new LocalDate[amountOfPeriods + 1];
        generatedDates[0] = startingDate;
        for (int i = 1; i < generatedDates.length; i++) {
            generatedDates[i] = generatedDates[i - 1].plusWeeks(1);
        }
        return generatedDates;
    }

    /**
     * Luo taulukon jossa periodien määrän verran päiviä kuukauden välein.
     *
     * @param startingDate aloituspäivä
     * @param amountOfPeriods periodien määrä
     * @return luodut päivämäärät taulukkona
     */
    public LocalDate[] createMonths(LocalDate startingDate, int amountOfPeriods) {
        LocalDate[] generatedDates = new LocalDate[amountOfPeriods + 1];
        generatedDates[0] = startingDate;
        for (int i = 1; i < generatedDates.length; i++) {
            generatedDates[i] = generatedDates[i - 1].plusMonths(1);
        }
        return generatedDates;
    }

    /**
     * Luo taulukon jossa periodien määrän verran päiviä vuoden välein.
     *
     * @param startingDate aloituspäivä
     * @param amountOfPeriods periodien määrä
     * @return luodut päivämäärät taulukkona
     */
    public LocalDate[] createYears(LocalDate startingDate, int amountOfPeriods) {
        LocalDate[] generatedDates = new LocalDate[amountOfPeriods + 1];
        generatedDates[0] = startingDate;
        for (int i = 1; i < generatedDates.length; i++) {
            generatedDates[i] = generatedDates[i - 1].plusYears(1);
        }
        return generatedDates;
    }

    /**
     *
     * Laskee Cost Averaging -strategian mukaan toteutetun sijoitussalkun
     * omistusosuudet eri periodeille.
     *
     * @param prices hinnat taulukkona
     * @param sum periodeittain sijoitettava summa
     * @param amountOfPeriods periodien määrä
     * @return omistusosuudet taulukkona eri periodeille
     */
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

    /**
     *
     * Laskee Value Averaging -strategian mukaan toteutetun sijoitussalkun
     * omistusosuudet eri periodeille.
     *
     * @param prices hinnat taulukkona
     * @param sum periodeittain sijoitettava summa
     * @param amountOfPeriods periodien määrä
     * @return omistusosuudet taulukkona eri periodeille
     */
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


    /**
     * Laskee jomman kumman strategian mukaan toteutetun sijoitussalkun arvot
     * eri periodeina listaan.
     *
     *
     * @param prices kohteen hinnat eri periodeina
     * @param shares sijoitussuunnitelman mukaiset omistusosuudet
     * @return salkun arvot taulukkona eri periodeina
     */
    public int[] generateValues(int[] prices, double[] shares) {
        int[] generatedValues = new int[shares.length];
        for (int i = 1; i < generatedValues.length; i++) {
            generatedValues[i] = roundToInt(prices[i] * shares[i]);

        }
        return generatedValues;
    }

    /**
     *
     * Laskee Value Averaging -strategian mukaan toteutetun sijoitussalkun ostot
     * eri periodeina. Myynnit lasketaan negatiivisina ostoina.
     *
     *
     * @param valueAverageValues Value Averaging sijoitussuunnitelman mukaiset
     * salkun arvot
     * @param sum kohteen hinnat eri periodeina
     * @return salkun ostot taulukkona eri periodeina
     */
    public int[] generateValueAveragePurchases(int[] valueAverageValues, int sum) {
        int[] generatedValueAveragePurchases = new int[valueAverageValues.length];
        int periodsTargetValue = 0;
        for (int i = 0; i < generatedValueAveragePurchases.length; i++) {
            periodsTargetValue += sum;
            generatedValueAveragePurchases[i] = periodsTargetValue - valueAverageValues[i];
        }
        return generatedValueAveragePurchases;
    }

    /**
     *
     * Laskee Cost Averaging -strategian mukaan toteutetun sijoitussalkun ostot
     * eri periodeina. Myynnit lasketaan negatiivisina ostoina.
     *
     *
     * @param costAverageValues Cost Averaging sijoitussuunnitelman mukaiset
     * salkun arvot
     * @param sum kohteen hinnat eri periodeina
     * @return salkun ostot taulukkona eri periodeina
     */
    public int[] generateCostAveragePurchases(int[] costAverageValues, int sum) {
        int[] generatedCostAveragePurchases = new int[costAverageValues.length];

        for (int i = 0; i < generatedCostAveragePurchases.length; i++) {
            generatedCostAveragePurchases[i] = sum;
        }
        return generatedCostAveragePurchases;
    }

    /**
     * Laskee Cost Averaging -strategian mukaan toteutetun sijoitussalkun
     * kertyneet tuotot eri periodeina
     *
     * @param costAveragePurchases Cost Averaging sijoitussuunnitelman mukaiset
     * ostot
     * @param costAverageValues Cost Averaging sijoitussuunnitelman mukaiset
     * salkun arvot
     * @return salkun kertyneet tuotot eri periodeina
     */
    public int[] generateCostAverageProfit(int[] costAveragePurchases, int[] costAverageValues) {
        int[] generatedCostAverageProfit = new int[costAveragePurchases.length];
        int totalPurchases = 0;

        for (int i = 0; i < generatedCostAverageProfit.length; i++) {
            generatedCostAverageProfit[i] = costAverageValues[i] - totalPurchases;
            totalPurchases += costAveragePurchases[i];
        }

        return generatedCostAverageProfit;
    }

    /**
     * Laskee Value Averaging -strategian mukaan toteutetun sijoitussalkun
     * kertyneet tuotot eri periodeina
     *
     * @param valueAveragePurchases Value Averaging sijoitussuunnitelman
     * mukaiset ostot
     * @param sum periodeittain sijoitettava summa
     * @return salkun kertyneet tuotot eri periodeina
     */
    public int[] generateValueAverageProfit(int[] valueAveragePurchases, int sum) {
        int[] generatedValueAverageProfit = new int[valueAveragePurchases.length];
        int accumulatedProfit = 0;
        for (int i = 0; i < generatedValueAverageProfit.length; i++) {
            accumulatedProfit += sum - valueAveragePurchases[i];
            generatedValueAverageProfit[i] += accumulatedProfit;
        }

        return generatedValueAverageProfit;
    }

    /**
     *
     * Laskee salkun tuottoprosentin eri periodeina riippuen tuotoista ja
     * sijoitetusta pääomasta.
     *
     * @param profit kohteen hinnat taulukkona eri periodeina
     * @param invested salkun kertyneet ostot (-myynnit) periodeittain
     * @return tuottoprosentti eri periodeina
     */
    public double[] generateROI(int[] profit, int[] invested) {
        double[] rois = new double[profit.length];

        for (int i = 1; i < rois.length; i++) {
            double roi = (double) profit[i] / invested[i] * 100;
            double roundedROI = roundToNDecimals(roi, 2);

            rois[i] = roundedROI;

        }

        return rois;
    }

    /**
     * Laskee kertyneet ostot jokaiselle periodille. Myynnit vähennetään
     * ostoista.
     *
     * @param purchases periodeittain tehtävät ostot/myynnit
     * @return kertyneet ostot periodeittain
     */
    public int[] generateInvested(int[] purchases) {
        int[] generatedInvested = new int[purchases.length];

        for (int i = 1; i < generatedInvested.length; i++) {
            generatedInvested[i] = purchases[i - 1] + generatedInvested[i - 1];

        }

        return generatedInvested;
    }

    /**
     * Pyöristää liukuluvun lähimpään kokonaislukuun.
     *
     * @param d pyöristettävä liukuluku
     * @return pyöristetty luku kokonaislukuna
     */
    public int roundToInt(double d) {
        return (int) Math.round(d);
    }

    /**
     *
     * Pyöristää liukuluvun halutun desimaalin tarkuuteen
     *
     * @param d pyöristettävä liukuluku
     * @param decimals haluttu desimaalitarkuus
     * @return pyöristetty liukuluku
     */
    public double roundToNDecimals(double d, int decimals) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimals, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
