package com.lianjia.other.lock;

import com.lianjia.common.lock.MysqlLock;
import com.lianjia.common.lock.ZkLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 测试卖火车票的多线程问题
 * Created by chenTianMing on 2018/6/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketTest {

    private int count = 100; // 100张票


    @Test
    public void test1() throws InterruptedException {

        TicketTask ticketTask = new TicketTask();
        Thread t1 = new Thread(ticketTask, "窗口A");
        Thread t2 = new Thread(ticketTask, "窗口B");
        Thread t3 = new Thread(ticketTask, "窗口C");
        Thread t4 = new Thread(ticketTask, "窗口D");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        Thread.currentThread().join();

    }

    public class TicketTask implements Runnable{

        @Override
        public void run() {
            ZkLock lock = new ZkLock();
            while (count > 0){
                if (count > 0){
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + "售出第" + (count--) + "张票" );
                    } finally {
                        lock.unlock();
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
