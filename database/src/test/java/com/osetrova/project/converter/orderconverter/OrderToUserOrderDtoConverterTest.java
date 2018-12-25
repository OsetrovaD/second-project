package com.osetrova.project.converter.orderconverter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.orderdto.UserOrderDto;
import com.osetrova.project.entity.Order;
import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.PaymentForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
public class OrderToUserOrderDtoConverterTest {

    @Autowired
    private OrderToUserOrderDtoConverter converter;

    @Test
    public void checkConvert() {
        Order order = Order.builder()
                .id(2L)
                .date(LocalDate.of(2018, 10, 25))
                .paymentForm(PaymentForm.CASH)
                .condition(Condition.COMPLETING)
                .deliveryDate(LocalDate.of(2018, 10, 28))
                .deliveryMethod(DeliveryMethod.PICKUP)
                .build();

        UserOrderDto orderDto = UserOrderDto.builder()
                .id(2L)
                .condition(Condition.COMPLETING)
                .date(LocalDate.of(2018, 10, 25))
                .deliveryDate(LocalDate.of(2018, 10, 28))
                .deliveryMethod(DeliveryMethod.PICKUP)
                .paymentForm(PaymentForm.CASH)
                .build();

        UserOrderDto convert = converter.convert(order);
        assertEquals(convert, orderDto);
    }
}
