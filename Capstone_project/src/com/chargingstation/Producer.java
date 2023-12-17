package com.chargingstation;
import java.util.ArrayList;
import java.util.Random;

class Producer implements Runnable {
    private final SharedResource sharedResource;
    private final ArrayList<Car> List;
    public Producer(SharedResource sharedResource,ArrayList<Car> List ) {
        this.sharedResource = sharedResource;
        this.List = List;

    }

    @Override
    public void run() {
		Random random = new Random();
		while(List.isEmpty()==false) {
			Car obj = List.remove(0);
			
			sharedResource.addCar(obj);
	            try {
	                Thread.sleep(random.nextInt(500));
	            } catch (InterruptedException e) {}
	        }
    }
        }
