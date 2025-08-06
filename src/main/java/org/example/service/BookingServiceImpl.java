package org.example.service;

import org.example.Strategy.BookingStrategy;
import org.example.Strategy.LongestIdleStrategy;
import org.example.model.Cab;
import org.example.model.CabState;
import org.example.model.City;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService{
    private final CabManagerService cabManager;
    private final Map<String, BookingStrategy> cityStrategies;

    public BookingServiceImpl(CabManagerService cabManager) {
        this.cabManager = cabManager;
        this.cityStrategies = new HashMap<>();
        BookingStrategy defaultStrategy = new LongestIdleStrategy();
        for (City city : cabManager.getAllCities()) {
            cityStrategies.put(city.getId(), defaultStrategy);
        }
    }

    @Override
    public Cab bookCab(String cityId) {
        List<Cab> available = cabManager.getAllCabs().stream()
                .filter(c -> c.getState() == CabState.IDLE && cityId.equals(c.getCityId()))
                .collect(Collectors.toList());

        if (available.isEmpty()) return null;

        BookingStrategy strategy = cityStrategies.getOrDefault(cityId, new LongestIdleStrategy());
        Cab selected = strategy.selectCab(available, cityId);

        selected.changeState(CabState.ON_TRIP);
        cabManager.getCity(cityId).recordBooking();
        return selected;
    }

    @Override
    public void setCityStrategy(String cityId, BookingStrategy strategy) {
        cityStrategies.put(cityId, strategy);
    }
}
