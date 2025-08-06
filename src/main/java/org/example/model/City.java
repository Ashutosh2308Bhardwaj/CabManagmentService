package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class City {
    private final String id;
    private final String name;
    private final List<Instant> bookingTimestamps;

    public City(String id, String name) {
        this.id = id;
        this.name = name;
        this.bookingTimestamps = new ArrayList<>();
    }

    public void recordBooking() {
        bookingTimestamps.add(Instant.now());
    }
}
