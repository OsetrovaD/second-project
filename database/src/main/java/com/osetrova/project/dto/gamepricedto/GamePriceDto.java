package com.osetrova.project.dto.gamepricedto;

import com.osetrova.project.entity.enumonly.GamePlatform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GamePriceDto {

    private Long id;
    private GamePlatform gamePlatform;
    private Integer price;
    private Short numberInStorage;
}
