package com.osetrova.project.dao.entitydao;

import com.osetrova.project.dao.BaseDaoImpl;
import com.osetrova.project.dao.entitydaointerface.UserDao;
import com.osetrova.project.dto.LoginPasswordDto;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public Optional<User> getByLoginAndPassword(LoginPasswordDto loginPasswordDto) {
        CriteriaBuilder cb = getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);

        criteria.select(userRoot)
                .where(
                    cb.equal(userRoot.get(User_.login), loginPasswordDto.getLogin()),
                    cb.equal(userRoot.get(User_.password), loginPasswordDto.getPassword())
                );
        return getSessionFactory().getCurrentSession().createQuery(criteria).list().stream().findFirst();
    }
}
