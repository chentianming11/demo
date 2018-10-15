package com.study.demo.controller.blog;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.study.demo.controller.base.BaseController;
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
import org.springframework.http.ResponseEntity;
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
import java.util.List;
import java.util.Map;

/**
 * @author 陈添明
 * @date 2018/8/5
 */
@RestController
@RequestMapping("/v1/blog")
@Slf4j
public class BlogController extends BaseController {

    public static final String LOGIN_COOKIE = "login_cookie";
    @Autowired
    BlogService blogService;
    @SneakyThrows
    private Integer getLoginId(HttpServletRequest request){
        Cookie loginCookie = CookieUtils.getCookieByName(request, "login_cookie");
        Integer id = null;
        if (loginCookie != null) {
            // 已登录
            String decrypt = EncryptUtils.aesDecrypt(loginCookie.getValue());
            id = Integer.valueOf(Splitter.on("-").splitToList(decrypt).get(0));
            log.info("解析出已登录的id为" + id);
        }
        return id;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody BlogUser blogUser, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        BlogUser loginBlogUser = blogService.login(blogUser);
        // 登录成功 将账号id加密后写入cookie
        log.info("[" + loginBlogUser.getUsername() + "] 登录成功, " + request.getHeader("host"));
        String encrypt = EncryptUtils.aesEncrypt(loginBlogUser.getId() + "-" + System.currentTimeMillis());
        log.info("加密信息为：" + encrypt);
        Cookie loginCookie = new Cookie(LOGIN_COOKIE, encrypt);
        loginCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(loginCookie);
        return ok().body(loginBlogUser).build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody BlogUser blogUser) {
        blogService.register(blogUser);
        return ok().build();
    }

    @GetMapping("/user/list")
    public ResponseEntity getUserList(){
        List<BlogUser> list = blogService.getUserList();
//        int i = 1/0;
        return ok().body(list).build();
    }

    @GetMapping("/user")
    public ResponseEntity getUserInfo(Integer userId){
        BlogUser userInfo = blogService.getUserInfo(userId);
        return ok().body(userInfo).build();
    }

    /**
     * 自动登陆
     *
     * @param request
     * @return
     */
    @GetMapping("/autoLogin")
    public ResponseEntity autoLogin(HttpServletRequest request) {
        Integer loginId = this.getLoginId(request);
        AppAssert.notNull(loginId, "未登录");
        BlogUser blogUser = blogService.autoLogin(loginId);
        return ok().body(blogUser).build();
    }


    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        Cookie loginCookie = new Cookie(LOGIN_COOKIE, "0");
        loginCookie.setMaxAge(0);
        response.addCookie(loginCookie);
        return ok().build();
    }

    @GetMapping("/collection")
    public ResponseEntity getCollectionByUserId(Integer userId) {
        List<BlogCollection> collectionList = blogService.getCollectionByUserId(userId);
        return ok().body(collectionList).build();
    }

    @PostMapping("/collection")
    public ResponseEntity addCollection(HttpServletRequest request, @RequestBody BlogCollection blogCollection) {
        Integer loginId = this.getLoginId(request);
        AppAssert.notNull(loginId, "未登录");
        blogCollection.setUserId(loginId);
        blogService.addCollection(blogCollection);
        return ok().build();
    }


    @PostMapping("/article")
    public ResponseEntity addArticle(HttpServletRequest request,@RequestBody BlogArticle blogArticle) {
        Integer loginId = this.getLoginId(request);
        AppAssert.notNull(loginId, "未登录");
        blogService.addArticle(blogArticle);
        return ok().build();
    }

    @GetMapping("/article/list")
    public ResponseEntity getAllArticle(Integer pageNum, Integer userId, Integer collectionId) {
        PageInfo<Map> pageInfo = blogService.getArticleList(pageNum, userId, collectionId);
        return ok().body(pageInfo).build();
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity getArticleDetail(@PathVariable("articleId") Integer articleId) {
        Map<String, Object> articleDetail = blogService.getArticleDetail(articleId);
        return ok().body(articleDetail).build();
    }

    @PostMapping("/imgUpload")
    public ResponseEntity imgUpload(MultipartFile img) throws IOException {
        log.info("图片上传成功");
        String originalFilename = RandomStringUtils.randomAlphabetic(8) + img.getOriginalFilename();
        File fileDir = new File("img/");
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        File file = new File(fileDir,originalFilename);
        FileCopyUtils.copy(img.getBytes(), file );
        //这里只做返回值测试用，url 参数为图片上传后访问地址。具体根据功能进行修改}
        return ok().body(ImmutableMap.of("url","http://localhost:8888/img/" + originalFilename)).build();

    }
    @GetMapping("/test/updateAll")
    public ResponseEntity testUpdateAll() {
        blogService.testUpdateAll();
        Map<String, String> map = ImmutableMap.of("status", "ok");
        return ok().body(map).build();
    }
}
