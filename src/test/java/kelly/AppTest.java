package kelly;

import kelly.simulation.domain.SimulationField;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

public class AppTest 
{

    @Test
    public void testSetIteration() {
        int cnt = 3;
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i< cnt; i++) {
            set.add(i);
        }

        for (int i=0; i<cnt; i++) {
            Integer x = SimulationField.nthElement(set, i);
            System.out.println("Test " + i + " = " + x);
            Assert.assertNotNull(x);
        }

        Assert.assertNull(SimulationField.nthElement(set,cnt + 1));
        Assert.assertNull(SimulationField.nthElement(set,cnt + 2));
    }
}
