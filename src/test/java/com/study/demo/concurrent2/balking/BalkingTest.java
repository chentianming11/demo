package com.study.demo.concurrent2.balking;

/**
 * @author 陈添明
 * @date 2018/9/23
 */
public class BalkingTest {

    public static void main(String[] args) {
        BalkingData balkingData = new BalkingData("test.txt", "---------begin----------");
        new CustomerThread(balkingData).start();
        new WaiterThread(balkingData).start();
    }
}
