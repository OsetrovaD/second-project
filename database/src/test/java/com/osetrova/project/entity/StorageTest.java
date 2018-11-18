package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.jparepository.GamePriceRepository;
import com.osetrova.project.jparepository.StorageRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class StorageTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePriceRepository gamePriceRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Test
    public void checkSaveAndGet() {
        Game game = Game.builder()
                .name("game_4")
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
                .number((short) 2)
                .lastAdditionDate(LocalDate.of(2018, 7, 18))
                .build();
        Storage savedStorage = storageRepository.save(storage);
        assertNotNull(savedStorage);
        Long savedStorageId = savedStorage.getId();

        manager.detach(storage);

        Optional<Storage> optionalStorage = storageRepository.findById(savedStorageId);
        assertTrue(optionalStorage.isPresent());
    }
}
