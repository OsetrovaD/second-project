package com.osetrova.project.controller.usercontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @GetMapping("/user-info")
    public String getUserInfo(Model model, HttpServletRequest request) {
        return "user-info";
    }
}
