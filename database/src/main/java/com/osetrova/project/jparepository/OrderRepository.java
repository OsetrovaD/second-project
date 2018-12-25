package com.osetrova.project.jparepository;

import com.osetrova.project.entity.Order;
import com.osetrova.project.entity.enumonly.Condition;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findAllByUserLogin(String username);

    List<Order> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Order> findAllByOrderByIdDesc();

    @Modifying
    @Query("update Order o set o.condition = :condition where o.id = :orderId")
    int changeOrderCondition(@Param("orderId") Long id, @Param("condition") Condition condition);

    @Modifying
    @Query("update Order o set o.deliveryDate = :deliveryDate where o.id = :orderId")
    int changeOrderDeliveryDate(@Param("orderId") Long id, @Param("deliveryDate") LocalDate deliveryDate);
}
