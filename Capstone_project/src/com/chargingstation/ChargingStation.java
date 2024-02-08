package com.chargingstation;
public class ChargingStation implements Runnable{
int id;
private final SharedResource sharedResource;
int space_available = 2;
long available_charge;
Object lock1 = new Object();

public ChargingStation(SharedResource sharedResource, int id)
{
    this.sharedResource = sharedResource;
    this.available_charge = 500;
	this.id = id;
}

public ChargingStation(SharedResource sharedResource) {

    this.sharedResource = sharedResource;
}
//setters
public void setAvailableCharge(long charge)
{
	this.available_charge = this.available_charge + charge;
}
//getters
public long getChargeavailability()
{
	return this.available_charge;
}
public long getChargingStationID()
{
	return this.id;
}
public void run()
   {
      while(true)
      {
         Car car=sharedResource.removeCar();//Car is removed from the List
         
         synchronized(lock1)//lock is required since space_available and available_charge is shared among threads.
         { 
        	//this code block is to show if there is a spot available in the charging station
            if(space_available>0&& car.getChargeState()==false&& (car.getstatus().equals("wait")==false))
            {
                  Logging.logTheEvents(this.id, "Car of car id "+car.getID()+"arrived at charging station "+ this.id);
                  //Decrement the slot to show it is occupied.
                  space_available--;
                  Logging.logTheEvents(this.id, "Car of car id "+car.getID()+" at charging station "+ this.id+ " has occupied a slot and the space available now is "+ space_available);
                  try {
                  if(this.available_charge>=250)
                  {
                	 //Reduce the charge for charging the car.
                     this.available_charge-=25;
                     Logging.logTheEvents(this.id, "Car of car id "+ car.getID()+" at charging station "+ this.id + " has reduced the charge.Charge now is "+available_charge);
                  }
                  else
                  {
                	  //No sufficient charge is present so free up the space.Throw an exception so that the rest of the block is not executed.
                	  Logging.logTheEvents(this.id,"Car of car id "+ car.getID() + " at charging station "+ this.id +" faced issue of no charge at the station");
                	  space_available++;
                	  Logging.logTheEvents(this.id,"Due to no charge, Car of car id "+ car.getID()+" at the charging station, freed up a space. Available space now is "+ space_available);
                	  lock1.notify();//notify the waiting thread about the slot availability
                      throw new InsufficientEnergyException("Charging Station needs some more energy.Please come again later");
                  }
                  //Update the status of the car as ongoing so that it does not go to the wait block.
                  car.setCarStatus("ongoing");
                  }
                  catch(InsufficientEnergyException e)
                  {
                	  Logging.logTheEvents(this.id, "Due to insufficient charge faced, initiating charging of the station");
                	  ChargeReserveCheck(this);
                  }
            }
         }
         
         synchronized(lock1)
         {
            //this code block is to show cars that had arrived at the charging station but there was no slot available
            while (space_available == 0 && (car.getstatus().equals("ongoing")==false)) 
            {
               if(car.getslotStatus()==true) 
               {
                  try
                  {
                      Logging.logTheEvents(this.id ,"Car ID " + car.getID() + " in charging station " + this.id + " is waiting for a slot in the charging station");
                      car.setCarStatus("wait");
                      car.setcartime(System.currentTimeMillis());
                      lock1.wait(3000); // Wait if there are no available slots
                      synchronized(lock1)
                 	  {
                         try 
                         {
                            //Check if the waiting time has not exceeded. If it has not exceeded, charge the car.
                            if(System.currentTimeMillis()-car.getcarwaittime()<3000)
                            {	  
                               if(space_available>0)
                               {
                            	  Logging.logTheEvents(this.id, "Car ID "+ car.getID() +" that was waiting, got a spot within its waiting capacity");
                                  space_available--;
                                  if(this.available_charge>=250)
                                  {
                                     this.available_charge-=25;
                                     car.setCarStatus("ongoing");
                                  }
                                  else
                                  {
                                	 Logging.logTheEvents(this.id,"Car of car id "+ car.getID() + " at charging station "+ this.id +"faced issue of no charge at the station");
                            	     //Free up space since there is no energy in the station
                                     space_available++;
                                     lock1.notify();
                            	     Logging.logTheEvents(this.id,"Due to no charge, Car of car id "+ car.getID()+" at the charging station, freed up a space. Available space now is "+ space_available);
                                     throw new InsufficientEnergyException("Charging Station needs some more energy.Please come again later");
		                          }
                               }
                               else
                               {      
                           	      Logging.logTheEvents(this.id,"Car of car id "+ car.getID()+ " got tired of waiting and is now added back to the queue");
                         	      sharedResource.addCar(car);
                                  car.setCarStatus("available");
                                  break;
                               }               	
                           }
                           else
                           {
                        	   //Since the car got tired of waiting, add it back to the queue.
                        	   Logging.logTheEvents(this.id,"Car of car id "+ car.getID()+ " got tired of waiting and is now added back to the queue");
                        	   sharedResource.addCar(car);
                               car.setCarStatus("available");
                               break;
                           }
                        }
                        catch(InsufficientEnergyException e)
                        {
                        	Logging.logTheEvents(this.id, "Due to insufficient charge faced, initiating charging of the station");
                        	ChargeReserveCheck(this);
                           
                        }
                 	   }

                     } 
                     catch (InterruptedException e) // catch block added for the wait thread.
                     {
                        Thread.currentThread().interrupt();
                        return;
                     }
                  }
                  else
                  {
                	  Logging.logTheEvents(this.id ,"Car ID " + car.getID() + " in charging station " + this.id + " had to book a slot first");
                  }
             }
          }
               //Create threads for charging the car
               if(car.getstatus().equals("ongoing"))
               {
               Thread chargingThread = new Thread(() -> {    
               try 
               {
             	  //Only cars with booked slots should be charged.
                  if(car.getslotStatus()==true)
                  {
                     Logging.logTheEvents(this.id ,"Car ID " + car.getID() + " in charging station " + this.id + " is charging in the charging station");
                     Thread.sleep(5000); // Simulate charging time
                     car.setChargeState(true);
                     Logging.logTheEvents(this.id ,"Car ID " + car.getID() + " in charging station " + this.id + " charged in charging station");
                     car.setslotStatus(false);
                     car.setCarStatus("available");
                  }
                  else
                  {
                     Logging.logTheEvents(this.id ,"Car ID " + car.getID() + " in charging station " + this.id + " had to book a slot first");
                  }
                  synchronized(lock1)// lock is used to prevent other threads from manipulating the shared variables and causing race conditions
                  {
                 	//Free up the spot in case slot is not booked or charging is complete
                     space_available++;
                     Logging.logTheEvents(this.id, "Car ID "+car.getID()+" freed up a spot and space available now is "+ space_available);
                     lock1.notify();//notify the waiting thread about the slot availability
                     //To check if the station has enough charge
                     ChargeReserveCheck(this);
                   }
                  } catch (InterruptedException e)
                    {
                       Thread.currentThread().interrupt();//catch block added for thread sleep
                       return;
                    }
               });
               chargingThread.start();
               }
      }
      }
   
private void ChargeReserveCheck(ChargingStation cs)
{
   synchronized(lock1)
   {
   if(cs.available_charge<=495)
    {
		Thread Thread1 = new Thread(new EnergyManagementSystem(cs));//Create a separate thread to charge the stations
		Thread1.start();
    }
   }
}
}
