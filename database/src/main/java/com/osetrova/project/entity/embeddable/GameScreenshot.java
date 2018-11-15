package com.osetrova.project.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
@Builder
@Embeddable
public class GameScreenshot implements Serializable {

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "screenshot_url")
    private String screenshotUrl;
}