package com.chargingstation;

public class UserName {
	String userName;
	Car c = new Car();
	UserName(String userName, Car c)
	{
		this.userName = userName;
		this.c = c;
		
	}
	UserName()
	{
		this.userName = "null";
	
	}

}
