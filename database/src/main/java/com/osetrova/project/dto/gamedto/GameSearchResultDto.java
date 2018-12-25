package com.osetrova.project.dto.gamedto;

import com.osetrova.project.entity.enumonly.AgeLimit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameSearchResultDto {

    private Long id;
    private String name;
    private Integer issueYear;
    private String description;
    private AgeLimit ageLimit;
}
