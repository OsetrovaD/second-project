package com.osetrova.project.servicedto;

import com.osetrova.project.dto.gamepricedto.GamePriceForBasketDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basket {

    private List<GamePriceForBasketDto> gamesInBasket;
}
