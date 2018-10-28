package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertNotNull;

public class SubgenreTest {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    @Test
    public void checkSaveAndGet() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Genre genre = Genre.builder()
                    .name("genre name")
                    .build();
            session.save(genre);

            Subgenre subgenre = Subgenre.builder()
                    .name("subgenre name")
                    .genre(genre)
                    .build();
            Serializable savedSubgenreId = session.save(subgenre);
            assertNotNull(savedSubgenreId);

            session.evict(subgenre);

            Subgenre savedSubgenre = session.find(Subgenre.class, savedSubgenreId);
            assertNotNull(savedSubgenre);
        }
    }
}
