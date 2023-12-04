package com.multithreading.car;
import java.util.ArrayList;

public class Carqueue {
	ChargingStation cs;
	int k;
	ArrayList<Integer> list;
	private boolean produced = false;
	Carqueue()
	{
		 this.list = new ArrayList<Integer>();
	}
    public void produce(int value) {
        while (produced) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // Toggle status.
        list.add(value);
        produced = true;
        // Notify consumer that status has changed.
        notifyAll();
    }
    
    public int consume() {
       
        while (!produced) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // Toggle status.
        produced = false;
        // Notify producer that status has changed.
        notifyAll();
        return list.remove(0);
        }
    
// public synchronized void put(int i)
// {
//	  while (!empty) {
//          try { 
//              wait();
//          } catch (InterruptedException e) {}
//      }
//      // Toggle status.
//      empty = false;
//      // Store message.
//	 synchronized(list)
//	 {
//		 list.add(i);
//	 }
//      
//      System.out.println("Value added"+ list.get(i));
//      // Notify consumer that status
//      // has changed.
//      notifyAll();
//	 
// }
// public synchronized ArrayList<Integer> take()
// {
//	    while (empty) {
//            try {
//                wait();
//            } catch (InterruptedException e) {}
//        }
//        // Toggle status.
//        empty = true;
//        // Notify producer that
//        // status has changed.
//        notifyAll();
//        return list;
//	 
// }
}
