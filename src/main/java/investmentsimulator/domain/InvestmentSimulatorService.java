package investmentsimulator.domain;

import java.time.LocalDate;
import java.util.*;
import investmentsimulator.dao.*;
import java.sql.SQLException;
import javafx.scene.chart.*;
import javafx.scene.control.*;

/**
 * Luokka tarjoaa UI-luokalle apumetodeja.
 *
 * @author Joakim Joensuu
 */
public class InvestmentSimulatorService {

    private Simulation selectedSimulation;
    private final SimulationDao simulationDao;

    /**
     * Luo InvestmentSimulatorService-olion ja määrittelee
     * "simulationDao"-luokkamuuttujan.
     *
     * @throws SQLException mikäli tietokannan luonti epäonnistuu
     */
    public InvestmentSimulatorService() throws SQLException {

        this.simulationDao = new SimulationDao("simulation.db");

    }

    /**
     * Määrittelee luokkamuuttujan "selectedSimulation" ja määrää sen alustamaan
     * itsensä käyttöliittymältä saatavien parametrien mukaan.
     *
     * Parametrit saadaan käyttöliittymältä.
     *
     * @param sum periodeittain sijoitettava summa
     * @param startingDate simulaation aloituspäivämäärä
     * @param periodType periodin tyyppi
     * @param amountOfPeriods periodien määrä
     * @param variance hintojen vaihtelutaso generoitaessa hinnat satunnaisesti
     * @return true, kun Simulaatio on alustanut itsensä
     */
    public boolean generateSimulation(String sum, LocalDate startingDate, Object periodType, String amountOfPeriods, Double variance) {
        selectedSimulation = new Simulation();

        selectedSimulation.initializeSimulation("", Integer.parseInt(sum) * 100, startingDate, String.valueOf(periodType), Integer.parseInt(amountOfPeriods), variance);

        return true;
    }

    /**
     *
     * Tallentaa valitun simulaation SimulationDao-luokan avulla ja saa
     * parametrinä käyttöliittymältä tallennettavan simulaation nimen.
     *
     *
     * @param text simulaation nimi
     */
    public void saveSimulation(String text) {
        selectedSimulation.setName(text);
        simulationDao.saveSimulation(selectedSimulation.getName(), selectedSimulation.getSum(), selectedSimulation.getStartingDate(), selectedSimulation.getPeriodType(), selectedSimulation.getAmountOfPeriods(), selectedSimulation.getPrices());
    }

    /**
     *
     * Määrittelee selectedSimulation-luokkamuuttujan sille parametrina annetun
     * Simulation-olioksi ja hakee kyseiseen simulaatioon liittyvät hinnat
     * SimulationDao-luokalta, jotka se asettaa simulaation kohteen hinnoiksi.
     * Lopuksi määrää valitun simulaation alustamaan taulukkonsa.
     *
     * @param simulation valituksi simulaatioksi annettava simulaatio
     */
    public void setLoadedSimulationSelected(Simulation simulation) {
        int[] prices = listToArray(simulationDao.getSimulationPrices(simulation.getId()));

        selectedSimulation = simulation;
        selectedSimulation.setPrices(prices);
        selectedSimulation.initializeArrays();

    }

    /**
     * Muuntaa sille annetun kokonaislukuja sisältävän listan taulukoksi.
     *
     * @param list kokonaislukuja sisältävä lista
     * @return kokonaislukuja sisältävä taulukko
     */
    public int[] listToArray(List<Integer> list) {
        int[] array = new int[list.size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    /**
     *
     * Hakee valitulta simulaatiolta simulaation päivämäärät taulukkona ja
     * muuntaa ne käyttöliittymäluokalle sopivaksi listaksi.
     *
     * @return valitun simulaation päivämäärät listana
     */
    public List<LocalDate> getDates() {
        List<LocalDate> dates = new ArrayList<>();

        dates.addAll(Arrays.asList(selectedSimulation.getDates()));

        return dates;
    }

    /**
     *
     * Asettaa käyttöliittymältä saadut käyttäjän syöttämät hinnat valitun
     * simulaation hinnoiksi.
     *
     * @param manualPrices käyttäjän syöttämät hinnat
     */
    public void setSimulationPrices(List<TextField> manualPrices) {
        int[] prices = new int[manualPrices.size()];
        for (int i = 0; i < prices.length; i++) {
            prices[i] = Integer.parseInt(manualPrices.get(i).getText()) * 100;
        }
        selectedSimulation.setPrices(prices);
    }

    /**
     *
     * Vaihtaa sille annetun viivakaavion X-akselin arvot valitun simulaation
     * strategioiden tuottoprosentteihin.
     *
     * @param simulationChart simulaation viivakaavio
     */
    public void chartXAxisToROI(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series valueAverageROIs = doubleValuesAndDatesToXYChart("Value Averaging ROI", selectedSimulation.getValueAverageROI(), selectedSimulation.getDates());
        XYChart.Series costAverageROIs = doubleValuesAndDatesToXYChart("Cost Averaging ROI", selectedSimulation.getCostAverageROI(), selectedSimulation.getDates());

        simulationChart.getData().add(valueAverageROIs);
        simulationChart.getData().add(costAverageROIs);

        simulationChart.getYAxis().setLabel("ROI");
    }

    /**
     *
     * Yhdistää sille taulukkoina annetut liukuluvut ja päivämäärät XY-sarjaksi.
     *
     * @param name XY-sarjan nimi
     * @param values X-akselin liukulukuarvot
     * @param dates Y-akselin päivämäärät
     * @return päivämäärät ja arvot XY-sarjana
     */
    public XYChart.Series doubleValuesAndDatesToXYChart(String name, double[] values, LocalDate[] dates) {
        XYChart.Series data = new XYChart.Series();
        data.setName(name);

        for (int i = 0; i < dates.length; i++) {
            data.getData().add(new XYChart.Data(dates[i].toString(), values[i]));
        }

        return data;
    }

    /**
     *
     * Vaihtaa sille annetun viivakaavion X-akselin arvot valitun simulaation
     * strategioiden salkun arvoihin.
     *
     * @param simulationChart simulaation viivakaavio
     */
    public void chartXAxisToValue(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series valueAverageValues = intValuesAndDatesToXYChart("Value Averaging ostot", selectedSimulation.getValueAverageValues(), selectedSimulation.getDates());
        XYChart.Series costAverageValues = intValuesAndDatesToXYChart("Cost Averaging ostot", selectedSimulation.getCostAverageValues(), selectedSimulation.getDates());

        simulationChart.getData().add(valueAverageValues);
        simulationChart.getData().add(costAverageValues);

        simulationChart.getYAxis().setLabel("Arvo");
    }

    /**
     *
     * Vaihtaa sille annetun viivakaavion X-akselin arvot valitun simulaation
     * strategioiden salkun tuottoihin.
     *
     * @param simulationChart simulaation viivakaavio
     */
    public void chartXAxisToProfit(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series valueAverageProfit = intValuesAndDatesToXYChart("Value Averaging ostot", selectedSimulation.getValueAverageProfit(), selectedSimulation.getDates());
        XYChart.Series costAverageProfit = intValuesAndDatesToXYChart("Cost Averaging ostot", selectedSimulation.getCostAverageProfit(), selectedSimulation.getDates());

        simulationChart.getData().add(valueAverageProfit);
        simulationChart.getData().add(costAverageProfit);

        simulationChart.getYAxis().setLabel("Tuotto");
    }

    /**
     *
     * Vaihtaa sille annetun viivakaavion X-akselin arvot valitun simulaation
     * kohteen hintoihin.
     *
     * @param simulationChart simulaation viivakaavio
     */
    public void chartXAxisToPrice(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series prices = intValuesAndDatesToXYChart("Kohteen hinnankehitys", selectedSimulation.getPrices(), selectedSimulation.getDates());

        simulationChart.getData().add(prices);

        simulationChart.getYAxis().setLabel("Kohteen hinta");
    }

    /**
     *
     * Vaihtaa sille annetun viivakaavion X-akselin arvot valitun simulaation
     * strategioiden salkun ostoihin.
     *
     * @param simulationChart simulaation viivakaavio
     */
    public void chartXAxisToPurchases(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series valueAveragePurchases = intValuesAndDatesToXYChart("Value Averaging ostot", selectedSimulation.getValueAveragePurchases(), selectedSimulation.getDates());
        XYChart.Series costAveragePurchases = intValuesAndDatesToXYChart("Cost Averaging ostot", selectedSimulation.getCostAveragePurchases(), selectedSimulation.getDates());

        simulationChart.getData().add(valueAveragePurchases);
        simulationChart.getData().add(costAveragePurchases);

        simulationChart.getYAxis().setLabel("Ostot");
    }

    /**
     *
     * Yhdistää sille taulukkoina annetut kokonaisluvut ja päivämäärät
     * XY-sarjaksi.
     *
     * @param name XY-sarjan nimi
     * @param values X-akselin kokonaislukuarvot
     * @param dates Y-akselin päivämäärät
     * @return päivämäärät ja arvot XY-sarjana
     */
    public XYChart.Series intValuesAndDatesToXYChart(String name, int[] values, LocalDate[] dates) {
        XYChart.Series data = new XYChart.Series();
        data.setName(name);

        for (int i = 0; i < dates.length; i++) {
            data.getData().add(new XYChart.Data(dates[i].toString(), values[i] / 100));
        }

        return data;
    }

    /**
     * Tarkistaa simulaation luomiseen käytetyn lomakkeen arvot.
     *
     * @param sum periodeittain sijoitettava summa
     * @param date simulaation aloituspäivä
     * @param periodType periodin tyyppi, Viikko, Kuukausi, Vuosi tai Päivä
     * @param periods periodien määrä
     * @param variation kurssihintojen vaihtelutaso
     * @return true, jos lomakkeen arvot ovat oikeat, false jos eivät
     */
    public boolean validateFormFields(String sum, LocalDate date, Object periodType, String periods, double variation) {
        if (sum == null || !(sum.matches("[0-9]+")) || sum.length() < 1 || Integer.parseInt(sum) < 1) {
            return false;
        } else if (date == null) {
            return false;
        } else if (!(String.valueOf(periodType).equals("Päivä")) && !(String.valueOf(periodType).equals("Viikko")) && !(String.valueOf(periodType).equals("Kuukausi")) && !(String.valueOf(periodType).equals("Vuosi"))) {
            return false;
        } else if (periods == null || !(periods.matches("[0-9]+")) || periods.length() < 1 || Integer.parseInt(periods) < 1) {
            return false;
        } else if (variation > 100 || variation < 0) {
            return false;
        }

        return true;
    }

    public boolean validateManualPrices(List<TextField> manualPrices) {
        for (TextField priceField : manualPrices) {
            String sum = priceField.getText();
            if (sum == null || !(sum.matches("[0-9]+")) || sum.length() < 1 || Integer.parseInt(sum) < 0) {
                return false;
            }
        }

        return true;
    }

    public Simulation getSelectedSimulation() {
        return selectedSimulation;
    }

    public List<Simulation> getSavedSimulations() {
        return simulationDao.getAllSimulations();
    }

    public void setSelectedSimulation(Simulation selectedSimulation) {
        this.selectedSimulation = selectedSimulation;
    }

}
