package com.gabigutu;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main<myAtomicNumber> {

    public static long NO_ELEMENTS;
    public static int NO_THREADS;
    public static int myNumber;
    public static AtomicInteger myAtomicNumber;
    public static Lock lock, lock2;

    public static Semaphore semaphoreWestToEast;
    public static Semaphore semaphoreNorthToSouth;
    public static boolean someoneChangedSemaphore;

    public static void main(String[] args) throws InterruptedException {

        NO_ELEMENTS = Long.valueOf(Integer.MAX_VALUE) * Long.valueOf(1);
        NO_THREADS = 200;

        //        startExtendedThreads();
//        try {
//            starImplementedThreads();
//        } catch (InterruptedException exception) {
//            System.err.println("InterruptedException: " + exception.getMessage());
//        }

//        computeSingleThreaded();
//        computeMultipleThreaded();

//        myNumber = 0;
        lock = new ReentrantLock();
        lock2 = new ReentrantLock();
//        incrementNumberParallel();

        semaphoreWestToEast = new Semaphore(10);
        semaphoreNorthToSouth = new Semaphore(0);
        someoneChangedSemaphore = false;
        crossroadSimulator();

    }

    private static void crossroadSimulator() throws InterruptedException {
        System.out.println("Crossroad simulator");
        Runnable crossroadThreads[] = new CrossroadThread[NO_THREADS];
        Thread threads[] = new Thread[NO_THREADS];
        for (int i = 0; i < NO_THREADS; i++) {
            crossroadThreads[i] = new CrossroadThread(i, CrossroadDirectionEnum.values()[(int)Math.round(Math.random())]);
            threads[i] = new Thread(crossroadThreads[i]);
            threads[i].start();
        }
        for (int i = 0; i < NO_THREADS; i++) {
            threads[i].join();
        }
    }

    private static void incrementNumberParallel() throws InterruptedException {
        System.out.println("Parallel number increment");
        for (int step = 0; step < 1000; step++) {
            Runnable incrementThreads[] = new IncrementThread[NO_THREADS];
            Thread threads[] = new Thread[NO_THREADS];
            for (int i = 0; i < NO_THREADS; i++) {
                incrementThreads[i] = new IncrementThread();
                threads[i] = new Thread(incrementThreads[i]);
                threads[i].start();
            }
            for (int i = 0; i < NO_THREADS; i++) {
                threads[i].join();
            }
        }
        System.out.println("My number = " + myNumber);
    }

    private static void startExtendedThreads() {
        Runnable extendedThread1 = new ExtendedThread();
        Runnable extendedThread2 = new ExtendedThread();

        new Thread(extendedThread1).start();
        new Thread(extendedThread2).start();
    }

    private static void starImplementedThreads() throws InterruptedException {
        Runnable implementedThread1 = new ImplementedThread(0);
        Runnable implementedThread2 = new ImplementedThread(1);

        for (int i = 0; i < 1000; i++) {
            System.out.println("Step no. " + i + ": ");
            Thread thread1 = new Thread(implementedThread1);
            thread1.start();
            System.out.println("Thread " + thread1.getName() + " started");
            Thread thread2 = new Thread(implementedThread2);
//            thread2.setPriority(Thread.MIN_PRIORITY);
            thread2.start();
            System.out.println("Thread " + thread2.getName() + " started");

            thread1.join();
            thread2.join();

            System.out.println("====================");
        }
    }

    private static void computeSingleThreaded() {
        long startTime = System.currentTimeMillis();
        System.out.println("Single threaded wants to calculate "  + NO_ELEMENTS + " elements");
        for (long i = 0; i < NO_ELEMENTS; i++) {
//            System.out.println("Single thread calculates element " + i);
            calculateElement(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[Single Threaded] Took " + (endTime - startTime) + " milliseconds");
    }

    private static void computeMultipleThreaded() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        System.out.println("Multiple threaded wants to calculate "  + NO_ELEMENTS + " elements");
        Runnable multiplyThreads[] = new MultiplyThread[NO_THREADS];
        Thread threads[] = new Thread[NO_THREADS];
        for (int i = 0; i < NO_THREADS; i++) {
            multiplyThreads[i] = new MultiplyThread(i, NO_ELEMENTS / NO_THREADS);
            threads[i] = new Thread(multiplyThreads[i]);
            threads[i].start();
        }
        long beforeJoinTime = System.currentTimeMillis();
        for (int i = 0; i < NO_THREADS; i++) {
            threads[i].join();
        }
        long endTime = System.currentTimeMillis();
//        System.out.println("[Multiple Threaded] Start time:  " + startTime + " milliseconds");
//        System.out.println("[Multiple Threaded] Before Join time:  " + beforeJoinTime + " milliseconds");
//        System.out.println("[Multiple Threaded] End time:  " + endTime + " milliseconds");
        System.out.println("[Multiple Threaded] Took " + (endTime - startTime) + " milliseconds");
    }

    public static int calculateElement(int nr) {
        return nr * 5;
    }

    public static long calculateElement(long nr) {
        return nr * 5;
    }
}
