package com.gabigutu;

public class IncrementThread implements Runnable {

    public IncrementThread() {  }

    @Override
    public void run() {

        Main.lock.lock();
        Main.myNumber += 2; // a = a+2 ; nu este atomica => in limbaj de asamblare are mai multe instruciuni
        Main.lock.unlock();
//        Main.myAtomicNumber.addAndGet(2);
        // race condition
        // RA = mem_a
        // RB = RA + 2
        // mem_a = RB
    }
}
