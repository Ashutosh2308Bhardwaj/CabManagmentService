package org.example.service;

import org.example.model.Cab;
import org.example.model.CabState;
import org.example.model.City;
import org.example.model.StateLog;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BookingServiceTest {
    @Test
    public void testBookingLongestIdleCab() throws InterruptedException {
        CabManagerServiceImpl manager = new CabManagerServiceImpl();
        manager.registerCity("BLR", "Bangalore");
        manager.registerCab("CAB1", CabState.IDLE, "BLR");
        Thread.sleep(1000);
        manager.registerCab("CAB2", CabState.IDLE, "BLR");

        BookingService bookingService = new BookingServiceImpl(manager);
        Cab booked = bookingService.bookCab("BLR");

        assertNotNull(booked);
        assertEquals("CAB1", booked.getId());
        assertEquals(CabState.ON_TRIP, booked.getState());
    }

    @Test
    public void testNoCabAvailable() {
        CabManagerServiceImpl manager = new CabManagerServiceImpl();
        manager.registerCity("BLR", "Bangalore");
        manager.registerCab("CAB1", CabState.ON_TRIP, null);

        BookingService bookingService = new BookingServiceImpl(manager);
        Cab booked = bookingService.bookCab("BLR");

        assertNull(booked);
    }

    @Test
    public void testCabHistoryRecording() {
        Cab cab = new Cab("CAB1", CabState.IDLE, "BLR");
        cab.changeState(CabState.MAINTENANCE);
        cab.changeState(CabState.IDLE);

        List<StateLog> history = cab.getHistory();
        assertEquals(3, history.size());
        assertEquals(CabState.IDLE, history.get(0).getState());
        assertEquals(CabState.MAINTENANCE, history.get(1).getState());
        assertEquals(CabState.IDLE, history.get(2).getState());
    }

    @Test
    public void testChangeCityWhileOnTripThrowsException() {
        Cab cab = new Cab("CAB2", CabState.IDLE, "BLR");
        cab.changeState(CabState.ON_TRIP);
        assertThrows(IllegalStateException.class, () -> cab.changeCity("DEL"));
    }

    @Test
    public void testInvalidStateTransitionThrowsException() {
        Cab cab = new Cab("CAB3", CabState.ON_TRIP, null);
        assertThrows(IllegalStateException.class, () -> cab.changeState(CabState.MAINTENANCE));
    }

    @Test
    public void testBookingUpdatesCityDemand() {
        CabManagerServiceImpl manager = new CabManagerServiceImpl();
        manager.registerCity("DEL", "Delhi");
        manager.registerCab("CAB4", CabState.IDLE, "DEL");

        BookingService bookingService = new BookingServiceImpl(manager);
        City city = manager.getCity("DEL");
        assertEquals(0, city.getBookingTimestamps().size());

        bookingService.bookCab("DEL");
        assertEquals(1, city.getBookingTimestamps().size());
    }
}
