package org.example.service;

import org.example.model.Cab;
import org.example.model.CabState;
import org.example.model.City;

import java.util.Collection;

public interface CabManagerService {
    void registerCity(String cityId, String name);
    void registerCab(String cabId, CabState state, String cityId);
    void changeCabCity(String cabId, String newCityId);
    void changeCabState(String cabId, CabState newState);
    Cab getCab(String cabId);
    City getCity(String cityId);
    Collection<Cab> getAllCabs();
    Collection<City> getAllCities();
}
