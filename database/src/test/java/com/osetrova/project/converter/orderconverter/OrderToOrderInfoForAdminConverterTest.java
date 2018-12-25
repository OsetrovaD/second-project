package com.osetrova.project.converter.orderconverter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.orderdto.OrderInfoForAdminDto;
import com.osetrova.project.dto.userdto.UserIdAndLoginDto;
import com.osetrova.project.entity.Admin;
import com.osetrova.project.entity.Order;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.PaymentForm;
import com.osetrova.project.entity.enumonly.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
public class OrderToOrderInfoForAdminConverterTest {

    @Autowired
    private OrderToOrderInfoForAdminConverter converter;

    @Test
    public void checkConvert() {
        User admin = new Admin("orderConverterAdmin", "password", "aa@email.email", Role.ADMIN, 35);
        admin.setId(1L);

        Order order = Order.builder()
                .id(1L)
                .date(LocalDate.of(2018, 10, 25))
                .paymentForm(PaymentForm.CASH)
                .condition(Condition.COMPLETING)
                .deliveryDate(LocalDate.of(2018, 10, 28))
                .user(admin)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .build();

        OrderInfoForAdminDto orderInfoDto = OrderInfoForAdminDto.builder()
                .id(1L)
                .condition(Condition.COMPLETING)
                .date(LocalDate.of(2018, 10, 25))
                .deliveryDate(LocalDate.of(2018, 10, 28))
                .deliveryMethod(DeliveryMethod.PICKUP)
                .paymentForm(PaymentForm.CASH)
                .userInfo(UserIdAndLoginDto.builder().id(1L).login("orderConverterAdmin").build())
                .build();
        OrderInfoForAdminDto convert = converter.convert(order);
        assertEquals(convert, orderInfoDto);
    }
}
