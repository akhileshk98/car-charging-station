package com.chargingstation;
import java.util.ArrayList;
import java.util.List;
// This class is created to have a common pool of cars which are waiting to be consumed by any of the charging stations
class SharedResource {
    private final List<Car> chargingList = new ArrayList<>();

    public synchronized void addCar(Car car) {
        chargingList.add(car);
        notify(); // Notify waiting threads that a car is available
    }

    public synchronized Car removeCar() {
        // Wait until a car is available
        while (chargingList.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        // Remove and return the first car from the list
        return chargingList.remove(0);
    }
}