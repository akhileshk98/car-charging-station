package com.chargingstation;

public class Main {

	public static void main(String[]args)
	{
		//Array created for Charging stations
		ChargingStation[] cs = new ChargingStation[7];
		//Array created for cars
        Car[] c = new Car[5];
        //Object creation
        for(int i =0;i<cs.length;i++)
        {
        	cs[i] = new ChargingStation();
        }
        for(int i =0;i<c.length;i++)
        {
        	c[i] = new Car();
        }
        
 //No concurrency considered. Every car in the array is matched with the charging station.
 //If the charge of the car is not true, then we move on to the next charging station.
outerloop:for(int i =0;i<c.length;i++)
		{
			for(int j=0;j<cs.length;j++)
			{
				cs[j].ChargeCar(c[i]);
				if(c[i].getchargeState()==true)
				{
					continue outerloop;
				}
					
			}
		}
     
		
	}
}
