package com.study.demo.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 不精确计数
 * @author 陈添明
 * @date 2018/11/6
 */
@Service
public class InaccurateCountService {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * HyperLogLog
     * Redis 提供了 HyperLogLog 数据结构就是用来解决这种统计问题的。
     * HyperLogLog 提供不精确的去重计数方案，虽然不精确但是也不是非常不精确，标准误差是 0.81%，
     */

    /**
     * 增加计数 -- 支持去重
     * @param key
     * @param value
     */
    public void addCount(String key, Object value){
        redisTemplate.opsForHyperLogLog().add(key, value);
    }

    /**
     * 获取计数
     * @param key
     * @return
     */
    public Long getCount(String key){
        Long size = redisTemplate.opsForHyperLogLog().size(key);
        return size;
    }

    /**
     * 联合
     * @param destination
     * @param source
     */
    public void union(String destination, String source){
        redisTemplate.opsForHyperLogLog().union(destination, source);
    }

    /**
     * 清空
     * @param key
     */
    public void clear(String key){
        redisTemplate.opsForHyperLogLog().delete(key);
    }



}
