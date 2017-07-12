package com.tony.billing.request.wapper;

import org.apache.catalina.util.ParameterMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

/**
 * Author by TonyJiang on 2017/7/2.
 */
public class TokenServletRequest extends HttpServletRequestWrapper {

    private static Map<String, String[]> params;

    public TokenServletRequest(HttpServletRequest request) {
        super(request);
        params = new ParameterMap<>();
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        params.putAll(super.getParameterMap());
        return params;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    public void addParameter(String name, String... value) {
        params.put(name, value);
    }

}
