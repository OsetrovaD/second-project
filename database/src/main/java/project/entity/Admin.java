package project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import project.entity.enumonly.Role;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Data
@EqualsAndHashCode(callSuper = true, exclude = "salary")
@Builder
@Entity
@Table(name = "admin_detail", schema = "computer_games_e_shop_storage")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {

    private Integer salary;

    public Admin(String login, String password, String email, Role role, Integer salary) {
        super(login, password, email, role);
        this.salary = salary;
    }
}