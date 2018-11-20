package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.embeddable.GameScreenshot;
import com.osetrova.project.jparepository.ScreenshotRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class ScreenshotTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EntityManager manager;

    @Autowired
    private ScreenshotRepository screenshotRepository;

    @Test
    public void checkSaveAndGet() {
        Game game = Game.builder()
                .name("game_3")
                .description("new game")
                .build();
        gameRepository.save(game);

        GameScreenshot gameScreenshot = GameScreenshot.of(game.getId(), "url_1");

        Screenshot screenshot = Screenshot.builder()
                .gameScreenshot(gameScreenshot)
                .build();
        screenshotRepository.save(screenshot);
        manager.flush();

        manager.detach(screenshot);

        Optional<Screenshot> optionalScreenshot = screenshotRepository.findById(gameScreenshot);
        assertTrue(optionalScreenshot.isPresent());
    }

    @Test
    public void checkGetGame() {
        Game game = Game.builder()
                .name("game_3")
                .description("new game")
                .build();
        gameRepository.save(game);

        GameScreenshot gameScreenshot = GameScreenshot.of(game.getId(), "url_2");

        Screenshot screenshot = Screenshot.builder()
                .gameScreenshot(gameScreenshot)
                .build();
        screenshotRepository.save(screenshot);
        manager.flush();

        manager.clear();
        Optional<Screenshot> optionalScreenshot = screenshotRepository.findById(gameScreenshot);
        if (optionalScreenshot.isPresent()) {
            screenshot = optionalScreenshot.get();
        }
        assertEquals(game, screenshot.getGame());
    }
}
