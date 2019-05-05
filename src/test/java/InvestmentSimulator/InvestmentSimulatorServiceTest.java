/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InvestmentSimulator;

import investmentsimulator.domain.InvestmentSimulatorService;
import investmentsimulator.domain.Simulation;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import javafx.embed.swing.JFXPanel;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author afkaaja
 */
public class InvestmentSimulatorServiceTest {

    InvestmentSimulatorService s;

    @Before
    public void setUp() {
        try {
            this.s = new InvestmentSimulatorService();
        } catch (SQLException ex) {
            Logger.getLogger(InvestmentSimulatorServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void listToArrayWorksCorrectly() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        int[] array = s.listToArray(list);

        assertTrue(array.length == list.size());
        assertTrue(array[0] == list.get(0));
        assertTrue(array[3] == list.get(3));
    }

    @Test
    public void simulationValuesAreCorrectAfterGeneratingSimulation() {
        s.generateSimulation("100", LocalDate.of(2018, Month.JANUARY, 1), "Päivä", "10", 20.0);

        Simulation simulation = s.getSelectedSimulation();

        assertTrue(simulation.getSum() == 10000);
        assertTrue(s.getDates().get(1).equals(LocalDate.of(2018, Month.JANUARY, 2)));
        assertTrue(simulation.getDates().length == 11);
        assertTrue(simulation.getPeriodType().equals("Päivä"));

    }

    @Test
    public void setSimulationPricesWorksCorrectly() {
        JFXPanel fxPanel = new JFXPanel();
        s.setSelectedSimulation(new Simulation());
        List<TextField> manualPrices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TextField manualPrice = new TextField("" + (i + 100));

            manualPrices.add(manualPrice);
        }

        s.setSimulationPrices(manualPrices);

        assertTrue(s.getSelectedSimulation().getPrices().length == 10);

        for (int i = 0; i < manualPrices.size(); i++) {
            int manualPriceInCents = Integer.parseInt(manualPrices.get(i).getText()) * 100;

            int settedPrice = s.getSelectedSimulation().getPrices()[i];

            assertTrue(manualPriceInCents == settedPrice);
        }

    }

    @Test
    public void validateFieldsWorksCorrectlyWhenFieldsAreCorrect() {
        boolean fieldsAreValid = s.validateFormFields("100", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "10", 55.5);
        assertTrue(fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPriceContainsCharacters() {
        boolean fieldsAreValid = s.validateFormFields("asdf", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "10", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPriceIsEmpty() {
        boolean fieldsAreValid = s.validateFormFields("", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "10", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPriceIsNull() {
        boolean fieldsAreValid = s.validateFormFields(null, LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "10", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPriceIsNegative() {
        boolean fieldsAreValid = s.validateFormFields("-1", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "10", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPriceIsZero() {
        boolean fieldsAreValid = s.validateFormFields("0", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "10", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenDateIsNull() {
        boolean fieldsAreValid = s.validateFormFields("100", null, "Viikko", "10", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPeriodTypeIsNotCorrect() {
        boolean fieldsAreValid = s.validateFormFields("100", LocalDate.of(2018, Month.JANUARY, 2), "Kvartaali", "10", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPeriodTypeIsNull() {
        boolean fieldsAreValid = s.validateFormFields("100", LocalDate.of(2018, Month.JANUARY, 2), null, "10", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPeriodsContainsCharacters() {
        boolean fieldsAreValid = s.validateFormFields("100", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "siudtnb", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPeriodsIsEmpty() {
        boolean fieldsAreValid = s.validateFormFields("100", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPeriodsIsNull() {
        boolean fieldsAreValid = s.validateFormFields("100", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", null, 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPeriodsIsZero() {
        boolean fieldsAreValid = s.validateFormFields("100", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "0", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenPeriodsIsNegative() {
        boolean fieldsAreValid = s.validateFormFields("100", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "-1", 55.5);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenVariationIsTooBig() {

        boolean fieldsAreValid = s.validateFormFields("100", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "10", 100.1);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateFormFieldsWorksCorrectlyWhenVariationIsNegative() {
        boolean fieldsAreValid = s.validateFormFields("100", LocalDate.of(2018, Month.JANUARY, 2), "Viikko", "10", -0.01);
        assertTrue(!fieldsAreValid);
    }

    @Test
    public void validateManualPricesWorksCorrectlyWhenOnePriceIsNegative() {
        JFXPanel fxPanel = new JFXPanel();

        List<TextField> manualPrices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TextField manualPrice = new TextField("" + (i + 100));

            manualPrices.add(manualPrice);
        }

        manualPrices.add(new TextField("" + -1));

        for (int i = 0; i < manualPrices.size(); i++) {
            boolean fieldsAreValid = s.validateManualPrices(manualPrices);

            assertTrue(!fieldsAreValid);
        }

    }

    @Test
    public void validateManualPricesWorksCorrectlyWhenOnePriceIsEmpty() {
        JFXPanel fxPanel = new JFXPanel();

        List<TextField> manualPrices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TextField manualPrice = new TextField("" + (i + 100));

            manualPrices.add(manualPrice);
        }

        manualPrices.add(new TextField(""));

        for (int i = 0; i < manualPrices.size(); i++) {
            boolean fieldsAreValid = s.validateManualPrices(manualPrices);

            assertTrue(!fieldsAreValid);
        }

    }

    @Test
    public void validateManualPricesWorksCorrectlyWhenOnePriceContainsDecimals() {
        JFXPanel fxPanel = new JFXPanel();

        List<TextField> manualPrices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TextField manualPrice = new TextField("" + (i + 100));

            manualPrices.add(manualPrice);
        }

        manualPrices.add(new TextField("" + 0.01));

        for (int i = 0; i < manualPrices.size(); i++) {
            boolean fieldsAreValid = s.validateManualPrices(manualPrices);

            assertTrue(!fieldsAreValid);
        }

    }

    @Test
    public void validateManualPricesWorksCorrectlyWhenAllPricesAreCorrect() {
        JFXPanel fxPanel = new JFXPanel();

        List<TextField> manualPrices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TextField manualPrice = new TextField("" + (i + 100));

            manualPrices.add(manualPrice);
        }



        for (int i = 0; i < manualPrices.size(); i++) {
            boolean fieldsAreValid = s.validateManualPrices(manualPrices);

            assertTrue(fieldsAreValid);
        }

    }

}
