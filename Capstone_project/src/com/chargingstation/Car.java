package com.chargingstation;


public class Car{
	boolean ChargeState;
	int id;
	boolean slot_booked=false;
	
	Car(int id)
	{
		this.ChargeState = false;
		this.id = id;
	}
	
	
}