package com.chargingstation;
import java.util.Scanner;
import java.io.*;
public class Main {

	public static void main(String[]args) throws ClassNotFoundException,CarNotChargedException,ChargeStationfullException,InsufficientEnergyError
	{
		//Array created for Charging stations
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
		ChargingStation[] cs = new ChargingStation[7];
		//Array created for cars
        Car[] c = new Car[5];
        UserName[] user = new UserName[5];
       
        //Object creation
        try
        {
        for(int i =0;i<cs.length;i++)
        {
        	cs[i] = new ChargingStation(i);
        }
        for(int i =0;i<c.length;i++)
        {
        	c[i] = new Car(i);
        	user[i]= new UserName();
        }
        user[0].userName = "Craig";
        user[0].c = c[0];
        user[1].userName = "Ronnie2";
        user[1].c = c[1];
        user[2].userName = "Mira3";
        user[2].c = c[2];
        user[3].userName = "Janet123";
        user[3].c = c[3];
        user[4].userName = "Simon";
        user[4].c = c[4];
        System.out.println("Would you like to open the timeslotManager");
        Scanner input = new Scanner(System.in);
        switch(input.nextLine())
        {
        case "yes":
              TimeslotManager ts = new TimeslotManager();
              ts.mode(user, cs);
            break;
        case "no":
        	System.out.println("Thank you, you cannot use the system without booking");
        default:
        	System.out.println("Unrecognized input");
        }
        StartCharging(c,cs);
        }catch(ChargeStationfullException e)
        {
        	throw new CarNotChargedException("Car is not charged",e);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
        	
        }
        
	}
	public static void StartCharging(Car c[], ChargingStation cs[])throws ClassNotFoundException,ChargeStationfullException,InsufficientEnergyError
	{
 //No concurrency considered. Every car in the array is matched with the charging station.
 //If the charge of the car is not true, then we move on to the next charging station.
		//cs[number].ChargeCar(null);
outerloop:for(int i =0;i<c.length;i++)
		{
			for(int j=0;j<cs.length;j++)
			{
				cs[j].ChargeCar(c[i]);
				if(c[i].getchargeState()==true)
				{
					continue outerloop;
				}
					
			}
            if(c[i].getchargeState()==false)
            {
            	throw new ChargeStationfullException("No empty slot found");
            }
		}
	}
     
}
