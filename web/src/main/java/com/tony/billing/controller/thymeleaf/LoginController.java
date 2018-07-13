package com.tony.billing.controller.thymeleaf;

import com.tony.billing.controller.BaseController;
import com.tony.billing.entity.Admin;
import com.tony.billing.request.admin.AdminLoginRequest;
import com.tony.billing.service.AdminService;
import com.tony.billing.util.AuthUtil;
import com.tony.billing.util.CookieUtil;
import com.tony.billing.util.RedisUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * </p>
 * <li></li>
 *
 * @author jiangwj20966 2018/2/27
 */
@Deprecated
@Controller
@RequestMapping("/thymeleaf")
public class LoginController extends BaseController {

    @Resource
    private AdminService adminService;
    @Resource
    private AuthUtil authUtil;

    @RequestMapping(value = "/login")
    public String login(Model model, HttpServletRequest httpServletRequest) {
        Cookie tokenCookie = CookieUtil.getCookie("token", httpServletRequest);
        try {
            String tokenId;
            if (tokenCookie != null && ((tokenId = authUtil.getUserTokenId(tokenCookie.getValue())) != null)) {
                Map store = RedisUtils.get(tokenId, Admin.class);
                if (store != null) {
                    return "/thymeleaf/login/success";
                }
            }
        } catch (Exception e) {
            logger.error("校验token信息失败", e);
        }
        return "/thymeleaf/login/login";
    }

    @RequestMapping(value = "/login/put", method = RequestMethod.POST)
    public String doLogin(Model model, @ModelAttribute("request") AdminLoginRequest request,
                          HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse) {
        try {
            Admin loginAdmin = new Admin();
            loginAdmin.setUserName(request.getUserName());
            loginAdmin.setPassword(request.getPassword());

            Admin admin = adminService.login(loginAdmin);
            if (admin != null) {
                authUtil.setCookieToken(admin.getTokenId(), httpServletResponse);
                return "/thymeleaf/login/success";
            }
        } catch (Exception e) {
            logger.error("/user/login error", e);
        }
        model.addAttribute("loginError", "账号或密码错误");
        return "/thymeleaf/login/login";
    }
}
