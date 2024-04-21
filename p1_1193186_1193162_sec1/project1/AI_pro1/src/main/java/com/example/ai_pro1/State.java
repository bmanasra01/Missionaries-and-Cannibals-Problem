package com.example.ai_pro1;

import java.util.Objects;

class State {
    int missionaries;
    int cannibals;
    boolean boat;

    public State(int missionaries, int cannibals, boolean boat) {
        this.missionaries = missionaries;
        this.cannibals = cannibals;
        this.boat = boat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(missionaries, cannibals, boat);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        State state = (State) obj;
        return missionaries == state.missionaries &&
                cannibals == state.cannibals &&
                boat == state.boat;
    }
}














