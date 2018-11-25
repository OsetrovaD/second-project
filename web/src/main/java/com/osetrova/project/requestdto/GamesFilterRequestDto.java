package com.osetrova.project.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GamesFilterRequestDto {

    private String issueYear;
    private String ageLimit;
    private String price;
    private String itemsOnPage;
    private boolean isIssueYearFilterChosen;
    private boolean isAgeLimitFilterChosen;
    private boolean isPriceFilterChosen;
}
