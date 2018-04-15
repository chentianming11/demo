package com.lianjia.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

/**
 * Created by chen on 2018/4/6.
 */
@Slf4j
@WebFilter(filterName = "myFilter",urlPatterns = {"/*"})
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        long end = System.currentTimeMillis();
        String msg = String.format("%s[%s] - %s,耗时%dms", httpServletRequest.getMethod(), httpServletResponse.getStatus(), httpServletRequest.getRequestURI(), end - start);
        log.info(msg);
    }

    @Override
    public void destroy() {

    }
}
