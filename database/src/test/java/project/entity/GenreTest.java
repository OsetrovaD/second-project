package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertNotNull;

public class GenreTest {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    @Test
    public void checkSaveAndGet() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Genre genre = Genre.builder()
                    .name("genre")
                    .build();
            Serializable savedGenreId = session.save(genre);
            assertNotNull(savedGenreId);

            session.evict(genre);

            Genre savedGenre = session.find(Genre.class, savedGenreId);
            assertNotNull(savedGenre);
        }
    }

    @Test
    public void checkGetSubgenres() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Genre genre = Genre.builder()
                    .name("new genre")
                    .build();
            session.save(genre);

            Subgenre subgenre = Subgenre.builder()
                    .name("subgenre")
                    .genre(genre)
                    .build();
            session.save(subgenre);

            session.refresh(genre);
            System.out.println(genre.getSubgenres());
        }
    }
}
