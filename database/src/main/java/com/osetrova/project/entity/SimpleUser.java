package com.osetrova.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.osetrova.project.entity.enumonly.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Data
@EqualsAndHashCode(callSuper = true, exclude = "lastVisitDate")
@Builder
@Entity
@Table(name = "simple_user_detail", schema = "computer_games_e_shop_storage")
@PrimaryKeyJoinColumn(name = "user_id")
public class SimpleUser extends User {

    @Column(name = "last_visit_date")
    private LocalDate lastVisitDate;

    public SimpleUser(String login, String password, String email, Role role, LocalDate lastVisitDate) {
        super(login, password, email, role);
        this.lastVisitDate = lastVisitDate;
    }
}