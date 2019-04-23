package investmentsimulator.domain;

import java.time.LocalDate;
import java.util.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.chart.XYChart;
import investmentsimulator.dao.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class InvestmentSimulatorService {

    private Simulation selectedSimulation;
    private final SimulationDao simulationDao;

    public InvestmentSimulatorService() throws SQLException {

        this.simulationDao = new SimulationDao("simulation.db");

    }

    public boolean generateSimulation(String sum, LocalDate startingDate, Object periodType, String amountOfPeriods, Double variance) {
        selectedSimulation = new Simulation();

        selectedSimulation.initializeSimulation("", Integer.parseInt(sum) * 100, startingDate, String.valueOf(periodType), Integer.parseInt(amountOfPeriods), variance);

        return true;
    }

    public Simulation getSelectedSimulation() {
        return selectedSimulation;
    }

    public void saveSimulation(String text) {
        selectedSimulation.setName(text);
        simulationDao.saveSimulation(selectedSimulation.getName(), selectedSimulation.getSum(), selectedSimulation.getStartingDate(), selectedSimulation.getPeriodType(), selectedSimulation.getAmountOfPeriods(), selectedSimulation.getPrices());
    }

    public List<Simulation> getSavedSimulations() {
        return simulationDao.getAllSimulations();
    }

    public void setLoadedSimulationSelected(Simulation simulation) {
        int[] prices = listToArray(simulationDao.getSimulationPrices(simulation.getId()));

        selectedSimulation = simulation;
        selectedSimulation.setPrices(prices);
        selectedSimulation.initializeArrays();

    }

    public int[] listToArray(List<Integer> list) {
        int[] array = new int[list.size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public List<LocalDate> getDates() {
        List<LocalDate> dates = new ArrayList<>();

        dates.addAll(Arrays.asList(selectedSimulation.getDates()));

        return dates;
    }

    public void setSimulationPrices(List<TextField> manualPrices) {
        int[] prices = new int[manualPrices.size()];
        for (int i = 0; i < prices.length; i++) {
            prices[i] = Integer.parseInt(manualPrices.get(i).getText()) * 100;
        }
        selectedSimulation.setPrices(prices);
    }

    public void chartXAxisToROI(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series valueAverageROIs = doubleValuesAndDatesToXYChart("Value Averaging ROI", selectedSimulation.getValueAverageROI(), selectedSimulation.getDates());
        XYChart.Series costAverageROIs = doubleValuesAndDatesToXYChart("Cost Averaging ROI", selectedSimulation.getCostAverageROI(), selectedSimulation.getDates());

        simulationChart.getData().add(valueAverageROIs);
        simulationChart.getData().add(costAverageROIs);

        simulationChart.getYAxis().setLabel("ROI");
    }

    public XYChart.Series doubleValuesAndDatesToXYChart(String name, double[] values, LocalDate[] dates) {
        XYChart.Series data = new XYChart.Series();
        data.setName(name);

        for (int i = 0; i < dates.length; i++) {
            data.getData().add(new XYChart.Data(dates[i].toString(), values[i] / 100));
        }

        return data;
    }

    public void chartXAxisToValue(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series valueAverageValues = intValuesAndDatesToXYChart("Value Averaging ostot", selectedSimulation.getValueAverageValues(), selectedSimulation.getDates());
        XYChart.Series costAverageValues = intValuesAndDatesToXYChart("Cost Averaging ostot", selectedSimulation.getCostAverageValues(), selectedSimulation.getDates());

        simulationChart.getData().add(valueAverageValues);
        simulationChart.getData().add(costAverageValues);

        simulationChart.getYAxis().setLabel("Arvo");
    }

    public void chartXAxisToProfit(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series valueAverageProfit = intValuesAndDatesToXYChart("Value Averaging ostot", selectedSimulation.getValueAverageProfit(), selectedSimulation.getDates());
        XYChart.Series costAverageProfit = intValuesAndDatesToXYChart("Cost Averaging ostot", selectedSimulation.getCostAverageProfit(), selectedSimulation.getDates());

        simulationChart.getData().add(valueAverageProfit);
        simulationChart.getData().add(costAverageProfit);

        simulationChart.getYAxis().setLabel("Tuotto");
    }

    public void chartXAxisToPrice(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series prices = intValuesAndDatesToXYChart("Kohteen hinnankehitys", selectedSimulation.getPrices(), selectedSimulation.getDates());

        simulationChart.getData().add(prices);

        simulationChart.getYAxis().setLabel("Kohteen hinta");
    }

    public void chartXAxisToPurchases(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series valueAveragePurchases = intValuesAndDatesToXYChart("Value Averaging ostot", selectedSimulation.getValueAveragePurchases(), selectedSimulation.getDates());
        XYChart.Series costAveragePurchases = intValuesAndDatesToXYChart("Cost Averaging ostot", selectedSimulation.getCostAveragePurchases(), selectedSimulation.getDates());

        simulationChart.getData().add(valueAveragePurchases);
        simulationChart.getData().add(costAveragePurchases);

        simulationChart.getYAxis().setLabel("Ostot");
    }

    public XYChart.Series intValuesAndDatesToXYChart(String name, int[] values, LocalDate[] dates) {
        XYChart.Series data = new XYChart.Series();
        data.setName(name);

        for (int i = 0; i < dates.length; i++) {
            data.getData().add(new XYChart.Data(dates[i].toString(), values[i] / 100));
        }

        return data;
    }

}
