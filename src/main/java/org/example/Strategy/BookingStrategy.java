package org.example.Strategy;

import org.example.model.Cab;

import java.util.List;

public interface BookingStrategy {
    Cab selectCab(List<Cab> availableCabs, String cityId);
}
