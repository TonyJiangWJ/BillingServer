package com.tony.billing.resolver;

import com.alibaba.fastjson.JSON;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: jiangwj20966 on 2018/1/25
 */
@Component
public class ExceptionResolver implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.error("biz error:", e);
        BaseResponse baseResponse = ResponseUtil.sysError(new BaseResponse());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            httpServletResponse.getWriter().write(JSON.toJSONString(baseResponse));
        } catch (IOException e1) {
            logger.error("与客户端通讯异常：" + e.getMessage(), e);
        }

        return new ModelAndView();
    }
}
