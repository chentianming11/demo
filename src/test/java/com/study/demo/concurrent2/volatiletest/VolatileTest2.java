package com.study.demo.concurrent2.volatiletest;

/**
 * @author 陈添明
 * @date 2018/9/9
 */
public class VolatileTest2 {

    private static int INIT_VALUE = 0;
    private static int MAX_LIMIT = 10;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (INIT_VALUE < MAX_LIMIT){
                System.out.println("t1 -> " + (++ INIT_VALUE));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "adder1").start();


        Thread.sleep(100);


        new Thread(() -> {
            while (INIT_VALUE < MAX_LIMIT){
                System.out.println("t2 -> " + (++ INIT_VALUE));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "adder2").start();


    }
}
