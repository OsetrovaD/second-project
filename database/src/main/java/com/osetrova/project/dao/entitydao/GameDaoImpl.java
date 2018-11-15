package com.osetrova.project.dao.entitydao;

import com.osetrova.project.dao.BaseDaoImpl;
import com.osetrova.project.dao.entitydaointerface.GameDao;
import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.entity.GamePrice_;
import com.osetrova.project.entity.Game_;
import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Genre_;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.entity.Subgenre_;
import com.osetrova.project.entity.embeddable.GameGamePlatform_;
import com.osetrova.project.entity.enumonly.GamePlatform;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GameDaoImpl extends BaseDaoImpl<Game, Long> implements GameDao {

    @Override
    public Class<Game> getEntityClass() {
        return Game.class;
    }

    @Override
    public List<Game> filterAllGames(GameFilterDto filters, LimitOffsetDto limitOffset) {
        CriteriaBuilder cb = getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);
        SetJoin<Game, GamePrice> gamePriceJoin = gameRoot.join(Game_.gamePrices);

        List<Predicate> gameFilters = getFilters(filters, cb, gameRoot, gamePriceJoin);

        criteria.select(gameRoot)
                .where(
                    gameFilters.toArray(new Predicate[]{})
                )
                .groupBy(gameRoot);
        return getSessionFactory().getCurrentSession().createQuery(criteria)
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
    public List<Game> findByGenre(Genre genre) {
        CriteriaBuilder cb = getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);
        SetJoin<Game, Subgenre> subgenreJoin = gameRoot.join(Game_.subgenres);
        Join<Subgenre, Genre> genreJoin = subgenreJoin.join(Subgenre_.genre);

        criteria.select(gameRoot)
                .where(
                    cb.equal(genreJoin.get(Genre_.name), genre.getName())
                );

        return getSessionFactory().getCurrentSession().createQuery(criteria).list();
    }

    @Override
    public List<Game> findBySubgenre(Subgenre subgenre) {
        CriteriaBuilder cb = getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);
        SetJoin<Game, Subgenre> subgenreJoin = gameRoot.join(Game_.subgenres);

        criteria.select(gameRoot)
                .where(
                    cb.equal(subgenreJoin.get(Subgenre_.name), subgenre.getName())
                );
        return getSessionFactory().getCurrentSession().createQuery(criteria).list();
    }

    @Override
    public List<Game> findByIssueYear(Integer issueYear) {
        CriteriaBuilder cb = getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);

        criteria.select(gameRoot)
                .where(
                    cb.equal(gameRoot.get(Game_.issueYear), issueYear)
                );
        return getSessionFactory().getCurrentSession().createQuery(criteria).list();
    }

    @Override
    public Optional<Game> findByName(String name) {
        CriteriaBuilder cb = getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);

        criteria.select(gameRoot)
                .where(
                        cb.equal(gameRoot.get(Game_.name), name)
                );
        return Optional.ofNullable(getSessionFactory().getCurrentSession().createQuery(criteria).getSingleResult());
    }

    @Override
    public List<Game> findByGamePlatform(GamePlatform platform) {
        CriteriaBuilder cb = getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);
        SetJoin<Game, GamePrice> gamePriceJoin = gameRoot.join(Game_.gamePrices);

        criteria.select(gameRoot)
                .where(
                    cb.equal(gamePriceJoin.get(GamePrice_.gameGamePlatform).get(GameGamePlatform_.gamePlatform), platform)
                );
        return getSessionFactory().getCurrentSession().createQuery(criteria).list();
    }
}
