package com.lianjia.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lianjia.service.WxService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

/**
 * Created by chen on 2018/5/13.
 */
@Controller
@RequestMapping("/wx")
@Slf4j
public class WxController {


    @Autowired
    private WxService wxService;

    private Cache<String, Object> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();


    /**
     * 微信授权
     */
    @GetMapping("/auth")
    @SneakyThrows
    public void auth(String backUrl, HttpServletResponse response) {
        String url = wxService.getWxAuthUrl(backUrl);
        response.sendRedirect(url);
    }

    /**
     * 微信授权回调
     */
    @GetMapping("/callback")
    @ResponseBody
    @SneakyThrows
    public void callback(String code, String state, HttpServletResponse response) {
        String jsonString = wxService.getAccseToken(code);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String access_token = (String) jsonObject.get("access_token");
        String refresh_token = (String) jsonObject.get("refresh_token");
        String openid = (String) jsonObject.get("openid");
        // 拉取用户信息(需scope为 snsapi_userinfo)
        String userInfo = wxService.getUserInfo(access_token, openid);
        // 将微信用户信息暂时保存到缓存中
        cache.put(openid, userInfo);
        // 重定向到初始请求页面
        String callbackUrl = URLDecoder.decode(state, "utf-8");
        response.sendRedirect(callbackUrl + "?openid=" + openid);
    }

    /**
     * 获取微信用户信息
     */
    @GetMapping("/userInfo")
    @ResponseBody
    public Object userInfo(String openid){
        Object userInfo = cache.getIfPresent(openid);
        return userInfo;
    }
}
