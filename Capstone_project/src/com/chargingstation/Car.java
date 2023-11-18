package com.chargingstation;

public class Car {
boolean chargeState;
int id;
//Constructor
Car()
{
	this.chargeState = false;
	this.id = 0;
	
}
//Constructor for passing the id
Car(int id)
{
	this.chargeState = false;
	this.id = id;
	
}
//setter
void setid(int id)
{
	this.id = id;
}
//getter
boolean getchargeState()
{
	return this.chargeState;
}
}
