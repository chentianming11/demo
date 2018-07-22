package com.study.demo.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 陈添明
 * @date 2018/7/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitAnnotationTest {
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发送字符串消息
     */
    @Test
    public void sendString() throws InterruptedException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("UTF-8");
        Message message = new Message("订单信息".getBytes(), messageProperties);
        rabbitTemplate.send("roberto.order", "add", message, new CorrelationData("201210704116"));

        MessageProperties messageProperties2 = new MessageProperties();
        messageProperties2.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties2.setContentType("UTF-8");
        Message message2 = new Message("订单信息2".getBytes(), messageProperties2);
        rabbitTemplate.send("roberto.order", "add", message2, new CorrelationData("201210704116"));
        Thread.sleep(10000);
    }

    /**
     * 发送字符串消息
     */
    @Test
    public void sendStringConfirm() throws InterruptedException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("text/plain");
        Message message = new Message("订单信息".getBytes(), messageProperties);
        rabbitTemplate.send("roberto.order", "add", message, new CorrelationData("201210704116"));
        rabbitTemplate.send("roberto.order", "addXXX", message, new CorrelationData("201210704116"));
        System.out.println("发送成功");
        Thread.sleep(10000);
    }


    /**
     * 发送字符串消息
     */
    @Test
    public void sendStringAE() throws InterruptedException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("UTF-8");
        Message message = new Message("订单信息".getBytes(), messageProperties);
        rabbitTemplate.send("roberto.order", "addxxx", message, new CorrelationData("201210704116"));

        Thread.sleep(10000);
    }

}
