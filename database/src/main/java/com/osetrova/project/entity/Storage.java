package com.osetrova.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.osetrova.project.entity.baseentity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(exclude = {"number", "lastAdditionDate", "gamePrice"})
@Entity
@Table(name = "storage", schema = "computer_games_e_shop_storage")
public class Storage implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Short number;

    @Column(name = "last_addition_date")
    private LocalDate lastAdditionDate;

    @OneToOne
    @JoinColumn(name = "game_game_platform_id")
    private GamePrice gamePrice;

    @Version
    @Column(name = "version")
    private Long version;

    public Storage(Short number, LocalDate lastAdditionDate) {
        this.number = number;
        this.lastAdditionDate = lastAdditionDate;
    }
}
