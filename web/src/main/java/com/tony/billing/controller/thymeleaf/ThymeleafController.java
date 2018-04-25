package com.tony.billing.controller.thymeleaf;

import com.tony.billing.request.BaseRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jiangwj20966 2018/2/9
 */
@Controller
public class ThymeleafController {

    @RequestMapping("/hello")
    public String hello(Model model, @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        model.addAttribute("name", name);
        return "hello";
    }

    @RequestMapping("/thymeleaf/asset")
    public String asset(Model model, @ModelAttribute("request") BaseRequest request) {
        model.addAttribute("totalLiability", 8000);
        return "/asset/asset";
    }
}
