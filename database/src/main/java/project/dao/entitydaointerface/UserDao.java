package project.dao.entitydaointerface;

import org.hibernate.Session;
import project.dao.BaseDao;
import project.dto.LoginPasswordDto;
import project.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User, Long> {

    Optional<User> getByLoginAndPassword(Session session, LoginPasswordDto loginPasswordDto);
}
