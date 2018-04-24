package com.tony.billing.filters;

import com.tony.billing.filters.wapper.TokenServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author by TonyJiang on 2017/7/2.
 */
@Order(1)
@WebFilter(filterName = "filterDemo", urlPatterns = "/*")
public class FilterDemo extends OncePerRequestFilter {

    private static final String multipartContent = "multipart/form-data";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if (StringUtils.equals(httpServletRequest.getContentType(), multipartContent)) {
            multipartResolver.resolveMultipart(httpServletRequest);
        }

        TokenServletRequestWrapper request = new TokenServletRequestWrapper(httpServletRequest);
//        synchronized (this) { // 在并发访问的时候过滤器链处理请求容易导致并发问题
        filterChain.doFilter(request, httpServletResponse);
//        }

    }
}
