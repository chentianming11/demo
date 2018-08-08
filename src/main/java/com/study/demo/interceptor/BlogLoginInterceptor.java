/*
package com.study.demo.interceptor;

import com.google.common.base.Splitter;
import com.study.demo.exception.AppException;
import com.study.demo.util.CookieUtils;
import com.study.demo.util.EncryptUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

*/
/**
 * Created by chenTianMing on 2018/6/3.
 *//*

@Component
@Slf4j
public class BlogLoginInterceptor extends HandlerInterceptorAdapter {

    EncryptUtils.AES aes = EncryptUtils.getAES("BLOG_USER_ENCRYPT_KEY_24456");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie login_cookie = CookieUtils.getCookieByName(request, "login_cookie");
        return Optional.ofNullable(login_cookie)
                .map(cookie -> {
                    try {
                        // 已登录
                        String decrypt = aes.decrypt(cookie.getValue());
                        Integer id = Integer.valueOf(Splitter.on("-").splitToList(decrypt).get(0));
                        log.info("解析出已登录的id为" + id);
                        request.setAttribute("loginId", id);
                        return true;
                    } catch (Exception e) {
                        // 异常 -- 重定向
                        log.error("解析异常", e);
                        returnData(response);
                        return false;
                    }

                })
                .orElseGet(() -> {
                    // 未登录 --》 重定向
                    returnData(response);
                    return false;
                });

    }

    @SneakyThrows
    private void returnData(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
//        response.sendRedirect("/");

        throw new AppException("未登录");
    }

}
*/
