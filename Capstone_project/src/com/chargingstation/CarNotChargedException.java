package com.chargingstation;

public class CarNotChargedException extends Exception {
	CarNotChargedException()
	{
		super();
	}
	CarNotChargedException(String s,Throwable cause)
	{
		super(s,cause);
	}
}
