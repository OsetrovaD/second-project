package project.entity;

import org.hamcrest.Matchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import project.connectionutil.ConnectionUtil;
import project.dao.entitydao.GameDaoImpl;
import project.dto.GameFilterDto;
import project.dto.LimitOffsetDto;
import project.entity.embeddable.GameGamePlatform;
import project.entity.enumonly.AgeLimit;
import project.entity.enumonly.GamePlatform;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class GameDaoImplTest {

    private static final SessionFactory FACTORY = ConnectionUtil.getSessionFactory();

    @BeforeClass
    public static void insertData() {
        try (Session session = FACTORY.openSession()) {
            Game massEffect3 = saveGame(session, "Mass Effect 3", 2012, AgeLimit.MATURE);
            Game diablo3 = saveGame(session, "Diablo III", 2012, AgeLimit.MATURE);
            Game farCry3 = saveGame(session, "Far Cry 3", 2012, AgeLimit.MATURE);
            Game massEffect = saveGame(session, "Mass Effect", 2007, AgeLimit.MATURE);
            Game syberia3 = saveGame(session, "Syberia 3", 2017, AgeLimit.TEEN);

            saveGamePrice(session, 47, massEffect3, GamePlatform.XBOX_ONE);
            saveGamePrice(session, 15, diablo3, GamePlatform.PC);
            saveGamePrice(session, 36, farCry3, GamePlatform.PLAYSTATION_3);
            saveGamePrice(session, 40, massEffect, GamePlatform.PC);
            saveGamePrice(session, 28, syberia3, GamePlatform.PLAYSTATION_4);
        }
    }

    @Test
    public void filterByAllParametersTest() {
        try (Session session = FACTORY.openSession()) {
            GameFilterDto filters = GameFilterDto.of(2012, AgeLimit.MATURE, 40);
            LimitOffsetDto limitOffset = LimitOffsetDto.of(0, 0);

            List<Game> games = GameDaoImpl.getInstance().filterAllGames(session, filters, limitOffset);
            List<String> gamesNames = games.stream().map(Game::getName).collect(toList());
            assertThat(games, hasSize(2));
            assertThat(gamesNames, containsInAnyOrder("Diablo III", "Far Cry 3"));
        }
    }

    @Test
    public void filterByTwoParametersTest() {
        try (Session session = FACTORY.openSession()) {
            GameFilterDto filters = GameFilterDto.builder()
                    .ageLimit(AgeLimit.TEEN)
                    .price(30)
                    .build();
            LimitOffsetDto limitOffset = LimitOffsetDto.of(0, 0);

            List<Game> games = GameDaoImpl.getInstance().filterAllGames(session, filters, limitOffset);
            List<String> gamesNames = games.stream().map(Game::getName).collect(toList());
            assertThat(games, hasSize(1));
            assertThat(gamesNames, contains("Syberia 3"));
        }
    }

    @Test
    public void filterByOneParameterTest() {
        try (Session session = FACTORY.openSession()) {
            GameFilterDto filters = GameFilterDto.builder()
                    .issueYear(2017)
                    .build();
            LimitOffsetDto limitOffset = LimitOffsetDto.of(0, 0);

            List<Game> games = GameDaoImpl.getInstance().filterAllGames(session, filters, limitOffset);
            List<String> gamesNames = games.stream().map(Game::getName).collect(toList());
            assertThat(games, hasSize(1));
            assertThat(gamesNames, contains("Syberia 3"));
        }
    }

    @Test
    public void filterWithLimitAndOffset() {
        try (Session session = FACTORY.openSession()) {
            GameFilterDto filters = GameFilterDto.builder()
                    .ageLimit(AgeLimit.MATURE)
                    .build();
            LimitOffsetDto limitOffset = LimitOffsetDto.of(2, 1);

            List<Game> games = GameDaoImpl.getInstance().filterAllGames(session, filters, limitOffset);
            List<String> gamesNames = games.stream().map(Game::getName).collect(toList());
            assertThat(games, hasSize(2));
            assertThat(gamesNames, containsInAnyOrder("Diablo III", "Far Cry 3"));
        }
    }

    @Test
    public void findByIdTest() {
        try (Session session = FACTORY.openSession()) {
            Game game = GameDaoImpl.getInstance().findById(session, 3L);
            assertNotNull(game);
        }
    }

    @Test
    public void findAllTest() {
        try (Session session = FACTORY.openSession()) {
            List<Game> allGames = GameDaoImpl.getInstance().findAll(session);
            List<String> gamesNames = allGames.stream().map(Game::getName).collect(toList());
           assertThat(gamesNames, hasItems("Diablo III", "Far Cry 3", "Mass Effect 3", "Mass Effect", "Syberia 3"));
        }
    }

    @Test
    public void updateTest() {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            Game game = session.find(Game.class, 2L);
            game.setDescription("description");
            session.getTransaction().commit();

            session.evict(game);

            game = session.find(Game.class, 2L);
            assertEquals(game.getDescription(), "description");
        }
    }

    @Test
    public void saveTest() {
        try (Session session = FACTORY.openSession()) {
            Game game = Game.builder()
                    .name("L.A. Noire")
                    .issueYear(2011)
                    .ageLimit(AgeLimit.MATURE)
                    .build();
            Long savedGameId = GameDaoImpl.getInstance().save(session, game);
            assertNotNull(savedGameId);

            session.beginTransaction();
            session.delete(game);
            session.getTransaction().commit();
        }
    }

    @Test
    public void deleteTest() {
        Long savedGameId;
        try (Session session = FACTORY.openSession()) {
            Game theLastOfUs = Game.builder()
                    .ageLimit(AgeLimit.MATURE)
                    .name("The Last of Us")
                    .issueYear(2013)
                    .build();
            savedGameId = (Long) session.save(theLastOfUs);

            session.beginTransaction();
            GameDaoImpl.getInstance().delete(session, theLastOfUs);
            session.getTransaction().commit();
        }

        try (Session session = FACTORY.openSession()) {
            Game game = session.find(Game.class, savedGameId);
            assertNull(game);
        }
    }

    private static Game saveGame(Session session, String name, Integer issueYear, AgeLimit ageLimit) {
        Game game = Game.builder()
                .name(name)
                .ageLimit(ageLimit)
                .issueYear(issueYear)
                .build();
        session.save(game);
        return game;
    }

    private static void saveGamePrice(Session session, Integer price, Game game, GamePlatform gamePlatform) {
        GamePrice gamePrice = GamePrice.builder()
                .price(price)
                .gameGamePlatform(GameGamePlatform.of(game.getId(), gamePlatform))
                .build();
        session.save(gamePrice);
    }
}
