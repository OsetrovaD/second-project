package project.dao.entitydao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.BaseDaoImpl;
import project.dao.entitydaointerface.GenreDao;
import project.entity.Genre;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreDaoImpl extends BaseDaoImpl<Genre, Integer> implements GenreDao {

    private static final GenreDaoImpl INSTANCE = new GenreDaoImpl();

    public static GenreDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Class<Genre> getEntityClass() {
        return Genre.class;
    }
}
