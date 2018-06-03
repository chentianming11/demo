package com.lianjia.config;

import com.lianjia.interceptor.LoginInterceptor;
import com.lianjia.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置拦截器
 * Created by chen on 2018/4/5.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    MyInterceptor myInterceptor;

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/account/**")
                .excludePathPatterns("/account/login/**");
    }
}
