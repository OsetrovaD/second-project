package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import project.entity.enumonly.Role;

import java.io.Serializable;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

public class CommentTest {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    @Test
    public void checkSaveAndGet() {
        try (Session session = SESSION_FACTORY.openSession()) {
            User user = new Admin("admin_test", "password", "email@email.email", Role.ADMIN, 100);
            session.save(user);

            Game game = Game.builder()
                    .name("new game")
                    .description("new game")
                    .build();
            session.save(game);

            Comment comment = Comment.builder()
                    .text("comment")
                    .date(LocalDate.of(2018, 4, 15))
                    .game(game)
                    .user(user)
                    .build();
            Serializable savedId = session.save(comment);
            assertNotNull(savedId);

            session.evict(comment);

            Comment savedComment = session.find(Comment.class, savedId);
            assertNotNull(savedComment);
            System.out.println(savedComment);
        }
    }
}
