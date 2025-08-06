package org.example.analytics;

import org.example.model.City;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DemandAnalyzerTest {
    private DemandAnalyzer analyzer;

    @Before
    public void setup() {
        analyzer = new DemandAnalyzer();
    }

    @Test
    public void testCityDemandCount() {
        City bangalore = new City("BLR", "Bangalore");
        City delhi = new City("DEL", "Delhi");

        bangalore.recordBooking();
        bangalore.recordBooking();
        delhi.recordBooking();

        Map<String, Integer> demand = analyzer.getCityDemand(Arrays.asList(bangalore, delhi));

        assertEquals(2, (int) demand.get("BLR"));
        assertEquals(1, (int) demand.get("DEL"));
    }

    @Test
    public void testHourlyDemandAccuracy() {
        City city = new City("TEST", "TestCity");

        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        int currentHour = now.getHour();
        int prevHour = (currentHour - 1 + 24) % 24;

        // Add 2 bookings in previous hour, 1 in current hour
        city.getBookingTimestamps().add(now.minusHours(1).toInstant());
        city.getBookingTimestamps().add(now.minusHours(1).plusMinutes(10).toInstant());
        city.getBookingTimestamps().add(now.toInstant());

        Map<Integer, Integer> hourlyDemand = analyzer.getHourlyDemand(city);

        assertEquals(2, (int) hourlyDemand.getOrDefault(prevHour, 0));
        assertEquals(1, (int) hourlyDemand.getOrDefault(currentHour, 0));
    }

    @Test
    public void testPeakHourDetection() {
        City city = new City("TEST", "TestCity");

        int peakHour = 14;
        ZonedDateTime peak = ZonedDateTime.now().withHour(peakHour).withMinute(0).withSecond(0).withNano(0);

        city.getBookingTimestamps().add(peak.toInstant());
        city.getBookingTimestamps().add(peak.plusMinutes(15).toInstant());
        city.getBookingTimestamps().add(peak.plusMinutes(30).toInstant());
        city.getBookingTimestamps().add(peak.minusHours(1).toInstant());

        int result = analyzer.getPeakHour(city);

        assertEquals(peakHour, result);
    }

    @Test
    public void testEmptyCityHasNoPeakHour() {
        City city = new City("EMPTY", "EmptyCity");
        int peak = analyzer.getPeakHour(city);
        assertEquals(-1, peak);
    }
}
