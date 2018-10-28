package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import project.entity.embeddable.GameGamePlatform;
import project.entity.enumonly.GamePlatform;

import java.io.Serializable;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

public class StorageTest {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    @Test
    public void checkSaveAndGet() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Game game = Game.builder()
                    .name("game_4")
                    .description("new game")
                    .build();
            session.save(game);

            GamePrice gamePrice = GamePrice.builder()
                    .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                    .price(20)
                    .build();
            session.save(gamePrice);

            Storage storage = Storage.builder()
                    .gamePrice(gamePrice)
                    .number((short) 2)
                    .lastAdditionDate(LocalDate.of(2018, 7, 18))
                    .build();
            Serializable savedStorageId = session.save(storage);
            assertNotNull(savedStorageId);

            session.evict(storage);

            Storage savedStorage = session.find(Storage.class, savedStorageId);
            assertNotNull(savedStorage);
        }
    }
}
