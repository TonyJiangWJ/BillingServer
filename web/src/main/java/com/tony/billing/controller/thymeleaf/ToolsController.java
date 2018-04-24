package com.tony.billing.controller.thymeleaf;

import com.tony.billing.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jiangwj20966 2018/4/24
 */

@Controller
@RequestMapping("/thymeleaf")
public class ToolsController extends BaseController {

    @RequestMapping("/tools")
    public String tools() {
        return "/thymeleaf/tools/tools";
    }
}
