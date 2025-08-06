package org.example;

import org.example.analytics.DemandAnalyzer;
import org.example.model.Cab;
import org.example.model.CabState;
import org.example.model.City;
import org.example.model.StateLog;
import org.example.service.BookingService;
import org.example.service.BookingServiceImpl;
import org.example.service.CabManagerService;
import org.example.service.CabManagerServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class CabManagementSystem {
    public static void main( String[] args ) throws InterruptedException {
        CabManagerService cabManager = new CabManagerServiceImpl();
        BookingService bookingService = new BookingServiceImpl(cabManager);

        // Register cities
        cabManager.registerCity("BLR", "Bangalore");
        cabManager.registerCity("DEL", "Delhi");

        // Register cabs
        cabManager.registerCab("CAB1", CabState.IDLE, "BLR");
        Thread.sleep(500); // Ensure CAB1 has longer idle time
        cabManager.registerCab("CAB2", CabState.IDLE, "BLR");

        // Book a cab in Bangalore
        Cab bookedCab = bookingService.bookCab("BLR");
        System.out.println("Booked cab: " + bookedCab.getId() + " in state " + bookedCab.getState());

        // Change city
        cabManager.changeCabCity("CAB2", "DEL");
        System.out.println("CAB2 moved to DEL");

        // Try booking in Delhi
        Cab cabInDelhi = bookingService.bookCab("DEL");
        System.out.println("Booked cab in DEL: " + (cabInDelhi != null ? cabInDelhi.getId() : "None"));

        DemandAnalyzer demandAnalyzer = new DemandAnalyzer();

        System.out.println("\n=== Demand Summary ===");
        Map<String, Integer> cityDemand = demandAnalyzer.getCityDemand(new ArrayList<>(cabManager.getAllCities()));
        for (Map.Entry<String, Integer> entry : cityDemand.entrySet()) {
            City city = cabManager.getCity(entry.getKey());
            int peakHour = demandAnalyzer.getPeakHour(city);
            System.out.println("City: " + city.getName() + ", Bookings: " + entry.getValue()
                    + ", Peak Hour: " + (peakHour == -1 ? "No Data" : peakHour + ":00"));
        }

        // Print history of CAB1
        Cab cab1 = cabManager.getCab("CAB1");
        List<StateLog> history = cab1.getHistory();
        System.out.println("\nHistory for " + cab1.getId() + ":");
        for (StateLog log : history) {
            System.out.println(log.getTimestamp() + " -> " + log.getState());
        }
    }
}
