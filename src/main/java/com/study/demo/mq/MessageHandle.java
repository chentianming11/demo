/*
package com.study.demo.mq;

import com.study.demo.entity.mq.Order;

import java.io.File;
import java.util.List;
import java.util.Map;

*/
/**
 * 消息处理实体
 *
 * @author mac
 *//*

public class MessageHandle {

    public void add(byte[] body) {
        System.out.println("----------byte[]方法进行处理----------");
        System.out.println("body");
    }

    public void add(String message) {
        System.out.println("----------String方法进行处理----------");
        System.out.println(message);
    }

    public void add(File file) {
        System.out.println("----------File方法进行处理----------");
        System.out.println(file.length());
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
    }

    public void add(Order order) {
        System.out.println("----------Order方法进行处理----------");
        System.out.println(order.getOrderId() + "---" + order.getOrderAmount());
    }

    public void add(List<Order> orderList) {
        System.out.println("----------List<Order>方法进行处理----------");
        System.out.println(orderList.size());
        for (Order order : orderList) {
            System.out.println(order.getOrderId() + "---" + order.getOrderAmount());
        }
    }

    public void add(Map<String, Order> orderMap) {
        System.out.println("----------Map<String, Order>方法进行处理----------");
        for (Map.Entry<String, Order> entry : orderMap.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().getOrderId() + "---" + entry.getValue().getOrderAmount());
        }
    }
}*/
