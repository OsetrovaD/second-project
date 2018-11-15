package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseConfiguration;
import com.osetrova.project.dao.entitydao.CommentDaoImpl;
import com.osetrova.project.dao.entitydao.GameDaoImpl;
import com.osetrova.project.dao.entitydao.UserDaoImpl;
import com.osetrova.project.entity.enumonly.Role;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
@Transactional
public class CommentTest {

    @Autowired
    private CommentDaoImpl commentDao;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private GameDaoImpl gameDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void checkSaveAndGet() {
        User user = new Admin("admin_test", "password", "email@email.email", Role.ADMIN, 100);
        userDao.save(user);

        Game game = Game.builder()
                .name("new game")
                .description("new game")
                .build();
        gameDao.save(game);

        Comment comment = Comment.builder()
                .text("comment")
                .date(LocalDate.of(2018, 4, 15))
                .game(game)
                .user(user)
                .build();
        Long savedId = commentDao.save(comment);
        assertNotNull(savedId);

        sessionFactory.getCurrentSession().evict(comment);

        Comment savedComment = commentDao.findById(savedId);
        assertNotNull(savedComment);
    }
}
