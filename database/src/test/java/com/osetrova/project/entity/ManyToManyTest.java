package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.jparepository.GenreRepository;
import com.osetrova.project.jparepository.SubgenreRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class ManyToManyTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SubgenreRepository subgenreRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void checkInsertByGameIntoGameSubgenre() {
        Game game = Game.builder()
                .name("game name")
                .description("new game")
                .ageLimit(AgeLimit.EVERYONE)
                .build();
        Game savedGame = gameRepository.save(game);
        Long gameId = savedGame.getId();

        Genre genre = Genre.builder()
                .name("some genre")
                .build();
        genreRepository.save(genre);

        Subgenre subgenre = Subgenre.builder()
                .name("some subgenre")
                .genre(genre)
                .build();
        subgenreRepository.save(subgenre);

        manager.refresh(game);
        manager.refresh(subgenre);

        game.addSubgenre(subgenre);
        manager.flush();
        manager.detach(game);

        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Game gameFromDB = null;
        if (optionalGame.isPresent()) {
            gameFromDB = optionalGame.get();
        }

        assertThat(Objects.requireNonNull(gameFromDB).getSubgenres(), hasSize(1));
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
        gameRepository.save(game);

        Genre genre = Genre.builder()
                .name("some new genre")
                .build();
        genreRepository.save(genre);

        Subgenre subgenre = Subgenre.builder()
                .name("new subgenre")
                .genre(genre)
                .build();
        Subgenre savedSubgenre = subgenreRepository.save(subgenre);
        Integer savedSubgenreId = savedSubgenre.getId();

        manager.refresh(game);
        manager.refresh(subgenre);

        subgenre.addGame(game);
        manager.flush();
        manager.detach(subgenre);

        Optional<Subgenre> optionalSubgenre = subgenreRepository.findById(savedSubgenreId);
        Subgenre subgenreFromDB = null;

        if (optionalSubgenre.isPresent()) {
            subgenreFromDB = optionalSubgenre.get();
        }

        assertThat(game.getSubgenres(), hasSize(1));
        assertThat(Objects.requireNonNull(subgenreFromDB).getGames(), hasSize(1));
    }
}
