package com.osetrova.project.service.userservice;

import com.osetrova.project.dto.userdto.AdminSalaryAndIdDto;
import com.osetrova.project.dto.userdto.RegisterUserDto;
import com.osetrova.project.dto.userdto.UserInfoDto;
import com.osetrova.project.dto.userdto.userforadmin.UserInfoForAdminDto;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.enumonly.Role;
import com.osetrova.project.servicedto.UserChangeDataDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User save(RegisterUserDto userInfo);

    UserInfoDto getUserInfo(String login);

    UserInfoDto getUserInfo(Long id);

    List<UserInfoForAdminDto> findAllByRoles(List<Role> roles);

    int changeRoleToUser(Long userId);

    int changeRoleToAdmin(AdminSalaryAndIdDto adminDetails);

    int changeSalary(AdminSalaryAndIdDto adminDetails);

    User changeData(UserChangeDataDto userInfo, String login);

    int changeFirstName(String login, String firstName);

    int changeLastName(String login, String lastName);

    int changeEmail(String login, String email);

    int changePhoneNumber(String login, String phoneNumber);

    int changeAddress(String login, String address);

    int changeLastVisitDate(String login);
}
