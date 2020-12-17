package com.gabigutu;

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
                if (Main.semaphoreWestToEast.availablePermits() == 0) {
                    System.out.println(direction + " waiting ");
                }
                Main.semaphoreWestToEast.acquire(); // 10 initial
                System.out.println("Car " + carNumber + "( "  + direction + ") passes through intersection");
                if (Main.semaphoreWestToEast.availablePermits() == 0) {
                    System.out.println(direction + " releases 10 permits ");
                    Main.semaphoreNorthToSouth.release(10); // 0 initial
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                if (Main.semaphoreNorthToSouth.availablePermits() == 0) {
                    System.out.println(direction + " waiting ");
                }
                Main.semaphoreNorthToSouth.acquire(); // 0 initial
                System.out.println("Car " + carNumber + "( "  + direction + ") passes through intersection");
                if (Main.semaphoreNorthToSouth.availablePermits() == 0) {
                    System.out.println(direction + " releases 10 permits ");
                    Main.semaphoreWestToEast.release(10);
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}

