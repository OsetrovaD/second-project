package com.osetrova.project.jparepository;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.entity.Storage;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.enumonly.GamePlatform;
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

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class StorageRepositoryTest {

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private EntityManager manager;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePriceRepository gamePriceRepository;

    @Test
    public void checkFindByGamePriceId() {
        Game game = Game.builder()
                .name("storageTestName")
                .description("new game")
                .build();
        gameRepository.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PLAYSTATION_3))
                .price(28)
                .build();
        GamePrice price = gamePriceRepository.save(gamePrice);

        Storage storage = Storage.builder()
                .gamePrice(gamePrice)
                .number((short) 2)
                .lastAdditionDate(LocalDate.of(2018, 7, 18))
                .build();
        storageRepository.save(storage);

        manager.detach(storage);
        Optional<Storage> foundStorage = storageRepository.findByGamePriceId(price.getId());

        assertTrue(foundStorage.isPresent());
    }
}
