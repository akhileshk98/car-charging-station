package com.chargingstation;
import java.io.*;
import java.nio.file.*;
public class ChargingStation{
	int capacity;
	int occupied_slots;
	int id;
	private boolean wait_time_complete;
	TimeslotManager ts;
    int Energy;
//public method to charge the car
	public void ChargeCar(Car c) throws ClassNotFoundException,ChargingError {
		try {
			if (capacity > occupied_slots) {
				initiate_charge(c);
				occupied_slots++;
			}
            
			else if (capacity == occupied_slots) {
				if (!wait_time_complete) {
					initiate_wait(c);
				}
			}
			else {
				throw new ClassNotFoundException("Car class not found");
			}
			if (Energy <=0 ) {
               throw new InsufficientEnergyError("Insufficient Energy to charge the car.");
            }

		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e.getMessage());
			throw e;
		}
	}
//private method to initiate the charging
	private void initiate_charge(Car c)
	{
		int timer = 1000;
		do
		{
			timer--;
		}while(timer!=0);
		occupied_slots--;
		//Energy will get reduced by 200 on every charge
		Energy = Energy - 200; 
		c.chargeState = true;
	}
//private method to initiate the waiting	
	private void initiate_wait(Car c)
	{
		int timer = 200;
		do
		{
			timer--;
		}while(timer!=0);
		wait_time_complete = true;
		
	}
//Constructor
	ChargingStation(int id)
	{
		this.capacity = 5;
		this.occupied_slots =0;		
        //Fixed Energy of 1000 units
		this.Energy = 1000;
		this.id = id;
		this.ts = new TimeslotManager(Paths.get("Timeslot"+this.id+".txt"));
	}
//getter for the occupied slot
    int getoccupiedslots()
    { 
    	return this.occupied_slots;
    }
    
}
