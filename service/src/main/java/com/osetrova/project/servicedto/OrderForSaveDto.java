package com.osetrova.project.servicedto;

import com.osetrova.project.dto.gamepricedto.GamePriceForBasketDto;
import com.osetrova.project.entity.Storage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderForSaveDto {

    private List<Storage> storage;
    private List<GamePriceForBasketDto> gamesInBasket;
    private NewOrderParameterDto orderParameter;
    private String login;
}
