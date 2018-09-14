package com.yao.express.service.user.webcontroller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebAdminController implements ErrorController {

    @RequestMapping(value={"/", "/index", "/home"})
    public String index() {
        return "index.html";
    }

    @RequestMapping(value={"/app", "/client"})
    public String client() {
        return "redirect:client/index.html";
    }

    @RequestMapping("/error")
    public String error() {
        return "redirect:"+getErrorPath();
    }

    @Override
    public String getErrorPath() {
        return "/#/404";
    }
}
