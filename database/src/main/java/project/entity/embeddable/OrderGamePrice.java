package project.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
@Builder
@Embeddable
public class OrderGamePrice {

    @Column(name = "game_game_platform_id")
    private Long gameGamePlatformId;

    @Column(name = "order_id")
    private Long orderId;
}
