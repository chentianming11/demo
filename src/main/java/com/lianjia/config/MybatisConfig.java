package com.lianjia.config;

import com.lianjia.interceptor.SQLStatsInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chen on 2018/4/5.
 */
@Configuration
public class MybatisConfig {

    @Bean
    public Interceptor getInterceptor(){
        SQLStatsInterceptor sqlStatsInterceptor = new SQLStatsInterceptor();

       return sqlStatsInterceptor;
    }
}
