package project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.entity.baseentity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"number", "lastAdditionDate", "gamePrice"})
@Entity
@Table(name = "storage", schema = "computer_games_e_shop_storage")
public class Storage extends BaseEntity<Long> {

    @Column(name = "number")
    private Short number;

    @Column(name = "last_addition_date")
    private LocalDate lastAdditionDate;

    @OneToOne
    @JoinColumn(name = "game_game_platform_id")
    private GamePrice gamePrice;
}
