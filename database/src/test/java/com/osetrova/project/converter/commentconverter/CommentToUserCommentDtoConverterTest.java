package com.osetrova.project.converter.commentconverter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.commentdto.UserCommentDto;
import com.osetrova.project.dto.gamedto.GameIdAndNameDto;
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
public class CommentToUserCommentDtoConverterTest {

    @Autowired
    private CommentToUserCommentDtoConverter converter;

    @Test
    public void checkConvert() {
        User user = new Admin("converterSecondUser", "password", "y@mail.mail", Role.ADMIN, 100);
        user.setId(1L);

        Game game = Game.builder()
                .id(1L)
                .name("converterSecondGame")
                .description("new game")
                .build();

        Comment firstComment = Comment.builder()
                .id(1L)
                .text("first comment")
                .date(LocalDate.of(2018, 9, 20))
                .game(game)
                .user(user)
                .build();

        UserCommentDto commentDto = UserCommentDto.builder()
                .id(1L)
                .gameInfo(GameIdAndNameDto.of(1L, "converterSecondGame"))
                .date(LocalDate.of(2018, 9, 20))
                .text("first comment")
                .build();

        UserCommentDto convert = converter.convert(firstComment);
        assertEquals(commentDto, convert);
    }
}
