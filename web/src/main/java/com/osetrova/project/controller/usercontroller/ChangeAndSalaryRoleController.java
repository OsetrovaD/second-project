package com.osetrova.project.controller.usercontroller;

import com.osetrova.project.dto.userdto.AdminSalaryAndIdDto;
import com.osetrova.project.dto.userdto.UserInfoDto;
import com.osetrova.project.entity.enumonly.Role;
import com.osetrova.project.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChangeAndSalaryRoleController {

    private UserService userService;

    @Autowired
    public ChangeAndSalaryRoleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/change-role-to-admin")
    public String getSetSalaryPage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("adminDetails", AdminSalaryAndIdDto.builder()
                .id(id)
                .build());

        return "set-salary";
    }

    @GetMapping("/change-role-to-user")
    public String changeRoleToUser(@RequestParam("id") Long id) {
        userService.changeRoleToUser(id);

        return "redirect:/all-users";
    }

    @GetMapping("/change-salary")
    public String changeSalary(@RequestParam("id") Long id, Model model) {
        return getSetSalaryPage(id, model);
    }

    @PostMapping("/set-salary")
    public String setData(AdminSalaryAndIdDto adminDetails) {
        UserInfoDto userInfo = userService.getUserInfo(adminDetails.getId());

        if (Role.ADMIN == userInfo.getRole()) {
            userService.changeSalary(adminDetails);
        } else {
            userService.changeRoleToAdmin(adminDetails);
        }

        return "redirect:/all-users";
    }
}
