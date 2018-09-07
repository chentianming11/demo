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
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
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


    @SneakyThrows
    private Integer getLoginId(HttpServletRequest request){
        Cookie loginCookie = CookieUtils.getCookieByName(request, "login_cookie");
        if (loginCookie != null) {
            // 已登录
            String decrypt = EncryptUtils.aesDecrypt(loginCookie.getValue());
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
        String encrypt = EncryptUtils.aesEncrypt(loginBlogUser.getId() + "-" + System.currentTimeMillis());
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

    @GetMapping("/article/{articleId}")
    public Map<String, Object> getArticleDetail(@PathVariable("articleId") Integer articleId) {
        return  blogService.getArticleDetail(articleId);
    }


    @PostMapping("/imgUpload")
    public Map<String, Object> imgUpload(MultipartFile img) throws IOException {

        log.info("图片上传成功");

        String originalFilename = RandomStringUtils.randomAlphabetic(8) + img.getOriginalFilename();
        File fileDir = new File("img/");
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }

        File file = new File(fileDir,originalFilename);
        FileCopyUtils.copy(img.getBytes(), file );
        Map<String ,Object> map=new HashMap<>();
        map.put("code",200);
        map.put("msg","上传成功");
        map.put("url","http://localhost:8888/img/" + originalFilename);
        //这里只做返回值测试用，url 参数为图片上传后访问地址。具体根据功能进行修改}
        return map;

    }


    @GetMapping("/test/updateAll")
    public Map<String, Object> testUpdateAll() {
        blogService.testUpdateAll();

        return ImmutableMap.of("status", "ok");
    }



}
