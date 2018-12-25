package com.osetrova.project.dto.gamedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class GameIdAndNameDto {

    private Long id;
    private String name;
}
