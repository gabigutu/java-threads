package com.gabigutu;

import java.util.Map;

public class CrossroadThread implements Runnable {

    private int carNumber;
    private CrossroadDirectionEnum direction;

    public CrossroadThread(int carNumber, CrossroadDirectionEnum direction) {
        this.carNumber = carNumber;
        this.direction = direction;
    }

    @Override
    public void run() {
        if (direction == CrossroadDirectionEnum.WEST_TO_EAST) {
            try {
                Main.semaphoreWestToEast.acquire(); // 10 initial
                System.out.println("Car " + carNumber + "( " + direction + ") passes through intersection [available = " + Main.semaphoreWestToEast.availablePermits() + "]");

                Main.lock.lock();
                System.out.println("Car " + carNumber + "( " + direction + ") checks if all passed");
                if (Main.semaphoreWestToEast.availablePermits() == 0 && Main.someoneChangedSemaphore == false) {
                    System.out.println("Car " + carNumber + "( " + direction + ") releases 10 permits ");
                    Main.semaphoreNorthToSouth.release(10); // 0 initial
                    Main.someoneChangedSemaphore = true;
                }
                Main.lock.unlock();

            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                Main.semaphoreNorthToSouth.acquire(); // 0 initial
                System.out.println("Car " + carNumber + "( " + direction + ") passes through intersection [available = " + Main.semaphoreNorthToSouth.availablePermits() + "]");

                Main.lock.lock();
                if (Main.semaphoreNorthToSouth.availablePermits() == 0 && Main.someoneChangedSemaphore == true) {
                    System.out.println("Car " + carNumber + "( " + direction + ") releases 10 permits ");
                    Main.semaphoreWestToEast.release(10); // 0 initial
                    Main.someoneChangedSemaphore = false;
                }
                Main.lock.unlock();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}

