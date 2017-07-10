package com.tony.filters;

import com.tony.request.wapper.TokenServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Author by TonyJiang on 2017/7/2.
 */
@Order(0)
@WebFilter(filterName = "filterDemo", urlPatterns = "/*")
public class FilterDemo implements Filter {
    Logger logger = LoggerFactory.getLogger(FilterDemo.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        Cookie cookie = CookieUtil.getCookie("token", (HttpServletRequest) servletRequest);
//        if (cookie != null) {
//            servletRequest.setAttribute("tokenId", cookie.getValue());
//        }else {
//            logger.info("cookie for token is null");
//        }
        if (servletRequest instanceof HttpServletRequest) {
            TokenServletRequest request = new TokenServletRequest((HttpServletRequest) servletRequest);
            request.addParameter("fuck", "you");
            filterChain.doFilter(request, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
