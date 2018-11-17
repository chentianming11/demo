package com.study.demo.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.study.demo.guava.eventbus.listener.ConcreteListener;

/**
 * @author 陈添明
 * @date 2018/11/17
 */
public class InheritEventBusExample {


    public static void main(String[] args) {
        // 创建一个eventBus
        EventBus eventBus = new EventBus();
        // 注册一个监听器
        eventBus.register(new ConcreteListener());
        // 发送一个事件
       eventBus.post("发送字符串事件");
       eventBus.post(1);
    }
}
