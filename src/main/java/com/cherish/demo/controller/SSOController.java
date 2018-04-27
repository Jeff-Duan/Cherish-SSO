package com.cherish.demo.controller;

import com.cherish.demo.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/sso/")
public class SSOController {

    @Autowired
    SSOService SSOService;

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "/save")
    public String save(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "password", required = false) String password, HttpSession session) {
        //登录验证
        String result = SSOService.login(name, password, session);
        if ("SUCCESS".equals(result)) {
            return "save";
        } else {
            return "login";
        }
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        String callBackUrl = (String) session.getAttribute("CallBackUrl");
        session.invalidate();
        return "redirect:/sso/login?callBackUrl=" + callBackUrl;
    }

    @GetMapping(value = "/callBack")
    public String callback(HttpSession session) {
        String callBackUrl = (String) session.getAttribute("CallBackUrl");
        return "redirect:" + callBackUrl;
    }

}
