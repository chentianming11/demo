package com.lianjia.config;

import com.lianjia.interceptor.MyInterceptor;
import com.lianjia.interceptor.MyInterceptor2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by chen on 2018/4/5.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(new MyInterceptor2())
                .addPathPatterns("/hello/**");
    }
}
