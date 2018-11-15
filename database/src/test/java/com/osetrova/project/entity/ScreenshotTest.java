package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseConfiguration;
import com.osetrova.project.connectionutil.ConnectionUtil;
import com.osetrova.project.dao.entitydao.GameDaoImpl;
import com.osetrova.project.dao.entitydao.ScreenshotDaoImpl;
import com.osetrova.project.entity.embeddable.GameScreenshot;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
@Transactional
public class ScreenshotTest {

    @Autowired
    private GameDaoImpl gameDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ScreenshotDaoImpl screenshotDao;

    @Test
    public void checkSaveAndGet() {
        Game game = Game.builder()
                .name("game_3")
                .description("new game")
                .build();
        gameDao.save(game);

        GameScreenshot gameScreenshot = GameScreenshot.of(game.getId(), "url_1");

        Screenshot screenshot = Screenshot.builder()
                .gameScreenshot(gameScreenshot)
                .build();
        screenshotDao.save(screenshot);
        sessionFactory.getCurrentSession().flush();

        sessionFactory.getCurrentSession().evict(screenshot);

        Screenshot savedScreenshot = screenshotDao.findById(gameScreenshot);
        assertNotNull(savedScreenshot);
    }

    @Test
    public void checkGetGame() {
        Game game = Game.builder()
                .name("game_3")
                .description("new game")
                .build();
        gameDao.save(game);

        GameScreenshot gameScreenshot = GameScreenshot.of(game.getId(), "url_2");

        Screenshot screenshot = Screenshot.builder()
                .gameScreenshot(gameScreenshot)
                .build();
        screenshotDao.save(screenshot);
        sessionFactory.getCurrentSession().flush();

        sessionFactory.getCurrentSession().clear();
        screenshot = screenshotDao.findById(gameScreenshot);
        assertEquals(game, screenshot.getGame());
    }
}
