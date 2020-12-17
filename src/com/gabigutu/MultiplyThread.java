package com.gabigutu;

import static com.gabigutu.Main.NO_ELEMENTS;

public class MultiplyThread implements Runnable {

    private int id;
    private long start;
    private long end;

    // 100 elements; 4 threads => 25 per thread:
    // [0, 25) ; [25, 50) ; [50, 75) ; [75; 100)

    // 97 elements; 4 threads => 24 per thread, 1 out

    public MultiplyThread(int id, long chunkSize) {
        this.id = id;
        start = id * chunkSize;
        end = (id + 1) * chunkSize; // exclusive
        if (end < NO_ELEMENTS) end = NO_ELEMENTS;
    }

    @Override
    public void run() {
        for (long i = start; i < end; i++) {
            Main.calculateElement(i);
        }
    }
}
