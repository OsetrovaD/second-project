package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.jparepository.GamePriceRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class GamePriceTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePriceRepository gamePriceRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkSaveAdnGet() {
        Game game = Game.builder()
                .name("test game")
                .description("new game")
                .build();
        gameRepository.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                .price(20)
                .build();
        GamePrice savedGamePrice = gamePriceRepository.save(gamePrice);
        assertNotNull(savedGamePrice);
        Long savedGamePriceId = savedGamePrice.getId();

        manager.detach(gamePrice);

        Optional<GamePrice> optionalGamePrice = gamePriceRepository.findById(savedGamePriceId);
        assertTrue(optionalGamePrice.isPresent());
    }
}
