package com.chargingstation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingTest {

	// Tests the logTheEvents() function in the Logging class
	@Test
	public void testLogTheEvents() {
		int id = 1;
		String message = "Test message";
		String expectedFileName = "Charging_Station_1_Log_" + getCurrentDate() + ".txt";

		Logging.logTheEvents(id, message);

		// Verify that the log file was created
		Path filePath = Paths.get("logs", expectedFileName);
		assertTrue(Files.exists(filePath));
	}

	// Tests the logTheEvents() function in the Logging class
	@Test
	public void testLogTheEvents1() {
		int id = 1;
		String message = "Test message";
		String expectedFileName = "Charging_Station_9_Log_" + getCurrentDate() + ".txt";

		Logging.logTheEvents(id, message);

		// Verify that the log file was not created
		Path filePath = Paths.get("logs", expectedFileName);
		assertFalse(Files.exists(filePath));
	}

	// Tests the logTheEnergyManagementEvents() function in the Logging class
	@Test
	public void testLogTheEnergyManagementEvents() {
		String message = "Test message";
		String expectedFileName = "EnergyManagement_Log_" + getCurrentDate() + ".txt";

		Logging.logTheEnergyManagementEvents(message);

		// Verify that the log file was created
		Path filePath = Paths.get("logs", expectedFileName);
		assertTrue(Files.exists(filePath));
	}

	// Tests the ValidateDate() function in the Logging class with a valid date
	@Test
	public void testValidateDateTrue() {
		assertTrue(Logging.ValidateDate("15-12-2023"));
	}

	// Tests the ValidateDate() function in the Logging class with an invalid date
	@Test
	public void testValidateDateFalse() {
		assertFalse(Logging.ValidateDate("invalid_date"));
	}

	// Helper method to get the current date in the desired format
	private String getCurrentDate() {
		return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	}
}
