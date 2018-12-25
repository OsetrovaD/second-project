package com.osetrova.project.service;

import com.osetrova.project.dto.commentdto.GameCommentDto;
import com.osetrova.project.dto.commentdto.UserCommentDto;
import com.osetrova.project.entity.Admin;
import com.osetrova.project.entity.Comment;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.enumonly.Role;
import com.osetrova.project.jparepository.CommentRepository;
import com.osetrova.project.jparepository.UserRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import com.osetrova.project.serviceconfig.ServiceConfiguration;
import com.osetrova.project.servicedto.NewCommentDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkFindGameComments() {
        User user = new Admin("commentServiceFirstUser", "password", "qa@mail.mail", Role.ADMIN, 100);
        userRepository.save(user);

        Game game = Game.builder()
                .name("commentServiceFirstGame")
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

        List<GameCommentDto> gameComments = commentService.findGameComments(savedId);

        assertThat(gameComments, hasSize(2));
    }

    @Test
    public void checkFindUserComments() {
        User user = new Admin("commentServiceSecondUser", "password", "qw@mail.mail", Role.ADMIN, 100);
        User savedUser = userRepository.save(user);

        Game game = Game.builder()
                .name("commentServiceSecondGame")
                .description("new game")
                .build();
        gameRepository.save(game);

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
        Long savedId = savedUser.getId();

        manager.detach(firstComment);
        manager.detach(secondComment);

        List<UserCommentDto> userComments = commentService.findUserComments(savedId);

        assertThat(userComments, hasSize(2));
    }

    @Test
    public void checkFindUserCommentsByUsername() {
        User user = new Admin("commentServiceThirdUser", "password", "qe@mail.mail", Role.ADMIN, 100);
        User savedUser = userRepository.save(user);

        Game game = Game.builder()
                .name("commentServiceThirdGame")
                .description("new game")
                .build();
        gameRepository.save(game);

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

        manager.detach(firstComment);
        manager.detach(secondComment);

        List<UserCommentDto> userComments = commentService.findUserComments(savedUser.getLogin());

        assertThat(userComments, hasSize(2));
    }

    @Test
    public void checkSave() {
        User user = new Admin("commentServiceFourthUser", "password", "qr@mail.mail", Role.ADMIN, 100);
        User savedUser = userRepository.save(user);

        Game game = Game.builder()
                .name("commentServiceFourthGame")
                .description("new game")
                .build();
        Game savedGame = gameRepository.save(game);

        NewCommentDto newCommentDto = NewCommentDto.builder()
                .gameId(savedGame.getId())
                .username(savedUser.getLogin())
                .text("some text")
                .build();
        Comment comment = commentService.save(newCommentDto);

        Comment firstComment = Comment.builder()
                .id(comment.getId())
                .text("some text")
                .date(LocalDate.now())
                .game(game)
                .user(user)
                .build();

        assertEquals(comment, firstComment);
    }

    @Test
    public void checkDeleteById() {
        User user = new Admin("commentServiceFifthUser", "password", "qt@mail.mail", Role.ADMIN, 100);
        userRepository.save(user);

        Game game = Game.builder()
                .name("commentServiceFifthGame")
                .description("new game")
                .build();
        gameRepository.save(game);

        Comment firstComment = Comment.builder()
                .text("some text")
                .date(LocalDate.of(2018, 7, 16))
                .game(game)
                .user(user)
                .build();
        Comment savedComment = commentRepository.save(firstComment);

        commentService.deleteById(savedComment.getId());
        manager.flush();

        Optional<Comment> optionalComment = commentRepository.findById(savedComment.getId());

        assertFalse(optionalComment.isPresent());
    }
}
