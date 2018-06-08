package com.lianjia.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.UUID;

/**
 * SETNX key val
    当且仅当key不存在时，set一个key为val的字符串，返回1；若key存在，则什么都不做，返回0。

 expire key timeout
 为key设置一个超时时间，单位为second，超过这个时间锁会自动释放，避免死锁。

 delete key
 删除key

 * Created by chenTianMing on 2018/6/8.
 */
@Component
@Slf4j
public class RedisDistributedLock {

    @Autowired
    JedisPool jedisPool;

    /**
     * 加锁
     * @param key  锁的key
     * @param acquireTimeout  获取超时时间
     * @param timeout   锁的超时时间
     * @return 锁标识
     */
    public String lockWithTimeout(String key,
                                  long acquireTimeout, long timeout) {
        String retIdentifier = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 随机生成一个value
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = "lock" + key;
            // 超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int)(timeout / 1000);
            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;

            while (System.currentTimeMillis() < end){
                if (jedis.setnx(lockKey, identifier) == 1){
                    // 设置超时时间
                    jedis.expire(lockKey, lockExpire);
                    // 返回锁的标识
                    return identifier;
                }
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (jedis.ttl(lockKey) == -1) {
                    jedis.expire(lockKey, lockExpire);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }catch (Exception e){
            log.error("", e);
        }finally {
            if (jedis != null){

                jedis.close();
            }
        }
        return retIdentifier;
    }

    /**
     * 释放锁
     * @param key 锁的key
     * @param identifier    释放锁的标识
     * @return
     */
    public boolean releaseLock(String key, String identifier){
        boolean retFlag = false;
        String lockKey = "lock" + key;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            while (true){

                // 监视lock，准备开始事务
                jedis.watch(lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(jedis.get(lockKey))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                jedis.unwatch();
                break;
            }
        }catch (Exception e){
            log.error("", e);
        }finally {
            if (jedis != null){

                jedis.close();
            }
        }
        return retFlag;
    }
}
