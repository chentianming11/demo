package com.study.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈添明
 * @date 2018/7/21
 */
@Configuration
public class SpringAMQPConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();

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
        connectionFactoryPropertiesMap.put("principal", "RobertoHuang");
        connectionFactoryPropertiesMap.put("description", "RGP订单系统V2.0");
        connectionFactoryPropertiesMap.put("emailAddress", "RobertoHuang@foxmail.com");
        connectionFactory.setClientProperties(connectionFactoryPropertiesMap);

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
        // 将CachingConnectionFactory的PublisherConfirms设置为true
        cachingConnectionFactory.setPublisherConfirms(true);
        // 将CachingConnectionFactory的PublisherReturns设置为true
        cachingConnectionFactory.setPublisherReturns(true);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 设置RabbitTemplate的Mandatory属性为true
        rabbitTemplate.setMandatory(true);
        // 为RabbitTemplate设置ReturnCallback
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            try {
                System.out.println("replyCode:" + replyCode);
                System.out.println("replyText:" + replyText);
                System.out.println("exchange:" + exchange);
                System.out.println("routingKey:" + routingKey);
                System.out.println("properties:" + message.getMessageProperties());
                System.out.println("body:" + new String(message.getBody(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        // 为RabbitTemplate设置ConfirmCallback
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("-----------------ack-----------------");
            System.out.println(ack);
            System.out.println(cause);
            System.out.println(correlationData.getId());
        });

        return rabbitTemplate;
    }


    /*@Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setQueueNames("roberto.order.add");

        // 设置消费者线程数
        messageListenerContainer.setConcurrentConsumers(5);
        // 设置最大消费者线程数
        messageListenerContainer.setMaxConcurrentConsumers(10);

        // 设置消费者属性信息
        Map<String, Object> argumentMap = new HashMap();
        messageListenerContainer.setConsumerArguments(argumentMap);

        // 设置消费者标签
        messageListenerContainer.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String s) {
                return "RGP订单系统ADD处理逻辑消费者";
            }
        });

        // 使用setAutoStartup方法可以手动设置消息消费时机
        messageListenerContainer.setAutoStartup(false);

        // 使用setAfterReceivePostProcessors方法可以增加消息后置处理器
        // messageListenerContainer.setAfterReceivePostProcessors();

        *//*messageListenerContainer.setMessageListener((MessageListener) message -> {
            try {
                System.out.println(new String(message.getBody(), "UTF-8"));
                System.out.println(message.getMessageProperties());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*//*

        // 设置消息确认模式为手动模式
        messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        messageListenerContainer.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            try {
                System.out.println(new String(message.getBody(), "UTF-8"));
                System.out.println(message.getMessageProperties());
                if ("订单信息2".equals(new String(message.getBody(), "UTF-8"))) {
                    throw new RuntimeException();
                } else {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                }
            } catch (Exception e) {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        });
        return messageListenerContainer;
    }*/


    /**
     * 消息Listenter容器
     * @param connectionFactory
     * @return
     *//*
    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setQueueNames("roberto.order.add");

        messageListenerContainer.setConcurrentConsumers(5);
        messageListenerContainer.setMaxConcurrentConsumers(10);

        Map<String, Object> argumentMap = new HashMap();
        messageListenerContainer.setConsumerArguments(argumentMap);

        messageListenerContainer.setConsumerTagStrategy(s -> "RGP订单系统ADD处理逻辑消费者");

        messageListenerContainer.setAutoStartup(true);

        // 新建消息处理器适配器
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessageHandle());
        // 设置默认处理消息方法
        messageListenerAdapter.setDefaultListenerMethod("handleMessage");
        Map<String, String> queueOrTagToMethodName = new HashMap<>();
        // 将roberto.order.add队列的消息 使用add方法进行处理
        queueOrTagToMethodName.put("roberto.order.add","add");
        messageListenerAdapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
        messageListenerContainer.setMessageListener(messageListenerAdapter);

        return messageListenerContainer;
    }
*/

    /**
     * 为不同ContentType消息设置消息转换器
     *
     * @param connectionFactory
     * @return
     */
    /*@Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setQueueNames("roberto.order.add");

        messageListenerContainer.setConcurrentConsumers(5);
        messageListenerContainer.setMaxConcurrentConsumers(10);

        Map<String, Object> argumentMap = new HashMap();
        messageListenerContainer.setConsumerArguments(argumentMap);

        messageListenerContainer.setConsumerTagStrategy(s -> "RGP订单系统ADD处理逻辑消费者");

        messageListenerContainer.setAutoStartup(true);

        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessageHandle());
        messageListenerAdapter.setDefaultListenerMethod("handleMessage");
        Map<String, String> queueOrTagToMethodName = new HashMap<>();
        queueOrTagToMethodName.put("roberto.order.add","add");
        messageListenerAdapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
        messageListenerContainer.setMessageListener(messageListenerAdapter);

        // 设置消息转换器
        ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter();
        StringMessageConverter stringMessageConverter = new StringMessageConverter();
        FileMessageConverter fileMessageConverter = new FileMessageConverter();
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("order", Order.class);
        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
        javaTypeMapper.setIdClassMapping(idClassMapping);
        jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);

        // 设置text/html text/plain 使用StringMessageConverter
        converter.addDelegate("text/html", stringMessageConverter);
        converter.addDelegate("text/plain", stringMessageConverter);
        // 设置application/json 使用Jackson2JsonMessageConverter
        converter.addDelegate("application/json", jackson2JsonMessageConverter);
        // 设置image/jpg image/png 使用FileMessageConverter
        converter.addDelegate("image/jpg", fileMessageConverter);
        converter.addDelegate("image/png", fileMessageConverter);
        messageListenerAdapter.setMessageConverter(converter);

        return messageListenerContainer;
    }*/
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);

        // 设置消费者线程数
        simpleRabbitListenerContainerFactory.setConcurrentConsumers(5);
        // 设置最大消费者线程数
        simpleRabbitListenerContainerFactory.setMaxConcurrentConsumers(10);

        // 设置消费者标签
        simpleRabbitListenerContainerFactory.setConsumerTagStrategy(s -> "RGP订单系统ADD处理逻辑消费者");

        return simpleRabbitListenerContainerFactory;
    }


    /**
     * 自动声明交换机
     * 如果要一次性声明多个 使用public List<Exchange> listExchange()即可
     *
     * @return
     */
    @Bean
    public List<Exchange> listExchange() {
//        Exchange orderFailure = new FanoutExchange("roberto.order.failure", true, false, null);
        // 设置AE
//        Exchange order = new DirectExchange("roberto.order", true, false, ImmutableMap.of("alternate-exchange", "roberto.order.failure"));
        Exchange order = new DirectExchange("roberto.order", true, false, null);

        return Arrays.asList(order);

    }

    /**
     * 自动声明队列
     * 如果要一次性声明多个 使用public List<Queue> listQueue()即可
     *
     * @return
     */
    @Bean
    public List<Queue> listQueue() {
        Queue orderAdd = new Queue("roberto.order.add", true, false, false, null);
        Queue orderAddFailure = new Queue("roberto.order.add.failure", true, false, false, null);

        return Arrays.asList(orderAdd, orderAddFailure);
    }

    /**
     * 自动声明绑定
     * 如果要一次性声明多个 使用public List<Binding> listBinding()即可
     *
     * @return
     */
    @Bean
    public List<Binding> listBinding() {

        Binding orderAdd = new Binding("roberto.order.add", Binding.DestinationType.QUEUE, "roberto.order", "add", null);
        Binding orderAddFailure = new Binding("roberto.order.add.failure", Binding.DestinationType.QUEUE, "roberto.order.failure", "", null);

        return Arrays.asList(orderAdd, orderAddFailure);
    }
}
