package com.osetrova.project.jparepository;

import com.osetrova.project.entity.Order;
import com.osetrova.project.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    List<Order> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}
