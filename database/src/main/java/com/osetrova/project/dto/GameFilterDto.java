package com.osetrova.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.osetrova.project.entity.enumonly.AgeLimit;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
@Builder
public class GameFilterDto {

    private Integer issueYear;
    private AgeLimit ageLimit;
    private Integer price;
}
