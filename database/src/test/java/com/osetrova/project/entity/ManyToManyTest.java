package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseConfiguration;
import com.osetrova.project.dao.entitydao.GameDaoImpl;
import com.osetrova.project.dao.entitydao.GenreDaoImpl;
import com.osetrova.project.dao.entitydao.SubgenreDaoImpl;
import com.osetrova.project.entity.enumonly.AgeLimit;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
@Transactional
public class ManyToManyTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private GenreDaoImpl genreDao;

    @Autowired
    private SubgenreDaoImpl subgenreDao;

    @Autowired
    private GameDaoImpl gameDao;

    @Test
    public void checkInsertByGameIntoGameSubgenre() {
        Game game = Game.builder()
                .name("game name")
                .description("new game")
                .ageLimit(AgeLimit.EVERYONE)
                .build();
        Long gameId = gameDao.save(game);

        Genre genre = Genre.builder()
                .name("some genre")
                .build();
        genreDao.save(genre);

        Subgenre subgenre = Subgenre.builder()
                .name("some subgenre")
                .genre(genre)
                .build();
        subgenreDao.save(subgenre);

        sessionFactory.getCurrentSession().refresh(game);
        sessionFactory.getCurrentSession().refresh(subgenre);

        game.addSubgenre(subgenre);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().evict(game);

        Game savedGame = gameDao.findById(gameId);

        assertThat(savedGame.getSubgenres(), hasSize(1));
        assertThat(subgenre.getGames(), hasSize(1));
    }

    @Test
    public void checkInsertBySubgenreIntoGameSubgenre() {
        Game game = Game.builder()
                .name("game_1")
                .description("new game")
                .ageLimit(AgeLimit.EVERYONE)
                .minimalSystemRequirements("minimal system requirements")
                .build();
        gameDao.save(game);

        Genre genre = Genre.builder()
                .name("some new genre")
                .build();
        genreDao.save(genre);

        Subgenre subgenre = Subgenre.builder()
                .name("new subgenre")
                .genre(genre)
                .build();
        Integer savedSubgenreId = subgenreDao.save(subgenre);

        sessionFactory.getCurrentSession().refresh(game);
        sessionFactory.getCurrentSession().refresh(subgenre);

        subgenre.addGame(game);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().evict(subgenre);

        Subgenre savedSubgenre = subgenreDao.findById(savedSubgenreId);

        assertThat(game.getSubgenres(), hasSize(1));
        assertThat(savedSubgenre.getGames(), hasSize(1));
    }
}
