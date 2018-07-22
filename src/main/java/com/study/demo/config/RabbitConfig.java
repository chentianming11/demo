//package com.lianjia.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class RabbitConfig {
//
//    /**
//     * hello队列
//     *
//     * @return
//     */
//    @Bean
//    public Queue hello() {
//        return new Queue("hello");
//    }
//
//
//    /**
//     * test.a队列
//     *
//     * @return
//     */
//    @Bean
//    public Queue testA() {
//        return new Queue("test.a");
//    }
//
//    /**
//     * test.b队列
//     *
//     * @return
//     */
//    @Bean
//    public Queue testB() {
//        return new Queue("test.b");
//    }
//
//    /**
//     * test.c队列
//     *
//     * @return
//     */
//    @Bean
//    public Queue testC() {
//        return new Queue("test.c");
//    }
//
//
//    /**
//     * test.exchange
//     *
//     * @return
//     */
//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange("test.exchange");
//    }
//
//
//    /**
//     * 将testA队列与交换机进行绑定，路由key是
//     *
//     * @param testA
//     * @param exchange
//     * @return
//     */
//    @Bean
//    Binding bindingExchangeA(Queue testA, TopicExchange exchange) {
//        return BindingBuilder.bind(testA).to(exchange).with("topic.message.a");
//    }
//
//    /**
//     * 将testC队列与交换机进行绑定，路由key是
//     *
//     * @param exchange
//     * @return
//     */
//    @Bean
//    Binding bindingExchangeC(Queue testC, TopicExchange exchange) {
//        return BindingBuilder.bind(testC).to(exchange).with("topic.message.a");
//    }
//
//
//    /**
//     * 将testB队列与交换机进行绑定，路由key是
//     *
//     * @param exchange
//     * @return
//     */
//    @Bean
//    Binding bindingExchangeB(Queue testB, TopicExchange exchange) {
//        return BindingBuilder.bind(testB).to(exchange).with("topic.message.#");
//    }
//
//
//    @Bean
//    public Queue AMessage() {
//        return new Queue("fanout.A");
//    }
//
//    @Bean
//    public Queue BMessage() {
//        return new Queue("fanout.B");
//    }
//
//    @Bean
//    public Queue CMessage() {
//        return new Queue("fanout.C");
//    }
//
//    @Bean
//    FanoutExchange fanoutExchange() {
//        return new FanoutExchange("fanoutExchange");
//    }
//
//    @Bean
//    Binding bindingFanoutExchangeA(Queue AMessage,FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(AMessage).to(fanoutExchange);
//    }
//
//    @Bean
//    Binding bindingFanoutExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(BMessage).to(fanoutExchange);
//    }
//
//    @Bean
//    Binding bindingFanoutExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(CMessage).to(fanoutExchange);
//    }
//
//
//
//}