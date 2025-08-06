package org.example.service;

import org.example.model.Cab;
import org.example.model.CabState;
import org.example.model.City;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CabManagerServiceImpl implements CabManagerService {
    private final Map<String, Cab> cabs = new HashMap<>();
    private final Map<String, City> cities = new HashMap<>();

    @Override
    public void registerCity(String cityId, String name) {
        cities.put(cityId, new City(cityId, name));
    }

    @Override
    public void registerCab(String cabId, CabState state, String cityId) {
        cabs.put(cabId, new Cab(cabId, state, cityId));
    }

    @Override
    public void changeCabCity(String cabId, String newCityId) {
        getCab(cabId).changeCity(newCityId);
    }

    @Override
    public void changeCabState(String cabId, CabState newState) {
        getCab(cabId).changeState(newState);
    }

    @Override
    public Cab getCab(String cabId) {
        if (!cabs.containsKey(cabId)) throw new NoSuchElementException("Cab not found");
        return cabs.get(cabId);
    }

    @Override
    public City getCity(String cityId) {
        if (!cities.containsKey(cityId)) throw new NoSuchElementException("City not found");
        return cities.get(cityId);
    }

    @Override
    public Collection<Cab> getAllCabs() {
        return cabs.values();
    }

    @Override
    public Collection<City> getAllCities() {
        return cities.values();
    }
}
