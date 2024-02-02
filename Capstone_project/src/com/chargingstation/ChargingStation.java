package com.chargingstation;
public class ChargingStation implements Runnable{
int id;
private final SharedResource sharedResource;
int space_available = 2;
long wait_time = 5000;
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

public void run()
   {
      while(true)
      {
         Car car=sharedResource.removeCar();//Car is removed from the List
         
         synchronized(lock1)
         { 
        	//this code block is to show if there is a spot available in the charging station
            if(space_available>0&& car.ChargeState==false&& (car.status.equals("wait")==false))
            {
                  Logging.logTheEvents(this.id, "Car of car id "+car.id+"arrived at charging station "+ this.id);
                  //Decrement the slot to show it is occupied.
                  space_available--;
                  Logging.logTheEvents(this.id, "Car of car id "+car.id+" at charging station "+ this.id+ " has occupied a slot and the space available now is "+ space_available);
                  try {
                  if(this.available_charge>=250)
                  {
                	 //Reduce the charge for charging the car.
                     this.available_charge-=250;
                     Logging.logTheEvents(this.id, "Car of car id "+ car.id+" at charging station "+ this.id + " has reduced the charge.Charge now is "+available_charge);
                  }
                  else
                  {
                	  //No sufficient charge is present so free up the space.Throw an exception so that the rest of the block is not executed.
                	  Logging.logTheEvents(this.id,"Car of car id "+ car.id + " at charging station "+ this.id +" faced issue of no charge at the station");
                	  space_available++;
                	  Logging.logTheEvents(this.id,"Due to no charge, Car of car id "+ car.id+" at the charging station, freed up a space. Available space now is "+ space_available);
                	  lock1.notify();//notify the waiting thread about the slot availability
                      throw new InsufficientEnergyException("Charging Station needs some more energy.Please come again later");
                  }
                  //Update the status of the car as ongoing so that it does not go to the wait block.
                  car.status = "ongoing";
                  Thread chargingThread = new Thread(() -> {    
                  try 
                  {
                	  //Only cars with booked slots should be charged.
                     if(car.slot_booked==true)
                     {
                        Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + " is charging in the charging station");
                        Thread.sleep(5000); // Simulate charging time
                        car.ChargeState = true;
                        Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + " charged in charging station");
                        car.slot_booked=false;
                     }
                     else
                     {
                        Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + " had to book a slot first");
                     }
                     synchronized(lock1)
                     {
                    	//Free up the spot in case slot is not booked or charging is complete
                        space_available++;
                        Logging.logTheEvents(this.id, "Car ID "+car.id+" freed up a spot and space available now is "+ space_available);
                        lock1.notify();//notify the waiting thread about the slot availability
                        //To check if the station has enough charge
                        ChargeReserveCheck(this);
                      }
                     } catch (InterruptedException e)
                       {
                          Thread.currentThread().interrupt();
                          return;
                       }
                  });
                  chargingThread.start();
                  }
                  catch(InsufficientEnergyException e)
                  {
                	  Logging.logTheEvents(this.id, "Due to insufficient charge faced, initiating charging of the station");
                	  ChargeReserveCheck(this);
                  }
               }
               //this code block is to show cars that had arrived at the charging station but there was no slot available
               while (space_available == 0 && (car.status.equals("ongoing")==false)) 
               {
                  if(car.slot_booked==true) 
                  {
                     try
                     {
                        Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + " is waiting for a slot in the charging station");
                        car.status = "wait";
                        long start_time = System.currentTimeMillis();
                        lock1.wait(5000); // Wait if there are no available slots
                        try 
                        {
                        //Check if the waiting time has not exceeded. If it has not exceeded, charge the car.
                           if(System.currentTimeMillis()-start_time<500)
                           {
                        	   
                              if(space_available>0)
                              {
                            	  Logging.logTheEvents(this.id, "Car ID "+ car.id +" that was waiting, got a spot within its waiting capacity");
                                 space_available--;
                                 if(this.available_charge>=250)
                                 {
                                    this.available_charge-=250;
                                 }
                                 else
                                 {
                                	 Logging.logTheEvents(this.id,"Car of car id "+ car.id + " at charging station "+ this.id +"faced issue of no charge at the station");
                            	  //Free up space since there is no energy in the station
                                  space_available++;
                            	  Logging.logTheEvents(this.id,"Due to no charge, Car of car id "+ car.id+" at the charging station, freed up a space. Available space now is "+ space_available);
                                  throw new InsufficientEnergyException("Charging Station needs some more energy.Please come again later");
		                         }
		                    	
                                 Thread.sleep(5000); // Simulate charging time
                                 car.ChargeState = true;
                                 car.slot_booked=false;
                                 Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + " that was waiting now got charged");
                                 space_available++;
                                 Logging.logTheEvents(this.id, "Car ID" +car.id+" that was waiting now freed up space. Available space now is "+ space_available);
                                 ChargeReserveCheck(this);
                                 }
                              else
                              {                 	
                                 
                              }
                                                  	
                           }
                           else
                           {
                        	   //Since the car got tired of waiting, add it back to the queue.
                        	   Logging.logTheEvents(this.id,"Car of car id "+ car.id+ " got tired of waiting and is now added back to the queue");
                              sharedResource.addCar(car);
                              car.status = "available";
                              
                           }
                        }
                        catch(InsufficientEnergyException e)
                        {
                        	Logging.logTheEvents(this.id, "Due to insufficient charge faced, initiating charging of the station");
                        	ChargeReserveCheck(this);
                           
                        }
                        catch (InterruptedException e) 
                        {
                           Thread.currentThread().interrupt();
                           return;
                        }
                     } 
                     catch (InterruptedException e) 
                     {
                        Thread.currentThread().interrupt();
                        return;
                     }
                  }
                  else
                  {
                	  Logging.logTheEvents(this.id ,"Car ID " + car.id + " in charging station " + this.id + " had to book a slot first");
                  }
             }
          }
      }
   }
private void ChargeReserveCheck(ChargingStation cs)
{
   synchronized(lock1)
   {
   if(cs.available_charge<=495)
    {
		Thread Thread1 = new Thread(new EnergyManagementSystem(cs));
		Thread1.start();
    }
   }
}
}
