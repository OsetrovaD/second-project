package com.osetrova.project.controller.usercontroller;

import com.osetrova.project.dto.userdto.RegisterUserDto;
import com.osetrova.project.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("registerDto", new RegisterUserDto());
        return "register-page";
    }

    @PostMapping("/register")
    public String registerNewUser(RegisterUserDto userInfo) {
        userService.save(userInfo);
        return "redirect:/login";
    }
}
