package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseConfiguration;
import com.osetrova.project.connectionutil.ConnectionUtil;
import com.osetrova.project.dao.entitydao.CommentDaoImpl;
import com.osetrova.project.dao.entitydao.GameDaoImpl;
import com.osetrova.project.dao.entitydao.UserDaoImpl;
import com.osetrova.project.entity.enumonly.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
@Transactional
public class UserTest {

    @Autowired
    private CommentDaoImpl commentDao;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private GameDaoImpl gameDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void checkSaveAndGetAdmin() {
        User admin = new Admin("adminLogin", "password", "mail_email@email.email", Role.ADMIN, 100);
        Long savedUserId = userDao.save(admin);
        assertNotNull(savedUserId);

        sessionFactory.getCurrentSession().evict(admin);

        User savedAdmin = userDao.findById(savedUserId);
        assertNotNull(savedAdmin);
    }

    @Test
    public void checkSaveAndGetSimpleUser() {
        User simpleUser = new SimpleUser("simpleUser", "password", "some_email@email.email",
                Role.USER, LocalDate.of(2018, 4, 7));
        Long savedUserId = userDao.save(simpleUser);
        assertNotNull(savedUserId);

        sessionFactory.getCurrentSession().evict(simpleUser);

        User savedSimpleUser = userDao.findById(savedUserId);
        assertNotNull(savedSimpleUser);
    }

    @Test
    public void checkGetComments() {
        User user = new Admin("someLogin", "password", "mail@email.email", Role.ADMIN, 100);
        Long userId = userDao.save(user);

        Game game = Game.builder()
                .name("game_5")
                .description("new game")
                .build();
        gameDao.save(game);

        Comment comment = Comment.builder()
                .text("comment")
                .date(LocalDate.of(2018, 4, 15))
                .game(game)
                .user(user)
                .build();
        commentDao.save(comment);

        sessionFactory.getCurrentSession().clear();
        user = userDao.findById(userId);
        assertThat(user.getComments(), hasSize(1));
    }
}
