package com.osetrova.project.jparepository;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Subgenre;
import org.junit.Assert;
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
public class SubgenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SubgenreRepository subgenreRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkFindByName() {
        Genre genre = Genre.builder()
                .name("subgenreTestGenre")
                .build();
        genreRepository.save(genre);

        Subgenre subgenre = Subgenre.builder()
                .name("subgenreTestSubgenre")
                .genre(genre)
                .build();
        Subgenre savedSubgenre = subgenreRepository.save(subgenre);

        manager.detach(subgenre);
        Optional<Subgenre> foundSubgenre = subgenreRepository.findByName(savedSubgenre.getName());
        assertTrue(foundSubgenre.isPresent());
    }
}
