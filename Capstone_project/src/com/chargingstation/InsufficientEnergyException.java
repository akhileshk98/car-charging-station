package com.chargingstation;

public class InsufficientEnergyException extends Exception {
    public InsufficientEnergyException() {
        super();
    }

    public InsufficientEnergyException(String message) {
        super(message);
    }
}