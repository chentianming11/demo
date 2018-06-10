package com.lianjia.service;

import com.lianjia.common.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Created by chenTianMing on 2018/6/8.
 */
@Service
@Slf4j
public class CommonService {

//    @Autowired
//    Jedis jedis;
//
//    ZookeeperDistributedLock lock;
//
//    /**
//     * 秒杀
//     * @param number
//     * @return
//     */
//    public Integer spike(Integer number) {
//        lock = new ZookeeperDistributedLock("127.0.0.1:2181", "test1");
//        lock.lock();
//
//        Integer store = Integer.valueOf(jedis.get("store"));
//        store--;
//        System.out.println(Thread.currentThread().getName() + "正在运行");
//        jedis.set("store", String.valueOf(store));
//
//        lock.unlock();
//        return store;
//    }
}
