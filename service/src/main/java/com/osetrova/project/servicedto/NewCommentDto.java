package com.osetrova.project.servicedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewCommentDto {

    private String text;
    private String username;
    private Long gameId;
}
