package com.chargingstation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

class EnergyManagementSystemLog {

    public static void logEnergyManagementSystemEvent(String message) {

        // try-with-resources
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("logs/energy_management_system_log.txt"), true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write(timestamp + " " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


