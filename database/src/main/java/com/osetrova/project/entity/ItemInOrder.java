package com.osetrova.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.osetrova.project.entity.baseentity.BaseEntity;
import com.osetrova.project.entity.embeddable.OrderGamePrice;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(
        exclude = {
        "orderGamePrice",
        "number",
        "order",
        "gamePrice",
        })
@Builder
@Entity
@Table(name = "items_in_order", schema = "computer_games_e_shop_storage")
public class ItemInOrder implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(unique = true)
    private OrderGamePrice orderGamePrice;

    @Column(name = "number")
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "game_game_platform_id", insertable = false, updatable = false)
    private GamePrice gamePrice;
}