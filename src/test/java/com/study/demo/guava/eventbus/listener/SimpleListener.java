package com.study.demo.guava.eventbus.listener;

import com.google.common.eventbus.Subscribe;

/**
 * @author 陈添明
 * @date 2018/11/17
 */
public class SimpleListener {

    /**
     * 1. 根据参数的类型进行事件订阅：
     *     String类型的事件，进入这个方法进行处理
     * 2. 将子类监听器注册到event bus中，父类监听器定义的监听也是有效的
     * 3. 可以监听到event类及其子类发送过来的事件。
     * @param event
     */
    @Subscribe
    public void doAction(String event){
        System.out.println("接收到一个事件：" + event);
    }
}
