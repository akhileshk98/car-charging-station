package com.chargingstation;

public class InsufficientEnergyError extends Exception {
    public InsufficientEnergyError() {
        super();
    }

    public InsufficientEnergyError(String message) {
        super(message);
    }
}