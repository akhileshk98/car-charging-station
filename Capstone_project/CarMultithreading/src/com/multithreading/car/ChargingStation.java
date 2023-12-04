package com.multithreading.car;
class ChargingStation implements Runnable {
    private final SharedResource sharedResource;
    int capacity = 3;
    volatile int space_available = capacity;
    int var1 =0;
    long wait_time = 5000;
    int remove;
    long available_charge;
    int id;
    
    Object lock1 = new Object();
    Object lock2 = new Object();
    public ChargingStation(SharedResource sharedResource,int id) {
        this.sharedResource = sharedResource;
        this.available_charge = 500;
        this.id = id;
    }

public void run()
{	while(true)
	{
		Car car = sharedResource.removeCar();
		synchronized (lock1) {
            while (space_available == 0) {
                try {
                    System.out.println("No slots available. Car " + car.id + " is waiting for a slot in charging station"+ this.id);
                    long start_time = System.currentTimeMillis();
                    lock1.wait(5000); // Wait if there are no available slots
                    if(System.currentTimeMillis()-start_time<5000)
                    {
                    synchronized (lock1) {
                    space_available--;
                    }
                    Thread.sleep(5000); // Simulate charging time
                    
                    synchronized (lock1) {
                    space_available++;
                    }
                    car.ChargeState = true;
                    System.out.println("Car " + car.id + " charged in charging station"+ this.id);
                    synchronized(lock2)
                    {
                    	this.available_charge-=25;
                    }
                    
                    break;
                    }
                    else
                    {
                    	System.out.println("Car " + car.id + "got tired of waiting and left Charging Station"+ this.id);
                    	break;
                    }
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
		}
		synchronized(lock1)
		{
		if(space_available>=1&& car.ChargeState==false)
		{
        Thread chargingThread = new Thread(() -> {    
        try {
            synchronized(lock1)
            {
        	space_available--;
            
        	System.out.println("Car " + car.id + " is charging in charging station"+ this.id);
            }
            Thread.sleep(1000); // Simulate charging time
            synchronized(lock1)
            {
            	synchronized(lock1)
            	{
            space_available++;
            	}
            lock1.notify();
            System.out.println("Car " + car.id + " charged in charging Station"+ this.id);
            synchronized(lock2)
            {
            	this.available_charge-=25;
            }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        });

        chargingThread.start();

		}
		}

        

    
	}
    
	
}

}