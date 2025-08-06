package org.example.Strategy;

import org.example.model.Cab;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LongestIdleStrategy implements BookingStrategy {

    @Override
    public Cab selectCab(List<Cab> availableCabs, String cityId) {
        Instant now = Instant.now();
        long maxIdle = -1;
        List<Cab> longestIdleCabs = new ArrayList<>();

        for (Cab cab : availableCabs) {
            long idle = cab.getIdleDurationInSeconds(now.minusSeconds(86400), now);
            if (idle > maxIdle) {
                maxIdle = idle;
                longestIdleCabs.clear();
                longestIdleCabs.add(cab);
            } else if (idle == maxIdle) {
                longestIdleCabs.add(cab);
            }
        }

        return longestIdleCabs.get(new Random().nextInt(longestIdleCabs.size()));
    }
}
