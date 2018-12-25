package com.osetrova.project.service;

import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.dto.gamedto.GameFullInfoDto;
import com.osetrova.project.dto.gamedto.GameSearchResultDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.exception.DaoException;
import com.osetrova.project.jparepository.GenreRepository;
import com.osetrova.project.jparepository.SubgenreRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import com.osetrova.project.serviceconfig.ServiceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
@Sql("classpath:test-script.sql")
public class GameServiceTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SubgenreRepository subgenreRepository;

    @Autowired
    private GameService gameService;

    @Test
    public void checkFindAll() {
        List<Game> games = gameService.findAll();
        List<String> gamesNames = games.stream().map(Game::getName).collect(toList());

        assertThat(games, hasSize(6));
        assertThat(gamesNames, contains("Deus Ex: Human Revolution", "Dragon Age: Origins", "Mass Effect", "Mass Effect 2", "Mass Effect 3", "The Elder Scrolls V: Skyrim"));
    }

    @Test
    public void checkFilterGamesTest() {
        GameFilterDto filters = GameFilterDto.of(2011, AgeLimit.MATURE, 13);
        LimitOffsetDto limitOffset = LimitOffsetDto.of(0, 0);

        List<Game> games = gameService.filterGames(filters, limitOffset);
        List<String> gamesNames = games.stream().map(Game::getName).collect(toList());
        assertThat(games, hasSize(3));
        assertThat(gamesNames, containsInAnyOrder("Deus Ex: Human Revolution", "Mass Effect 3", "The Elder Scrolls V: Skyrim"));
    }

    @Test
    public void checkFindById() {
        Game game = gameRepository.findByNameIgnoreCase("Deus Ex: Human Revolution").orElseThrow(DaoException::new);

        GameFullInfoDto infoDto = gameService.findById(game.getId());
        assertNotNull(infoDto);
    }

    @Test
    public void checkFindByName() {
        GameFullInfoDto infoDto = gameService.findByName("Mass Effect 3");
        assertNotNull(infoDto);
    }

    @Test
    public void checkFindByPlatform() {
        List<GameSearchResultDto> byPlatform = gameService.findByPlatform(GamePlatform.NINTENDO_WII_U);
        assertThat(byPlatform, hasSize(2));
    }

    @Test
    public void checkFindByGenre() {
        Genre genre = genreRepository.findByName("Action").orElseThrow(DaoException::new);
        List<GameSearchResultDto> byGenre = gameService.findByGenre(genre);
        assertThat(byGenre, hasSize(4));
    }

    @Test
    public void checkFindBySubgenre() {
        Subgenre subgenre = subgenreRepository.findByName("Шутер от третьего лица").orElseThrow(DaoException::new);
        List<GameSearchResultDto> bySubgenre = gameService.findBySubgenre(subgenre);
        assertThat(bySubgenre, hasSize(3));
    }

    @Test
    public void checkFindByIssueYear() {
        List<GameSearchResultDto> byIssueYear = gameService.findByIssueYear(2011);
        assertThat(byIssueYear, hasSize(2));
    }
}
