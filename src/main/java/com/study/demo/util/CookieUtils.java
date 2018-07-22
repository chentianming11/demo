package com.study.demo.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class CookieUtils {

    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (Objects.equals(name, cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }
}