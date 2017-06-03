package com.tony.util;

import com.tony.response.BaseResponse;

/**
 * Author jiangwj20966 on 2017/6/2.
 */
public class ResponseUtil {
    public static BaseResponse sysError(BaseResponse response) {
        response.setCode("0003");
        response.setMsg("系统异常");
        return response;
    }

    public static BaseResponse success(BaseResponse response) {
        response.setCode("0001");
        response.setMsg("成功");
        return response;
    }

    public static BaseResponse paramError(BaseResponse response) {
        response.setCode("0002");
        response.setMsg("参数错误");
        return response;
    }

    public static BaseResponse dataNotExisting(BaseResponse response) {
        response.setMsg("数据不存在");
        response.setCode("0004");
        return response;
    }

    public static BaseResponse error(BaseResponse response) {
        response.setMsg("失败");
        response.setCode("0005");
        return response;
    }
}
