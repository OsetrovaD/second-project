package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import project.connectionutil.ConnectionUtil;
import project.entity.embeddable.GameScreenshot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ScreenshotTest {

    private static final SessionFactory SESSION_FACTORY = ConnectionUtil.getSessionFactory();

    @Test
    public void checkSaveAndGet() {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            Game game = Game.builder()
                    .name("game_3")
                    .description("new game")
                    .build();
            session.save(game);

            GameScreenshot gameScreenshot = GameScreenshot.of(game.getId(), "url_1");

            Screenshot screenshot = Screenshot.builder()
                    .gameScreenshot(gameScreenshot)
                    .build();
            session.save(screenshot);
            session.getTransaction().commit();

            session.evict(screenshot);

            Screenshot savedScreenshot = session.find(Screenshot.class, gameScreenshot);
            assertNotNull(savedScreenshot);
        }
    }

    @Test
    public void checkGetGame() {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            Game game = Game.builder()
                    .name("game_3")
                    .description("new game")
                    .build();
            session.save(game);

            Screenshot screenshot = Screenshot.builder()
                    .gameScreenshot(GameScreenshot.of(game.getId(), "url_2"))
                    .build();
            session.save(screenshot);
            session.getTransaction().commit();

            session.refresh(screenshot);
            assertEquals(game, screenshot.getGame());
        }
    }
}
