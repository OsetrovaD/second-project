package project.dao.entitydao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import project.dao.BaseDaoImpl;
import project.dao.entitydaointerface.GameDao;
import project.dto.GameFilterDto;
import project.dto.LimitOffsetDto;
import project.entity.Game;
import project.entity.GamePrice;
import project.entity.GamePrice_;
import project.entity.Game_;
import project.entity.Genre;
import project.entity.Genre_;
import project.entity.Subgenre;
import project.entity.Subgenre_;
import project.entity.embeddable.GameGamePlatform_;
import project.entity.enumonly.GamePlatform;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameDaoImpl extends BaseDaoImpl<Game, Long> implements GameDao {

    private static final GameDaoImpl INSTANCE = new GameDaoImpl();

    public static GameDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Class<Game> getEntityClass() {
        return Game.class;
    }

    @Override
    public List<Game> filterAllGames(Session session, GameFilterDto filters, LimitOffsetDto limitOffset) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);
        SetJoin<Game, GamePrice> gamePriceJoin = gameRoot.join(Game_.gamePrices);

        List<Predicate> gameFilters = getFilters(filters, cb, gameRoot, gamePriceJoin);

        criteria.select(gameRoot)
                .where(
                    gameFilters.toArray(new Predicate[]{})
                )
                .groupBy(gameRoot);
        return session.createQuery(criteria)
                .setMaxResults(limitOffset.getLimit())
                .setFirstResult(limitOffset.getOffset())
                .list();
    }

    private List<Predicate> getFilters(GameFilterDto filters, CriteriaBuilder cb,
                                       Root<Game> gameRoot, SetJoin<Game, GamePrice> gamePriceJoin) {
        List<Predicate> gameFilters = new ArrayList<>();
        if (filters.getAgeLimit() != null) {
            gameFilters.add(cb.equal(gameRoot.get(Game_.ageLimit), filters.getAgeLimit()));
        }
        if (filters.getIssueYear() != null) {
            gameFilters.add(cb.ge(gameRoot.get(Game_.issueYear), filters.getIssueYear()));
        }
        if (filters.getPrice() != null) {
            gameFilters.add(cb.le(gamePriceJoin.get(GamePrice_.price), filters.getPrice()));
        }

        return gameFilters;
    }

    @Override
    public List<Game> findByGenre(Session session, Genre genre) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);
        SetJoin<Game, Subgenre> subgenreJoin = gameRoot.join(Game_.subgenres);
        Join<Subgenre, Genre> genreJoin = subgenreJoin.join(Subgenre_.genre);

        criteria.select(gameRoot)
                .where(
                    cb.equal(genreJoin.get(Genre_.name), genre.getName())
                );

        return session.createQuery(criteria).list();
    }

    @Override
    public List<Game> findBySubgenre(Session session, Subgenre subgenre) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);
        SetJoin<Game, Subgenre> subgenreJoin = gameRoot.join(Game_.subgenres);

        criteria.select(gameRoot)
                .where(
                    cb.equal(subgenreJoin.get(Subgenre_.name), subgenre.getName())
                );
        return session.createQuery(criteria).list();
    }

    @Override
    public List<Game> findByIssueYear(Session session, Integer issueYear) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);

        criteria.select(gameRoot)
                .where(
                    cb.equal(gameRoot.get(Game_.issueYear), issueYear)
                );
        return session.createQuery(criteria).list();
    }

    @Override
    public List<Game> findByName(Session session, String name) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);

        criteria.select(gameRoot)
                .where(
                        cb.equal(gameRoot.get(Game_.name), name)
                );
        return session.createQuery(criteria).list();
    }

    @Override
    public List<Game> findByGamePlatfrom(Session session, GamePlatform platform) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);
        SetJoin<Game, GamePrice> gamePriceJoin = gameRoot.join(Game_.gamePrices);

        criteria.select(gameRoot)
                .where(
                    cb.equal(gamePriceJoin.get(GamePrice_.gameGamePlatform).get(GameGamePlatform_.gamePlatform), platform)
                );
        return null;
    }
}
