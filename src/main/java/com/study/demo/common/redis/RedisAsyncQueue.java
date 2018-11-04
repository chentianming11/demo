package com.study.demo.common.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 异步消息队列
 * Redis 的 list(列表) 数据结构常用来作为异步消息队列使用
 * @author 陈添明
 * @date 2018/10/28
 */
public class RedisAsyncQueue<T> {

    private RedisTemplate redisTemplate;
    private String key;

    public  RedisAsyncQueue(RedisTemplate redisTemplate, String key) {
        this.redisTemplate = redisTemplate;
        this.key = key;
    }

    /**
     * 放置消息
     * @param message
     */
    public void setMessage(T message){
        redisTemplate.opsForList().leftPush(key, message);
    }

    /**
     * 处理消息
     * @return
     */
    public void handleMessage(Consumer<T> messageHandler){
        T message = (T) redisTemplate.opsForList().rightPop(key, 10, TimeUnit.SECONDS);
        messageHandler.accept(message);
    }

}
