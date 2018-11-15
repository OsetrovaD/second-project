package com.osetrova.project.dao;

import com.osetrova.project.entity.baseentity.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T extends BaseEntity<Y>, Y extends Serializable> {

    T findById(Y id);

    Y save(T entity);

    void update(T entity);

    void delete(T entity);

    List<T> findAll();
}
