package com.osetrova.project.servicedto;

import com.osetrova.project.entity.enumonly.Condition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderConditionDto {

    private Long id;
    private Condition condition;
}
