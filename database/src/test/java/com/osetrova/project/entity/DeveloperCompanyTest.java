package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseConfiguration;
import com.osetrova.project.dao.entitydao.DeveloperCompanyDaoImpl;
import com.osetrova.project.dao.entitydao.GameDaoImpl;
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
public class DeveloperCompanyTest {

    @Autowired
    private DeveloperCompanyDaoImpl developerCompanyDao;

    @Autowired
    private GameDaoImpl gameDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void checkSaveAndGet() {
        DeveloperCompany developerCompany = DeveloperCompany.builder()
                .name("company")
                .build();
        Integer savedId = developerCompanyDao.save(developerCompany);
        assertNotNull(savedId);

        sessionFactory.getCurrentSession().evict(developerCompany);

        DeveloperCompany savedCompany = developerCompanyDao.findById(savedId);
        assertNotNull(savedCompany);
    }

    @Test
    public void checkGetGames() {
        DeveloperCompany developerCompany = DeveloperCompany.builder()
                .name("developer company")
                .build();
        developerCompanyDao.save(developerCompany);

        Game game = Game.builder()
                .name("game")
                .description("new game")
                .developerCompany(developerCompany)
                .build();
        gameDao.save(game);

        sessionFactory.getCurrentSession().refresh(developerCompany);
        assertThat(developerCompany.getGames(), hasSize(1));
    }
}
