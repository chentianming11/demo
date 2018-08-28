//package com.study.demo.service;
//
//import com.study.demo.common.lock.ZookeeperLock;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.zookeeper.ZooKeeper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Created by chenTianMing on 2018/6/8.
// */
//@Service
//@Slf4j
//public class CommonService {
//
//
//    private static Integer store = 100;
//    @Autowired
//    ZooKeeper zooKeeper;
////
//
//    /**
//     * 秒杀
//     *
//     * @param number
//     * @return
//     */
//    @SneakyThrows
//    public Integer spike(Integer number) {
//        ZookeeperLock lock = new ZookeeperLock(zooKeeper);
//        lock.lock("spike");
//        store--;
//        log.info("正在进行减库存");
//        Thread.sleep(5000);
//        System.out.println(Thread.currentThread().getName() + "正在运行");
//        lock.unlock();
//        return store;
//    }
//
//    @SneakyThrows
//    public void refresh(String data) {
//        zooKeeper.setData("/conf", data.getBytes(), -1);
//    }
//}
