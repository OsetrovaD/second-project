package com.osetrova.project.converter.gameconverter;

import com.osetrova.project.converter.Converter;
import com.osetrova.project.entity.Game;
import com.osetrova.project.dto.gamedto.GameSearchResultDto;
import org.springframework.stereotype.Component;

@Component
public class GameToGameSearchResultConverter implements Converter<GameSearchResultDto, Game> {

    @Override
    public GameSearchResultDto convert(Game game) {
        return GameSearchResultDto.builder()
                .id(game.getId())
                .name(game.getName())
                .issueYear(game.getIssueYear())
                .description(game.getDescription())
                .ageLimit(game.getAgeLimit())
                .build();
    }
}
