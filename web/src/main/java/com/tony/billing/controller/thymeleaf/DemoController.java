package com.tony.billing.controller.thymeleaf;

import com.tony.billing.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jiangwj20966 2018/3/14
 */
@Controller
@RequestMapping("/thymeleaf/demo")
public class DemoController extends BaseController {

    @RequestMapping("/fragment")
    public String fragmentDemo(Model model){
        return "/thymeleaf/demo/fragment_demo";
    }
}
