package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseConfiguration;
import com.osetrova.project.dao.entitydao.GenreDaoImpl;
import com.osetrova.project.dao.entitydao.SubgenreDaoImpl;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
@Transactional
public class GenreTest {

    @Autowired
    private GenreDaoImpl genreDao;

    @Autowired
    private SubgenreDaoImpl subgenreDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void checkSaveAndGet() {
        Genre genre = Genre.builder()
                .name("genre")
                .build();
        Integer savedGenreId = genreDao.save(genre);
        assertNotNull(savedGenreId);

        sessionFactory.getCurrentSession().evict(genre);

        Genre savedGenre = genreDao.findById(savedGenreId);
        assertNotNull(savedGenre);
    }

    @Test
    public void checkGetSubgenres() {
        Genre genre = Genre.builder()
                .name("new genre")
                .build();
        genreDao.save(genre);

        Subgenre subgenre = Subgenre.builder()
                .name("subgenre")
                .genre(genre)
                .build();
        subgenreDao.save(subgenre);

        sessionFactory.getCurrentSession().refresh(genre);
        assertThat(genre.getSubgenres(), hasSize(1));
    }
}
