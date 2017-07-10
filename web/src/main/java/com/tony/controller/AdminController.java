package com.tony.controller;

import com.tony.entity.Admin;
import com.tony.request.AdminLoginRequest;
import com.tony.request.AdminRegisterRequest;
import com.tony.request.BaseRequest;
import com.tony.response.BaseResponse;
import com.tony.service.AdminService;
import com.tony.util.AuthUtil;
import com.tony.util.CodeGeneratorUtil;
import com.tony.util.Md5Util;
import com.tony.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
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

    @RequestMapping(value = "/user/register/put", method = RequestMethod.POST)
    public BaseResponse register(@ModelAttribute("request") AdminRegisterRequest registerRequest) {
        BaseResponse response = new BaseResponse();
        try {
            if (StringUtils.isEmpty(registerRequest.getPassword())
                    || StringUtils.isEmpty(registerRequest.getUserName())) {
                return ResponseUtil.paramError(response);
            }
            Admin admin = new Admin();
            admin.setUserName(registerRequest.getUserName());
            admin.setPassword(Md5Util.md5(registerRequest.getPassword()));
            admin.setCode(CodeGeneratorUtil.getCode(20));
            Long flag = 0L;
            if ((flag = adminService.register(admin)) > 0) {
                ResponseUtil.success(response);
            } else {
                ResponseUtil.error(response);
                if (flag.equals(-2L)) {
                    response.setMsg("账号已存在");
                }
            }
        } catch (Exception e) {
            logger.error("/user/register/put error", e);
            ResponseUtil.sysError(response);
        }
        return response;
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public BaseResponse logout(@ModelAttribute("request") BaseRequest request) {
        BaseResponse response = new BaseResponse();
        if (adminService.logout(request.getUserId()) > 0) {
            return ResponseUtil.success(response);
        } else {
            return ResponseUtil.error(response);
        }
    }

}
