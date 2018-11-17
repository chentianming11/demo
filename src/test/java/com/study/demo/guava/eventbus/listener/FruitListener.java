package com.study.demo.guava.eventbus.listener;

import com.google.common.eventbus.Subscribe;
import com.study.demo.guava.eventbus.event.Apple;
import com.study.demo.guava.eventbus.event.Fruit;

/**
 * @author 陈添明
 * @date 2018/11/17
 */
public class FruitListener {

    @Subscribe
    public void eat(Fruit event){
        System.out.println("eat: 收到一个Fruit事件 -> " + event);
    }


    @Subscribe
    public void eat(Apple event){
        System.out.println("eat: 收到一个Apple事件 -> " + event);
    }



}
