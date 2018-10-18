package service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.UserDao;
import project.entity.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    public User getById(Long userId) {
        return UserDao.getInstance().getById(userId);
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
