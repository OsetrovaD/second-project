package com.osetrova.project.jparepository;

import com.osetrova.project.entity.InterestingFact;
import org.springframework.data.repository.CrudRepository;

public interface InterestingFactRepository extends CrudRepository<InterestingFact, Long> {
}
