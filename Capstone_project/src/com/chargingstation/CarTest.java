package com.chargingstation;

import static org.junit.Assert.*;
import org.junit.Test;

public class CarTest {

	@Test
	public void testCarInitialization_ChargeState() {
		// Test case for car initialization charge state
		int carId = 1;
		Car car = new Car(carId);

		// Assert that the car charge state is initially false
		assertFalse(car.ChargeState);
	}

	@Test
	public void testCarInitialization_ID() {
		// Test case for car initialization ID
		int carId = 2;
		Car car = new Car(carId);

		// Assert that the car ID matches the provided carId
		assertEquals(carId, car.id);
	}

	@Test
	public void testCarInitialization_BookingStatus() {
		// Test case for car initialization booking status
		int carId = 3;
		Car car = new Car(carId);

		// Assert that the car booking status is initially false
		assertFalse(car.slot_booked);
	}
}
