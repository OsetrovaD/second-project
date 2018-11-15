package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseConfiguration;
import com.osetrova.project.dao.entitydao.CommentDaoImpl;
import com.osetrova.project.dao.entitydao.DeveloperCompanyDaoImpl;
import com.osetrova.project.dao.entitydao.GameDaoImpl;
import com.osetrova.project.dao.entitydao.GamePriceDaoImpl;
import com.osetrova.project.dao.entitydao.ScreenshotDaoImpl;
import com.osetrova.project.dao.entitydao.UserDaoImpl;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.embeddable.GameScreenshot;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.entity.enumonly.Role;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
@Transactional
public class GameTest {

    @Autowired
    private DeveloperCompanyDaoImpl developerCompanyDao;

    @Autowired
    private GameDaoImpl gameDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ScreenshotDaoImpl screenshotDao;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private CommentDaoImpl commentDao;

    @Autowired
    private GamePriceDaoImpl gamePriceDao;

    @Test
    public void checkSaveAndGet() {
        DeveloperCompany developerCompany = DeveloperCompany.builder()
                .name("developer company name")
                .build();
        developerCompanyDao.save(developerCompany);

        Game game = Game.builder()
                .name("some new game")
                .description("new game")
                .developerCompany(developerCompany)
                .ageLimit(AgeLimit.EVERYONE)
                .build();
        Long savedGameId = gameDao.save(game);
        assertNotNull(savedGameId);

        sessionFactory.getCurrentSession().evict(game);

        Game savedGame = gameDao.findById(savedGameId);
        assertNotNull(savedGame);
    }

    @Test
    public void checkGetScreenshots() {
        Game game = Game.builder()
                .name("some game")
                .description("new game")
                .build();
        gameDao.save(game);

        Screenshot screenshot = Screenshot.builder()
                .gameScreenshot(GameScreenshot.of(game.getId(), "url"))
                .build();
        screenshotDao.save(screenshot);
        sessionFactory.getCurrentSession().flush();

        sessionFactory.getCurrentSession().refresh(game);

        assertThat(game.getScreenshots(), hasSize(1));
    }

    @Test
    public void checkGetComments() {
        Game game = Game.builder()
                .name("game game")
                .description("new game")
                .build();
        gameDao.save(game);

        User user = new Admin("new_admin", "password", "email_email@email.email", Role.ADMIN, 100);
        userDao.save(user);

        Comment comment = Comment.builder()
                .text("comment")
                .date(LocalDate.of(2018, 4, 15))
                .game(game)
                .user(user)
                .build();
        commentDao.save(comment);

        sessionFactory.getCurrentSession().refresh(game);
        assertThat(game.getComments(), hasSize(1));
    }

    @Test
    public void checkGetGamePrices() {
        Game game = Game.builder()
                .name("some test")
                .description("new game")
                .build();
        gameDao.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                .price(20)
                .build();
        gamePriceDao.save(gamePrice);

        sessionFactory.getCurrentSession().refresh(game);
        assertThat(game.getGamePrices(), hasSize(1));
    }
}
