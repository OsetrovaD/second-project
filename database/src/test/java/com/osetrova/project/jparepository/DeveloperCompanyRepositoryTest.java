package com.osetrova.project.jparepository;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.DeveloperCompany;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class DeveloperCompanyRepositoryTest {

    @Autowired
    private DeveloperCompanyRepository repository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkFindByName() {
        DeveloperCompany company = DeveloperCompany.builder()
                .name("firstTestCompany")
                .build();
        DeveloperCompany savedCompany = repository.save(company);

        manager.detach(company);

        DeveloperCompany foundCompany = repository.findByName(savedCompany.getName());

        assertEquals(company, foundCompany);
    }

    @Test
    public void checkFindAllByOrderByNameAsc() {
        DeveloperCompany firstCompany = DeveloperCompany.builder()
                .name("secondTestFirstCompany")
                .build();
        repository.save(firstCompany);

        DeveloperCompany secondCompany = DeveloperCompany.builder()
                .name("secondTestSecondCompany")
                .build();
        repository.save(secondCompany);

        DeveloperCompany thirdCompany = DeveloperCompany.builder()
                .name("secondTestThirdCompany")
                .build();
        repository.save(thirdCompany);

        manager.detach(firstCompany);
        manager.detach(secondCompany);
        manager.detach(thirdCompany);

        List<DeveloperCompany> companies = repository.findAllByOrderByNameAsc();
        List<String> names = companies.stream().map(DeveloperCompany::getName).collect(Collectors.toList());

        assertThat(companies, hasSize(3));
        assertThat(names, contains("secondTestFirstCompany", "secondTestSecondCompany", "secondTestThirdCompany"));
    }
}
