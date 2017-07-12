package com.tony.billing.util;

import com.tony.billing.response.BaseResponse;

/**
 * Author jiangwj20966 on 2017/6/2.
 */
public class ResponseUtil {
    private static final String CODE_SUCCESS = "0001";
    private static final String CODE_PARAM_ERROR = "0002";
    private static final String CODE_SYS_ERROR = "0003";
    private static final String CODE_DATA_NOT_EXIST = "0004";
    private static final String CODE_ERROR = "0005";
    private static final String CODE_LOGIN_VERIFY = "0006";

    private static final String MSG_SUCCESS = "成功";
    private static final String MSG_PARAM_ERROR = "参数错误";
    private static final String MSG_SYS_ERROR = "系统异常";
    private static final String MSG_DATA_NOT_EXIST = "数据不存在";
    private static final String MSG_ERROR = "失败";
    private static final String MSG_LOGIN_VERIFY = "not login";


    public static BaseResponse sysError(BaseResponse response) {
        response.setCode(CODE_SYS_ERROR);
        response.setMsg(MSG_SYS_ERROR);
        return response;
    }

    public static BaseResponse success(BaseResponse response) {
        response.setCode(CODE_SUCCESS);
        response.setMsg(MSG_SUCCESS);
        return response;
    }

    public static BaseResponse paramError(BaseResponse response) {
        response.setCode(CODE_PARAM_ERROR);
        response.setMsg(MSG_PARAM_ERROR);
        return response;
    }

    public static BaseResponse dataNotExisting(BaseResponse response) {
        response.setMsg(MSG_DATA_NOT_EXIST);
        response.setCode(CODE_DATA_NOT_EXIST);
        return response;
    }

    public static BaseResponse error(BaseResponse response) {
        response.setMsg(MSG_ERROR);
        response.setCode(CODE_ERROR);
        return response;
    }

    public static BaseResponse loginError() {
        BaseResponse response = new BaseResponse();
        response.setCode(CODE_LOGIN_VERIFY);
        response.setMsg(MSG_LOGIN_VERIFY);
        return response;
    }
}
