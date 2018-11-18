package com.osetrova.project.jparepository;

import com.osetrova.project.entity.Storage;
import org.springframework.data.repository.CrudRepository;

public interface StorageRepository extends CrudRepository<Storage, Long> {
}
