package com.osetrova.project.dto.commentdto;

import com.osetrova.project.dto.userdto.UserIdAndLoginDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameCommentDto {

    private Long id;
    private Long gameId;
    private String text;
    private LocalDate date;
    private UserIdAndLoginDto userInfo;
}
