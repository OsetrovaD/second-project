package com.osetrova.project.converter.userconverter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.userdto.UserInfoDto;
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
public class UserToUserInfoConverterTest {

    @Autowired
    private UserToUserInfoConverter converter;

    @Test
    public void checkConvert() {
        User simpleUser = new SimpleUser("converterFirstSimpleUser", "password", "firstName", "lastName",
                "111100","address", "qw@mail.mail", Role.USER, LocalDate.of(2018, 5, 16));
        simpleUser.setId(3L);

        UserInfoDto infoDto = UserInfoDto.builder()
                .id(3L)
                .role(Role.USER)
                .address("address")
                .email("qw@mail.mail")
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("111100")
                .build();
        UserInfoDto convert = converter.convert(simpleUser);
        assertEquals(convert, infoDto);
    }
}
