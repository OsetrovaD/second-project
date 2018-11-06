package project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.entity.baseentity.BaseEntity;
import project.entity.embeddable.OrderGamePrice;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true,
        exclude = {
        "orderGamePrice",
        "number",
        "order",
        "gamePrice",
        })
@Builder
@Entity
@Table(name = "items_in_order", schema = "computer_games_e_shop_storage")
public class ItemInOrder extends BaseEntity<Long> {

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