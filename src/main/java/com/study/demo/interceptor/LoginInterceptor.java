package com.study.demo.interceptor;

import com.google.common.base.Splitter;
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

/**
 * Created by chenTianMing on 2018/6/3.
 */
@Component
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie login_cookie = CookieUtils.getCookieByName(request, "login_cookie");
        return Optional.ofNullable(login_cookie)
                .map(cookie -> {
                    try {
                        // 已登录
                        String decrypt = EncryptUtils.aesDecrypt(cookie.getValue());
                        Integer id = Integer.valueOf(Splitter.on("-").splitToList(decrypt).get(0));
                        log.info("解析出已登录的id为" + id);
                        request.setAttribute("loginId", id);
                        return true;
                    } catch (Exception e) {
                        // 异常 -- 重定向
                        this.redirect(response);
                        return false;
                    }

                })
                .orElseGet(() -> {
                    // 未登录 --》 重定向
                    this.redirect(response);
                    return false;
                });

    }

    @SneakyThrows
    private void redirect(HttpServletResponse response) {
        log.info("重定向登录页面");
        response.getOutputStream().print("redirect to login page");
    }
}
