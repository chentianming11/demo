package com.study.demo.common.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;


/**
 * Created by chenTianMing on 2018/6/16.
 */
@Slf4j
public class ZookeeperLock {

    private static final String rootPath = "/lock";
    ZooKeeper zooKeeper;
    private String currentPath;
    private String beforePath;

    public ZookeeperLock(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    /**
     * 加锁
     */
    public void lock(String path) {
        try {
            // 如果lock根目录不存在，创建根目录
            if (zooKeeper.exists(rootPath, false) == null) {
                zooKeeper.create(rootPath, "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            if (!tryLock(path)) {
                waitForLock();
            }
            log.info("获取分布式锁成功：" + currentPath);
        } catch (Exception e) {
            log.error("获取分布式锁异常", e);
        }
    }


    /**
     * 释放锁
     */

    public void unlock() {
        try {
            zooKeeper.delete(rootPath + "/" + currentPath, -1);
            log.info("释放分布式锁" + currentPath);
        } catch (Exception e) {
            log.error("释放分布式锁异常", e);
        }

    }


    public boolean tryLock(String path) throws KeeperException, InterruptedException {
        // 创建临时顺序节点
        currentPath = zooKeeper.create(rootPath + "/" + path, "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL).substring(6);
        // 获取所有子目录节点
        List<String> children = zooKeeper.getChildren(rootPath, false);
        // 排序
        Collections.sort(children);
        // 判断当前节点是否是第一个
        int index = children.indexOf(currentPath);
        if (index == 0) {
            // 获取锁成功
            return true;
        } else {
            // 找到他的前一个节点
            beforePath = children.get(index - 1);
            return false;
        }
    }

    /**
     * 等待锁
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void waitForLock() throws KeeperException, InterruptedException {
        CountDownLatch cd1 = new CountDownLatch(1);
        Stat exists = zooKeeper.exists(rootPath + "/" + beforePath, event -> {
            // 在上一个节点注册watch  前一个节点删除，countDown
            if (Objects.equals(Watcher.Event.EventType.NodeDeleted, event.getType())) {
                cd1.countDown();
            }
        });
        // 等待
        if (exists != null) {
            log.info("当前节点" + currentPath + "，不是排在第一位，等待。");
            cd1.await();
        }
    }
}

