package com.osetrova.project.service;

import com.osetrova.project.dto.InterestingFactDto;
import com.osetrova.project.entity.InterestingFact;
import com.osetrova.project.jparepository.InterestingFactRepository;
import com.osetrova.project.serviceconfig.ServiceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class InterestingFactServiceTest {

    @Autowired
    private InterestingFactRepository repository;

    @Autowired
    private InterestingFactService interestingFactService;

    @Test
    public void checkFindAll() {
        InterestingFact firstFact = InterestingFact.builder()
                .fact("interesting fact 1")
                .build();
        repository.save(firstFact);

        InterestingFact secondFact = InterestingFact.builder()
                .fact("interesting fact 2")
                .build();
        repository.save(secondFact);

        List<InterestingFactDto> interestingFacts = interestingFactService.findAll();
        assertThat(interestingFacts, hasSize(2));
    }
}
