package org.example.analytics;

import org.example.model.City;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemandAnalyzer {
    public Map<String, Integer> getCityDemand(List<City> cities) {
        Map<String, Integer> result = new HashMap<>();
        for (City city : cities) {
            result.put(city.getId(), city.getBookingTimestamps().size());
        }
        return result;
    }

    // Returns booking count per hour (0-23) for a given city
    public Map<Integer, Integer> getHourlyDemand(City city) {
        Map<Integer, Integer> hourly = new HashMap<>();
        for (Instant instant : city.getBookingTimestamps()) {
            int hour = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).getHour();
            hourly.put(hour, hourly.getOrDefault(hour, 0) + 1);
        }
        return hourly;
    }

    // Returns the hour of the day with the highest demand
    public int getPeakHour(City city) {
        return getHourlyDemand(city).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1); // -1 means no data
    }
}
