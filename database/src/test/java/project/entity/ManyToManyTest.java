package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import project.connectionutil.ConnectionUtil;
import project.entity.enumonly.AgeLimit;

import java.io.Serializable;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ManyToManyTest {

    private static final SessionFactory SESSION_FACTORY = ConnectionUtil.getSessionFactory();

    @Test
    public void checkInsertByGameIntoGameSubgenre() {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            Game game = Game.builder()
                    .name("game name")
                    .description("new game")
                    .ageLimit(AgeLimit.EVERYONE)
                    .build();
            Serializable savedGameId = session.save(game);

            Genre genre = Genre.builder()
                    .name("some genre")
                    .build();
            session.save(genre);

            Subgenre subgenre = Subgenre.builder()
                    .name("some subgenre")
                    .genre(genre)
                    .build();
            session.save(subgenre);
            session.getTransaction().commit();

            session.refresh(game);
            session.refresh(subgenre);

            session.beginTransaction();
            game.addSubgenre(subgenre);
            session.getTransaction().commit();

            session.evict(game);

            Game savedGame = session.find(Game.class, savedGameId);

            assertThat(savedGame.getSubgenres(), hasSize(1));
            assertThat(subgenre.getGames(), hasSize(1));
        }
    }

    @Test
    public void checkInsertBySubgenreIntoGameSubgenre() {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            Game game = Game.builder()
                    .name("game_1")
                    .description("new game")
                    .ageLimit(AgeLimit.EVERYONE)
                    .minimalSystemRequirements("minimal system requirements")
                    .build();
            session.save(game);

            Genre genre = Genre.builder()
                    .name("some new genre")
                    .build();
            session.save(genre);

            Subgenre subgenre = Subgenre.builder()
                    .name("new subgenre")
                    .genre(genre)
                    .build();
            Serializable savedSubgenreId = session.save(subgenre);
            session.getTransaction().commit();

            session.refresh(game);
            session.refresh(subgenre);

            session.beginTransaction();
            subgenre.addGame(game);
            session.getTransaction().commit();

            session.evict(subgenre);

            Subgenre savedSubgenre = session.find(Subgenre.class, savedSubgenreId);

            assertThat(game.getSubgenres(), hasSize(1));
            assertThat(savedSubgenre.getGames(), hasSize(1));
        }
    }
}
