package project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import project.entity.baseentity.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "games")
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"name", "games"})
@Entity
@Table(name = "game_developer_company", schema = "computer_games_e_shop_storage")
public class DeveloperCompany extends BaseEntity<Integer> {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "developerCompany", cascade = CascadeType.ALL)
    private Set<Game> games = new HashSet<>();
}
