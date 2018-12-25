package com.osetrova.project.converter.userconverter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.userdto.userforadmin.AdminInfoDto;
import com.osetrova.project.dto.userdto.userforadmin.SimpleUserInfoDto;
import com.osetrova.project.dto.userdto.userforadmin.UserInfoForAdminDto;
import com.osetrova.project.entity.Admin;
import com.osetrova.project.entity.SimpleUser;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.enumonly.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
public class UserToUserInfoForAdminConverterTest {

    @Autowired
    private UserToUserInfoForAdminConverter converter;

    @Test
    public void checkConvertAdmin() {
        User admin = new Admin("convertAdminLogin", "password", "aw@mail.mail", Role.ADMIN, 115);
        admin.setId(1L);
        admin.setFirstName("fName");
        admin.setLastName("lName");
        admin.setPhoneNumber("121212121");
        admin.setAddress("address");

        UserInfoForAdminDto infoDto = new AdminInfoDto(1L, "convertAdminLogin", "fName",
                "lName", "121212121", "address",
                "aw@mail.mail", Role.ADMIN, 115);

        UserInfoForAdminDto convert = converter.convert(admin);

        assertEquals(convert, infoDto);
    }

    @Test
    public void checkConvertSimpleUser() {
        User simpleUser = new SimpleUser("converterSimpleUserLogin", "password", "firstName", "lastName",
                "1100000","address", "cd@mail.mail", Role.USER, LocalDate.of(2018, 5, 16));
        simpleUser.setId(22L);

        UserInfoForAdminDto infoDto = new SimpleUserInfoDto(22L, "converterSimpleUserLogin", "firstName",
                "lastName", "1100000", "address",
                "cd@mail.mail", Role.USER, LocalDate.of(2018, 5, 16));

        UserInfoForAdminDto convert = converter.convert(simpleUser);

        assertEquals(convert, infoDto);
    }
}
