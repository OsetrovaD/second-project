package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import project.connectionutil.ConnectionUtil;
import project.entity.embeddable.GameGamePlatform;
import project.entity.embeddable.GameScreenshot;
import project.entity.enumonly.AgeLimit;
import project.entity.enumonly.GamePlatform;
import project.entity.enumonly.Role;

import java.io.Serializable;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class GameTest {

    private static final SessionFactory SESSION_FACTORY = ConnectionUtil.getSessionFactory();

    @Test
    public void checkSaveAndGet() {
        try (Session session = SESSION_FACTORY.openSession()) {
            DeveloperCompany developerCompany = DeveloperCompany.builder()
                    .name("developer company name")
                    .build();
            session.save(developerCompany);

            Game game = Game.builder()
                    .name("some new game")
                    .description("new game")
                    .developerCompany(developerCompany)
                    .ageLimit(AgeLimit.EVERYONE)
                    .build();
            Serializable savedGameId = session.save(game);
            assertNotNull(savedGameId);

            session.evict(game);

            Game savedGame = session.find(Game.class, savedGameId);
            assertNotNull(savedGame);
        }
    }

    @Test
    public void checkGetScreenshots() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Game game = Game.builder()
                    .name("some game")
                    .description("new game")
                    .build();
            session.save(game);

            session.beginTransaction();
            Screenshot screenshot = Screenshot.builder()
                    .gameScreenshot(GameScreenshot.of(game.getId(), "url"))
                    .build();
            session.save(screenshot);
            session.getTransaction().commit();

            session.refresh(game);

            assertThat(game.getScreenshots(), hasSize(1));
        }
    }

    @Test
    public void checkGetComments() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Game game = Game.builder()
                    .name("game game")
                    .description("new game")
                    .build();
            session.save(game);

            User user = new Admin("new_admin", "password", "email_email@email.email", Role.ADMIN, 100);
            session.save(user);

            Comment comment = Comment.builder()
                    .text("comment")
                    .date(LocalDate.of(2018, 4, 15))
                    .game(game)
                    .user(user)
                    .build();
            session.save(comment);

            session.refresh(game);
            assertThat(game.getComments(), hasSize(1));
        }
    }

    @Test
    public void checkGetGamePrices() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Game game = Game.builder()
                    .name("some test")
                    .description("new game")
                    .build();
            session.save(game);

            GamePrice gamePrice = GamePrice.builder()
                    .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                    .price(20)
                    .build();
            session.save(gamePrice);

            session.refresh(game);
            assertThat(game.getGamePrices(), hasSize(1));
        }
    }
}
