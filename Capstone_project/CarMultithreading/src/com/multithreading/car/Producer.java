package com.multithreading.car;
import java.util.Random;

class Producer implements Runnable {
    private final SharedResource sharedResource;
    public Producer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;

    }

    @Override
    public void run() {
		Random random = new Random();
		for (int i = 0;
	             i < 5;
	             i++) {
			Car c = new Car(i);
			sharedResource.addCar(c);
	            try {
	                Thread.sleep(random.nextInt(500));
	            } catch (InterruptedException e) {}
	        }
        }
}