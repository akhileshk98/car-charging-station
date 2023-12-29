package com.chargingstation;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeSlotManagerTest {

    @Test
    public void testValidateDate_ValidDate_ReturnsTrue() {
        TimeSlotManager timeSlotManager = new TimeSlotManager();
        boolean result = timeSlotManager.ValidateDate("29/11/2023");
        assertTrue(result);
    }

    @Test
    public void testValidateDate_InvalidDate_ReturnsFalse() {
        TimeSlotManager timeSlotManager = new TimeSlotManager();
        boolean result = timeSlotManager.ValidateDate("32/13/2023");
        assertFalse(result);
    }

    @Test
    public void testBookSlot_SlotBookedSuccessfully() throws IOException {
        TimeSlotManager timeSlotManager = new TimeSlotManager();
        Car car = new Car(1);
        String date = "29/12/2023";
        String filepath = "TimeslotManager\\Timeslot.txt";

        timeSlotManager.BookSlot(car, date, filepath);

        assertTrue(car.slot_booked);
    }
}