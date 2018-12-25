package com.osetrova.project.service.userservice;

import com.osetrova.project.dto.userdto.AdminSalaryAndIdDto;
import com.osetrova.project.dto.userdto.RegisterUserDto;
import com.osetrova.project.dto.userdto.UserInfoDto;
import com.osetrova.project.dto.userdto.userforadmin.UserInfoForAdminDto;
import com.osetrova.project.entity.Admin;
import com.osetrova.project.entity.SimpleUser;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.enumonly.Role;
import com.osetrova.project.jparepository.UserRepository;
import com.osetrova.project.serviceconfig.ServiceConfiguration;
import com.osetrova.project.servicedto.UserChangeDataDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkLoadUserByUsername() {
        User admin = new Admin("userServiceTestFirstUser", "$2a$10$bx8mfPPdDrR3WCGD/4pHHOYCIs6ksCZ3uEIESsVnGWQEykQpKrI1W",
                "nb@email.email", Role.ADMIN, 100);
        userRepository.save(admin);

        UserDetails userDetails = userService.loadUserByUsername("userServiceTestFirstUser");
        assertNotNull(userDetails);
    }

    @Test
    public void checkSave() {
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .login("userServiceTestSecondUser")
                .password("password")
                .email("wasd@mail.mail")
                .firstName("first name")
                .build();
        User registeredUser = userService.save(registerUserDto);
        assertNotNull(registeredUser);
    }

    @Test
    public void checkGetUserInfo() {
        User simpleUser = new SimpleUser("userServiceTestThirdUser", "password", "firstName", "lastName",
                "111100","address", "mk@mail.mail", Role.USER, LocalDate.of(2018, 4, 16));
        User savedUser = userRepository.save(simpleUser);

        UserInfoDto userInfo = userService.getUserInfo(savedUser.getId());
        assertNotNull(userInfo);
    }

    @Test
    public void checkGetUserInfoByUsername() {
        User simpleUser = new SimpleUser("userServiceTestFourthUser", "password", "firstName", "lastName",
                "111100","address", "as@mail.mail", Role.USER, LocalDate.of(2018, 4, 16));
        userRepository.save(simpleUser);

        UserInfoDto userInfo = userService.getUserInfo("userServiceTestFourthUser");
        assertNotNull(userInfo);
    }

    @Test
    public void checkFindAllByRoles() {
        User firstSimpleUser = new SimpleUser("userServiceTestFifthUser", "password", "firstName", "lastName",
                "111100","address", "aaaaa@mail.mail", Role.USER, LocalDate.of(2018, 4, 16));
        userRepository.save(firstSimpleUser);

        User secondSimpleUser = new SimpleUser("userServiceTestSixthUser", "password", "firstName", "lastName",
                "111100","address", "bbbbbb@mail.mail", Role.USER, LocalDate.of(2018, 4, 16));
        userRepository.save(secondSimpleUser);

        List<UserInfoForAdminDto> byRoles = userService.findAllByRoles(Collections.singletonList(Role.USER));
        assertThat(byRoles, hasSize(2));
    }

    @Test
    public void checkChangeRoleToUser() {
        User admin = new Admin("userServiceTestSeventhUser", "password",
                "ccccccc@email.email", Role.ADMIN, 100);
        User savedUser = userRepository.save(admin);

        int changeRoleToUser = userService.changeRoleToUser(savedUser.getId());
        assertEquals(1, changeRoleToUser);
    }

    @Test
    public void checkChangeRoleToAdmin() {
        User simpleUser = new SimpleUser("userServiceTestEighthUser", "password", "ddddd@email.email",
                Role.USER, LocalDate.of(2018, 4, 7));
        User savedUser = userRepository.save(simpleUser);

        int changeRoleToAdmin = userService.changeRoleToAdmin(AdminSalaryAndIdDto.of(savedUser.getId(), 40));
        assertEquals(1, changeRoleToAdmin);
    }

    @Test
    public void checkChangeSalary() {
        User admin = new Admin("userServiceTest9User", "password",
                "eeeeee@email.email", Role.ADMIN, 100);
        User savedUser = userRepository.save(admin);

        int changeSalary = userService.changeSalary(AdminSalaryAndIdDto.of(savedUser.getId(), 40));
        assertEquals(1, changeSalary);
    }

    @Test
    public void checkChangeData() {
        User admin = new Admin("userServiceTest10User", "password",
                "ffffff@email.email", Role.ADMIN, 100);
        User savedUser = userRepository.save(admin);

        UserChangeDataDto changeDataDto = UserChangeDataDto.builder()
                .address("address")
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("121141545")
                .build();

        userService.changeData(changeDataDto, savedUser.getLogin());
        manager.flush();
        manager.detach(admin);

        UserInfoDto userInfo = userService.getUserInfo(savedUser.getLogin());
        assertEquals("address", userInfo.getAddress());
        assertEquals("firstName", userInfo.getFirstName());
        assertEquals("lastName", userInfo.getLastName());
        assertEquals("121141545", userInfo.getPhoneNumber());
    }

    @Test
    public void checkChangeFirstName() {
        User admin = new Admin("userServiceTest11User", "password",
                "gggg@email.email", Role.ADMIN, 100);
        userRepository.save(admin);

        int changeFirstName = userService.changeFirstName("userServiceTest11User", "firstName");
        assertEquals(1, changeFirstName);
    }

    @Test
    public void checkChangeLastName() {
        User admin = new Admin("userServiceTest12User", "password",
                "hhhhh@email.email", Role.ADMIN, 100);
        userRepository.save(admin);

        int changeLastName = userService.changeLastName("userServiceTest12User", "lastName");
        assertEquals(1, changeLastName);
    }

    @Test
    public void checkChangeEmail() {
        User admin = new Admin("userServiceTest13User", "password",
                "jjjjj@email.email", Role.ADMIN, 100);
        userRepository.save(admin);

        int changeEmail = userService.changeEmail("userServiceTest13User", "jjj@email.email");
        assertEquals(1, changeEmail);
    }

    @Test
    public void checkChangeAddress() {
        User admin = new Admin("userServiceTest14User", "password",
                "iiiiii@email.email", Role.ADMIN, 100);
        userRepository.save(admin);

        int changeAddress = userService.changeAddress("userServiceTest14User", "address");
        assertEquals(1, changeAddress);
    }

    @Test
    public void checkChangePhoneNumber() {
        User admin = new Admin("userServiceTest15User", "password",
                "kkkkkkk@email.email", Role.ADMIN, 100);
        userRepository.save(admin);

        int changePhoneNumber = userService.changePhoneNumber("userServiceTest15User", "4447777");
        assertEquals(1, changePhoneNumber);
    }

    @Test
    public void checkChangeLastVisitDate() {
        User simpleUser = new SimpleUser("userServiceTest16User", "password", "ddddd@email.email",
                Role.USER, LocalDate.of(2018, 4, 7));
        userRepository.save(simpleUser);

        int changeLastVisitDate = userService.changeLastVisitDate("userServiceTest16User");
        assertEquals(1, changeLastVisitDate);
    }
}
