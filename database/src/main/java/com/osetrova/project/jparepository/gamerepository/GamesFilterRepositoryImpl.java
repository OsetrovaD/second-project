package com.osetrova.project.jparepository.gamerepository;

import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.entity.GamePrice_;
import com.osetrova.project.entity.Game_;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import java.util.ArrayList;
import java.util.List;

public class GamesFilterRepositoryImpl implements GamesFilterRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Game> filterAllGames(GameFilterDto filters, LimitOffsetDto limitOffset) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Game> criteria = cb.createQuery(Game.class);
        Root<Game> gameRoot = criteria.from(Game.class);
        SetJoin<Game, GamePrice> gamePriceJoin = gameRoot.join(Game_.gamePrices);

        List<Predicate> gameFilters = getFilters(filters, cb, gameRoot, gamePriceJoin);

        criteria.select(gameRoot)
                .where(
                        gameFilters.toArray(new Predicate[]{})
                )
                .groupBy(gameRoot);
        return entityManager.createQuery(criteria)
                .setMaxResults(limitOffset.getLimit())
                .setFirstResult(limitOffset.getOffset())
                .getResultList();
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
}
