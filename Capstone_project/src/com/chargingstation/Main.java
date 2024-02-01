package com.chargingstation;
import java.util.ArrayList;
import java.io.*;


@ProjectMetadata(
	projectName = "car-charging-station",
	version = "5.0",
	description = "JAVA Capstone Project Team 10", 
	developer = {"Akhilesh Kakkayamkode", "Akhil Narayanaswamy", "Krithika Premkumar", "Vipin Krishna Vijayakumar"}
)

public class Main{
	public static void main(String[] args)
	{
	  ChargingStation [] cs = new ChargingStation[3];
	  Thread[]thread_consumer = new Thread[3];
	  SharedResource sharedResource = new SharedResource();
	  ArrayList<UserName> User_List = new ArrayList<>();
	  ArrayList<Car> Car_List = new ArrayList<>();
	 
	  for(int i = 0;i<2;i++)
	  {
		  new UserName().NewUserRegistration(User_List);
	  }
	  
	  
	  for(int i =0;i<cs.length;i++)
	  {
		  cs[i]= new ChargingStation(sharedResource,i);
		  thread_consumer[i] =  new Thread(cs[i]);
	  }
	  for(int i =0;i<2;i++)
	  {
	    new TimeSlotManager().timeslot(cs, User_List,Car_List);
	  }
	  Thread thread_producer = new Thread(new Producer(sharedResource,Car_List));
	  
      thread_producer.start();
      for(int i=0;i<cs.length;i++)
      {
      	
      	thread_consumer[i].start();
      }
      Logging.FetchLogFiles();
      //LogManager();
      
	}
	public static void LogManager()
	{
		File systemLogFile = new File("logs/system_log.txt");
        File chargingStationLogFile = new File("logs/charging_station_log.txt");
        File energyManagementSystemLogFile = new File("logs/energy_management_system.txt");

        long systemFileTime = systemLogFile.lastModified();
        long chargingStationFileTime = chargingStationLogFile.lastModified();
        long energyManagementFileTime = systemLogFile.lastModified();

        long currentTime = System.currentTimeMillis();
        long daysSinceModifiedSystemFile = (currentTime - systemFileTime) / (1000 * 60 * 60 * 24);
        long daysSinceModifiedChargingStationFile = (currentTime - chargingStationFileTime) / (1000 * 60 * 60 * 24);
        long daysSinceModifiedEnergyManagementFile = (currentTime - energyManagementFileTime) / (1000 * 60 * 60 * 24);

        // Creating three log files for system, charging station and energy management system
        LogManager.createLogFile("system", "system_log.txt");
        LogManager.createLogFile("charging_station", "charging_station_log.txt");
        LogManager.createLogFile("energy_management_system", "energy_management_system_log.txt");

        // Log system events
        SystemLog.logSystemEvent("Car id : 1234 added to charging station.");

        // Log charging station events
        ChargingStationLog.logChargingStationEvent("Car 1234 connected to charging station location 1.");
        ChargingStationLog.logChargingStationEvent("Car 1234 charging progress: 20%.");
        ChargingStationLog.logChargingStationEvent("Car 1234 charging progress: 80%.");

        // Log some energy management system events
        EnergyManagementSystemLog.logEnergyManagementSystemEvent("Switching to solar energy source.");
        EnergyManagementSystemLog.logEnergyManagementSystemEvent("Reducing energy consumption due to low battery levels.");


        // Moving the old log files to new file when size of log file increases more than 10MB
        if (systemLogFile.length() > 19485760) { // 10MB = 10485760 bytes
            // Move the log file to a new location
            LogManager.moveLogFile("system", "system_log.txt", "system_log_old.txt");
            // Create a new log file
            LogManager.createLogFile("system", "system_log.txt");
        }
        if (chargingStationLogFile.length() > 19485760) {
            LogManager.moveLogFile("charging_station", "charging_station_log.txt", "charging_station_log_old.txt");
            LogManager.createLogFile("charging_station", "charging_station_log.txt");
        }
        if (energyManagementSystemLogFile.length() > 19485760) {
            LogManager.moveLogFile("energy_management_system", "energy_management_system_log.txt", "energy_management_system_log_old.txt");
            LogManager.createLogFile("energy_management_system", "energy_management_system_log.txt");
        }


        // This will delete the files if it was last modified before 30 days
        if (daysSinceModifiedSystemFile > 30) {
            // Delete the log file
            LogManager.deleteLogFile("system", "system_log.txt");
            // Create a new log file
            LogManager.createLogFile("system", "system_log.txt");
        }
        if (daysSinceModifiedChargingStationFile > 30) {
            LogManager.deleteLogFile("charging_station", "charging_station_log.txt");
            LogManager.createLogFile("charging_station", "charging_station_log.txt");
        }
        if (daysSinceModifiedEnergyManagementFile > 30) {
            LogManager.deleteLogFile("energy_management_system", "energy_management_system_log.txt");
            LogManager.createLogFile("energy_management_system", "energy_management_system_log.txt");
        }

	}
}
