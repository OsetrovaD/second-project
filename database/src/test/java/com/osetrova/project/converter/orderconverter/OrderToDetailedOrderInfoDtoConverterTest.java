package com.osetrova.project.converter.orderconverter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.converter.ItemInOrderToDtoConverter;
import com.osetrova.project.dto.ItemInOrderDto;
import com.osetrova.project.dto.gamedto.GameIdAndNameDto;
import com.osetrova.project.dto.orderdto.DetailedOrderInfoDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.entity.ItemInOrder;
import com.osetrova.project.entity.Order;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.entity.enumonly.PaymentForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
public class OrderToDetailedOrderInfoDtoConverterTest {

    @Autowired
    private OrderToDetailedOrderInfoDtoConverter converter;

    @Test
    public void checkConvert() {
        Order order = Order.builder()
                .id(1L)
                .date(LocalDate.of(2018, 10, 25))
                .paymentForm(PaymentForm.CASH)
                .condition(Condition.COMPLETING)
                .deliveryDate(LocalDate.of(2018, 10, 28))
                .itemsInOrder(new HashSet<>(Collections.singletonList(ItemInOrder.builder()
                        .gamePrice(GamePrice.builder()
                                .gameGamePlatform(GameGamePlatform.of(1L, GamePlatform.PLAYSTATION_3))
                                .game(Game.builder().id(1L).name("convGameName").build())
                                .build())
                        .number(1)
                        .build())))
                .deliveryMethod(DeliveryMethod.PICKUP)
                .build();

        DetailedOrderInfoDto orderInfoDto = DetailedOrderInfoDto.builder()
                .id(1L)
                .condition(Condition.COMPLETING)
                .date(LocalDate.of(2018, 10, 25))
                .deliveryDate(LocalDate.of(2018, 10, 28))
                .deliveryMethod(DeliveryMethod.PICKUP)
                .paymentForm(PaymentForm.CASH)
                .itemsInOrder(new HashSet<>(Collections.singletonList(ItemInOrderDto.builder()
                        .gameInfo(GameIdAndNameDto.of(1L, "convGameName"))
                        .gamePlatform(GamePlatform.PLAYSTATION_3)
                        .number(1)
                        .build())))
                .build();
        DetailedOrderInfoDto convert = converter.convert(order);
        assertEquals(convert, orderInfoDto);
    }
}
