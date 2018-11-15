package com.osetrova.project.dao.entitydao;

import com.osetrova.project.dao.BaseDaoImpl;
import com.osetrova.project.dao.entitydaointerface.StorageDao;
import com.osetrova.project.entity.Storage;
import org.springframework.stereotype.Repository;

@Repository
public class StorageDaoImpl extends BaseDaoImpl<Storage, Long> implements StorageDao {

    @Override
    public Class<Storage> getEntityClass() {
        return Storage.class;
    }
}
