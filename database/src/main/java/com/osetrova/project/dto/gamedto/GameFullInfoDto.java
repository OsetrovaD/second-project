package com.osetrova.project.dto.gamedto;

import com.osetrova.project.entity.DeveloperCompany;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.dto.gamepricedto.GamePriceDto;
import com.osetrova.project.dto.ScreenshotDto;
import com.osetrova.project.dto.subgenredto.SubgenreDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameFullInfoDto {

    private Long id;
    private String name;
    private String description;
    private Integer issueYear;
    private String image;
    private String minimalSystemRequirements;
    private String recommendedSystemRequirements;
    private AgeLimit ageLimit;
    private DeveloperCompany developerCompany;
    private Set<ScreenshotDto> screenshots = new HashSet<>();
    private Set<GamePriceDto> gamePrices = new HashSet<>();
    private Set<SubgenreDto> subgenres = new HashSet<>();
}
