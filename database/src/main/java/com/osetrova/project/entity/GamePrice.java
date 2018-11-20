package com.osetrova.project.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.osetrova.project.entity.baseentity.BaseEntity;
import com.osetrova.project.entity.embeddable.GameGamePlatform;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"itemInOrders", "game", "storage"})
@Data
@Builder
@EqualsAndHashCode(
        exclude = {
        "gameGamePlatform",
        "price",
        "game",
        "itemInOrders",
        "storage",
        })
@Entity
@Table(name = "game_game_platform", schema = "computer_games_e_shop_storage")
public class GamePrice implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(unique = true)
    private GameGamePlatform gameGamePlatform;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    private Game game;

    @OneToMany(mappedBy = "gamePrice")
    private Set<ItemInOrder> itemInOrders = new HashSet<>();

    @OneToOne(mappedBy = "gamePrice")
    private Storage storage;
}
