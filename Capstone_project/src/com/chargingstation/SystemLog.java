package com.chargingstation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

class SystemLog {
	
    public static void logSystemEvent(String message) {

        // try-with-resources
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("logs/system_log.txt"), true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write(timestamp + " " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


