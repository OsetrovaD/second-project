package project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import project.entity.baseentity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "games")
@Data
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"name", "genre", "games"})
@Entity
@Table(name = "subgenre", schema = "computer_games_e_shop_storage")
public class Subgenre extends BaseEntity<Integer> {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany
    @JoinTable(name = "game_subgenre", schema = "computer_games_e_shop_storage",
            joinColumns = @JoinColumn(name = "subgenre_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<Game> games = new HashSet<>();

    public void addGame(Game game) {
        this.games.add(game);
    }
}
