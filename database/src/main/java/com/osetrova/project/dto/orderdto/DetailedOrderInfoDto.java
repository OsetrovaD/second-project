package com.osetrova.project.dto.orderdto;

import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.PaymentForm;
import com.osetrova.project.dto.ItemInOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedOrderInfoDto {

    private Long id;
    private DeliveryMethod deliveryMethod;
    private PaymentForm paymentForm;
    private Condition condition;
    private LocalDate date;
    private LocalDate deliveryDate;
    private Set<ItemInOrderDto> itemsInOrder;
}
