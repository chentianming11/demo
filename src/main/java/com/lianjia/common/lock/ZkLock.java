package com.lianjia.common.lock;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by chenTianMing on 2018/6/10.
 */
@Slf4j
public class ZkLock implements Lock {

    private static final String LOCK_PATH = "/LOCK";
    private static final String ZK_IP_PORT = "127.0.0.1:2181";
    private ZkClient zkClient = new ZkClient(ZK_IP_PORT);
    private CountDownLatch cd1;

    private String beforePath; // 当前请求的前一个节点
    private String currentPath; // 当前节点

    /**
     * 判断是否存在LOCK目录，如果不存在，则创建
     */
    {
        if (!zkClient.exists(LOCK_PATH)){
            zkClient.createPersistent(LOCK_PATH);
        }
    }

    /**
     * 尝试进行加锁
     * @return
     */
    @Override
    public  boolean tryLock() {
        // currentPath ==null 创建临时节点
        if (StringUtils.isBlank(currentPath) || !zkClient.exists(currentPath)){
            // 当前节点不存在，创建一个临时顺序节点
            currentPath = zkClient.createEphemeralSequential(LOCK_PATH + "/", "lock");
            log.info("创建临时顺序节点：" + currentPath);
        }
        // 获取所有的临时节点并且排序：临时节点的名称为自增长的字符串 00000001
        List<String> children = zkClient.getChildren(LOCK_PATH);
        Collections.sort(children);
        if (Objects.equals(currentPath, LOCK_PATH + "/" +children.get(0))){
            // 当前节点排在第一位，则获取锁成功
            log.info("获取锁成功 " + currentPath);
            return true;
        }else {
            // 当前节点不在第一位，则获取前面节点的名称，并赋值给beforePath
            int pos = Collections.binarySearch(children, currentPath.substring(6));
            beforePath = LOCK_PATH + "/" + children.get(pos -1);
        }
        return false;
    }

    /**
     * 释放锁
     */
    @Override
    public void unlock() {
        zkClient.delete(currentPath);
    }

    /**
     * 加锁
     */
    @Override
    public void lock() {
        if (!tryLock()){
            waitForLock();
            lock();
        }else {
            log.info(Thread.currentThread().getName() + "获取分布式锁");
        }
    }

    /**
     * 等待获取锁
     */
     private void waitForLock() {
        log.info(Thread.currentThread().getName() + "尝试获取锁");
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (cd1 != null) {
                    cd1.countDown();
                }
            }
        };
         zkClient.subscribeDataChanges(beforePath, listener);
         // 给排在前面的节点增加数据删除的watcher
         if (zkClient.exists(beforePath)){
            cd1 = new CountDownLatch(1);
            try {
                cd1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
         }
         zkClient.unsubscribeDataChanges(beforePath, listener);
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
