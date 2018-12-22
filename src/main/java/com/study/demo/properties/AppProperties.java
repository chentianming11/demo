package com.study.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈添明
 * @date 2018/12/19
 */
@Configuration
@ConfigurationProperties("app")
@Data
public class AppProperties {

    private String name;

}
