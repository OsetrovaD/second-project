package com.osetrova.project.dao.entitydaointerface;

import com.osetrova.project.dao.BaseDao;
import com.osetrova.project.entity.Order;
import com.osetrova.project.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao extends BaseDao<Order, Long> {

    List<Order> getByUser(User user);

    List<Order> getByDate(LocalDate startDate, LocalDate endDate);
}
