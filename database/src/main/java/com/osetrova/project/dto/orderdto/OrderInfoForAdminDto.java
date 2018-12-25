package com.osetrova.project.dto.orderdto;

import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.PaymentForm;
import com.osetrova.project.dto.userdto.UserIdAndLoginDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfoForAdminDto {

    private Long id;
    private DeliveryMethod deliveryMethod;
    private PaymentForm paymentForm;
    private Condition condition;
    private LocalDate date;
    private LocalDate deliveryDate;
    private UserIdAndLoginDto userInfo;

}
