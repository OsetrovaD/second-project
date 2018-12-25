package com.osetrova.project.converter.gameconverter;

import com.osetrova.project.converter.Converter;
import com.osetrova.project.converter.GamePriceToGamePriceDtoConverter;
import com.osetrova.project.converter.ScreenshotToScreenshotDtoConverter;
import com.osetrova.project.converter.SubgenreToSubgenreDtoConverter;
import com.osetrova.project.entity.Game;
import com.osetrova.project.dto.gamedto.GameFullInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameToGameFullInfoDtoConverter implements Converter<GameFullInfoDto, Game> {

    private final ScreenshotToScreenshotDtoConverter screenshotConverter;
    private final GamePriceToGamePriceDtoConverter gamePriceConverter;
    private final SubgenreToSubgenreDtoConverter subgenreConverter;

        @Override
    public GameFullInfoDto convert(Game game) {
        return GameFullInfoDto.builder()
                .id(game.getId())
                .name(game.getName())
                .description(game.getDescription())
                .ageLimit(game.getAgeLimit())
                .developerCompany(game.getDeveloperCompany())
                .image(game.getImage())
                .issueYear(game.getIssueYear())
                .minimalSystemRequirements(game.getMinimalSystemRequirements())
                .recommendedSystemRequirements(game.getRecommendedSystemRequirements())
                .gamePrices(game.getGamePrices().stream()
                                .map(gamePriceConverter::convert)
                                .collect(toSet()))
                .screenshots(game.getScreenshots().stream()
                                .map(screenshotConverter::convert)
                                .collect(toSet()))
                .subgenres(game.getSubgenres().stream()
                                .map(subgenreConverter::convert)
                                .collect(toSet()))
                .build();
    }
}
