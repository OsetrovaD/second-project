package com.osetrova.project.controller.usercontroller;

import com.osetrova.project.dto.userdto.userforadmin.AdminInfoDto;
import com.osetrova.project.dto.userdto.userforadmin.SimpleUserInfoDto;
import com.osetrova.project.entity.enumonly.Role;
import com.osetrova.project.service.userservice.UserService;
import com.osetrova.project.dto.userdto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-info")
    public String getUserInfo(Model model, @RequestParam(value = "id", required = false) Long id) {
        UserInfoDto userInfo;
        if (id != null) {
            userInfo = userService.getUserInfo(id);
        } else {
            userInfo = userService.getUserInfo(getCurrentUser().getUsername());
        }

        model.addAttribute("userInfo", userInfo);

        return "user-info";
    }

    @GetMapping("/all-users")
    public String getAllUsers(Model model) {
        List<AdminInfoDto> admins = userService.findAllByRoles(Arrays.asList(Role.ADMIN, Role.SUPER_ADMIN)).stream()
                .map(x -> (AdminInfoDto) x)
                .collect(Collectors.toList());
        List<SimpleUserInfoDto> users = userService.findAllByRoles(Collections.singletonList(Role.USER)).stream()
                .map(x -> (SimpleUserInfoDto) x)
                .collect(Collectors.toList());

        model.addAttribute("admins", admins);
        model.addAttribute("users", users);
        model.addAttribute("username", getCurrentUser().getUsername());

        return "all-users";
    }

    private UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (UserDetails) authentication.getPrincipal();
    }
}
