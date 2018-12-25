package com.osetrova.project.converter;

import com.osetrova.project.entity.ItemInOrder;
import com.osetrova.project.dto.ItemInOrderDto;
import com.osetrova.project.dto.gamedto.GameIdAndNameDto;
import org.springframework.stereotype.Component;

@Component
public class ItemInOrderToDtoConverter implements Converter<ItemInOrderDto, ItemInOrder> {

    @Override
    public ItemInOrderDto convert(ItemInOrder item) {
        return ItemInOrderDto.builder()
                .gameInfo(GameIdAndNameDto.of(item.getGamePrice().getGame().getId(), item.getGamePrice().getGame().getName()))
                .gamePlatform(item.getGamePrice().getGameGamePlatform().getGamePlatform())
                .number(item.getNumber())
                .build();
    }
}
