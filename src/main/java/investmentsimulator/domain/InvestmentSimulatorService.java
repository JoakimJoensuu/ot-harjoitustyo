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
        XYChart.Series valueAverageROIs = valueAverageROIsForChart();
        XYChart.Series costAverageROIs = costAverageROIsForChart();

        simulationChart.getData().add(valueAverageROIs);
        simulationChart.getData().add(costAverageROIs);

        simulationChart.getYAxis().setLabel("ROI");
    }

    public XYChart.Series valueAverageROIsForChart() {
        XYChart.Series data = new XYChart.Series();
        data.setName("VA ROI");
        for (int i = 0; i < selectedSimulation.getDates().length; i++) {
            data.getData().add(new XYChart.Data(selectedSimulation.getDates()[i].toString(), selectedSimulation.getValueAverageROI()[i]));
        }
        return data;
    }

    public XYChart.Series costAverageROIsForChart() {
        XYChart.Series data = new XYChart.Series();
        data.setName("CA ROI");
        for (int i = 0; i < selectedSimulation.getDates().length; i++) {

            data.getData().add(new XYChart.Data(selectedSimulation.getDates()[i].toString(), selectedSimulation.getCostAverageROI()[i]));
        }
        return data;
    }

    public void chartXAxisToValue(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series valueAverageValues = valueAverageValuesForChart();
        XYChart.Series costAverageValues = costAverageValuesForChart();

        simulationChart.getData().add(valueAverageValues);
        simulationChart.getData().add(costAverageValues);

        simulationChart.getYAxis().setLabel("Arvo");
    }

    public XYChart.Series valueAverageValuesForChart() {
        XYChart.Series data = new XYChart.Series();
        data.setName("VA arvonvaihtelu");
        for (int i = 0; i < selectedSimulation.getDates().length; i++) {
            data.getData().add(new XYChart.Data(selectedSimulation.getDates()[i].toString(), selectedSimulation.getValueAverageValues()[i] / 100));
        }

        return data;
    }

    public XYChart.Series costAverageValuesForChart() {
        XYChart.Series data = new XYChart.Series();
        data.setName("CA hankintahinta");
        for (int i = 0; i < selectedSimulation.getDates().length; i++) {
            data.getData().add(new XYChart.Data(selectedSimulation.getDates()[i].toString(), selectedSimulation.getCostAverageValues()[i] / 100));
        }

        return data;
    }

    public void chartXAxisToProfit(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series valueAverageProfit = valueAverageProfitsForChart();
        XYChart.Series costAverageProfit = costAverageProfitsForChart();

        simulationChart.getData().add(valueAverageProfit);
        simulationChart.getData().add(costAverageProfit);

        simulationChart.getYAxis().setLabel("Tuotto");
    }

    public XYChart.Series valueAverageProfitsForChart() {
        XYChart.Series data = new XYChart.Series();
        data.setName("VA tuotto");
        for (int i = 0; i < selectedSimulation.getDates().length; i++) {
            data.getData().add(new XYChart.Data(selectedSimulation.getDates()[i].toString(), selectedSimulation.getValueAverageProfit()[i] / 100));
        }

        return data;
    }

    public XYChart.Series costAverageProfitsForChart() {
        XYChart.Series data = new XYChart.Series();
        data.setName("CA tuotto");
        for (int i = 0; i < selectedSimulation.getDates().length; i++) {
            data.getData().add(new XYChart.Data(selectedSimulation.getDates()[i].toString(), selectedSimulation.getCostAverageProfit()[i] / 100));
        }

        return data;
    }

    public void chartXAxisToPrice(LineChart<String, Number> simulationChart) {
        simulationChart.getData().clear();
        XYChart.Series prices = simulationPricesForChart();

        simulationChart.getData().add(prices);

        simulationChart.getYAxis().setLabel("Kohteen hinta");
    }

    public XYChart.Series simulationPricesForChart() {
        XYChart.Series data = new XYChart.Series();
        data.setName("Kohteen hinnankehitys");
        for (int i = 0; i < selectedSimulation.getDates().length; i++) {
            data.getData().add(new XYChart.Data(selectedSimulation.getDates()[i].toString(), selectedSimulation.getPrices()[i] / 100));
        }

        return data;
    }

}
