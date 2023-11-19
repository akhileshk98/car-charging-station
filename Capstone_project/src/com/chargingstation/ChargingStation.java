package com.chargingstation;

public class ChargingStation {
	int capacity;
	int occupied_slots;
	private boolean wait_time_complete;

//public method to charge the car
	public void ChargeCar(Car c) throws ClassNotFoundException {
		try {
			if (capacity > occupied_slots) {
				initiate_charge(c);
				occupied_slots++;
			}
			else if (capacity == occupied_slots) {
				if (!wait_time_complete) {
					initiate_wait(c);
				}
			}else {
				throw new ClassNotFoundException("Car class not found");
			}
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e.getMessage());
			throw e;
		}
	}

	

//private method to initiate the charging
	private void initiate_charge(Car c) {
		int timer = 1000;
		do {
			timer--;
		} while (timer != 0);
		occupied_slots--;
		c.chargeState = true;
	}

//private method to initiate the waiting	
	private void initiate_wait(Car c) {
		int timer = 200;
		do {
			timer--;
		} while (timer != 0);
		wait_time_complete = true;

	}

//Constructor
	ChargingStation() {
		this.capacity = 5;
		this.occupied_slots = 0;
	}

//getter for the occupied slot
	int getoccupiedslots() {
		return this.occupied_slots;
	}

}
