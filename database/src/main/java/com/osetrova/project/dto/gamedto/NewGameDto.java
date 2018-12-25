package com.osetrova.project.dto.gamedto;

import com.osetrova.project.dto.DeveloperCompanyDto;
import com.osetrova.project.dto.ScreenshotDto;
import com.osetrova.project.dto.gamepricedto.NewGamePriceDto;
import com.osetrova.project.dto.subgenredto.SubgenreNameDto;
import com.osetrova.project.entity.enumonly.AgeLimit;
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
public class NewGameDto {

    private String name;
    private String description;
    private Integer issueYear;
    private String image;
    private String minimalSystemRequirements;
    private String recommendedSystemRequirements;
    private AgeLimit ageLimit;
    private DeveloperCompanyDto developerCompany;
    private Set<ScreenshotDto> screenshots = new HashSet<>();
    private Set<SubgenreNameDto> subgenres = new HashSet<>();
    private Set<NewGamePriceDto> prices = new HashSet<>();
}
