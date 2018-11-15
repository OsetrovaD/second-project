package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseConfiguration;
import com.osetrova.project.dao.entitydao.GameDaoImpl;
import com.osetrova.project.dao.entitydao.GamePriceDaoImpl;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.enumonly.GamePlatform;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
@Transactional
public class GamePriceTest {

    @Autowired
    private GameDaoImpl gameDao;

    @Autowired
    private GamePriceDaoImpl gamePriceDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void checkSaveAdnGet() {
        Game game = Game.builder()
                .name("test game")
                .description("new game")
                .build();
        gameDao.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                .price(20)
                .build();
        Long savedGamePriceId = gamePriceDao.save(gamePrice);
        assertNotNull(savedGamePriceId);

        sessionFactory.getCurrentSession().evict(gamePrice);

        GamePrice savedGamePrice = gamePriceDao.findById(savedGamePriceId);
        assertNotNull(savedGamePrice);
    }
}
