package com.osetrova.project.converter.commentconverter;

import com.osetrova.project.converter.Converter;
import com.osetrova.project.entity.Comment;
import com.osetrova.project.dto.commentdto.GameCommentDto;
import com.osetrova.project.dto.userdto.UserIdAndLoginDto;
import org.springframework.stereotype.Component;

@Component
public class CommentToGameCommentDtoConverter implements Converter<GameCommentDto, Comment> {

    @Override
    public GameCommentDto convert(Comment comment) {
        return GameCommentDto.builder()
                .id(comment.getId())
                .gameId(comment.getGame().getId())
                .text(comment.getText())
                .date(comment.getDate())
                .userInfo(UserIdAndLoginDto.of(comment.getUser().getId(), comment.getUser().getLogin()))
                .build();
    }
}
