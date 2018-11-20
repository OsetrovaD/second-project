package com.osetrova.project.jparepository;

import com.osetrova.project.entity.Subgenre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubgenreRepository extends CrudRepository<Subgenre, Integer> {

    Optional<Subgenre> findByName(String name);
}
