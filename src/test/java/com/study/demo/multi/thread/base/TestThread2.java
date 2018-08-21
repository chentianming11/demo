package com.study.demo.multi.thread.base;

class ThreadDemo2 extends Thread {
    private String threadName;

    ThreadDemo2(String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(50);
            }
        }catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        super.start();
    }
}

public class TestThread2 {

    public static void main(String args[]) {
        ThreadDemo2 T1 = new ThreadDemo2( "Thread-1");
        T1.start();

        ThreadDemo2 T2 = new ThreadDemo2( "Thread-2");
        T2.start();
    }
}