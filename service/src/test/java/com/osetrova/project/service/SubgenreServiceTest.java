package com.osetrova.project.service;

import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.jparepository.GenreRepository;
import com.osetrova.project.jparepository.SubgenreRepository;
import com.osetrova.project.serviceconfig.ServiceConfiguration;
import org.hamcrest.Matchers;
import org.junit.Assert;
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
public class SubgenreServiceTest {

    @Autowired
    private SubgenreService subgenreService;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SubgenreRepository subgenreRepository;

    @Test
    public void checkFindAll() {
        Genre genre = Genre.builder()
                .name("subgenreServiceTestGenre")
                .build();
        genreRepository.save(genre);

        Subgenre firstSubgenre = Subgenre.builder()
                .name("subgenreServiceTestFirstSubgenre")
                .genre(genre)
                .build();
        subgenreRepository.save(firstSubgenre);

        Subgenre secondSubgenre = Subgenre.builder()
                .name("subgenreServiceTestSecondSubgenre")
                .genre(genre)
                .build();
        subgenreRepository.save(secondSubgenre);

        List<Subgenre> subgenres = subgenreService.findAll();
        assertThat(subgenres, hasSize(2));
    }

    @Test
    public void checkFindByName() {
        Genre genre = Genre.builder()
                .name("subgenreServiceTestSecondGenre")
                .build();
        genreRepository.save(genre);

        Subgenre subgenre = Subgenre.builder()
                .name("subgenreServiceTestThirdSubgenre")
                .genre(genre)
                .build();
        subgenreRepository.save(subgenre);

        Subgenre byName = subgenreService.findByName("subgenreServiceTestThirdSubgenre");
        assertNotNull(byName);
    }
}
