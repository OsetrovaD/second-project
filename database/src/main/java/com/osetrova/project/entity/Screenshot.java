package com.osetrova.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.osetrova.project.entity.baseentity.BaseEntity;
import com.osetrova.project.entity.embeddable.GameScreenshot;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "game")
@Builder
@Entity
@Table(name = "game_screenshot", schema = "computer_games_e_shop_storage")
public class Screenshot implements BaseEntity<GameScreenshot> {

    @EmbeddedId
    private GameScreenshot gameScreenshot;

    @ManyToOne
    @JoinColumn(name = "game_id", updatable = false, insertable = false)
    private Game game;

    @Override
    public GameScreenshot getId() {
        return this.gameScreenshot;
    }
}