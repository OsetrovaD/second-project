package com.osetrova.project.converter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.ItemInOrderDto;
import com.osetrova.project.dto.gamedto.GameIdAndNameDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.entity.ItemInOrder;
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
public class ItemInOrderToDtoConverterTest {

    @Autowired
    private ItemInOrderToDtoConverter converter;

    @Test
    public void checkConvert() {
        ItemInOrder item = ItemInOrder.builder()
                .gamePrice(GamePrice.builder()
                        .game(Game.builder().id(15L).name("itemGameName").build())
                        .gameGamePlatform(GameGamePlatform.of(15L, GamePlatform.NINTENDO_SWITCH))
                        .build())
                .number(15)
                .build();

        ItemInOrderDto itemInOrderDto = ItemInOrderDto.builder()
                .gameInfo(GameIdAndNameDto.of(15L, "itemGameName"))
                .gamePlatform(GamePlatform.NINTENDO_SWITCH)
                .number(15)
                .build();

        ItemInOrderDto convert = converter.convert(item);
        assertEquals(convert, itemInOrderDto);
    }
}
