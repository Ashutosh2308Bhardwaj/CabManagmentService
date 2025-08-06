package org.example.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CityTest {

    @Test
    public void testRecordBookingInCity() {
        City city = new City("BLR", "Bangalore");
        assertEquals(0, city.getBookingTimestamps().size());

        city.recordBooking();
        assertEquals(1, city.getBookingTimestamps().size());
    }

    @Test
    public void testCityIdAndName() {
        City city = new City("DEL", "Delhi");
        assertEquals("DEL", city.getId());
        assertEquals("Delhi", city.getName());
    }
}
