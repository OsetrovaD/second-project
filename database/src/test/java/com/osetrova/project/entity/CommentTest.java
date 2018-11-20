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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class CommentTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkSaveAndGet() {
        User user = new Admin("admin_test", "password", "email@email.email", Role.ADMIN, 100);
        userRepository.save(user);

        Game game = Game.builder()
                .name("new game")
                .description("new game")
                .build();
        gameRepository.save(game);

        Comment comment = Comment.builder()
                .text("comment")
                .date(LocalDate.of(2018, 4, 15))
                .game(game)
                .user(user)
                .build();
        Comment savedComment = commentRepository.save(comment);
        Long savedId = savedComment.getId();
        assertNotNull(savedComment);

        manager.detach(comment);

        Optional<Comment> commentOptional = commentRepository.findById(savedId);
        assertTrue(commentOptional.isPresent());
    }
}
