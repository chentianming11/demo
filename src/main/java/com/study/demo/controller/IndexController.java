package com.study.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by chen on 2018/4/5.
 */
@Controller
public class IndexController {


    @GetMapping({"/",
            "/index/**",
            "/hello/**"})
    public String index() {
        return "classpath:vue/dist/index";
    }
}
