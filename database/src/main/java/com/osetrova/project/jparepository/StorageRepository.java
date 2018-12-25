package com.osetrova.project.jparepository;

import com.osetrova.project.entity.Storage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StorageRepository extends CrudRepository<Storage, Long> {

    Optional<Storage> findByGamePriceId(Long id);
}
