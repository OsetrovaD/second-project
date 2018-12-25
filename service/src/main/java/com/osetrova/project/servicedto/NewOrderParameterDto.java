package com.osetrova.project.servicedto;

import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.PaymentForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewOrderParameterDto {

    private DeliveryMethod deliveryMethod;
    private PaymentForm paymentForm;
}
