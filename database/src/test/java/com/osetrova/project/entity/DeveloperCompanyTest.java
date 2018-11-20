package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.jparepository.DeveloperCompanyRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
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
public class DeveloperCompanyTest {

    @Autowired
    private DeveloperCompanyRepository developerCompanyRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkSaveAndGet() {
        DeveloperCompany developerCompany = DeveloperCompany.builder()
                .name("company")
                .build();
        DeveloperCompany company = developerCompanyRepository.save(developerCompany);
        assertNotNull(company);
        Integer companyId = company.getId();

        manager.detach(developerCompany);

        Optional<DeveloperCompany> savedCompany = developerCompanyRepository.findById(companyId);
        assertTrue(savedCompany.isPresent());
    }

    @Test
    public void checkGetGames() {
        DeveloperCompany developerCompany = DeveloperCompany.builder()
                .name("developer company")
                .build();
        developerCompanyRepository.save(developerCompany);

        Game game = Game.builder()
                .name("game")
                .description("new game")
                .developerCompany(developerCompany)
                .build();
        gameRepository.save(game);

        manager.refresh(developerCompany);
        assertThat(developerCompany.getGames(), hasSize(1));
    }
}
