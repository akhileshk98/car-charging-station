package com.chargingstation;

public class CarNotChargedException extends Exception {
	// Default constructor for the exception
	CarNotChargedException() {
		super();
	}

	// Constructor with a message and cause for the exception
	CarNotChargedException(String s, Throwable cause) {
		super(s, cause);
	}
}
