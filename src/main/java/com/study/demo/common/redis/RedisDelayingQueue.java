package com.study.demo.common.redis;

import com.alibaba.fastjson.TypeReference;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 延时队列可以通过 Redis 的 zset(有序列表) 来实现。
 * 我们将消息序列化成一个字符串作为 zset 的value，这个消息的到期处理时间作为score，
 * 然后用多个线程轮询 zset 获取到期的任务进行处理，多个线程是为了保障可用性，
 * 万一挂了一个线程还有其它线程可以继续处理。因为有多个线程，所以需要考虑并发争抢任务，确保任务不能被多次执行。...
 * @author mac
 */
public class RedisDelayingQueue<T> {

  static class TaskItem<T> {
    public String id;
    public T msg;
  }

  /**
   *  fastjson 序列化对象中存在 generic 类型时，需要使用 TypeReference
   */
  private Type TaskType = new TypeReference<TaskItem<T>>() {
  }.getType();



  private RedisTemplate redisTemplate;
  private String queueKey;
  private Consumer<T> messageHandler;

  public RedisDelayingQueue(RedisTemplate redisTemplate, String queueKey, Consumer<T> messageHandler) {
    this.redisTemplate = redisTemplate;
    this.queueKey = queueKey;
    this.messageHandler = messageHandler;
  }

  public void delay(T msg, int delayMillis) {
    TaskItem<T> task = new TaskItem<T>();
    // 分配唯一的 uuid
    task.id = UUID.randomUUID().toString();
    task.msg = msg;
    // 塞入延时队列 ,delayMillis毫秒重试
    redisTemplate.opsForZSet().add(queueKey, task, System.currentTimeMillis() + delayMillis);
  }

  /**
   * 轮询队列处理消息
   * Redis 的 zrem 方法是多线程多进程争抢任务的关键，它的返回值决定了当前实例有没有抢到任务，
   * 因为 loop 方法可能会被多个线程、多个进程调用，同一个任务可能会被多个进程线程抢到，通过 zrem 来决定唯一的属主。
   同时，我们要注意一定要对 handle_msg 进行异常捕获，避免因为个别任务处理问题导致循环异常退出。
   以下是 Java 版本的延时队列实现，因为要使用到 Json 序列化，所以还需要 fastjson 库的支持。...
   */
  public void loop() {
    while (!Thread.interrupted()) {
      // 只取一条
      Set values = redisTemplate.opsForZSet().rangeByScore(queueKey, 0, System.currentTimeMillis(), 0, 1);
      if (values.isEmpty()) {
        try {
          // 歇会继续
          Thread.sleep(500);
        } catch (InterruptedException e) {
          break;
        }
        continue;
      }
      Object value = values.iterator().next();
      // 抢到了 从消息队列中移除该消息
      if (redisTemplate.opsForZSet().remove(queueKey, value) > 0) {
        TaskItem<T> task = (TaskItem) value;
        messageHandler.accept(task.msg);
      }
    }
  }
}

