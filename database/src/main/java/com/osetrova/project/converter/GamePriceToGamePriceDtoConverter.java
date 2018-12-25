package com.osetrova.project.converter;

import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.dto.gamepricedto.GamePriceDto;
import org.springframework.stereotype.Component;

@Component
public class GamePriceToGamePriceDtoConverter implements Converter<GamePriceDto, GamePrice> {

    @Override
    public GamePriceDto convert(GamePrice gamePrice) {
        return GamePriceDto.builder()
                .id(gamePrice.getId())
                .gamePlatform(gamePrice.getGameGamePlatform().getGamePlatform())
                .numberInStorage(gamePrice.getStorage().getNumber())
                .price(gamePrice.getPrice())
                .build();
    }
}
