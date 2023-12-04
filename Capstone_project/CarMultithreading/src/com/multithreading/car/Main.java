package com.multithreading.car;

public class Main {
    public static void main(String[] args) {
    	ChargingStation[]cs = new ChargingStation[5];
    	Thread[]thread_consumer = new Thread[5];
    	Thread[]thread_producer = new Thread[5];
    	for(int i=0;i<cs.length;i++)
    	{
    		SharedResource sharedResource = new SharedResource();
    		cs[i] = new ChargingStation(sharedResource,i);
    		thread_consumer[i] =  new Thread(cs[i]);
    		thread_producer[i] = new Thread(new Producer(sharedResource));
    		
    	}
        Thread ChargingThread1 = new Thread(new ChargingReserve(cs[1],"Solar"));
        Thread ChargingThread2 = new Thread(new ChargingReserve(cs[3],"Wind"));
        Thread ChargingThread3 = new Thread(new ChargingReserve(cs[2],"Solar"));
        Thread ChargingThread4 = new Thread(new ChargingReserve(cs[1],"Hydro"));
        Thread ChargingThread5 = new Thread(new ChargingReserve(cs[3],"Solar"));
        Thread ChargingThread6 = new Thread(new ChargingReserve(cs[2],"Wind"));
        for(int i=0;i<cs.length;i++)
        {
        	thread_producer[i].start();
        	thread_consumer[i].start();
        }
        ChargingThread1.start();
        ChargingThread2.start();
        ChargingThread3.start();
          ChargingThread4.start();
          ChargingThread5.start();
          ChargingThread6.start();
       
    }
}
