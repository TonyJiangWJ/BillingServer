package com.tony.controller;

import com.tony.entity.Admin;
import com.tony.request.AdminLoginRequest;
import com.tony.response.BaseResponse;
import com.tony.service.AdminService;
import com.tony.util.AuthUtil;
import com.tony.util.Md5Util;
import com.tony.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Author by TonyJiang on 2017/7/2.
 */
@RestController
@RequestMapping("/bootDemo")
public class AdminController extends BaseController {

    @Resource
    private AdminService adminService;

    @RequestMapping("/user/login")
    public BaseResponse login(@ModelAttribute("request") AdminLoginRequest request, HttpServletResponse httpServletResponse) {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isEmpty(request.getUserName())
                || StringUtils.isEmpty(request.getPassword())) {
            return ResponseUtil.paramError(response);
        }
        try {
            Admin loginAdmin = new Admin();
            loginAdmin.setUserName(request.getUserName());
            loginAdmin.setPassword(Md5Util.md5(request.getPassword()));
            Admin admin = adminService.login(loginAdmin);
            if (admin != null) {
                AuthUtil.setCookieToken(admin.getTokenId(), httpServletResponse);
                ResponseUtil.success(response);
            } else {
                ResponseUtil.error(response);
            }
        } catch (Exception e) {
            logger.error("/user/login error", e);
            ResponseUtil.sysError(response);
        }
        return response;
    }
}
