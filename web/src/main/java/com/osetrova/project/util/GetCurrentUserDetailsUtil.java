package com.osetrova.project.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class GetCurrentUserDetailsUtil {

    public static UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (UserDetails) authentication.getPrincipal();
    }
}
