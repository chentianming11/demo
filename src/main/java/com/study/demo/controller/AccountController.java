package com.study.demo.controller;

import com.google.common.collect.ImmutableMap;
import com.study.demo.entity.Account;
import com.study.demo.service.AccountService;
import com.study.demo.util.AppAssert;
import com.study.demo.util.EncryptUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 通过对称加密方式实现一个简单登录
 * Created by chenTianMing on 2018/6/2.
 */
@RestController
@Slf4j
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;
    EncryptUtils.AES aes = EncryptUtils.getAES("TEST_ACCOUNT_ENCRYPT_KEY_24456");

    /**
     * 登录接口
     */
    @PostMapping("login")
    @SneakyThrows
    public Map login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        Account account = accountService.selectByUsernameAndPassword(username, password);
        AppAssert.notNull(account, "用户名或者密码错误");
        // 登录成功 将账号id加密后写入cookie
        log.info("[" + account.getNickname() + "] 登录成功, " + request.getHeader("host"));
        String encrypt = aes.encrypt(account.getId() + "-" + System.currentTimeMillis());
        log.info("加密信息为：" + encrypt);
        Cookie loginCookie = new Cookie("login_cookie", encrypt);
        loginCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(loginCookie);
        return ImmutableMap.of("status", "ok");
    }

    /**
     * 登录成功可见信息
     */
    @GetMapping("/list")
    public Map list(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("loginId");
        Account account = accountService.selectById(id);
        return ImmutableMap.of("status", "据说，只有登录成功了才可以看到这个。", "account", account);
    }


}
