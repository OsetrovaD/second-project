package com.osetrova.project.converter.orderconverter;

import com.osetrova.project.converter.Converter;
import com.osetrova.project.entity.Order;
import com.osetrova.project.dto.orderdto.OrderInfoForAdminDto;
import com.osetrova.project.dto.userdto.UserIdAndLoginDto;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderInfoForAdminConverter implements Converter<OrderInfoForAdminDto, Order> {

    @Override
    public OrderInfoForAdminDto convert(Order order) {
        return OrderInfoForAdminDto.builder()
                .id(order.getId())
                .condition(order.getCondition())
                .date(order.getDate())
                .deliveryDate(order.getDeliveryDate())
                .deliveryMethod(order.getDeliveryMethod())
                .paymentForm(order.getPaymentForm())
                .userInfo(UserIdAndLoginDto.of(order.getUser().getId(), order.getUser().getLogin()))
                .build();
    }
}
