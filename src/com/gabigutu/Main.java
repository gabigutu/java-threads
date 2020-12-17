package com.gabigutu;

public class Main {

    public static long NO_ELEMENTS;
    public static int NO_THREADS;

    public static void main(String[] args) throws InterruptedException {

        NO_ELEMENTS = Long.valueOf(Integer.MAX_VALUE) * Long.valueOf(1);
        NO_THREADS = 32;
//        startExtendedThreads();
//        try {
//            starImplementedThreads();
//        } catch (InterruptedException exception) {
//            System.err.println("InterruptedException: " + exception.getMessage());
//        }

        computeSingleThreaded();
        computeMultipleThreaded();
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
