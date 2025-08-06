package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class StateLog {
    private CabState state;
    private Instant timestamp;

    public StateLog(CabState state, Instant timestamp) {
        this.state = state;
        this.timestamp = timestamp;
    }
}
