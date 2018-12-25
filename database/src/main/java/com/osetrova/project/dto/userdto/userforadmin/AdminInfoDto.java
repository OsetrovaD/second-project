package com.osetrova.project.dto.userdto.userforadmin;

import com.osetrova.project.entity.enumonly.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfoDto extends UserInfoForAdminDto {

    private Integer salary;

    public AdminInfoDto(Long id, String login, String firstName,
                        String lastName, String phoneNumber, String address,
                        String email, Role role, Integer salary) {
        super(id, login, firstName, lastName, phoneNumber, address, email, role);
        this.salary = salary;
    }
}
