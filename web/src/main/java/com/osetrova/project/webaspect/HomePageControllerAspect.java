package com.osetrova.project.webaspect;

import com.osetrova.project.service.userservice.UserService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class HomePageControllerAspect {

    private UserService userService;
    private Map<String, LocalDate> dates = new HashMap<>();

    @Autowired
    public HomePageControllerAspect(UserService userService) {
        this.userService = userService;
    }

    @Pointcut("execution(* com.osetrova.project.controller.HomePageController.getHomePage(..))")
    public void authenticatedUser() {}

    @AfterReturning("authenticatedUser()")
    public void changeLastVisitDate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();

        if (user.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            if (!dates.containsKey(user.getUsername()) || LocalDate.now().isAfter(dates.get(user.getUsername()))) {
                userService.changeLastVisitDate(user.getUsername());
                dates.put(user.getUsername(), LocalDate.now());
            }
        }
    }
}
