package com.osetrova.project.jparepository;

import com.osetrova.project.entity.ItemInOrder;
import org.springframework.data.repository.CrudRepository;

public interface ItemInOrderRepository extends CrudRepository<ItemInOrder, Long> {
}
