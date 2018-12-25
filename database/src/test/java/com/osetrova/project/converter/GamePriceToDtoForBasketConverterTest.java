package com.osetrova.project.converter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.gamepricedto.GamePriceForBasketDto;
import com.osetrova.project.entity.Game;
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
public class GamePriceToDtoForBasketConverterTest {

    @Autowired
    private GamePriceToDtoForBasketConverter converter;

    @Test
    public void checkConvert() {
        GamePrice gamePrice = GamePrice.builder()
                .id(1L)
                .game(Game.builder().id(1L).name("forBasket").build())
                .gameGamePlatform(GameGamePlatform.of(1L, GamePlatform.XBOX_ONE))
                .price(42)
                .storage(Storage.builder().number((short) 25).build())
                .build();

        GamePriceForBasketDto forBasketDto = GamePriceForBasketDto.builder()
                .id(1L)
                .gameId(1L)
                .gameName("forBasket")
                .gamePlatform(GamePlatform.XBOX_ONE)
                .price(42)
                .numberInStorage((short) 25)
                .number((short) 1)
                .build();

        GamePriceForBasketDto convert = converter.convert(gamePrice);
        assertEquals(convert, forBasketDto);
    }
}
