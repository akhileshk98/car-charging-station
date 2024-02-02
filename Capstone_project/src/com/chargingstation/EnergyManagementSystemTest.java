package com.chargingstation;

import static org.junit.Assert.*;

import org.junit.Test;

public class EnergyManagementSystemTest {

	// Testing the Energy Source for SunnyWeather
	@Test
	public void testHandleEnergySources_SunnyWeather() {
		ChargingStation chargingStation = new ChargingStation(new SharedResource(), 1);
		EnergyManagementSystem energyManagementSystem = new EnergyManagementSystem(chargingStation);
		energyManagementSystem.weatherCondition = EnergyManagementSystem.WeatherCondition.SUNNY;
		EnergyManagementSystem.EnergySource energySource = energyManagementSystem.HandleEnergySources();

		// Assert that the energy source is SOLAR
		assertEquals(EnergyManagementSystem.EnergySource.SOLAR, energySource);
	}

	// Testing the Energy Source for OverCastWeather
	@Test
	public void testHandleEnergySources_OverCastWeather() {
		ChargingStation chargingStation = new ChargingStation(new SharedResource(), 2);
		EnergyManagementSystem energyManagementSystem = new EnergyManagementSystem(chargingStation);
		energyManagementSystem.weatherCondition = EnergyManagementSystem.WeatherCondition.OVERCAST;
		EnergyManagementSystem.EnergySource energySource = energyManagementSystem.HandleEnergySources();

		// Assert that the energy source is GRID
		assertEquals(EnergyManagementSystem.EnergySource.GRID, energySource);
	}

	// Testing the Energy Source for WindyWeather
	@Test
	public void testHandleEnergySources_WindyWeather() {
		ChargingStation chargingStation = new ChargingStation(new SharedResource(), 3);
		EnergyManagementSystem energyManagementSystem = new EnergyManagementSystem(chargingStation);
		energyManagementSystem.weatherCondition = EnergyManagementSystem.WeatherCondition.WINDY;
		EnergyManagementSystem.EnergySource energySource = energyManagementSystem.HandleEnergySources();

		// Assert that the energy source is WIND
		assertEquals(EnergyManagementSystem.EnergySource.WIND, energySource);
	}

	// Testing the Energy Source for RainyCastWeather
	@Test
	public void testHandleEnergySources_RainyCastWeather() {
		ChargingStation chargingStation = new ChargingStation(new SharedResource(), 4);
		EnergyManagementSystem energyManagementSystem = new EnergyManagementSystem(chargingStation);
		energyManagementSystem.weatherCondition = EnergyManagementSystem.WeatherCondition.RAINY;
		EnergyManagementSystem.EnergySource energySource = energyManagementSystem.HandleEnergySources();

		// Assert that the energy source is GRID
		assertEquals(EnergyManagementSystem.EnergySource.GRID, energySource);
	}

}
