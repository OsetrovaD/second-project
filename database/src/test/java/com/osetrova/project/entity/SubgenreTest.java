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

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
@Transactional
public class SubgenreTest {

    @Autowired
    private GenreDaoImpl genreDao;

    @Autowired
    private SubgenreDaoImpl subgenreDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void checkSaveAndGet() {
        Genre genre = Genre.builder()
                .name("genre name")
                .build();
        genreDao.save(genre);

        Subgenre subgenre = Subgenre.builder()
                .name("subgenre name")
                .genre(genre)
                .build();
        Integer savedSubgenreId = subgenreDao.save(subgenre);
        assertNotNull(savedSubgenreId);

        sessionFactory.getCurrentSession().evict(subgenre);

        Subgenre savedSubgenre = subgenreDao.findById(savedSubgenreId);
        assertNotNull(savedSubgenre);
    }
}
