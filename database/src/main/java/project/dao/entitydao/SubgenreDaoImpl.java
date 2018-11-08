package project.dao.entitydao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.BaseDaoImpl;
import project.dao.entitydaointerface.SubgenreDao;
import project.entity.Subgenre;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubgenreDaoImpl extends BaseDaoImpl<Subgenre, Integer> implements SubgenreDao {

    private static final SubgenreDaoImpl INSTANCE = new SubgenreDaoImpl();

    public static SubgenreDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Class<Subgenre> getEntityClass() {
        return Subgenre.class;
    }
}
