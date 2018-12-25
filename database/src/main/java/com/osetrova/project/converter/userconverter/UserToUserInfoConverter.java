package com.osetrova.project.converter.userconverter;

import com.osetrova.project.converter.Converter;
import com.osetrova.project.dto.userdto.UserInfoDto;
import com.osetrova.project.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserInfoConverter implements Converter<UserInfoDto, User> {

    @Override
    public UserInfoDto convert(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }
}
