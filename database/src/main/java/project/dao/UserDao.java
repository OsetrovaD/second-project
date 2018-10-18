package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.entity.User;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();
    private static final String GET_BY_ID =
            "SELECT id, login, role, first_name, last_name, address, e_mail, phone_number " +
                    "FROM computer_games_e_shop_storage.user_data WHERE id = ?";

    public User getById(Long userId) {
        User user = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = User.builder()
                        .userId(resultSet.getLong("id"))
                        .login(resultSet.getString("login"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .address(resultSet.getString("address"))
                        .email(resultSet.getString("e_mail"))
                        .phoneNumber(resultSet.getString("phone_number"))
                        .build();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return user;
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
