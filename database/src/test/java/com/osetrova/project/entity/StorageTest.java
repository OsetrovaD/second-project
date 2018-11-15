package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseConfiguration;
import com.osetrova.project.dao.entitydao.GameDaoImpl;
import com.osetrova.project.dao.entitydao.GamePriceDaoImpl;
import com.osetrova.project.dao.entitydao.StorageDaoImpl;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.enumonly.GamePlatform;
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
public class StorageTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private GameDaoImpl gameDao;

    @Autowired
    private GamePriceDaoImpl gamePriceDao;

    @Autowired
    private StorageDaoImpl storageDao;

    @Test
    public void checkSaveAndGet() {
        Game game = Game.builder()
                .name("game_4")
                .description("new game")
                .build();
        gameDao.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                .price(20)
                .build();
        gamePriceDao.save(gamePrice);

        Storage storage = Storage.builder()
                .gamePrice(gamePrice)
                .number((short) 2)
                .lastAdditionDate(LocalDate.of(2018, 7, 18))
                .build();
        Long savedStorageId = storageDao.save(storage);
        assertNotNull(savedStorageId);

        sessionFactory.getCurrentSession().evict(storage);

        Storage savedStorage = storageDao.findById(savedStorageId);
        assertNotNull(savedStorage);
    }
}
