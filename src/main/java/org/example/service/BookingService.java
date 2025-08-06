package org.example.service;

import org.example.Strategy.BookingStrategy;
import org.example.model.Cab;

public interface BookingService {
    Cab bookCab(String cityId);
    void setCityStrategy(String cityId, BookingStrategy strategy);
}
