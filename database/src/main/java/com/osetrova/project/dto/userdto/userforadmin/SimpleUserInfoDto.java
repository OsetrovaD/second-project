package com.osetrova.project.dto.userdto.userforadmin;

import com.osetrova.project.entity.enumonly.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserInfoDto extends UserInfoForAdminDto {

    private LocalDate lastVisitDate;

    public SimpleUserInfoDto(Long id, String login, String firstName,
                             String lastName, String phoneNumber, String address,
                             String email, Role role, LocalDate lastVisitDate) {
        super(id, login, firstName, lastName, phoneNumber, address, email, role);
        this.lastVisitDate = lastVisitDate;
    }
}
