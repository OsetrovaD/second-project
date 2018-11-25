package com.osetrova.project.controller.exceptioncontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedExceptionController {

    @GetMapping("/access-denied")
    public String getErrorPage() {
       return "access-denied";
    }
}
