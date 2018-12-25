package com.osetrova.project.servicedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserChangeDataDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
}
