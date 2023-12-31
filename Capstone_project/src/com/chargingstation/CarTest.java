package com.chargingstation;

import static org.junit.Assert.*;

import org.junit.Test;


public class CarTest {

    @Test
    public void testCarInitialization_ChargeState() {
        int carId = 1;
        Car car = new Car(carId);

        assertFalse(car.ChargeState);
    }

    @Test
    public void testCarInitialization_ID() {
        int carId = 2;
        Car car = new Car(carId);

        assertEquals(carId, car.id);
    }

    @Test
    public void testCarInitialization_BookingStatus() {
        int carId = 3;
        Car car = new Car(carId);

        assertFalse(car.slot_booked);
    }
}
