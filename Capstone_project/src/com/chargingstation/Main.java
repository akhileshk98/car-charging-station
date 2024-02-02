package com.chargingstation;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

@ProjectMetadata(
	projectName = "car-charging-station",
	version = "5.0",
	description = "JAVA Capstone Project Team 10", 
	developer = {"Akhilesh Kakkayamkode", "Akhil Narayanaswamy", "Krithika Premkumar", "Vipin Krishna Vijayakumar"}
)

public class Main{
	public static void main(String[] args)
	{
	  //Created a fixed number of consumers. Charging Station is the consumer. In this case 3.
	  ChargingStation [] cs = new ChargingStation[3];
	  //creating separate threads for charging stations to facilitate simultaneous charging of cars
	  Thread[]thread_consumer = new Thread[3];
	  //Shared resource is the pool in which cars(producer) get added and from which consumers(charging station) consume
	  SharedResource sharedResource = new SharedResource();
	  //Array list to add all registered users
	  ArrayList<UserName> User_List = new ArrayList<>();
	  //Car List has the list of users who are registered and who have booked a slot.This is used by producer to fetch from 
	  //this list and push it into the sharedresource pool at random time intervals
	  ArrayList<Car> Car_List = new ArrayList<>();
	 
	  System.out.println("How would you like to see the results of our project.\nEnter 1 for an interactive display \nEnter 2 for a predefined set of producers");
	  Scanner in = new Scanner(System.in);
	  int Value = Integer.parseInt(in.nextLine());
	  switch (Value){
	  case 1: //For having a registered user list to access the charging station
		  for(int i = 0;i<3;i++)
		  {
			  new UserName().NewUserRegistration(User_List);
		  }
		  
		  for(int i =0;i<cs.length;i++)
		  {
			  cs[i]= new ChargingStation(sharedResource,i);
			  thread_consumer[i] =  new Thread(cs[i]);
		  }
		  for(int i =0;i<3;i++)
		  {
			//prompt the user for booking the slots in the timeslot manager and getting added to the queue
		    new TimeSlotManager().timeslot(cs, User_List,Car_List);
		  }
		  
		  Thread thread_producer = new Thread(new Producer(sharedResource,Car_List,Value));
		//start producing the cars
	      thread_producer.start();
	      for(int i=0;i<cs.length;i++)
	      {
	      	//start consuming the cars
	      	thread_consumer[i].start();
	      }
	      break;
	  case 2: // for producing a large number of cars which will be difficult with a interactive user list
		  Thread thread_producer1 = new Thread(new Producer(sharedResource,Car_List,Value));
		  for(int i =0;i<cs.length;i++)
		  {
			  //create new charging stations to facilitate multi threading
			  cs[i]= new ChargingStation(sharedResource,i);
			  thread_consumer[i] =  new Thread(cs[i]);
		  }
		  //start producing
	      thread_producer1.start();
	      for(int i=0;i<cs.length;i++)
	      {
	      	//start consuming
	      	thread_consumer[i].start();
	      }
	      break;
	      default:
	    	  System.out.println("Invalid Input ");
	  }
	  // To allow access to the log files of the charging station or energy management system
      Logging.FetchLogFiles();
      
	}
}
