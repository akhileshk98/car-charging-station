package com.chargingstation;

public class Car {
	boolean ChargeState;
	int id;
	boolean slot_booked;

	// Constructor to initialize the Car object with an id
	Car(int id) {
		// Initialize the charging state to false (not charging)
		this.ChargeState = false;

		// Set the car id to the provided id
		this.id = id;

		// Initialize the slot booked status to false (not booked)
		this.slot_booked = false;
	}
}