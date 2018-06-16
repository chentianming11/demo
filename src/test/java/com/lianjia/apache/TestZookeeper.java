package com.lianjia.apache;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * \Zookeeper测试类
 * Created by chenTianMing on 2018/6/16.
 */
public class TestZookeeper {

    @Test
    public void test1() throws IOException, InterruptedException, KeeperException {
        String connectionStr = "127.0.0.1:2181";
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 创建zookeeper  异步的
        ZooKeeper zooKeeper = new ZooKeeper(connectionStr, 2000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (Objects.equals(event.getState(), Event.KeeperState.SyncConnected)){
                    // 连接zookeeper服务器成功
                    countDownLatch.countDown();
                }
                System.out.println("事件" + event);
            }
        });
        countDownLatch.await();
        System.out.println("连接成功");

        // 创建持久的目录节点
        String testNode = zooKeeper.create("/testNode", "testNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("目录节点创建成功：" + testNode);
        // 创建子目录节点
        String childNode = zooKeeper.create("/testNode/testChildNode", "childNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("子节点创建成功" + childNode);
        String childNode2 = zooKeeper.create("/testNode/testChildNode2", "childNode2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("子节点创建成功" + childNode2);

        // 查看某个目录下所有的子目录节点
        List<String> children = zooKeeper.getChildren("/testNode", false);
        System.out.println("/testNode下的所有子节点：" + children);

        // 修改节点数据
        zooKeeper.setData("/testNode/testChildNode", "new childNode".getBytes(),-1);

        // 获取节点数据
        byte[] data = zooKeeper.getData("/testNode/testChildNode", false, null);
        System.out.println("节点数据：" + new String(data));

        // 删除子节点
        zooKeeper.delete("/testNode/testChildNode", -1);
        zooKeeper.delete("/testNode/testChildNode2", -1);
        // 删除父节点
        zooKeeper.delete("/testNode", -1);
        // 关闭连接
        zooKeeper.close();
    }
}
