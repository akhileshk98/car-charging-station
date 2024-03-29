package com.chargingstation;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TimeSlotManagerTest {

	// Tests the ValidateDate method in the TimeSlotManager class with a valid date
	@Test
	public void testValidateDate_ValidDate_ReturnsTrue() {
		TimeSlotManager timeSlotManager = new TimeSlotManager();
		boolean result = timeSlotManager.ValidateDate("29/11/2023");
		assertTrue(result);
	}

	// Tests the ValidateDate method in the TimeSlotManager class with an invalid date
	@Test
	public void testValidateDate_InvalidDate_ReturnsFalse() {
		TimeSlotManager timeSlotManager = new TimeSlotManager();
		boolean result = timeSlotManager.ValidateDate("32/13/2023");
		assertFalse(result);
	}

	// Tests the BookSlot method in the TimeSlotManager class by simulating user input 
	@Test
	public void testBookSlot_SlotBookedSuccessfully() {
		TimeSlotManager timeSlotManager = new TimeSlotManager();
		Car car = new Car(1);
		String date = "29/12/2023";
		String filepath = "TimeslotManager\\Timeslot.txt";
		String input = "12:00 to 13:00";
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		timeSlotManager.BookSlot(car, date, filepath);

		assertTrue(car.slot_booked);
	}
}