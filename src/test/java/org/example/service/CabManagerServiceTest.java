package org.example.service;

import org.example.model.Cab;
import org.example.model.CabState;
import org.example.model.City;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CabManagerServiceTest {
    @Test
    public void testRegisterAndFetchCity() {
        CabManagerServiceImpl manager = new CabManagerServiceImpl();
        manager.registerCity("HYD", "Hyderabad");

        City city = manager.getCity("HYD");
        assertEquals("Hyderabad", city.getName());
    }

    @Test
    public void testRegisterAndFetchCab() {
        CabManagerServiceImpl manager = new CabManagerServiceImpl();
        manager.registerCity("MUM", "Mumbai");
        manager.registerCab("CAB99", CabState.IDLE, "MUM");

        Cab cab = manager.getCab("CAB99");
        assertEquals("MUM", cab.getCityId());
    }

    @Test
    public void testChangeCabState() {
        CabManagerServiceImpl manager = new CabManagerServiceImpl();
        manager.registerCity("MUM", "Mumbai");
        manager.registerCab("CAB88", CabState.IDLE, "MUM");
        manager.changeCabState("CAB88", CabState.MAINTENANCE);

        assertEquals(CabState.MAINTENANCE, manager.getCab("CAB88").getState());
    }

    @Test
    public void testChangeCabCity() {
        CabManagerServiceImpl manager = new CabManagerServiceImpl();
        manager.registerCity("BLR", "Bangalore");
        manager.registerCity("DEL", "Delhi");
        manager.registerCab("CAB77", CabState.IDLE, "BLR");

        manager.changeCabCity("CAB77", "DEL");
        assertEquals("DEL", manager.getCab("CAB77").getCityId());
    }
}
