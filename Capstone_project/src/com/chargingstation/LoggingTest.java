package com.chargingstation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LoggingTest {

    

    @Test
    public void testLogTheEvents() {
    	int id = 1;
        String message = "Test message";
        String expectedFileName = "Charging_Station_1_Log_" + getCurrentDate() + ".txt";

        Logging.logTheEvents(id, message);

        Path filePath = Paths.get("logs", expectedFileName);
        assertTrue(Files.exists(filePath));
    }

    @Test
    public void testLogTheEvents1() {
    	int id = 1;
        String message = "Test message";
        String expectedFileName = "Charging_Station_9_Log_" + getCurrentDate() + ".txt";

        Logging.logTheEvents(id, message);

        Path filePath = Paths.get("logs", expectedFileName);
        assertFalse(Files.exists(filePath));
    }
    
    @Test
    public void testLogTheEnergyManagementEvents() {
    	String message = "Test message";
        String expectedFileName = "EnergyManagement_Log_" + getCurrentDate() + ".txt";

        Logging.logTheEnergyManagementEvents(message);

        Path filePath = Paths.get("logs", expectedFileName);
        assertTrue(Files.exists(filePath));
    }

    @Test
    public void testValidateDateTrue() {
        assertTrue(Logging.ValidateDate("15-12-2023"));
    }
    
    @Test
    public void testValidateDateFalse() {
        assertFalse(Logging.ValidateDate("invalid_date"));
    }
    
    private String getCurrentDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }
}
