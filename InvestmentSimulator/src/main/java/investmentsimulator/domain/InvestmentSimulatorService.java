/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package investmentsimulator.domain;

import java.time.LocalDate;
import java.util.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class InvestmentSimulatorService {

    private ArrayList<Simulation> simulations;
    private Simulation selectedSimulation;

    public InvestmentSimulatorService() {
        this.simulations = new ArrayList<>();

    }

    private HashMap<String, Object> parseForm(ObservableList<Node> form) {
        HashMap<String, Object> details = new HashMap<>();

        details.put("sum", form.get(1));
        details.put("startingDate", form.get(2));
        details.put("periodType", form.get(3));
        details.put("amountOfPeriods", form.get(4));
        details.put("variance", form.get(5));

        return details;
    }

    public boolean GenerateSimulation(String sum, LocalDate startingDate, Object periodType, String amountOfPeriods, Double variance) {
        selectedSimulation = new Simulation();

        selectedSimulation.initializeSimulation(Integer.parseInt(sum) * 100, startingDate, String.valueOf(periodType), Integer.parseInt(amountOfPeriods), variance);

        return true;
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

    public Simulation getSelectedSimulation() {
        return selectedSimulation;
    }

   
}
