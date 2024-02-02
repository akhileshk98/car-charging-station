package com.chargingstation;

public class InsufficientEnergyException extends Exception {
	// Default constructor without a message
	public InsufficientEnergyException() {
		super();
	}

	// Constructor with a custom message
	public InsufficientEnergyException(String message) {
		super(message);
	}
}