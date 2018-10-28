package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import project.entity.enumonly.Role;

import java.io.Serializable;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

public class UserTest {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    @Test
    public void checkSaveAndGetAdmin() {
        try (Session session = SESSION_FACTORY.openSession()) {
            User admin = new Admin("adminLogin", "password", "mail_email@email.email", Role.ADMIN, 100);
            Serializable savedUserId = session.save(admin);
            assertNotNull(savedUserId);

            session.evict(admin);

            User savedAdmin = session.find(User.class, savedUserId);
            assertNotNull(savedAdmin);
            System.out.println(savedAdmin);
        }
    }

    @Test
    public void checkSaveAndGetSimpleUser() {
        try (Session session = SESSION_FACTORY.openSession()) {
            User simpleUser = new SimpleUser("simpleUser", "password", "some_email@email.email",
                    Role.USER, LocalDate.of(2018, 4, 7));
            Serializable savedUserId = session.save(simpleUser);
            assertNotNull(savedUserId);

            session.evict(simpleUser);

            User savedSimpleUser = session.find(SimpleUser.class, savedUserId);
            assertNotNull(savedSimpleUser);
            System.out.println(savedSimpleUser);
        }
    }

    @Test
    public void checkGetComments() {
        try (Session session = SESSION_FACTORY.openSession()) {
            User user = new Admin("someLogin", "password", "mail@email.email", Role.ADMIN, 100);
            session.save(user);

            Game game = Game.builder()
                    .name("game_5")
                    .description("new game")
                    .build();
            session.save(game);

            Comment comment = Comment.builder()
                    .text("comment")
                    .date(LocalDate.of(2018, 4, 15))
                    .game(game)
                    .user(user)
                    .build();
            session.save(comment);

            session.refresh(user);
            System.out.println(user.getComments());
        }
    }
}
