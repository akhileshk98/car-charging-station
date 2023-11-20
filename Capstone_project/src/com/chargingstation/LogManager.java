package com.chargingstation;

import java.io.File;
import java.io.IOException;

class LogManager {
    private static final String LOG_DIRECTORY = "logs";
    public static void createLogFile(String logType, String fileName) {
        File logFile = new File(LOG_DIRECTORY, fileName);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void moveLogFile(String logType, String oldFileName, String newFileName) {
        File oldLogFile = new File(LOG_DIRECTORY, oldFileName);
        File newLogFile = new File(LOG_DIRECTORY, newFileName);
        if (oldLogFile.exists()) {
            oldLogFile.renameTo(newLogFile);
        }
    }

    public static void deleteLogFile(String logType, String fileName) {
        File logFile = new File(LOG_DIRECTORY, fileName);
        if (logFile.exists()) {
            logFile.delete();
        }
    }

}

