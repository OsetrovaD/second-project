package com.osetrova.project.dao.entitydao;

import com.osetrova.project.dao.BaseDaoImpl;
import com.osetrova.project.dao.entitydaointerface.OrderDao;
import com.osetrova.project.entity.Order;
import com.osetrova.project.entity.Order_;
import com.osetrova.project.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {

    @Override
    public Class<Order> getEntityClass() {
        return Order.class;
    }

    @Override
    public List<Order> getByUser(User user) {
        CriteriaBuilder cb = getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> orderRoot = criteria.from(Order.class);

        criteria.select(orderRoot)
                .where(
                    cb.equal(orderRoot.get(Order_.user), user)
                );
        return getSessionFactory().getCurrentSession().createQuery(criteria).list();
    }

    @Override
    public List<Order> getByDate(LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> orderRoot = criteria.from(Order.class);

        criteria.select(orderRoot)
                .where(
                        cb.between(orderRoot.get(Order_.date), startDate, endDate)
                );
        return getSessionFactory().getCurrentSession().createQuery(criteria).list();
    }
}
