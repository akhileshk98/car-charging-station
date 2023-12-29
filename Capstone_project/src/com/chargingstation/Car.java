package com.chargingstation;


public class Car{
	boolean ChargeState;
	int id;
	boolean slot_booked;
	
	Car(int id)
	{
		this.ChargeState = false;
		this.id = id;
		this.slot_booked = false;
	}
	
	
}