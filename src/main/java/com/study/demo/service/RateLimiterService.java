package com.study.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 限流
 * @author 陈添明
 * @date 2018/11/2
 */
@Service
public class RateLimiterService {


    @Autowired
    RedisTemplate redisTemplate;



    public boolean isActionAllowed(String actionSubjectId, String actionType, String actionValue ,long duration, TimeUnit timeUnit , int maxCount) {
        String key = String.format("hc-limit-%s-%s", actionSubjectId, actionType);
        long nowTs = System.currentTimeMillis();
        boolean result;
        List list = redisTemplate.executePipelined(new SessionCallback() {
            @Override
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                // 移除时间窗口外的行为记录
                operations.opsForZSet().removeRangeByScore(key, 0, nowTs - timeUnit.toMillis(duration));
                Long count = operations.opsForZSet().zCard(key);
                if (count < maxCount) {
                    operations.opsForZSet().add(key, actionValue, nowTs);
                    return true;
                } else {
                    return false;
                }
            }
        });
        return false;
    }
}
