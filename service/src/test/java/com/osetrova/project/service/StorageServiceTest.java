package com.osetrova.project.service;

import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.entity.Storage;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.exception.DaoException;
import com.osetrova.project.jparepository.GamePriceRepository;
import com.osetrova.project.jparepository.StorageRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import com.osetrova.project.serviceconfig.ServiceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class StorageServiceTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePriceRepository gamePriceRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkFindByGamePriceId() {
        Game game = Game.builder()
                .name("storageServiceTestFirstGame")
                .description("new game")
                .build();
        gameRepository.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                .price(20)
                .build();
        GamePrice savedGamePrice = gamePriceRepository.save(gamePrice);

        Storage storage = Storage.builder()
                .gamePrice(gamePrice)
                .number((short)15)
                .lastAdditionDate(LocalDate.of(2018, 7, 18))
                .build();
        storageRepository.save(storage);

        Storage storageByGamePriceId = storageService.findByGamePriceId(savedGamePrice.getId());
        assertNotNull(storageByGamePriceId);
    }

    @Test
    public void checkAddToStorage() {
        Game game = Game.builder()
                .name("orderServiceTestSecondGame")
                .description("new game")
                .build();
        gameRepository.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                .price(20)
                .build();
        gamePriceRepository.save(gamePrice);

        Storage storage = Storage.builder()
                .gamePrice(gamePrice)
                .number((short)15)
                .lastAdditionDate(LocalDate.of(2018, 1, 12))
                .build();
        Storage savedStorage = storageRepository.save(storage);
        storage.setNumber((short)10);
        Storage updatedStorage = storageService.addToStorage(storage);
        manager.flush();

        manager.detach(storage);
        Storage foundStorage = storageRepository.findById(savedStorage.getId()).orElseThrow(DaoException::new);
        assertEquals(updatedStorage.getNumber(), foundStorage.getNumber());
    }
}
