package investmentsimulator.domain;

import java.time.LocalDate;

/**
 * Pitää sisällään tiedot simulaation eri arvoista ja tarjoaa metodeja
 * simulaation arvojen muttamiselle.
 *
 * @author Joakim Joensuu
 */
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

    /**
     * Määrittelee "generator"-luokkamuuttujan.
     */
    public Simulation() {
        this.generator = new Generator();
    }

    /**
     * Alustaa simulaation tiedot määrittelemällä luokkamuuttujat itse ja
     * kutsumalla initializeArrays()-metodia, joka laskee määrittelee loput
     * luokkamuuttujat.
     *
     * @param name simulaation nimi
     * @param sum periodeittain sijoitettava summa
     * @param startingDate simulaation aloituspäivämäärä
     * @param periodType periodin tyyppi
     * @param amountOfPeriods periodien määrä
     * @param variation satunnaisesti generoitujen hintojen vaihtelutaso
     *
     * @see investmentsimulator.domain.Simulation#initializeArrays()
     */
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

    /**
     * Alustaa simulaation tiedot määrittelemällä luokkamuuttujat ilman kohteen
     * hintojen satunnaisgeneroimista ja strategiakohtaisien arvojen laskemista.
     *
     * Käytetään hyväksi ladattaessa tietokantaan tallennettua luokkaa.
     *
     * @param id simulaation tietokannasta saatava id
     * @param name simulaation nimi
     * @param sum periodeittain sijoitettava summa
     * @param startingDate simulaation aloituspäivämäärä
     * @param periodType periodin tyyppi
     * @param amountOfPeriods periodien määrä
     */
    public void setSimulationDetails(int id, String name, int sum, LocalDate startingDate, String periodType, int amountOfPeriods) {
        this.id = id;
        this.name = name;
        this.amountOfPeriods = amountOfPeriods;
        this.sum = sum;
        this.periodType = periodType;
        this.startingDate = startingDate;
    }

    /**
     * Määrittelee päivämäärät ja strategiakohtaiset luokkamuuttujat.
     */
    public void initializeArrays() {
        this.dates = generator.datesCreator(startingDate, amountOfPeriods, periodType);
        this.costAverageShares = generator.generateCostAverageShares(prices, sum, amountOfPeriods);
        this.valueAverageShares = generator.generateValueAverageShares(prices, sum, amountOfPeriods);
        this.costAverageValues = generator.generateValues(prices, costAverageShares);
        this.valueAverageValues = generator.generateValues(prices, valueAverageShares);
        this.valueAveragePurchases = generator.generateValueAveragePurchases(valueAverageValues, sum);
        this.costAveragePurchases = generator.generateCostAveragePurchases(costAverageValues, sum);
        this.costAverageProfit = generator.generateCostAverageProfit(costAveragePurchases, costAverageValues);
        this.valueAverageProfit = generator.generateValueAverageProfit(valueAveragePurchases, sum);
        this.valueAverageInvested = generator.generateInvested(valueAveragePurchases);
        this.costAverageInvested = generator.generateInvested(costAveragePurchases);
        this.costAverageROI = generator.generateROI(costAverageProfit, costAverageInvested);
        this.valueAverageROI = generator.generateROI(valueAverageProfit, valueAverageInvested);
    }

    /**
     * Etsii indeksin jollekkin päivämäärälle, jotka löytyvät simulaatiosta.
     *
     * @param date päivämäärä, jonka indeksiä etsitään
     * @return simulaation päivämäärän indeksi, -1 jos päivämäärää ei löydy
     * simulaatiosta
     */
    public int getIndexOfTheDate(LocalDate date) {
        return dateIndexBinarySearch(dates, date);
    }

    /**
     * Etsii annetusta tualukosta annetun päivämäärän indeksin.
     *
     * @param dates päivämäärät, josta indeksiä etsitään
     * @param dateToBeSearched päivämäärä, jonka indeksiä etsitään
     * @return etsittävän päivämäärän indeksi, -1 jos indeksiä ei löydy
     */
    public int dateIndexBinarySearch(LocalDate dates[], LocalDate dateToBeSearched) {
        int l = 0, r = dates.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            if (dates[m].equals(dateToBeSearched)) {
                return m;
            }

            if (dates[m].compareTo(dateToBeSearched) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        return -1;
    }

    /**
     *
     * Muuntaa sentit merkkijonoksi, jossa tarvittaessa etumerkki (-), eurot ja
     * sentit pilkulla eroteltuna ja lopussa euromerkki.
     *
     * Esimerkki: Syöte: 1 lopputulos: - 0,01 € Syöte: -103 lopputulos: -1,03 €
     *
     * @param money rahamäärä sentteinä
     * @return merkkijonoon muutettu rahamäärä
     */
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

    public void setDates(LocalDate[] dates) {
        this.dates = dates;
    }

}
