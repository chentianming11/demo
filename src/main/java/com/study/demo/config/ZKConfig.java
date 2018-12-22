package com.study.demo.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈添明
 * @date 2018/12/17
 */
@Configuration
public class ZKConfig {

    @Value("${zookeeper.connection.str}")
    private String connectionStr;

    @Value("${app.name}")
    private String appName;


    @Bean
    public CuratorFramework getCuratorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1_000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(connectionStr)
                        .sessionTimeoutMs(30_000)
                        .connectionTimeoutMs(30_000)
                        .retryPolicy(retryPolicy)
                        .namespace(appName)
                        .build();
        return client;
    }

}
