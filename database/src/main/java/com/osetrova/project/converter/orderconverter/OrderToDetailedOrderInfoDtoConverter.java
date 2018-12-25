package com.osetrova.project.converter.orderconverter;

import com.osetrova.project.converter.Converter;
import com.osetrova.project.converter.ItemInOrderToDtoConverter;
import com.osetrova.project.entity.Order;
import com.osetrova.project.dto.orderdto.DetailedOrderInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderToDetailedOrderInfoDtoConverter implements Converter<DetailedOrderInfoDto, Order> {

    private ItemInOrderToDtoConverter converter;

    @Autowired
    public OrderToDetailedOrderInfoDtoConverter(ItemInOrderToDtoConverter converter) {
        this.converter = converter;
    }

    @Override
    public DetailedOrderInfoDto convert(Order order) {
        return DetailedOrderInfoDto.builder()
                .id(order.getId())
                .condition(order.getCondition())
                .date(order.getDate())
                .deliveryDate(order.getDeliveryDate())
                .deliveryMethod(order.getDeliveryMethod())
                .paymentForm(order.getPaymentForm())
                .itemsInOrder(order.getItemsInOrder().stream()
                                    .map(converter::convert)
                                    .collect(Collectors.toSet()))
                .build();
    }
}
