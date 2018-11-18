package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.enumonly.Role;
import com.osetrova.project.jparepository.CommentRepository;
import com.osetrova.project.jparepository.UserRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class UserTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkSaveAndGetAdmin() {
        User admin = new Admin("adminLogin", "password", "mail_email@email.email", Role.ADMIN, 100);
        User savedUser = userRepository.save(admin);
        assertNotNull(savedUser);
        Long savedUserId = savedUser.getId();

        manager.detach(admin);

        Optional<User> userOptional = userRepository.findById(savedUserId);
        assertTrue(userOptional.isPresent());
    }

    @Test
    public void checkSaveAndGetSimpleUser() {
        User simpleUser = new SimpleUser("simpleUser", "password", "some_email@email.email",
                Role.USER, LocalDate.of(2018, 4, 7));
        User savedUser = userRepository.save(simpleUser);
        assertNotNull(savedUser);
        Long savedUserId = savedUser.getId();

        manager.detach(simpleUser);

        Optional<User> optionalUser = userRepository.findById(savedUserId);
        assertTrue(optionalUser.isPresent());
    }

    @Test
    public void checkGetComments() {
        User user = new Admin("someLogin", "password", "mail@email.email", Role.ADMIN, 100);
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        Game game = Game.builder()
                .name("game_5")
                .description("new game")
                .build();
        gameRepository.save(game);

        Comment comment = Comment.builder()
                .text("comment")
                .date(LocalDate.of(2018, 4, 15))
                .game(game)
                .user(user)
                .build();
        commentRepository.save(comment);

        manager.clear();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        assertThat(user.getComments(), hasSize(1));
    }
}
