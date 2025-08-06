package org.example.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CabTest {
    @Test
    public void testInitialStateAndCityAssignment() {
        Cab cab = new Cab("CAB10", CabState.IDLE, "BLR");
        assertEquals(CabState.IDLE, cab.getState());
        assertEquals("BLR", cab.getCityId());
    }

    @Test
    public void testChangeStateAndRecordHistory() {
        Cab cab = new Cab("CAB11", CabState.IDLE, "BLR");
        cab.changeState(CabState.MAINTENANCE);
        cab.changeState(CabState.IDLE);

        List<StateLog> history = cab.getHistory();
        assertEquals(3, history.size());
    }

    @Test
    public void testChangeCity() {
        Cab cab = new Cab("CAB12", CabState.IDLE, "BLR");
        cab.changeCity("DEL");
        assertEquals("DEL", cab.getCityId());
    }
}
