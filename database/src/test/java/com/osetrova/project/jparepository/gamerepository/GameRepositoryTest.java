package com.osetrova.project.jparepository.gamerepository;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.jparepository.GenreRepository;
import com.osetrova.project.jparepository.SubgenreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
@Sql("classpath:test-script.sql")
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SubgenreRepository subgenreRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void findByGamePlatformTest() {
        List<Game> games = gameRepository.findAllByGamePlatform(GamePlatform.PC);
        assertThat(games, hasSize(2));
    }

    @Test
    public void filterGamesTest() {
        GameFilterDto filters = GameFilterDto.of(2012, AgeLimit.MATURE, 40);
        LimitOffsetDto limitOffset = LimitOffsetDto.of(0, 0);

        List<Game> games = gameRepository.filterAllGames(filters, limitOffset);
        List<String> gamesNames = games.stream().map(Game::getName).collect(toList());
        assertThat(games, hasSize(2));
        assertThat(gamesNames, containsInAnyOrder("Diablo III", "Far Cry 3"));
    }

    @Test
    public void findByIssueYearTest() {
        List<Game> games = gameRepository.findByIssueYear(2012);
        assertThat(games, hasSize(3));
    }

    @Test
    public void findByIdTest() {
        Optional<Game> diablo3 = gameRepository.findByNameIgnoreCase("Diablo III");
        Long gameId = null;

        if (diablo3.isPresent()) {
            gameId = diablo3.get().getId();
        }
        manager.clear();

        Optional<Game> game = gameRepository.findById(Objects.requireNonNull(gameId));
        assertTrue(game.isPresent());
    }

    @Test
    public void updateTest() {
        Optional<Game> diablo3 = gameRepository.findByNameIgnoreCase("Diablo III");
        Long gameId = null;
        Game game = null;

        if (diablo3.isPresent()) {
            gameId = diablo3.get().getId();
        }
        manager.clear();

        Optional<Game> optionalGame = gameRepository.findById(Objects.requireNonNull(gameId));

        if (optionalGame.isPresent()) {
            game = optionalGame.get();
        }
        Objects.requireNonNull(game).setDescription("description");

        Integer update = gameRepository.update(game, gameId);
        assertThat(update, not(0));
        manager.flush();
        manager.detach(game);

        Optional<Game> gameOptional = gameRepository.findById(gameId);
        assertTrue(gameOptional.isPresent());
        assertEquals("description", gameOptional.get().getDescription());
    }

    @Test
    public void saveTest() {
        Game game = Game.builder()
                .name("L.A. Noire")
                .issueYear(2011)
                .ageLimit(AgeLimit.MATURE)
                .build();
        Game savedGame = gameRepository.save(game);
        assertNotNull(savedGame);
    }

    @Test
    public void deleteTest() {
        Game theLastOfUs = Game.builder()
                .ageLimit(AgeLimit.MATURE)
                .name("The Last of Us")
                .issueYear(2013)
                .build();
        Game game = gameRepository.save(theLastOfUs);
        Long savedGameId = game.getId();

        gameRepository.delete(theLastOfUs);
        manager.flush();
        manager.clear();

        List<Game> allGames = new ArrayList<>();
        gameRepository.findAll().forEach(allGames::add);
        boolean present = allGames.stream().anyMatch(x -> savedGameId.equals(x.getId()));
        assertFalse(present);
    }

    @Test
    public void findAllTest() {
        List<Game> allGames = new ArrayList<>();
        gameRepository.findAll().forEach(allGames::add);
        List<String> gamesNames = allGames.stream().map(Game::getName).collect(toList());
        assertThat(gamesNames, hasItems("Diablo III", "Far Cry 3", "Mass Effect 3", "Mass Effect", "Syberia 3"));
    }

    @Test
    public void filterByTwoParametersTest() {
        GameFilterDto filters = GameFilterDto.builder()
                .ageLimit(AgeLimit.TEEN)
                .price(30)
                .build();
        LimitOffsetDto limitOffset = LimitOffsetDto.of(0, 0);

        List<Game> games = gameRepository.filterAllGames(filters, limitOffset);
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

        List<Game> games = gameRepository.filterAllGames(filters, limitOffset);
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

        List<Game> games = gameRepository.filterAllGames(filters, limitOffset);
        List<String> gamesNames = games.stream().map(Game::getName).collect(toList());
        assertThat(games, hasSize(2));
        assertThat(gamesNames, containsInAnyOrder("Diablo III", "Far Cry 3"));
    }

    @Test
    public void findByGenreTest() {
        Optional<Genre> genre = genreRepository.findByName("RPG");
        Genre genreFromBD = null;

        if (genre.isPresent()) {
            genreFromBD = genre.get();
        }

        List<Game> games = gameRepository.findByGenre(genreFromBD);
        assertThat(games, hasSize(3));
    }

    @Test
    public void findBySubgenreTest() {
        Optional<Subgenre> subgenre = subgenreRepository.findByName("Квест");
        Subgenre subgenreFromBD = null;

        if (subgenre.isPresent()) {
            subgenreFromBD = subgenre.get();
        }

        List<Game> games = gameRepository.findBySubgenres(subgenreFromBD);
        assertThat(games, hasSize(1));
    }
}
