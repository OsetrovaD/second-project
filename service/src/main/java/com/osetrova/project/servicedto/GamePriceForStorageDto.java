package com.osetrova.project.servicedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GamePriceForStorageDto {

    private Long gameGamePlatformId;
    private Short number;
}
