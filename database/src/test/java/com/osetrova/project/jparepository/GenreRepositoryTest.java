package com.osetrova.project.jparepository;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkFindByName() {
        Genre genre = Genre.builder()
                .name("repositoryTestGenre")
                .build();
        Genre savedGenre = repository.save(genre);

        manager.detach(genre);

        Optional<Genre> optionalGenre = repository.findByName(savedGenre.getName());
        assertTrue(optionalGenre.isPresent());
    }
}
