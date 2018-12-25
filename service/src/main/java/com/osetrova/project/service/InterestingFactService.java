package com.osetrova.project.service;

import com.osetrova.project.converter.InterestingFactToInterestingFactDtoConverter;
import com.osetrova.project.dto.InterestingFactDto;
import com.osetrova.project.jparepository.InterestingFactRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InterestingFactService {

    private InterestingFactRepository repository;
    private InterestingFactToInterestingFactDtoConverter converter;

    @Cacheable(cacheNames = "rarely-changing-entities")
    public List<InterestingFactDto> findAll() {
        List<InterestingFactDto> facts = new ArrayList<>();
        repository.findAll().forEach(x -> facts.add(converter.convert(x)));

        return facts;
    }
}
