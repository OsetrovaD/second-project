package com.osetrova.project.service;

import com.osetrova.project.entity.DeveloperCompany;
import com.osetrova.project.jparepository.DeveloperCompanyRepository;
import com.osetrova.project.serviceconfig.ServiceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class DeveloperCompanyServiceTest {

    @Autowired
    private DeveloperCompanyService companyService;

    @Autowired
    private DeveloperCompanyRepository repository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkFindAll() {
        DeveloperCompany firstCompany = DeveloperCompany.builder()
                .name("firstServiceTestCompany")
                .build();
        repository.save(firstCompany);

        DeveloperCompany secondCompany = DeveloperCompany.builder()
                .name("secondServiceTestCompany")
                .build();
        repository.save(secondCompany);

        DeveloperCompany thirdCompany = DeveloperCompany.builder()
                .name("thirdServiceTestCompany")
                .build();
        repository.save(thirdCompany);

        manager.detach(firstCompany);
        manager.detach(secondCompany);
        manager.detach(thirdCompany);

        List<DeveloperCompany> companies = companyService.findAll();
        List<String> companiesNames = companies.stream().map(DeveloperCompany::getName).collect(toList());

        assertThat(companies, hasSize(3));
        assertThat(companiesNames, contains("firstServiceTestCompany", "secondServiceTestCompany", "thirdServiceTestCompany"));
    }
}
