package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.jparepository.GenreRepository;
import com.osetrova.project.jparepository.SubgenreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class GenreTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SubgenreRepository subgenreRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkSaveAndGet() {
        Genre genre = Genre.builder()
                .name("genre")
                .build();
        Genre savedGenre = genreRepository.save(genre);
        assertNotNull(savedGenre);
        Integer savedGenreId = savedGenre.getId();

        manager.detach(genre);

        Optional<Genre> genreOptional = genreRepository.findById(savedGenreId);
        assertTrue(genreOptional.isPresent());
    }

    @Test
    public void checkGetSubgenres() {
        Genre genre = Genre.builder()
                .name("new genre")
                .build();
        genreRepository.save(genre);

        Subgenre subgenre = Subgenre.builder()
                .name("subgenre")
                .genre(genre)
                .build();
        subgenreRepository.save(subgenre);

        manager.refresh(genre);
        assertThat(genre.getSubgenres(), hasSize(1));
    }
}
