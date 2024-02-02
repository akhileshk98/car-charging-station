package com.chargingstation;


public class Car{
	boolean ChargeState;
	int id;
	boolean slot_booked;
	String status;
	
	Car(int id)
	{
		this.ChargeState = false;
		this.id = id;
		this.slot_booked = false;
		this.status="available";
	}
	
	
}