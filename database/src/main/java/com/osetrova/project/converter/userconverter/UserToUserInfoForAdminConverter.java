package com.osetrova.project.converter.userconverter;

import com.osetrova.project.converter.Converter;
import com.osetrova.project.dto.userdto.userforadmin.AdminInfoDto;
import com.osetrova.project.dto.userdto.userforadmin.SimpleUserInfoDto;
import com.osetrova.project.entity.Admin;
import com.osetrova.project.entity.SimpleUser;
import com.osetrova.project.entity.User;
import com.osetrova.project.dto.userdto.userforadmin.UserInfoForAdminDto;
import org.springframework.stereotype.Component;

@Component
public class UserToUserInfoForAdminConverter implements Converter<UserInfoForAdminDto, User> {

    @Override
    public UserInfoForAdminDto convert(User user) {
        UserInfoForAdminDto userInfo;

        if (user instanceof SimpleUser) {
            SimpleUser simpleUser = (SimpleUser) user;
            userInfo = new SimpleUserInfoDto(simpleUser.getId(), simpleUser.getLogin(), simpleUser.getFirstName(),
                    simpleUser.getLastName(), simpleUser.getPhoneNumber(), simpleUser.getAddress(),
                    simpleUser.getEmail(), simpleUser.getRole(), simpleUser.getLastVisitDate());
        } else {
            Admin admin = (Admin) user;
            userInfo = new AdminInfoDto(admin.getId(), admin.getLogin(), admin.getFirstName(),
                    admin.getLastName(), admin.getPhoneNumber(), admin.getAddress(),
                    admin.getEmail(), admin.getRole(), admin.getSalary());
        }

        return userInfo;
    }
}
