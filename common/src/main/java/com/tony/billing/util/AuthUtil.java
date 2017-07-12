package com.tony.billing.util;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.tony.billing.util.JavaWebToken.verifyJavaWebToken;

/**
 * Author by TonyJiang on 2017/7/1.
 */
public class AuthUtil {
    private static Map<String, Object> getClientLoginInfo(String token) throws Exception {
        Map<String, Object> r;

        if (token != null) {
            r = decodeSession(token);
            return r;
        }
        throw new Exception("token解析错误");
    }

    public static String getUserTokenId(String token) throws Exception {
        return (String) getClientLoginInfo(token).get("tokenId");
    }

    public static void setCookieToken(String tokenId, HttpServletResponse response) {
        Map<String, Object> param = new HashMap<>();
        param.put("tokenId", tokenId);
        String token = JavaWebToken.createJavaWebToken(param);
        CookieUtil.setCookie(response, "token", token);
    }

    /**
     * session解密
     */
    public static Map<String, Object> decodeSession(String token) {
        try {
            return verifyJavaWebToken(token);
        } catch (Exception e) {
            System.err.println("");
            return null;
        }
    }
}
