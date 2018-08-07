/*
package com.study.demo.mq;

import com.rabbitmq.client.*;
import com.study.demo.util.ChannelUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

*/
/**
 * @author 陈添明
 * @date 2018/7/21
 *//*

public class RabbitJunitTest {

    */
/**
 * 消息的生产者 -- 发送消息到Exchange
 *
 * @throws IOException
 *//*

    @Test
    public void testMessageProducer() throws IOException, InterruptedException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统消息生产者");
        // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
        channel.exchangeDeclare("roberto.order", BuiltinExchangeType.DIRECT, true, false, false, new HashMap<>());
        // 设置消息属性 发布消息 (交换机名, Routing key, 可靠消息相关属性 后续会介绍, 消息属性, 消息体);
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").build();
        channel.basicPublish("roberto.order", "add", false, basicProperties, "订单信息".getBytes());
        channel.basicPublish("roberto.order", "add", false, basicProperties, "订单信息2".getBytes());
        System.out.println("发送成功");
        Thread.sleep(100000);
    }

    */
/**
 * 消息的消费者 -- 定义与Exchange绑定的队列，消费队列消息
 *
 * @throws IOException
 *//*

    @Test
    public void testMessageConsumer() throws IOException, InterruptedException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统消息消费者");

        // 声明队列 (队列名, 是否持久化, 是否排他, 是否自动删除, 队列属性);
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare("roberto.order.add", true, false, false, new HashMap<>());

        // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
        channel.exchangeDeclare("roberto.order", BuiltinExchangeType.DIRECT, true, false, false, new HashMap<>());

        // 将队列Binding到交换机上 (队列名, 交换机名, Routing key, 绑定属性);
        channel.queueBind(declareOk.getQueue(), "roberto.order", "add", new HashMap<>());

        // 消费者订阅消息 监听如上声明的队列 (队列名, 是否自动应答(与消息可靠有关 后续会介绍), 消费者标签, 消费者)
        channel.basicConsume(declareOk.getQueue(), true, "RGP订单系统ADD处理逻辑消费者", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(consumerTag);
                System.out.println(envelope.toString());
                System.out.println(properties.toString());
                System.out.println("消息内容:" + new String(body));
                // 抛出个异常
//                System.out.println(1/0);
            }
        });

        Thread.sleep(100000);
    }


    */
/**
 * 消息的生产者 -- 发送消息到Exchange
 *
 * @throws IOException
 *//*

    @Test
    public void testMessageProducerConfirm() throws IOException, InterruptedException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统消息生产者");
        // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
        channel.exchangeDeclare("roberto.order", BuiltinExchangeType.DIRECT, true, false, false, new HashMap<>());
        // 设置消息属性 发布消息 (交换机名, Routing key, 可靠消息相关属性 后续会介绍, 消息属性, 消息体);
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").build();

        // 当消息没有被正确路由时 回调ReturnListener
        channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> {
            System.out.println("replyCode:" + replyCode);
            System.out.println("replyText:" + replyText);
            System.out.println("exchange:" + exchange);
            System.out.println("routingKey:" + routingKey);
            System.out.println("properties:" + properties);
            System.out.println("body:" + new String(body, "UTF-8"));
        });
        // 开启消息确认
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("----------Ack----------");
                System.out.println(deliveryTag);
                System.out.println(multiple);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("----------Nack----------");
                System.out.println(deliveryTag);
                System.out.println(multiple);
            }
        });

        // 将mandatory属性设置成true
        channel.basicPublish("roberto.order", "add", true, basicProperties, "订单信息".getBytes());
        channel.basicPublish("roberto.order", "addXXX", true, basicProperties, "订单信息".getBytes());

        System.out.println("发送成功");
        Thread.sleep(200000);
    }

    */
/**
 * 消息的消费者 -- 定义与Exchange绑定的队列，消费队列消息
 *
 * @throws IOException
 *//*

    @Test
    public void testMessageConsumerAck() throws IOException, InterruptedException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统消息消费者");

        // 声明队列 (队列名, 是否持久化, 是否排他, 是否自动删除, 队列属性);
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare("roberto.order.add", true, false, false, new HashMap<>());

        // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
        channel.exchangeDeclare("roberto.order", BuiltinExchangeType.DIRECT, true, false, false, new HashMap<>());

        // 将队列Binding到交换机上 (队列名, 交换机名, Routing key, 绑定属性);
        channel.queueBind(declareOk.getQueue(), "roberto.order", "add", new HashMap<>());

        // 消费者订阅消息 监听如上声明的队列 (队列名, 是否自动应答(与消息可靠有关 后续会介绍), 消费者标签, 消费者)
        channel.basicConsume(declareOk.getQueue(), false, "RGP订单系统ADD处理逻辑消费者", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                try {
                    Thread.sleep(5000);
                    System.out.println(consumerTag);
                    System.out.println(envelope.toString());
                    System.out.println(properties.toString());
                    System.out.println("消息内容:" + new String(body));
                    if ("订单信息2".equals(new String(body))) {
                        throw new RuntimeException();
                    } else {
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                } catch (Exception e) {
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                }
            }
        });

        Thread.sleep(300000);
    }

    */
/**
 * 消息的生产者 -- 发送消息到Exchange
 *
 * @throws IOException
 *//*

    @Test
    public void testMessageProducerAE() throws IOException, InterruptedException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统消息生产者");
        // 声明AE 类型为Fanout
        channel.exchangeDeclare("roberto.order.failure", BuiltinExchangeType.FANOUT, true, false, false, new HashMap<>());
        // 为roberto.order设置AE
        Map<String, Object> exchangeProperties = new HashMap<>();
        exchangeProperties.put("alternate-exchange", "roberto.order.failure");
        channel.exchangeDeclare("roberto.order", BuiltinExchangeType.DIRECT, true, false, false, exchangeProperties);

        // 发送一条不能正确路由的消息
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").build();
        channel.basicPublish("roberto.order", "addXXX", false, basicProperties, "订单信息".getBytes());

        System.out.println("发送成功");
        Thread.sleep(100000);
    }

    @Test
    public void testMessageConsumerAE() throws IOException, InterruptedException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统消息消费者");

        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare("roberto.order.add", true, false, false, new HashMap<>());
        // 声明AE 类型为Fanout
        channel.exchangeDeclare("roberto.order.failure", BuiltinExchangeType.FANOUT, true, false, false, new HashMap<>());
        // 为roberto.order设置AE
        Map<String, Object> exchangeProperties = new HashMap<>();
        exchangeProperties.put("alternate-exchange", "roberto.order.failure");
        channel.exchangeDeclare("roberto.order", BuiltinExchangeType.DIRECT, true, false, false, exchangeProperties);
        channel.queueBind(declareOk.getQueue(), "roberto.order", "add", new HashMap<>());

        // 将roberto.order.add.failure队列绑定到roberto.order.failure交换机上 无需指定routing key
        AMQP.Queue.DeclareOk declareOk2 = channel.queueDeclare("roberto.order.add.failure", true, false, false, new HashMap<>());
        channel.queueBind(declareOk2.getQueue(), "roberto.order.failure", "", new HashMap<>());

        // 消费roberto.order.add队列
        channel.basicConsume(declareOk.getQueue(), false, "RGP订单系统ADD处理逻辑消费者", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    System.out.println("----------roberto.order.add----------");
                    System.out.println(new String(body, "UTF-8"));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                }
            }
        });

        // 消费roberto.order.add.failure队列
        channel.basicConsume(declareOk2.getQueue(), false, "RGP订单系统ADD FAILURE处理逻辑消费者", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    System.out.println("----------roberto.order.add.failure----------");
                    System.out.println(new String(body, "UTF-8"));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                }
            }
        });
        Thread.sleep(100000);
    }


    */
/**
 * RPC服务端
 *//*

    @Test
    public void testRPCServer() throws IOException, InterruptedException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统Server端");

        channel.queueDeclare("roberto.order.add", true, false, false, new HashMap<>());
        channel.exchangeDeclare("roberto.order", BuiltinExchangeType.DIRECT, true, false, false, new HashMap<>());

        channel.basicConsume("roberto.order.add", true, "RGP订单系统Server端", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String replyTo = properties.getReplyTo();
                String correlationId = properties.getCorrelationId();

                System.out.println("----------收到RPC调用请求消息----------");
                System.out.println(consumerTag);
                System.out.println("消息属性为:" + properties);
                System.out.println("消息内容为" + new String(body));
                try {
                    String orderId = RPCMethod.addOrder(new String(body));
                    AMQP.BasicProperties replyProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").correlationId(correlationId).build();
                    channel.basicPublish("", replyTo, replyProperties, orderId.getBytes());
                    System.out.println("----------RPC调用成功 结果已返回----------");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(100000);
    }


    */
/**
 * RPC的客户端
 *//*

    @Test
    public void testRPCClient() throws IOException, InterruptedException {
        Channel channel = ChannelUtils.getChannelInstance("RGP订单系统Client端");

        channel.queueDeclare("roberto.order.add", true, false, false, new HashMap<>());
        channel.exchangeDeclare("roberto.order", BuiltinExchangeType.DIRECT, true, false, new HashMap<>());
        channel.queueBind("roberto.order.add", "roberto.order", "add", new HashMap<>());

        String replyTo = "roberto.order.add.replay";
        channel.queueDeclare(replyTo, true, false, false, new HashMap<>());
        String correlationId = UUID.randomUUID().toString();
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").correlationId(correlationId).replyTo(replyTo).build();
        channel.basicPublish("roberto.order", "add", true, basicProperties, "订单消息信息".getBytes());
        channel.basicConsume("roberto.order.add.replay", true, "RGP订单系统Client端", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("----------RPC调用结果----------");
                System.out.println(consumerTag);
                System.out.println("消息属性为:" + properties);
                System.out.println("消息内容为" + new String(body));
            }
        });
        Thread.sleep(100000);
    }

}
*/
