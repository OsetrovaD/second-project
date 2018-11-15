package com.osetrova.project.dao.entitydao;

import com.osetrova.project.dao.BaseDaoImpl;
import com.osetrova.project.dao.entitydaointerface.GenreDao;
import com.osetrova.project.entity.Genre;
import org.springframework.stereotype.Repository;

@Repository
public class GenreDaoImpl extends BaseDaoImpl<Genre, Integer> implements GenreDao {

    @Override
    public Class<Genre> getEntityClass() {
        return Genre.class;
    }
}
