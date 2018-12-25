package com.osetrova.project.converter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.InterestingFactDto;
import com.osetrova.project.entity.InterestingFact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
public class InterestingFactToInterestingFactDtoConverterTest {

    @Autowired
    private InterestingFactToInterestingFactDtoConverter converter;

    @Test
    public void checkConvert() {
        InterestingFact fact = new InterestingFact(4L, "interestingFact");

        InterestingFactDto factDto = InterestingFactDto.of("interestingFact");

        InterestingFactDto convert = converter.convert(fact);
        assertEquals(convert, factDto);
    }
}
