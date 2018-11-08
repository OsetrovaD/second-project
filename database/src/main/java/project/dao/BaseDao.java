package project.dao;

import org.hibernate.Session;
import project.entity.baseentity.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T extends BaseEntity<Y>, Y extends Serializable> {

    T findById(Session session, Y id);

    Y save(Session session, T entity);

    void update(Session session, T entity);

    void delete(Session session, T entity);

    List<T> findAll(Session session);
}
