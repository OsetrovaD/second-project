package com.osetrova.project.dto;

import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.dto.gamedto.GameIdAndNameDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemInOrderDto {

    private GameIdAndNameDto gameInfo;
    private Integer number;
    private GamePlatform gamePlatform;
}
