package com.chargingstation;
import java.util.Random;

public class EnergyManagementSystem implements Runnable{

    private static final Random RANDOM = new Random();
    EnergySource energySource;
    Object lock1 = new Object();
    ChargingStation cs;
    public enum EnergySource{
        SOLAR,
        WIND,
        GRID
    }
    public enum WeatherCondition {
        SUNNY,
        RAINY,
        WINDY,
        OVERCAST
    }

    int tempWeatherCondition;
    WeatherCondition weatherCondition;
    EnergyManagementSystem(ChargingStation cs)
    {
    	this.cs = cs;
    	this.weatherCondition = getWeatherCondition();
    }


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

        //System.out.println("Current weather: " + weatherCondition + "Energy Source: "+ energySource);
        return energySource;
    }

    public WeatherCondition getWeatherCondition() {
        int weatherConditionIndex = RANDOM.nextInt(4);
        return WeatherCondition.values()[weatherConditionIndex];
    }
    public void run()
    {
    	EnergySource es = HandleEnergySources();
    	switch (es) {
    	case SOLAR: 
    		synchronized(lock1)
    		{
    			try {
    				Logging.logTheEnergyManagementEvents("Charging Station " + cs.id + "getting charged with SOLAR Energy");
    				Thread.sleep(3000);
    				cs.available_charge+=200;
    				Logging.logTheEnergyManagementEvents("Charging of station " + cs.id + "with SOLAR Energy done.Available charge now is "+ cs.available_charge);
    			}
    			catch(InterruptedException e) {}	
    		}
    	break;
    	case WIND:
    		synchronized(lock1)
    		{
    		try {
    			Logging.logTheEnergyManagementEvents("Charging Station " + cs.id + "getting charged with WIND Energy");
    			Thread.sleep(3000);
    			cs.available_charge+=300;
    			Logging.logTheEnergyManagementEvents("Charging of station " + cs.id + "with WIND Energy done.Available charge now is "+ cs.available_charge);
    		}
    		catch(InterruptedException e) {}
    		}
    		break;
    	case GRID:
    		synchronized(lock1)
    		{
    		try {
    			Logging.logTheEnergyManagementEvents("Charging Station " + cs.id + "getting charged from the GRID");
    			Thread.sleep(300);
    			cs.available_charge+=250;
    			Logging.logTheEnergyManagementEvents("Charging of station " + cs.id + "from the GRID is done.Available charge now is "+ cs.available_charge);
    		}
    		catch(InterruptedException e) {}
    		}
    		break;
    	default:
    		break;
    	
    	}
    	
    }
}

