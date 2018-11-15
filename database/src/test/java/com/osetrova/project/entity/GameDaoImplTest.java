package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseConfiguration;
import com.osetrova.project.dao.entitydao.GameDaoImpl;
import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.enumonly.AgeLimit;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
@Sql("classpath:test-script.sql")
@Transactional
public class GameDaoImplTest {

    @Autowired
    private GameDaoImpl gameDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void filterByAllParametersTest() {
        GameFilterDto filters = GameFilterDto.of(2012, AgeLimit.MATURE, 40);
        LimitOffsetDto limitOffset = LimitOffsetDto.of(0, 0);

        List<Game> games = gameDao.filterAllGames(filters, limitOffset);
        List<String> gamesNames = games.stream().map(Game::getName).collect(toList());
        assertThat(games, hasSize(2));
        assertThat(gamesNames, containsInAnyOrder("Diablo III", "Far Cry 3"));
    }

    @Test
    public void filterByTwoParametersTest() {
        GameFilterDto filters = GameFilterDto.builder()
            .ageLimit(AgeLimit.TEEN)
            .price(30)
            .build();
        LimitOffsetDto limitOffset = LimitOffsetDto.of(0, 0);

        List<Game> games = gameDao.filterAllGames(filters, limitOffset);
        List<String> gamesNames = games.stream().map(Game::getName).collect(toList());
        assertThat(games, hasSize(1));
        assertThat(gamesNames, contains("Syberia 3"));
    }

    @Test
    public void filterByOneParameterTest() {
        GameFilterDto filters = GameFilterDto.builder()
            .issueYear(2017)
            .build();
        LimitOffsetDto limitOffset = LimitOffsetDto.of(0, 0);

        List<Game> games = gameDao.filterAllGames(filters, limitOffset);
        List<String> gamesNames = games.stream().map(Game::getName).collect(toList());
        assertThat(games, hasSize(1));
        assertThat(gamesNames, contains("Syberia 3"));
    }

    @Test
    public void filterWithLimitAndOffset() {
        GameFilterDto filters = GameFilterDto.builder()
            .ageLimit(AgeLimit.MATURE)
            .build();
        LimitOffsetDto limitOffset = LimitOffsetDto.of(2, 1);

        List<Game> games = gameDao.filterAllGames(filters, limitOffset);
        List<String> gamesNames = games.stream().map(Game::getName).collect(toList());
        assertThat(games, hasSize(2));
        assertThat(gamesNames, containsInAnyOrder("Diablo III", "Far Cry 3"));
    }

    @Test
    public void findByIdTest() {
        Optional<Game> diablo3 = gameDao.findByName("Diablo III");
        Long gameId = null;

        if (diablo3.isPresent()) {
            gameId = diablo3.get().getId();
        }
        sessionFactory.getCurrentSession().clear();

        Game game = gameDao.findById(gameId);
        assertNotNull(game);
    }

    @Test
    public void findAllTest() {
        List<Game> allGames = gameDao.findAll();
        List<String> gamesNames = allGames.stream().map(Game::getName).collect(toList());
        assertThat(gamesNames, hasItems("Diablo III", "Far Cry 3", "Mass Effect 3", "Mass Effect", "Syberia 3"));
    }

    @Test
    public void updateTest() {
        Optional<Game> diablo3 = gameDao.findByName("Diablo III");
        Long gameId = null;

        if (diablo3.isPresent()) {
            gameId = diablo3.get().getId();
        }
        sessionFactory.getCurrentSession().clear();

        Game game = gameDao.findById(gameId);
        game.setDescription("description");

        gameDao.update(game);
        sessionFactory.getCurrentSession().flush();

        sessionFactory.getCurrentSession().evict(game);

        game = gameDao.findById(gameId);
        assertEquals("description", game.getDescription());
    }

    @Test
    public void saveTest() {
        Game game = Game.builder()
            .name("L.A. Noire")
            .issueYear(2011)
            .ageLimit(AgeLimit.MATURE)
            .build();
        Long savedGameId = gameDao.save(game);
        assertNotNull(savedGameId);

        gameDao.delete(game);
    }

    @Test
    public void deleteTest() {
        Game theLastOfUs = Game.builder()
            .ageLimit(AgeLimit.MATURE)
            .name("The Last of Us")
            .issueYear(2013)
            .build();
        Long savedGameId = gameDao.save(theLastOfUs);

        gameDao.delete(theLastOfUs);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        List<Game> allGames = gameDao.findAll();
        boolean present = allGames.stream().anyMatch(game -> savedGameId.equals(game.getId()));
        assertFalse(present);
    }
}
