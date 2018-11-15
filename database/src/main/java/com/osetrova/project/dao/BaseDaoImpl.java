package com.osetrova.project.dao;

import com.osetrova.project.entity.baseentity.BaseEntity;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Getter
public abstract class BaseDaoImpl<T extends BaseEntity<Y>, Y extends Serializable> implements BaseDao<T, Y> {

    @Autowired
    private SessionFactory sessionFactory;

    public abstract Class<T> getEntityClass();

    @Override
    public T findById(Y id) {
        return sessionFactory.getCurrentSession().find(getEntityClass(), id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Y save(T entity) {
        return (Y) sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(getEntityClass());
        Root<T> root = criteria.from(getEntityClass());

        criteria.select(root);

        return sessionFactory.getCurrentSession().createQuery(criteria).list();
    }
}
