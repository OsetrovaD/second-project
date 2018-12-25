package com.osetrova.project.jparepository;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.Admin;
import com.osetrova.project.entity.Comment;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.enumonly.Role;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkFindAllByGameId() {
        User user = new Admin("commentByUserTestUser", "password", "mail@mail.mail", Role.ADMIN, 100);
        userRepository.save(user);

        Game game = Game.builder()
                .name("commentTestFirstGame")
                .description("new game")
                .build();
        Game savedGame = gameRepository.save(game);

        Comment firstComment = Comment.builder()
                .text("first comment")
                .date(LocalDate.of(2018, 4, 15))
                .game(game)
                .user(user)
                .build();
        commentRepository.save(firstComment);

        Comment secondComment = Comment.builder()
                .text("second comment")
                .date(LocalDate.of(2018, 4, 15))
                .game(game)
                .user(user)
                .build();
        commentRepository.save(secondComment);
        Long savedId = savedGame.getId();

        manager.detach(firstComment);
        manager.detach(secondComment);

        List<Comment> comments = commentRepository.findAllByGameId(savedId);

        assertThat(comments, hasSize(2));
    }

    @Test
    public void checkFindAllByUserId() {
        User user = new Admin("secondCommentTestUser", "password", "eemail@mail.mail", Role.ADMIN, 100);
        User savedUser = userRepository.save(user);

        Game game = Game.builder()
                .name("commentTestSecondGame")
                .description("new game")
                .build();
        gameRepository.save(game);

        Comment firstComment = Comment.builder()
                .text("first comment")
                .date(LocalDate.of(2018, 10, 25))
                .game(game)
                .user(user)
                .build();
        commentRepository.save(firstComment);

        Comment secondComment = Comment.builder()
                .text("second comment")
                .date(LocalDate.of(2017, 7, 11))
                .game(game)
                .user(user)
                .build();
        commentRepository.save(secondComment);
        Long savedId = savedUser.getId();

        manager.detach(firstComment);
        manager.detach(secondComment);

        List<Comment> comments = commentRepository.findAllByUserId(savedId);

        assertThat(comments, hasSize(2));
    }

    @Test
    public void checkFindAllByUserLogin() {
        User user = new Admin("thirdCommentTestUser", "password", "mmail@mail.mail", Role.ADMIN, 100);
        User savedUser = userRepository.save(user);

        Game game = Game.builder()
                .name("commentTestThirdGame")
                .description("new game")
                .build();
        gameRepository.save(game);

        Comment firstComment = Comment.builder()
                .text("first comment")
                .date(LocalDate.of(2018, 11, 5))
                .game(game)
                .user(user)
                .build();
        commentRepository.save(firstComment);

        Comment secondComment = Comment.builder()
                .text("second comment")
                .date(LocalDate.of(2018, 7, 19))
                .game(game)
                .user(user)
                .build();
        commentRepository.save(secondComment);

        manager.detach(firstComment);
        manager.detach(secondComment);

        List<Comment> comments = commentRepository.findAllByUserLogin(savedUser.getLogin());

        assertThat(comments, hasSize(2));
    }
}
