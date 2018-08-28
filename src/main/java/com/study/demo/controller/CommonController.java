package com.study.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chenTianMing on 2018/6/8.
 */
@Controller
@Slf4j
@RequestMapping("common")
public class CommonController {


//    @Autowired
//    CommonService commonService;
//
//    /**
//     * 测试zk实现的分布式锁
//     */
//    @GetMapping("/lock")
//    @ResponseBody
//    public Map<String, Object> testRedisLock(Integer number) {
//        Integer store = commonService.spike(number);
//        return ImmutableMap.of("序号：", number, "剩余库存：", store);
//    }
//
//    /**
//     * 测试zk实现动态刷新配置
//     */
//    @GetMapping("/refresh")
//    @ResponseBody
//    public Map<String, Object> refresh(String data) {
//        commonService.refresh(data);
//        return ImmutableMap.of("status", "ok");
//    }

}
