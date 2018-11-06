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
@ToString(exclude = "subgenres")
@Data
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"name", "subgenres"})
@Entity
@Table(name = "genre", schema = "computer_games_e_shop_storage")
public class Genre extends BaseEntity<Integer> {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private Set<Subgenre> subgenres = new HashSet<>();
}
