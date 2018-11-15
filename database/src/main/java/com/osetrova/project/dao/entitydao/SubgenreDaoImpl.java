package com.osetrova.project.dao.entitydao;

import com.osetrova.project.dao.BaseDaoImpl;
import com.osetrova.project.dao.entitydaointerface.SubgenreDao;
import com.osetrova.project.entity.Subgenre;
import org.springframework.stereotype.Repository;

@Repository
public class SubgenreDaoImpl extends BaseDaoImpl<Subgenre, Integer> implements SubgenreDao {

    @Override
    public Class<Subgenre> getEntityClass() {
        return Subgenre.class;
    }
}
