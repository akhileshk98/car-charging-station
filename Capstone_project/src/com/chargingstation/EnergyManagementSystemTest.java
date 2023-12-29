package com.chargingstation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class EnergyManagementSystemTest {

    @Test
    public void testHandleEnergySources_SunnyWeather() {
        ChargingStation chargingStation = new ChargingStation(new SharedResource(), 1);
        EnergyManagementSystem energyManagementSystem = new EnergyManagementSystem(chargingStation);
        energyManagementSystem.weatherCondition = EnergyManagementSystem.WeatherCondition.SUNNY;
        EnergyManagementSystem.EnergySource energySource = energyManagementSystem.HandleEnergySources();
        assertEquals(EnergyManagementSystem.EnergySource.WIND, energySource);
    }

    @Test
    public void testHandleEnergySources_OverCastWeather() {
        ChargingStation chargingStation = new ChargingStation(new SharedResource(), 2);
        EnergyManagementSystem energyManagementSystem = new EnergyManagementSystem(chargingStation);
        energyManagementSystem.weatherCondition = EnergyManagementSystem.WeatherCondition.OVERCAST;
        EnergyManagementSystem.EnergySource energySource = energyManagementSystem.HandleEnergySources();
        assertEquals(EnergyManagementSystem.EnergySource.GRID, energySource);
    }

    @Test
    public void testHandleEnergySources_WindyWeather() {
        ChargingStation chargingStation = new ChargingStation(new SharedResource(), 3);
        EnergyManagementSystem energyManagementSystem = new EnergyManagementSystem(chargingStation);
        energyManagementSystem.weatherCondition = EnergyManagementSystem.WeatherCondition.WINDY;
        EnergyManagementSystem.EnergySource energySource = energyManagementSystem.HandleEnergySources();
        assertEquals(EnergyManagementSystem.EnergySource.WIND, energySource);
    }

    @Test
    public void testHandleEnergySources_RainyCastWeather() {
        ChargingStation chargingStation = new ChargingStation(new SharedResource(), 4);
        EnergyManagementSystem energyManagementSystem = new EnergyManagementSystem(chargingStation);
        energyManagementSystem.weatherCondition = EnergyManagementSystem.WeatherCondition.RAINY;
        EnergyManagementSystem.EnergySource energySource = energyManagementSystem.HandleEnergySources();
        assertEquals(EnergyManagementSystem.EnergySource.GRID, energySource);
    }

}
