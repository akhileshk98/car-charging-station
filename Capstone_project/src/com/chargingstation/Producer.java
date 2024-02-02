package com.chargingstation;
import java.util.ArrayList;
import java.util.Random;

class Producer implements Runnable {
    private final SharedResource sharedResource;
    int Value = 0;
    private final ArrayList<Car> List;
    public Producer(SharedResource sharedResource,ArrayList<Car> List, int Value) {
        this.sharedResource = sharedResource;
        this.Value = Value;
        this.List = List;

    }

    @Override
    public void run() {
		Random random = new Random();
		//this code is for producing cars from the pre-registered user list
		if(this.Value==1)
		{
			while(List.isEmpty()==false) {
				Car obj = List.remove(0);
				
				sharedResource.addCar(obj);
		            try {
		                Thread.sleep(random.nextInt(500));
		            } catch (InterruptedException e) {}
		        }
		}
		//this code is for producing cars statically to simulate more number of cars
		else if (Value ==2)
		{
			Car obj[]=new Car[20];
			for(int i=0;i<obj.length;i++)
			{
			   obj[i] = new Car(i,true);// Simulating the slot booked as true to demonstrate multi threading charging for more number of cars.
	            try {
	                Thread.sleep(random.nextInt(500));
	            } catch (InterruptedException e) {}
			   sharedResource.addCar(obj[i]);
			}
		}
	}
  }
