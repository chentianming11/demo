package com.study.demo.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.demo.entity.mq.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈添明
 * @date 2018/7/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTest {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitAdmin rabbitAdmin;
    @Autowired
    MessageListenerContainer messageListenerContainer;


    /**
     * 发送消息到Exchange
     */
    @Test
    public void testSpringSender() throws InterruptedException {
        // 声明交换机
        rabbitAdmin.declareExchange(new DirectExchange("roberto.order", true, false, new HashMap<>()));
        // 声明消息 (消息体, 消息属性)
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("UTF-8");
        Message message = new Message("订单信息".getBytes(), messageProperties);

        // 发布消息 (交换机名, Routing key, 消息);
        // 发布消息还可以使用rabbitTemplate.convertAndSend(); 其支持消息后置处理
        rabbitTemplate.send("roberto.order", "add", message);
        Thread.sleep(10000);
    }


    @Test
    public void testSpringListener() throws InterruptedException {
        // 声明队列 (队列名", 是否持久化, 是否排他, 是否自动删除, 队列属性);
        rabbitAdmin.declareQueue(new Queue("roberto.order.add", true, false, false, new HashMap<>()));

        // 声明Direct Exchange (交换机名, 是否持久化, 是否自动删除, 交换机属性);
        rabbitAdmin.declareExchange(new DirectExchange("roberto.order", true, false, new HashMap<>()));

        // 将队列Binding到交换机上 Routing key为add
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("roberto.order.add")).to(new DirectExchange("roberto.order")).with("add"));

        // 开始监听队列
        messageListenerContainer.start();
        Thread.sleep(10000);
    }

    /**
     * 发送消息到Exchange
     */
    @Test
    public void testSpringSenderAuto() throws InterruptedException {
        // 发布消息 (交换机名, Routing key, 消息);
        // 发布消息还可以使用rabbitTemplate.convertAndSend(); 其支持消息后置处理
//        rabbitTemplate.convertAndSend("roberto.order", "add", "订单信息");
        this.sendString();
        System.out.println("消息发送成功");
        Thread.sleep(10000);
    }

    @Test
    public void testSpringListenerAuto() throws InterruptedException {
        // 开始监听队列
        messageListenerContainer.start();
        Thread.sleep(10000);
    }

    /**
     * 发送多种类型的消息到Exchange
     */
    @Test
    public void testSpringSenderManyType() throws InterruptedException, IOException {
        this.sendString();
        this.sendObj();
        this.sendList();
        this.sendMap();
        this.sendFile();
        System.out.println("消息发送成功");
        Thread.sleep(10000);
    }

    /**
     * 发送字符串消息
     */
    public void sendString() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("text/plain");
        Message message = new Message("订单信息".getBytes(), messageProperties);
        rabbitTemplate.send("roberto.order", "add", message);
    }

    /**
     * 发送对象的json串
     */
    public void sendObj() throws JsonProcessingException {
        Order order = new Order("OD0000001", new BigDecimal(888888.888888));
        // 声明消息 (消息体, 消息属性)
        ObjectMapper objectMapper = new ObjectMapper();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("__TypeId__", "order");
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("application/json");
        Message message = new Message(objectMapper.writeValueAsString(order).getBytes(), messageProperties);
        rabbitTemplate.send("roberto.order", "add", message);
    }

    /**
     * 发送List的json串
     */
    public void sendList() throws JsonProcessingException {
        Order order = new Order("OD0000001", new BigDecimal(888888.888888));
        Order order2 = new Order("OD0000002", new BigDecimal(888888.888888));
        List<Order> orderList = Arrays.asList(order, order2);
        ObjectMapper objectMapper = new ObjectMapper();
        // 声明消息 (消息体, 消息属性)
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("__TypeId__", "java.util.List");
        messageProperties.getHeaders().put("__ContentTypeId__", "order");
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("application/json");
        Message message = new Message(objectMapper.writeValueAsString(orderList).getBytes(), messageProperties);

        rabbitTemplate.send("roberto.order", "add", message);
    }

    /**
     * 发送Map的json串
     */
    public void sendMap() throws JsonProcessingException {
        Order order = new Order("OD0000001", new BigDecimal(888888.888888));
        Order order2 = new Order("OD0000002", new BigDecimal(888888.888888));
        Map<String, Order> orderMap = new HashMap<>();
        orderMap.put(order.getOrderId(), order);
        orderMap.put(order2.getOrderId(), order2);
        ObjectMapper objectMapper = new ObjectMapper();

        // 声明消息 (消息体, 消息属性)
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("__TypeId__", "java.util.Map");
        messageProperties.getHeaders().put("__KeyTypeId__", "java.lang.String");
        messageProperties.getHeaders().put("__ContentTypeId__", "order");
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("application/json");
        Message message = new Message(objectMapper.writeValueAsString(orderMap).getBytes(), messageProperties);

        rabbitTemplate.send("roberto.order", "add", message);
    }

    /**
     * 发送文件
     */
    public void sendFile() throws IOException {
        File file = new File("/Users/mac/Downloads/test.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        int length;
        byte[] b = new byte[1024];
        while ((length = fileInputStream.read(b)) != -1) {
            byteArrayOutputStream.write(b, 0, length);
        }
        fileInputStream.close();
        byteArrayOutputStream.close();
        byte[] buffer = byteArrayOutputStream.toByteArray();

        // 声明消息 (消息体, 消息属性)
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("_extName", "jpg");
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setContentType("image/jpg");
        Message message = new Message(buffer, messageProperties);

        rabbitTemplate.send("roberto.order", "add", message);
    }

}
