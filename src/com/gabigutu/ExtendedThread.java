package com.gabigutu;

public class ExtendedThread extends Thread {

    public ExtendedThread() { }

    @Override
    public void run() {
        System.out.println("Hello from thread " + this.getName());
        for(int i = 1; i <= Main.NO_ELEMENTS; i++) {
            System.out.println("Thread " + this.getName() + " shows number " + i);
        }
    }

}
