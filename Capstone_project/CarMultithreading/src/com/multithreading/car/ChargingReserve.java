package com.multithreading.car;

public class ChargingReserve implements Runnable{
ChargingStation cs;
String source;
Object lock1 = new Object();
ChargingReserve(ChargingStation cs,String source)
{
	this.cs=cs;
	this.source = source;
}
public void run()
{
	switch (source) {
	case "Solar": 
		synchronized(lock1)
		{
			try {
				Thread.sleep(3000);
				cs.available_charge+=200;
			}
			catch(InterruptedException e) {}
			
		
		}
	break;
	case "Hydro":
		try {
			Thread.sleep(3000);
			cs.available_charge+=300;
		}
		catch(InterruptedException e) {}
		break;
	case "Wind":
		try {
			Thread.sleep(300);
			cs.available_charge+=250;
		}
		catch(InterruptedException e) {}
		break;
	default:
		break;
	
	}
	System.out.println("Charge now in charging station "+cs.id+"is"+ cs.available_charge);
}
}
