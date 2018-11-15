package com.osetrova.project.dao.entitydao;

import com.osetrova.project.dao.BaseDaoImpl;
import com.osetrova.project.dao.entitydaointerface.DeveloperCompanyDao;
import com.osetrova.project.entity.DeveloperCompany;
import org.springframework.stereotype.Repository;

@Repository
public class DeveloperCompanyDaoImpl extends BaseDaoImpl<DeveloperCompany, Integer> implements DeveloperCompanyDao {

    @Override
    public Class<DeveloperCompany> getEntityClass() {
        return DeveloperCompany.class;
    }
}
