package com.study.demo.mq;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
//@RabbitListener(bindings = {@QueueBinding(value = @Queue(value = "roberto.order.add", durable = "true", autoDelete = "false", exclusive = "false"),
//        exchange = @Exchange( "roberto.order", ), key = "add")})


public class SpringAMQPMessageHandle {

    @RabbitListener(queues = {"roberto.order.add"})
    @RabbitHandler
    public void add(byte[] body) {
        System.out.println("----------byte[]方法进行处理----------");
        try {
            System.out.println(new String(body, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    @RabbitListener(queues = {"roberto.order.add"})
    public void add(String msg) {
        System.out.println("----------roberto.order.add----------");
        System.out.println(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = {"roberto.order.add.failure"})
    public void failureAdd(String msg) {
        System.out.println("----------roberto.order.add.failure----------");
        System.out.println(msg);
    }
}
