package org.example.strategy;

import org.example.Strategy.LongestIdleStrategy;
import org.example.model.Cab;
import org.example.model.CabState;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LongestIdleStrategyTest {
    @Test
    public void testSelectCabWithLongestIdleTime() throws InterruptedException {
        Cab cab1 = new Cab("CAB1", CabState.IDLE, "BLR");
        Thread.sleep(1000);
        Cab cab2 = new Cab("CAB2", CabState.IDLE, "BLR");

        LongestIdleStrategy strategy = new LongestIdleStrategy();
        List<Cab> cabs = new ArrayList<>();
        cabs.add(cab1);
        cabs.add(cab2);

        Cab selected = strategy.selectCab(cabs, "BLR");

        assertEquals("CAB1", selected.getId());  // cab1 has been idle longer
    }

    @Test
    public void testRandomSelectionOnEqualIdle() {
        // Two cabs created at almost the same time
        Cab cab1 = new Cab("CAB1", CabState.IDLE, "BLR");
        Cab cab2 = new Cab("CAB2", CabState.IDLE, "BLR");

        LongestIdleStrategy strategy = new LongestIdleStrategy();
        List<Cab> cabs = new ArrayList<>();
        cabs.add(cab1);
        cabs.add(cab2);

        // Run the selection multiple times to ensure both get selected
        boolean cab1Picked = false;
        boolean cab2Picked = false;

        for (int i = 0; i < 20; i++) {
            Cab picked = strategy.selectCab(cabs, "BLR");
            if (picked.getId().equals("CAB1")) cab1Picked = true;
            if (picked.getId().equals("CAB2")) cab2Picked = true;
        }

        assertTrue(cab1Picked && cab2Picked);
    }
}
