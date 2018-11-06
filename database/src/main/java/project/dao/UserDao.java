package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import project.connection.ApplicationSessionFactory;
import project.entity.User;
import project.exception.DaoException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    public User getById(Long userId) {
        User user;

        try (Session session = ApplicationSessionFactory.getSessionFactory().openSession()) {
            user = session.find(User.class, userId);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
        return user;
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
