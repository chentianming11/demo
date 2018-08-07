package com.study.demo.controller.blog;

import com.google.common.collect.ImmutableMap;
import com.study.demo.entity.blog.BlogArticle;
import com.study.demo.entity.blog.BlogCollection;
import com.study.demo.entity.blog.BlogUser;
import com.study.demo.service.blog.BlogService;
import com.study.demo.util.AppAssert;
import com.study.demo.util.EncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 陈添明
 * @date 2018/8/5
 */
@RestController
@RequestMapping("/v1/blog")
@Slf4j
public class BlogController {

    @Autowired
    BlogService blogService;

    EncryptUtils.AES aes = EncryptUtils.getAES("BLOG_USER_ENCRYPT_KEY_24456");

    @PostMapping("/login")
    public BlogUser login(@RequestBody BlogUser blogUser, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        BlogUser loginBlogUser = blogService.login(blogUser);
        // 登录成功 将账号id加密后写入cookie
        log.info("[" + loginBlogUser.getUsername() + "] 登录成功, " + request.getHeader("host"));
        String encrypt = aes.encrypt(loginBlogUser.getId() + "-" + System.currentTimeMillis());
        log.info("加密信息为：" + encrypt);
        Cookie loginCookie = new Cookie("login_cookie", encrypt);
        loginCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(loginCookie);
        return loginBlogUser;
    }

    @PostMapping("/register")
    public Map register(@RequestBody BlogUser blogUser) {
        blogService.register(blogUser);
        return ImmutableMap.of("status", "ok");
    }

    /**
     * 自动登陆
     *
     * @param request
     * @return
     */
    @GetMapping("/autoLogin")
    public BlogUser autoLogin(HttpServletRequest request) {
        Object loginId = request.getAttribute("loginId");
        AppAssert.notNull(loginId, "自动登录失败");
        return blogService.autoLogin((Integer) loginId);

    }


    @PostMapping("/logout")
    public Map logout(HttpServletResponse response) {
        Cookie loginCookie = new Cookie("login_cookie", "0");
        loginCookie.setMaxAge(0);
        response.addCookie(loginCookie);
        return ImmutableMap.of("status", "ok");
    }

    @GetMapping("/collection")
    public List<BlogCollection> getCollectionByUserId(HttpServletRequest request) {
        Object loginId = request.getAttribute("loginId");
        AppAssert.notNull(loginId, "自动登录失败");
        return blogService.getCollectionByUserId((Integer) loginId);

    }

    @PostMapping("/collection")
    public void addCollection(HttpServletRequest request, @RequestBody BlogCollection blogCollection) {
        Object loginId = request.getAttribute("loginId");
        AppAssert.notNull(loginId, "自动登录失败");
        blogCollection.setUserId((Integer) loginId);
        blogCollection.setCreateTime(new Date());
        blogService.addCollection(blogCollection);
    }


    @PostMapping("/article")
    public void addArticle(@RequestBody BlogArticle blogArticle) {
        blogService.addArticle(blogArticle);
    }

    @GetMapping("/article/all")
    public List<Map> getAllArticle(Integer pageNo) {
        List<Map> list = blogService.getAllArticle(pageNo);


        return list;
    }

}
