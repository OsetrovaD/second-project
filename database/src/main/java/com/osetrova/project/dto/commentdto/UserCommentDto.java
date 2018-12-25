package com.osetrova.project.dto.commentdto;

import com.osetrova.project.dto.gamedto.GameIdAndNameDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCommentDto {

    private Long id;
    private String text;
    private LocalDate date;
    private GameIdAndNameDto gameInfo;
}
