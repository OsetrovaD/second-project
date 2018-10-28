package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import project.entity.enumonly.AgeLimit;

import java.io.Serializable;

public class ManyToManyTest {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

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

            System.out.println(savedGame.getSubgenres());
            System.out.println(subgenre.getGames());
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

            System.out.println(game.getSubgenres());
            System.out.println(savedSubgenre.getGames());
        }
    }
}
