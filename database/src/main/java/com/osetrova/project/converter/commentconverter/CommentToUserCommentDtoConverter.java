package com.osetrova.project.converter.commentconverter;

import com.osetrova.project.converter.Converter;
import com.osetrova.project.entity.Comment;
import com.osetrova.project.dto.commentdto.UserCommentDto;
import com.osetrova.project.dto.gamedto.GameIdAndNameDto;
import org.springframework.stereotype.Component;

@Component
public class CommentToUserCommentDtoConverter implements Converter<UserCommentDto, Comment> {

    @Override
    public UserCommentDto convert(Comment comment) {
        return UserCommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .date(comment.getDate())
                .gameInfo(GameIdAndNameDto.of(comment.getGame().getId(), comment.getGame().getName()))
                .build();
    }
}
