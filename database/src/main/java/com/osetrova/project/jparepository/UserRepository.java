package com.osetrova.project.jparepository;

import com.osetrova.project.entity.User;
import com.osetrova.project.entity.enumonly.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String login);

    List<User> findAllByRoleInOrderById(List<Role> roles);

    @Modifying
    @Query(value = "DELETE FROM computer_games_e_shop_storage.simple_user_detail WHERE user_id = :userId", nativeQuery = true)
    int deleteSimpleUserDetails(@Param("userId") Long id);

    @Modifying
    @Query(value = "DELETE FROM computer_games_e_shop_storage.admin_detail WHERE user_id = :userId", nativeQuery = true)
    int deleteAdminDetails(@Param("userId") Long id);

    @Modifying
    @Query(value = "INSERT INTO computer_games_e_shop_storage.admin_detail (user_id, salary) VALUES (:userId, :salary)", nativeQuery = true)
    int addAdminDetails(@Param("userId") Long id, @Param("salary") Integer salary);

    @Modifying
    @Query(value = "UPDATE computer_games_e_shop_storage.simple_user_detail "
            + "SET last_visit_date = :lastVisitDate "
            + "WHERE (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = :login) = user_id", nativeQuery = true)
    int changeLastVisitDate(@Param("login") String login, @Param("lastVisitDate") LocalDate lastVisitDate);

    @Modifying
    @Query(value = "INSERT INTO computer_games_e_shop_storage.simple_user_detail (user_id, last_visit_date) VALUES (:userId, :lastVisitDate)", nativeQuery = true)
    int addSimpleUserDetails(@Param("userId") Long id, @Param("lastVisitDate") LocalDate lastVisitDate);

    @Modifying
    @Query("update User u set u.role = :role where u.id = :userId")
    int changeUserRole(@Param("userId") Long userId, @Param("role") Role role);

    @Modifying
    @Query(value = "UPDATE computer_games_e_shop_storage.admin_detail SET salary = :salary WHERE user_id = :userId", nativeQuery = true)
    int changeSalary(@Param("userId") Long userId, @Param("salary") Integer salary);

    @Modifying
    @Query("update User u set u.firstName = :firstName where u.login = :login")
    int changeFirstName(@Param("login") String login, @Param("firstName") String firstName);

    @Modifying
    @Query("update User u set u.lastName = :lastName where u.login = :login")
    int changeLastName(@Param("login") String login, @Param("lastName") String lastName);

    @Modifying
    @Query("update User u set u.email = :email where u.login = :login")
    int changeEmail(@Param("login") String login, @Param("email") String email);

    @Modifying
    @Query("update User u set u.phoneNumber = :phoneNumber where u.login = :login")
    int changePhoneNumber(@Param("login") String login, @Param("phoneNumber") String phoneNumber);

    @Modifying
    @Query("update User u set u.address = :address where u.login = :login")
    int changeAddress(@Param("login") String login, @Param("address") String address);
}
