package com.lianjia.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.impl.DefaultExceptionHandler;

import java.util.HashMap;
import java.util.Map;

public class ChannelUtils {
    public static Channel getChannelInstance(String connectionDescription) {
        try {
            ConnectionFactory connectionFactory = getConnectionFactory();
            Connection connection = connectionFactory.newConnection(connectionDescription);
            return connection.createChannel();
        } catch (Exception e) {
            throw new RuntimeException("获取Channel连接失败");
        }
    }

    private static ConnectionFactory getConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // 配置连接信息
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("mac");
        connectionFactory.setPassword("123456");

        // 网络异常自动连接恢复
        connectionFactory.setAutomaticRecoveryEnabled(true);
        // 每10秒尝试重试连接一次
        connectionFactory.setNetworkRecoveryInterval(10000);

        // 设置ConnectionFactory属性信息
        Map<String, Object> connectionFactoryPropertiesMap = new HashMap();
        connectionFactoryPropertiesMap.put("principal", "mac");
        connectionFactoryPropertiesMap.put("description", "RGP订单系统V2.0");
        connectionFactoryPropertiesMap.put("emailAddress", "chentianming11@foxmail.com");
        connectionFactory.setClientProperties(connectionFactoryPropertiesMap);

        // 设置自定义异常处理器
        connectionFactory.setExceptionHandler(new DefaultExceptionHandler() {
            @Override
            public void handleConsumerException(Channel channel, Throwable exception, Consumer consumer, String consumerTag, String methodName) {
                System.out.println("----------消息消费异常处理----------");
                System.out.println("消息消费异常日志记录:" + exception.getMessage());
                super.handleConsumerException(channel, exception, consumer, consumerTag, methodName);
            }
        });

        return connectionFactory;
    }
}