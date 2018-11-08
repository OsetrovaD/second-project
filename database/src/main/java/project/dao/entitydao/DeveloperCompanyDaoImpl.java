package project.dao.entitydao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.BaseDaoImpl;
import project.dao.entitydaointerface.DeveloperCompanyDao;
import project.entity.DeveloperCompany;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeveloperCompanyDaoImpl extends BaseDaoImpl<DeveloperCompany, Integer> implements DeveloperCompanyDao {

    private static final DeveloperCompanyDaoImpl INSTANCE = new DeveloperCompanyDaoImpl();

    public static DeveloperCompanyDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Class<DeveloperCompany> getEntityClass() {
        return DeveloperCompany.class;
    }
}
