package com.osetrova.project.converter.userconverter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.Admin;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.enumonly.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
public class UserToUserDetailsConverterTest {

    @Autowired
    public UserToUserDetailsConverter converter;

    @Test
    public void checkConvert() {
        User admin = new Admin("userDetailsConverterAdmin", "password", "ab@email.email", Role.ADMIN, 35);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username("userDetailsConverterAdmin")
                .password("password")
                .authorities(Role.ADMIN.toString())
                .build();

        UserDetails convert = converter.convert(admin);
        assertEquals(convert, userDetails);
    }
}
