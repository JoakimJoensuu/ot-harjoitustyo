/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InvestmentSimulator;

import investmentsimulator.domain.InvestmentSimulatorService;
import investmentsimulator.domain.Simulation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

}
