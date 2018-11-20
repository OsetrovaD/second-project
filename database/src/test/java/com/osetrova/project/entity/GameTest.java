package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.embeddable.GameScreenshot;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.entity.enumonly.Role;
import com.osetrova.project.jparepository.CommentRepository;
import com.osetrova.project.jparepository.DeveloperCompanyRepository;
import com.osetrova.project.jparepository.GamePriceRepository;
import com.osetrova.project.jparepository.ScreenshotRepository;
import com.osetrova.project.jparepository.UserRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class GameTest {

    @Autowired
    private DeveloperCompanyRepository developerCompanyRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EntityManager manager;

    @Autowired
    private ScreenshotRepository screenshotRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GamePriceRepository gamePriceRepository;

    @Test
    public void checkSaveAndGet() {
        DeveloperCompany developerCompany = DeveloperCompany.builder()
                .name("developer company name")
                .build();
        developerCompanyRepository.save(developerCompany);

        Game game = Game.builder()
                .name("some new game")
                .description("new game")
                .developerCompany(developerCompany)
                .ageLimit(AgeLimit.EVERYONE)
                .build();
        Game savedGame = gameRepository.save(game);
        assertNotNull(savedGame);
        Long savedGameId = savedGame.getId();

        manager.detach(game);

        Optional<Game> optionalGame = gameRepository.findById(savedGameId);
        assertTrue(optionalGame.isPresent());
    }

    @Test
    public void checkGetScreenshots() {
        Game game = Game.builder()
                .name("some game")
                .description("new game")
                .build();
        gameRepository.save(game);

        Screenshot screenshot = Screenshot.builder()
                .gameScreenshot(GameScreenshot.of(game.getId(), "url"))
                .build();
        screenshotRepository.save(screenshot);
        manager.flush();

        manager.refresh(game);

        assertThat(game.getScreenshots(), hasSize(1));
    }

    @Test
    public void checkGetComments() {
        Game game = Game.builder()
                .name("game game")
                .description("new game")
                .build();
        gameRepository.save(game);

        User user = new Admin("new_admin", "password", "email_email@email.email", Role.ADMIN, 100);
        userRepository.save(user);

        Comment comment = Comment.builder()
                .text("comment")
                .date(LocalDate.of(2018, 4, 15))
                .game(game)
                .user(user)
                .build();
        commentRepository.save(comment);

        manager.refresh(game);
        assertThat(game.getComments(), hasSize(1));
    }

    @Test
    public void checkGetGamePrices() {
        Game game = Game.builder()
                .name("some test")
                .description("new game")
                .build();
        gameRepository.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                .price(20)
                .build();
        gamePriceRepository.save(gamePrice);

        manager.refresh(game);
        assertThat(game.getGamePrices(), hasSize(1));
    }
}
