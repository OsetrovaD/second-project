package com.osetrova.project.converter.userconverter;

import com.osetrova.project.converter.Converter;
import com.osetrova.project.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDetailsConverter implements Converter<UserDetails, User> {

    @Override
    public UserDetails convert(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(user.getRole().toString())
                .build();
    }
}
