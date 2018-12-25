package com.osetrova.project.converter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.gamepricedto.GamePriceDto;
import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.entity.Storage;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.enumonly.GamePlatform;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
public class GamePriceToGamePriceDtoConverterTest {

    @Autowired
    private GamePriceToGamePriceDtoConverter converter;

    @Test
    public void checkConvert() {
        GamePrice gamePrice = GamePrice.builder()
                .id(1L)
                .gameGamePlatform(GameGamePlatform.of(1L, GamePlatform.XBOX_ONE))
                .price(42)
                .storage(Storage.builder().number((short) 25).build())
                .build();

        GamePriceDto gamePriceDto = GamePriceDto.builder()
                .id(1L)
                .gamePlatform(GamePlatform.XBOX_ONE)
                .numberInStorage((short) 25)
                .price(42)
                .build();

        GamePriceDto convert = converter.convert(gamePrice);
        assertEquals(convert, gamePriceDto);
    }
}
