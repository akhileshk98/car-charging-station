package com.chargingstation;

import java.util.Random;

public class EnergyManagementSystem implements Runnable {

	private static final Random RANDOM = new Random();
	EnergySource energySource;
	Object lock1 = new Object();
	ChargingStation cs;

	// Enum to represent energy sources
	public enum EnergySource {
		SOLAR, WIND, GRID
	}

	// Enum to represent weather conditions
	public enum WeatherCondition {
		SUNNY, RAINY, WINDY, OVERCAST
	}

	int tempWeatherCondition;
	WeatherCondition weatherCondition;

	// Constructor for EnergyManagementSystem
	EnergyManagementSystem(ChargingStation cs) {
		this.cs = cs;
		this.weatherCondition = getWeatherCondition();
	}

	// Method to handle energy sources based on the weather condition
	public synchronized EnergySource HandleEnergySources() {

		switch (weatherCondition) {
		case SUNNY:
			energySource = EnergySource.SOLAR;
			break;
		case WINDY:
			energySource = EnergySource.WIND;
			break;
		case OVERCAST:
		case RAINY:
			energySource = EnergySource.GRID;
			break;
		}
		return energySource;
	}

	// Method to get a random weather condition
	public WeatherCondition getWeatherCondition() {
		int weatherConditionIndex = RANDOM.nextInt(4);
		return WeatherCondition.values()[weatherConditionIndex];
	}

	// Implementation of the Runnable interface's run method
	public void run() {
		EnergySource es = HandleEnergySources();
		switch (es) {
		case SOLAR:
			synchronized (lock1) {
				try {
					Logging.logTheEnergyManagementEvents(
							"Charging Station " + cs.getChargingStationID() + "getting charged with SOLAR Energy");
					Thread.sleep(3000);
					cs.setAvailableCharge(200);
					Logging.logTheEnergyManagementEvents("Charging of station " + cs.getChargingStationID()
							+ "with SOLAR Energy done.Available charge now is " + cs.getChargeavailability());
				} catch (InterruptedException e) {
					// Handle any interrupted exception that occurs during sleep
				}
			}
			break;
		case WIND:
			synchronized (lock1) {
				try {
					Logging.logTheEnergyManagementEvents(
							"Charging Station " + cs.getChargingStationID() + "getting charged with WIND Energy");
					Thread.sleep(3000);
					cs.setAvailableCharge(300);
					Logging.logTheEnergyManagementEvents("Charging of station " + cs.getChargingStationID()
							+ "with WIND Energy done.Available charge now is " + cs.getChargeavailability());
				} catch (InterruptedException e) {
					// Handle any interrupted exception that occurs during sleep
				}
			}
			break;
		case GRID:
			synchronized (lock1) {
				try {
					Logging.logTheEnergyManagementEvents("Charging Station " + cs.getChargingStationID() + "getting charged from the GRID");
					Thread.sleep(300);
					cs.setAvailableCharge(250);
					Logging.logTheEnergyManagementEvents("Charging of station " + cs.getChargingStationID()
							+ "from the GRID is done.Available charge now is " + cs.getChargeavailability());
				} catch (InterruptedException e) {
					// Handle any interrupted exception that occurs during sleep
				}
			}
			break;
		default:
			break;

		}

	}
}
