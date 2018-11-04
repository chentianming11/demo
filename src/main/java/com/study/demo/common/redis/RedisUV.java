package com.study.demo.common.redis;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis 提供了 HyperLogLog 数据结构就是用来解决这种统计问题的。
 * HyperLogLog 提供不精确的去重计数方案，虽然不精确但是也不是非常不精确，标准误差是 0.81%，
 * 这样的精确度已经可以满足上面的 UV 统计需求了。
 * @author 陈添明
 * @date 2018/10/28
 */
public class RedisUV {

    private RedisTemplate redisTemplate;

    public RedisUV(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 计数加1
     * @param pageView
     * @param userId 用户id 未登陆用户使用sessionId
     */
    public void addCount(String pageView, String userId){
        redisTemplate.opsForHyperLogLog().add(pageView, userId);
    }

    public long getCount(String pageView){
        Long size = redisTemplate.opsForHyperLogLog().size(pageView);
        return size;
    }

}
