package com.chargingstation;

import static org.junit.Assert.*;

import org.junit.Test;

public class SharedResourceTest {

	// Verify that the ID of the removed car matches the ID of the car that was added
	@Test
	public void testaddandremoveCar() {
		SharedResource sharedResource = new SharedResource();
		Car c1 = new Car(2);
		sharedResource.addCar(c1);
		assertEquals(sharedResource.removeCar().id,c1.id);

	}

}
