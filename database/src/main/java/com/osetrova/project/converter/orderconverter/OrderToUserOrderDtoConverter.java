package com.osetrova.project.converter.orderconverter;

import com.osetrova.project.converter.Converter;
import com.osetrova.project.entity.Order;
import com.osetrova.project.dto.orderdto.UserOrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderToUserOrderDtoConverter implements Converter<UserOrderDto, Order> {

    @Override
    public UserOrderDto convert(Order order) {
        return UserOrderDto.builder()
                .id(order.getId())
                .condition(order.getCondition())
                .date(order.getDate())
                .deliveryDate(order.getDeliveryDate())
                .deliveryMethod(order.getDeliveryMethod())
                .paymentForm(order.getPaymentForm())
                .build();
    }
}
