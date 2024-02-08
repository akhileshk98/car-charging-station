package com.chargingstation;

public class Car {
	boolean ChargeState;
	int id;
	boolean slot_booked;
	String status;
	long car_time;

	// Constructor to initialize the Car object with an id
	Car(int id) {
		// Initialize the charging state to false (not charging)
		this.ChargeState = false;

		// Set the car id to the provided id
		this.id = id;

		// Initialize the slot booked status to false (not booked)
		this.slot_booked = false;
		
		//Initialize the status of the car to available(neither waiting nor an ongoing charge happening.
		this.status="available";
		
		//Initialize the wait time of the car
		this.car_time = 0;
	}
	//This constructor is added  for simulation purpose for more number of cars
	Car(int id, boolean status)
	{
		this.ChargeState = false;
		this.id = id;
		this.slot_booked = status;
		this.status="available";
	}
	//getter
	public int getID()
	{
		return this.id;
	}
	public String getstatus()
	{
		return this.status;
	}
	public boolean getChargeState()
	{
		return this.ChargeState;
	}
	public boolean getslotStatus()
	{
		return this.slot_booked;
	}
	public long getcarwaittime()
	{
		return this.car_time;
	}
	//setter
	public void setCarStatus(String S)
	{
		this.status = S;
	}
	public void setChargeState(boolean state)
	{
		this.ChargeState= state;
	}
	public void setslotStatus(boolean slotbooked)
	{
		 this.slot_booked = slotbooked;
	}
	public void setcartime(long time)
	{
		this.car_time = time;
	}
	
	
}