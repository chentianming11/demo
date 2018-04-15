package com.lianjia.filter;

import com.lianjia.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author huang.peijie
 * @since 2016/10/14 09:17
 */
@WebFilter(filterName = "logFilter2",urlPatterns = {"/*"})
@Slf4j
public class LogFilter implements Filter {

    static final Set<String> ignoreURIs = new HashSet<>();

    static {
        ignoreURIs.add("/it/ping");
        ignoreURIs.add("/api/it/ping");
        ignoreURIs.add("/favicon.ico");
        ignoreURIs.add("/v1/td/oneTimeIcon/redis/isExist");
        ignoreURIs.add("/v1/td/oneTimeIcon/isExist");
        ignoreURIs.add("/v1/decryptPhone");
        ignoreURIs.add("/v1/decryptPhoneView");
        ignoreURIs.add("/v1/encryptPhone");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (response.getStatus() != 200 || !ignoreURIs.contains(request.getRequestURI())) {
                StringBuilder logBuff = new StringBuilder()
                        .append(request.getMethod())
                        .append("[")
                        .append(response.getStatus())
                        .append("] - ")
                        .append(request.getRequestURI())
                        .append(",耗时")
                        .append(System.currentTimeMillis() - startTime)
                        .append("ms");
                String appExceptionMessage = (String) request.getAttribute(AppException.class.getCanonicalName() + ".message");
                if (appExceptionMessage != null) {
                    logBuff.append(" - AppException:").append(appExceptionMessage);
                }
                log.info(logBuff.toString());
            }
        }
    }

    @Override
    public void destroy() {

    }
}
