package project.dao.entitydao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import project.dao.BaseDaoImpl;
import project.dao.entitydaointerface.UserDao;
import project.dto.LoginPasswordDto;
import project.entity.User;
import project.entity.User_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public Optional<User> getByLoginAndPassword(Session session, LoginPasswordDto loginPasswordDto) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);

        criteria.select(userRoot)
                .where(
                    cb.equal(userRoot.get(User_.login), loginPasswordDto.getLogin()),
                    cb.equal(userRoot.get(User_.password), loginPasswordDto.getPassword())
                );
        return session.createQuery(criteria).list().stream().findFirst();
    }
}
