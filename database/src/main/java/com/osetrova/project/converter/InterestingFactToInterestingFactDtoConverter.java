package com.osetrova.project.converter;

import com.osetrova.project.dto.InterestingFactDto;
import com.osetrova.project.entity.InterestingFact;
import org.springframework.stereotype.Component;

@Component
public class InterestingFactToInterestingFactDtoConverter implements Converter<InterestingFactDto, InterestingFact> {

    @Override
    public InterestingFactDto convert(InterestingFact fact) {
        return InterestingFactDto.of(fact.getFact());
    }
}
