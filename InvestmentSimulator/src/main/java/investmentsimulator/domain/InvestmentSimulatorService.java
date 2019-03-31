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
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class InvestmentSimulatorService {

    private ArrayList<Simulation> simulations;

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

    public void GenerateSimulation(String sum, LocalDate startingDate, Object periodType, String amountOfPeriods, Double variance) {      
        Simulation newSimulation = new Simulation(Integer.parseInt(sum)*100, startingDate, String.valueOf(periodType), Integer.parseInt(amountOfPeriods), variance);
        
        simulations.add(newSimulation);
        
        
    }

}
