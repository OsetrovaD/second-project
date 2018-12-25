package com.osetrova.project.service;

import com.osetrova.project.entity.Genre;
import com.osetrova.project.jparepository.GenreRepository;
import com.osetrova.project.serviceconfig.ServiceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class GenreServiceTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void checkFindAll() {
        Genre firstGenre = Genre.builder()
                .name("genreServiceFirstGenre")
                .build();
        genreRepository.save(firstGenre);

        Genre secondGenre = Genre.builder()
                .name("genreServiceSecondGenre")
                .build();
        genreRepository.save(secondGenre);

        List<Genre> genres = genreService.findAll();
        assertThat(genres, hasSize(2));
    }

    @Test
    public void checkFindByName() {
        Genre genre = Genre.builder()
                .name("genreServiceThirdGenre")
                .build();
        genreRepository.save(genre);

        Genre byName = genreService.findByName("genreServiceThirdGenre");
        assertNotNull(byName);
    }
}
