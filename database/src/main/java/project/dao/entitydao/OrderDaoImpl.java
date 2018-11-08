package project.dao.entitydao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import project.dao.BaseDaoImpl;
import project.dao.entitydaointerface.OrderDao;
import project.entity.Order;
import project.entity.Order_;
import project.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {

    private static final OrderDaoImpl INSTANCE = new OrderDaoImpl();

    public static OrderDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Class<Order> getEntityClass() {
        return Order.class;
    }

    @Override
    public List<Order> getByUser(Session session, User user) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> orderRoot = criteria.from(Order.class);

        criteria.select(orderRoot)
                .where(
                    cb.equal(orderRoot.get(Order_.user), user)
                );
        return session.createQuery(criteria).list();
    }

    @Override
    public List<Order> getByDate(Session session, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> orderRoot = criteria.from(Order.class);

        criteria.select(orderRoot)
                .where(
                        cb.between(orderRoot.get(Order_.date), startDate, endDate)
                );
        return session.createQuery(criteria).list();
    }
}
