package com.chargingstation;
public class ChargingStation implements Runnable{
int id;
private final SharedResource sharedResource;
int capacity = 3;
volatile int space_available = capacity;
int var1 =0;
long wait_time = 5000;
int remove;
long available_charge;
Object lock1 = new Object();
Object lock2 = new Object();
public ChargingStation(SharedResource sharedResource, int id)
{
    this.sharedResource = sharedResource;
    this.available_charge = 500;
	this.id = id;
}

    public ChargingStation(SharedResource sharedResource) {

        this.sharedResource = sharedResource;
    }

    public void run()
{	while(true)
	{
		Car car = sharedResource.removeCar();
		
		synchronized (lock1) {

            while (space_available == 0) {
                try {
                	Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + " is waiting for a slot in the charging station");
                    //System.out.println("No slots available. Car " + car.id + " is waiting for a slot in charging station"+ this.id);
                    long start_time = System.currentTimeMillis();
                    lock1.wait(5000); // Wait if there are no available slots
                    if(System.currentTimeMillis()-start_time<5000)
                    {
                    synchronized (lock1) {
                    space_available--;
                    }
                    if(this.available_charge>=100)
                    {
                    	if(car.slot_booked==true)
                    	{
                          Thread.sleep(5000); // Simulate charging time
                          car.ChargeState = true;
                          car.slot_booked=false;
                          Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + "charged in charging station");
                          //System.out.println("Car " + car.id + " charged in charging Station"+ this.id);
                        }
                    	else
                    	{
                    		Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + "had to book a slot first");
                    		//System.out.println("Car"+car.id+"please book a slot first");
                    	}
                    }
                    else
                    {
                    	throw new InsufficientEnergyException("Charging Station needs some more energy.Please try again");
                    }
                    
                    
                    synchronized (lock1) {
                    space_available++;
                    }
                    Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + "is charged");
                    //System.out.println("Car ID " + car.id + " in charging station " + this.id + " charged in charging station"+ this.id);
                    this.available_charge-=25;
                    ChargeReserveCheck(this);
                    
                    break;
                    }
                    else
                    {                 	
                    	throw new CarNotChargedException();
                    }
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                catch(CarNotChargedException e)
                {
                	Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + "got tired of waiting and left the Charging Station");
                	//System.out.println("Car " + car.id + "got tired of waiting and left Charging Station"+ this.id);
                }
                catch(InsufficientEnergyException e)
                {
                	
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
            }
            if(this.available_charge>=100)
            {
            	if(car.slot_booked==true)
            	{
            		Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + "is charging in the charging station");
            	  //System.out.println("Car " + car.id + " is charging in charging station"+ this.id);
                  Thread.sleep(5000); // Simulate charging time
                  car.ChargeState = true;
                  car.slot_booked=false;
                  Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + "charged in charging station");
                  //System.out.println("Car " + car.id + " charged in charging Station"+ this.id);
                }
            	else
            	{
            		Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + "had to book a slot first");
            		//System.out.println("Car"+car.id+"please book a slot first");
            	}
            }
            else
            {
            	throw new InsufficientEnergyException("Charging Station needs some more energy.Please try again");
            }
            synchronized(lock1)
            {
            space_available++;
            lock1.notify();
            	this.available_charge-=25;
            	ChargeReserveCheck(this);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        catch(InsufficientEnergyException e)
        {
        	
        }
        });

        chargingThread.start();
		}
		}


    
	}	
}
private void ChargeReserveCheck(ChargingStation cs)
{
   if(cs.available_charge<=495)
    {
		Thread Thread1 = new Thread(new EnergyManagementSystem(cs));
		Thread1.start();
    }
}
}
