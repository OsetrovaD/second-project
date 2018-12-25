package com.osetrova.project.service;

import com.osetrova.project.dto.gamepricedto.GamePriceForBasketDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.entity.Storage;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.enumonly.GamePlatform;
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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class GamePriceServiceTest {

    @Autowired
    private GamePriceService gamePriceService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePriceRepository gamePriceRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkFindByIdForBasket() {
        Game game = Game.builder()
                .name("priceServiceTestGame")
                .description("new game")
                .build();
        Game savedGame = gameRepository.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                .price(20)
                .build();
        GamePrice savedGamePrice = gamePriceRepository.save(gamePrice);

        Storage storage = Storage.builder()
                .gamePrice(gamePrice)
                .number((short) 2)
                .lastAdditionDate(LocalDate.of(2018, 7, 18))
                .build();
        storageRepository.save(storage);

        GamePriceForBasketDto forBasketDto = GamePriceForBasketDto.builder()
                .id(savedGamePrice.getId())
                .gameId(savedGame.getId())
                .gameName("priceServiceTestGame")
                .gamePlatform(GamePlatform.PC)
                .price(20)
                .numberInStorage((short) 2)
                .number((short) 1)
                .build();

        manager.detach(game);
        manager.detach(gamePrice);
        manager.detach(storage);

        GamePriceForBasketDto forBasket = gamePriceService.findByIdForBasket(savedGamePrice.getId());

        assertEquals(forBasket, forBasketDto);
    }
}
