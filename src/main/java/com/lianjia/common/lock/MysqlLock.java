package com.lianjia.common.lock;

import com.lianjia.entity.MyLock;
import com.lianjia.mapper.LockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by chenTianMing on 2018/6/9.
 */
@Component
public class MysqlLock implements Lock {

    @Autowired
    private LockMapper lockMapper;

    private static final Integer LOCK_NUM = 1;

    /**
     * 阻塞式锁的实现
     * @return
     */
    @Override
    public void lock() {
        if (!tryLock()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock();
        }
    }

    /**
     * 非阻塞式锁的实现
     * @return
     */
    @Override
    public boolean tryLock() {
        try {
            lockMapper.insert(new MyLock(LOCK_NUM));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 释放锁
     * @return
     */
    @Override
    public void unlock() {
        lockMapper.deleteByPrimaryKey(LOCK_NUM);
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
