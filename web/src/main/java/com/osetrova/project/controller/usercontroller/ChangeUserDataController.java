package com.osetrova.project.controller.usercontroller;

import com.osetrova.project.exception.DaoException;
import com.osetrova.project.service.userservice.UserService;
import com.osetrova.project.servicedto.UserChangeDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChangeUserDataController {

    private UserService userService;

    @Autowired
    public ChangeUserDataController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/change-data")
    public String getChangeDataPage(Model model) {
        model.addAttribute("userData", new UserChangeDataDto());

        return "change-data";
    }

    @PostMapping("/change-data")
    public String changeData(UserChangeDataDto userData) {
        userService.changeData(userData, getUsernameFromContext());

        return "redirect:/user-info";
    }

    @GetMapping("/change-first-name")
    public String getChangeFirstNamePage() {
        return "change-first-name";
    }


    @GetMapping("/change-last-name")
    public String getChangeLastNamePage() {
        return "change-last-name";
    }

    @GetMapping("/change-email")
    public String getChangeEmailPage() {
        return "change-email";
    }

    @GetMapping("/change-phone-number")
    public String getChangePhoneNumberPage() {
        return "change-phone-number";
    }

    @GetMapping("/change-address")
    public String getChangeAddressPage() {
        return "change-address";
    }

    @PostMapping("/change-user-data")
    public String changeFirstName(@RequestParam("param") String param, String paramValue) {

        switch (param) {
            case "firstName":
                userService.changeFirstName(getUsernameFromContext(), paramValue);
                break;
            case "lastName":
                userService.changeLastName(getUsernameFromContext(), paramValue);
                break;
            case "email":
                userService.changeEmail(getUsernameFromContext(), paramValue);
                break;
            case "phoneNumber":
                userService.changePhoneNumber(getUsernameFromContext(), paramValue);
                break;
            case "address":
                userService.changeAddress(getUsernameFromContext(), paramValue);
                break;
            default:
                throw new DaoException();
        }

        return "redirect:/user-info";
    }

    private String getUsernameFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return user.getUsername();
    }
}
