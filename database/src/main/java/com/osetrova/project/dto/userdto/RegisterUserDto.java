package com.osetrova.project.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {

    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;
}
