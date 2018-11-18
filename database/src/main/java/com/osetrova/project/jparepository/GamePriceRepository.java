package com.osetrova.project.jparepository;

import com.osetrova.project.entity.GamePrice;
import org.springframework.data.repository.CrudRepository;

public interface GamePriceRepository extends CrudRepository<GamePrice, Long> {
}
