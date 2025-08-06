package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cab {
    private final String id;
    private CabState state;
    private String cityId;
    @Getter
    private final List<StateLog> history;

    public Cab(String id, CabState state, String cityId) {
        this.id = id;
        this.state = state;
        this.cityId = state == CabState.ON_TRIP ? null : cityId;
        this.history = new ArrayList<>();
        this.history.add(new StateLog(state, Instant.now()));
    }

    public void changeState(CabState newState) {
        if (state == CabState.ON_TRIP && newState != CabState.IDLE)
            throw new IllegalStateException("Invalid state transition from ON_TRIP");
        if (state == CabState.MAINTENANCE && newState != CabState.IDLE)
            throw new IllegalStateException("Invalid state transition from MAINTENANCE");

        this.state = newState;
        if (newState == CabState.ON_TRIP) this.cityId = null;
        this.history.add(new StateLog(newState, Instant.now()));
    }

    public void changeCity(String newCityId) {
        if (this.state == CabState.ON_TRIP)
            throw new IllegalStateException("Cannot change city while ON_TRIP");
        this.cityId = newCityId;
    }

    public long getIdleDurationInSeconds(Instant from, Instant to) {
        long idleTime = 0;
        Instant lastIdleStart = null;
        for (StateLog log : history) {
            if (log.getTimestamp().isBefore(from)) continue;
            if (log.getTimestamp().isAfter(to)) break;
            if (log.getState() == CabState.IDLE) {
                lastIdleStart = log.getTimestamp();
            } else if (lastIdleStart != null) {
                idleTime += log.getTimestamp().getEpochSecond() - lastIdleStart.getEpochSecond();
                lastIdleStart = null;
            }
        }
        if (this.state == CabState.IDLE && lastIdleStart != null) {
            idleTime += to.getEpochSecond() - lastIdleStart.getEpochSecond();
        }
        return idleTime;
    }

}
