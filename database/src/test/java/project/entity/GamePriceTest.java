package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import project.connectionutil.ConnectionUtil;
import project.entity.embeddable.GameGamePlatform;
import project.entity.enumonly.GamePlatform;

import java.io.Serializable;

import static org.junit.Assert.assertNotNull;

public class GamePriceTest {

    private static final SessionFactory SESSION_FACTORY = ConnectionUtil.getSessionFactory();

    @Test
    public void checkSaveAdnGet() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Game game = Game.builder()
                    .name("test game")
                    .description("new game")
                    .build();
            session.save(game);

            GamePrice gamePrice = GamePrice.builder()
                    .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                    .price(20)
                    .build();
            Serializable savedGamePriceId = session.save(gamePrice);
            assertNotNull(savedGamePriceId);

            session.evict(gamePrice);

            GamePrice savedGamePrice = session.find(GamePrice.class, savedGamePriceId);
            assertNotNull(savedGamePrice);
        }
    }
}
