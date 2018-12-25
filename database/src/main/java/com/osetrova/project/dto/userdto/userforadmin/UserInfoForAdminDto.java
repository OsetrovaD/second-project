package com.osetrova.project.dto.userdto.userforadmin;

import com.osetrova.project.entity.enumonly.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserInfoForAdminDto {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;
    private Role role;
}
