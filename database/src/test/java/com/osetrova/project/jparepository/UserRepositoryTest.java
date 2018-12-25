package com.osetrova.project.jparepository;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.Admin;
import com.osetrova.project.entity.SimpleUser;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.enumonly.Role;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class UserRepositoryTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void checkFindByLogin() {
        User admin = new Admin("userTestFirstUser", "password", "l@email.email", Role.ADMIN, 100);
        User savedUser = userRepository.save(admin);

        manager.detach(admin);
        Optional<User> optionalUser = userRepository.findByLogin(savedUser.getLogin());
        assertTrue(optionalUser.isPresent());
    }

    @Test
    public void checkFindAllByRoleInOrderById() {
        User firstAdmin = new Admin("userTestFirstAdmin", "password", "a@email.email", Role.ADMIN, 100);
        userRepository.save(firstAdmin);

        User secondAdmin = new Admin("userTestSecondAdmin", "password", "b@email.email", Role.ADMIN, 120);
        userRepository.save(secondAdmin);

        manager.detach(firstAdmin);
        manager.detach(secondAdmin);
        List<User> users = userRepository.findAllByRoleInOrderById(Collections.singletonList(Role.ADMIN));
        assertThat(users, Matchers.hasSize(2));
    }

    @Test
    public void checkDeleteSimpleUserDetails() {
        User user = new SimpleUser("userTestFirstSimpleUser", "password",
                "c@email.email", Role.USER, LocalDate.of(2018, 2, 3));
        User savedUser = userRepository.save(user);

        int deleteSimpleUserDetails = userRepository.deleteSimpleUserDetails(savedUser.getId());
        manager.flush();
        assertEquals(1, deleteSimpleUserDetails);
    }

    @Test
    public void checkDeleteAdminDetails() {
        User user = new Admin("userTestFirstAdmin", "password",
                "d@email.email", Role.ADMIN, 35);
        User savedUser = userRepository.save(user);

        int deleteSimpleUserDetails = userRepository.deleteAdminDetails(savedUser.getId());
        manager.flush();
        assertEquals(1, deleteSimpleUserDetails);
    }

    @Test
    public void checkAddAdminDetails() {
        User user = new Admin("userTestSecondAdmin", "password",
                "e@email.email", Role.ADMIN, 35);
        User savedUser = userRepository.save(user);

        userRepository.deleteAdminDetails(savedUser.getId());
        manager.flush();

        int adminDetails = userRepository.addAdminDetails(savedUser.getId(), 100);
        manager.flush();
        assertEquals(1, adminDetails);
    }

    @Test
    public void checkAddSimpleUserDetails() {
        User user = new SimpleUser("userTestSecondSimpleUser", "password",
                "f@email.email", Role.USER, LocalDate.of(2018, 5, 20));
        User savedUser = userRepository.save(user);

        userRepository.deleteSimpleUserDetails(savedUser.getId());
        manager.flush();

        int userDetails = userRepository.addSimpleUserDetails(savedUser.getId(), LocalDate.of(2018, 10, 15));
        manager.flush();
        assertEquals(1, userDetails);
    }

    @Test
    public void checkChangeLastVisitDate() {
        User user = new SimpleUser("userTestThirdSimpleUser", "password",
                "g@email.email", Role.USER, LocalDate.of(2018, 6, 11));
        User savedUser = userRepository.save(user);

        int changeLastVisitDate = userRepository.changeLastVisitDate(savedUser.getLogin(), LocalDate.of(2018, 7, 12));
        manager.flush();
        assertEquals(1, changeLastVisitDate);
    }

    @Test
    public void checkChangeUserRole() {
        User user = new SimpleUser("userTestFourthSimpleUser", "password",
                "h@email.email", Role.USER, LocalDate.of(2018, 6, 11));
        User savedUser = userRepository.save(user);

        int changeUserRole = userRepository.changeUserRole(savedUser.getId(), Role.ADMIN);
        manager.flush();
        assertEquals(1, changeUserRole);
    }

    @Test
    public void checkChangeSalary() {
        User user = new Admin("userTestThirdAdmin", "password",
                "j@email.email", Role.ADMIN, 35);
        User savedUser = userRepository.save(user);

        int changeSalary = userRepository.changeSalary(savedUser.getId(), 100);
        manager.flush();
        assertEquals(1, changeSalary);
    }

    @Test
    public void checkChangeFirstName() {
        User user = new Admin("userTestFourthAdmin", "password",
                "k@email.email", Role.ADMIN, 35);
        User savedUser = userRepository.save(user);

        int changeFirstName = userRepository.changeFirstName(savedUser.getLogin(), "firstName");
        manager.flush();
        assertEquals(1, changeFirstName);
    }

    @Test
    public void checkChangeLastName() {
        User user = new Admin("userTestFifthAdmin", "password",
                "m@email.email", Role.ADMIN, 35);
        User savedUser = userRepository.save(user);

        int changeLastName = userRepository.changeLastName(savedUser.getLogin(), "lastName");
        manager.flush();
        assertEquals(1, changeLastName);
    }

    @Test
    public void checkChangeEmail() {
        User user = new Admin("userTestSixthAdmin", "password",
                "n@email.email", Role.ADMIN, 35);
        User savedUser = userRepository.save(user);

        int changeEmail = userRepository.changeEmail(savedUser.getLogin(), "o@email.email");
        manager.flush();
        assertEquals(1, changeEmail);
    }

    @Test
    public void checkChangePhoneNumber() {
        User user = new Admin("userTestSeventhAdmin", "password",
                "p@email.email", Role.ADMIN, 35);
        User savedUser = userRepository.save(user);

        int changePhoneNumber = userRepository.changePhoneNumber(savedUser.getLogin(), "1111000000");
        manager.flush();
        assertEquals(1, changePhoneNumber);
    }

    @Test
    public void checkChangeAddress() {
        User user = new Admin("userTestEighthAdmin", "password",
                "q@email.email", Role.ADMIN, 35);
        User savedUser = userRepository.save(user);

        int changeAddress = userRepository.changeAddress(savedUser.getLogin(), "Address");
        manager.flush();
        assertEquals(1, changeAddress);
    }
}
