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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class SubgenreTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SubgenreRepository subgenreRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkSaveAndGet() {
        Genre genre = Genre.builder()
                .name("genre name")
                .build();
        genreRepository.save(genre);

        Subgenre subgenre = Subgenre.builder()
                .name("subgenre name")
                .genre(genre)
                .build();
        Subgenre savedSubgenre = subgenreRepository.save(subgenre);
        assertNotNull(savedSubgenre);
        Integer savedSubgenreId = savedSubgenre.getId();

        manager.detach(subgenre);

        Optional<Subgenre> optionalSubgenre = subgenreRepository.findById(savedSubgenreId);
        assertTrue(optionalSubgenre.isPresent());
    }
}
