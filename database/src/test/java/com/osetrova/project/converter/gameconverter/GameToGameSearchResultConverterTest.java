package com.osetrova.project.converter.gameconverter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.gamedto.GameSearchResultDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.enumonly.AgeLimit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
public class GameToGameSearchResultConverterTest {

    @Autowired
    private GameToGameSearchResultConverter converter;

    @Test
    public void checkConvert() {
        Game game = Game.builder()
                .id(1L)
                .name("converterSearchResultGame")
                .issueYear(2010)
                .ageLimit(AgeLimit.TEEN)
                .description("new game")
                .build();

        GameSearchResultDto resultDto = GameSearchResultDto.builder()
                .ageLimit(AgeLimit.TEEN)
                .description("new game")
                .id(1L)
                .issueYear(2010)
                .name("converterSearchResultGame")
                .build();

        GameSearchResultDto convert = converter.convert(game);
        assertEquals(convert, resultDto);
    }
}
