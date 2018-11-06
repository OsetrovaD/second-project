package project.dao.entitydaointerface;

import org.hibernate.Session;
import project.dao.BaseDao;
import project.entity.Order;
import project.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao extends BaseDao<Order, Long> {

    List<Order> getByUser(Session session, User user);

    List<Order> getByDate(Session session, LocalDate startDate, LocalDate endDate);
}
