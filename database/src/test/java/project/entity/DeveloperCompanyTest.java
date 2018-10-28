package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertNotNull;

public class DeveloperCompanyTest {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    @Test
    public void checkSaveAndGet() {
        try (Session session = SESSION_FACTORY.openSession()) {
            DeveloperCompany developerCompany = DeveloperCompany.builder()
                    .name("company")
                    .build();
            Serializable savedId = session.save(developerCompany);
            assertNotNull(savedId);

            session.evict(developerCompany);

            DeveloperCompany savedCompany = session.find(DeveloperCompany.class, savedId);
            assertNotNull(savedCompany);
        }
    }

    @Test
    public void checkGetGames() {
        try (Session session = SESSION_FACTORY.openSession()) {
            DeveloperCompany developerCompany = DeveloperCompany.builder()
                    .name("developer company")
                    .build();
            session.save(developerCompany);

            Game game = Game.builder()
                    .name("game")
                    .description("new game")
                    .developerCompany(developerCompany)
                    .build();
            session.save(game);

            session.refresh(developerCompany);
            System.out.println(developerCompany.getGames());
        }
    }
}
