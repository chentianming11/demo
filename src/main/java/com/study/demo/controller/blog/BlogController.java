package com.study.demo.controller.blog;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.study.demo.entity.blog.BlogArticle;
import com.study.demo.entity.blog.BlogCollection;
import com.study.demo.entity.blog.BlogUser;
import com.study.demo.service.blog.BlogService;
import com.study.demo.util.AppAssert;
import com.study.demo.util.CookieUtils;
import com.study.demo.util.EncryptUtils;
import lombok.SneakyThrows;
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


    @SneakyThrows
    private Integer getLoginId(HttpServletRequest request){
        Cookie loginCookie = CookieUtils.getCookieByName(request, "login_cookie");
        if (loginCookie != null) {
            // 已登录
            String decrypt = aes.decrypt(loginCookie.getValue());
            Integer id = Integer.valueOf(Splitter.on("-").splitToList(decrypt).get(0));
            log.info("解析出已登录的id为" + id);
            return id;
        }
        return null;
    }

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

    @GetMapping("/user/list")
    public List<BlogUser> getUserList(){
        List<BlogUser> list = blogService.getUserList();
        return list;
    }

    @GetMapping("/user")
    public BlogUser getUserInfo(Integer userId){
         return blogService.getUserInfo(userId);
    }

    /**
     * 自动登陆
     *
     * @param request
     * @return
     */
    @GetMapping("/autoLogin")
    public BlogUser autoLogin(HttpServletRequest request) {
        Integer loginId = this.getLoginId(request);
        AppAssert.notNull(loginId, "未登录");
        return blogService.autoLogin(loginId);

    }


    @PostMapping("/logout")
    public Map logout(HttpServletResponse response) {
        Cookie loginCookie = new Cookie("login_cookie", "0");
        loginCookie.setMaxAge(0);
        response.addCookie(loginCookie);
        return ImmutableMap.of("status", "ok");
    }

    @GetMapping("/collection")
    public List<BlogCollection> getCollectionByUserId(Integer userId) {
        return blogService.getCollectionByUserId(userId);

    }

    @PostMapping("/collection")
    public void addCollection(HttpServletRequest request, @RequestBody BlogCollection blogCollection) {
        Integer loginId = this.getLoginId(request);
        AppAssert.notNull(loginId, "未登录");
        blogCollection.setUserId(loginId);
        blogCollection.setCreateTime(new Date());
        blogService.addCollection(blogCollection);
    }


    @PostMapping("/article")
    public void addArticle(HttpServletRequest request,@RequestBody BlogArticle blogArticle) {
        Integer loginId = this.getLoginId(request);
        AppAssert.notNull(loginId, "未登录");
        blogService.addArticle(blogArticle);
    }

    @GetMapping("/article/list")
    public PageInfo<Map> getAllArticle(Integer pageNum, Integer userId, Integer collectionId) {
        PageInfo<Map> pageInfo = blogService.getArticleList(pageNum, userId, collectionId);
        return pageInfo;
    }

}
