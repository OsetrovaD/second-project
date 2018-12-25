package com.osetrova.project.converter;

import com.osetrova.project.dto.gamepricedto.GamePriceForBasketDto;
import com.osetrova.project.entity.GamePrice;
import org.springframework.stereotype.Component;

@Component
public class GamePriceToDtoForBasketConverter implements Converter<GamePriceForBasketDto, GamePrice> {

    @Override
    public GamePriceForBasketDto convert(GamePrice gamePrice) {
        return GamePriceForBasketDto.builder()
                .id(gamePrice.getId())
                .gameId(gamePrice.getGame().getId())
                .gameName(gamePrice.getGame().getName())
                .gamePlatform(gamePrice.getGameGamePlatform().getGamePlatform())
                .price(gamePrice.getPrice())
                .numberInStorage(gamePrice.getStorage().getNumber())
                .number((short) 1)
                .build();
    }
}
