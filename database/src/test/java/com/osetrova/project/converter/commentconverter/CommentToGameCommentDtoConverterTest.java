package com.osetrova.project.converter.commentconverter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.commentdto.GameCommentDto;
import com.osetrova.project.dto.userdto.UserIdAndLoginDto;
import com.osetrova.project.entity.Admin;
import com.osetrova.project.entity.Comment;
import com.osetrova.project.entity.Game;
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
public class CommentToGameCommentDtoConverterTest {

    @Autowired
    private CommentToGameCommentDtoConverter converter;

    @Test
    public void checkConvert() {
        User user = new Admin("converterFirstUser", "password", "z@mail.mail", Role.ADMIN, 100);
        user.setId(1L);

        Game game = Game.builder()
                .id(1L)
                .name("converterFirstGame")
                .description("new game")
                .build();

        Comment firstComment = Comment.builder()
                .id(1L)
                .text("first comment")
                .date(LocalDate.of(2018, 4, 15))
                .game(game)
                .user(user)
                .build();

        GameCommentDto commentDto = GameCommentDto.builder()
                .id(1L)
                .gameId(1L)
                .date(LocalDate.of(2018, 4, 15))
                .text("first comment")
                .userInfo(UserIdAndLoginDto.of(1L, "converterFirstUser"))
                .build();

        GameCommentDto convert = converter.convert(firstComment);
        assertEquals(commentDto, convert);
    }
}
