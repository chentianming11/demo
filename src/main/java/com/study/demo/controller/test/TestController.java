package com.study.demo.controller.test;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author mac
 * @作者： 陈添明
 * @创建日期: 2018/7/21
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @GetMapping("/topicExchange")
    public Map topicExchange(String msg) {
        rabbitTemplate.convertAndSend("test.exchange", "topic.message.a", msg);
        return ImmutableMap.of("status", "ok");
    }

    @GetMapping("/fanoutExchange")
    public Map fanoutExchange(String msg) {
        rabbitTemplate.convertAndSend("fanoutExchange", "", msg);
        return ImmutableMap.of("status", "ok");
    }
}
