//package com.study.demo.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.util.Objects;
//import java.util.concurrent.CountDownLatch;
//
///**
// * Created by chenTianMing on 2018/6/16.
// */
//@Configuration
//@Slf4j
//public class ZookeeperConfig {
//
//    private static CountDownLatch countDownLatch = new CountDownLatch(1);
//    @Value("${zookeeper.connection.str}")
//    private String connectionStr;
//
//    @Bean
//    public ZooKeeper zooKeeper(){
//        ZooKeeper zooKeeper = null;
//        try {
//            log.info("开始连接zookeeper服务器" + connectionStr);
//            zooKeeper = new ZooKeeper(connectionStr, 10000, event -> {
//                if (Objects.equals(event.getState(), Watcher.Event.KeeperState.SyncConnected)) {
//                    countDownLatch.countDown();
//                }
//            });
//            countDownLatch.await();
//            log.info("连接zookeeper服务器成功");
//
//            // 配置管理
//            Stat conf = zooKeeper.exists("/conf",false);
//            if (conf == null){
//                zooKeeper.create("/conf", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            }
//
//            zooKeeper.exists("/conf",  event -> {
//                System.out.println(event);
//                if (Objects.equals(event.getType(), Watcher.Event.EventType.NodeDataChanged)) {
//                    log.info("检测到配置更改，重新刷新配置");
//                }
//            });
//
//        } catch (Exception e) {
//            throw new RuntimeException("连接zookeeper服务器失败", e);
//        }
//        return zooKeeper;
//    }
//
//
//}
