package com.gabigutu;

public class ImplementedThread implements Runnable {

    private int id;

    public ImplementedThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Hello from thread no " + id);
        for(int i = 1; i <= Main.NO_ELEMENTS; i++) {
            System.out.println("Thread " + id + " shows number " + i);
        }
    }
}
