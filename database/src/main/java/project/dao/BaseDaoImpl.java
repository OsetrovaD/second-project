package project.dao;

import org.hibernate.Session;
import project.entity.baseentity.BaseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class BaseDaoImpl<T extends BaseEntity<Y>, Y extends Serializable> implements BaseDao<T, Y> {

    public abstract Class<T> getEntityClass();

    @Override
    public T findById(Session session, Y id) {
        return session.find(getEntityClass(), id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Y save(Session session, T entity) {
        return (Y) session.save(entity);
    }

    @Override
    public void update(Session session, T entity) {
        session.update(entity);
    }

    @Override
    public void delete(Session session, T entity) {
        session.delete(entity);
    }

    @Override
    public List<T> findAll(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(getEntityClass());
        Root<T> root = criteria.from(getEntityClass());

        criteria.select(root);

        return session.createQuery(criteria).list();
    }
}
