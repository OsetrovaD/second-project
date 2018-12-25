package com.osetrova.project.jparepository.gamerepository;

import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.entity.enumonly.GamePlatform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Long>, GamesFilterRepository {

    List<Game> findAllBySubgenres(Subgenre subgenre);

    @Query("select gm from Game gm join gm.subgenres s where s.genre = :genre")
    List<Game> findAllByGenre(@Param("genre") Genre genre);

    Optional<Game> findByNameIgnoreCase(String name);

    List<Game> findAllByIssueYear(Integer issueYear);

    @Query("select gm from Game gm join gm.gamePrices gp where gp.gameGamePlatform.gamePlatform = :platform")
    List<Game> findAllByGamePlatform(@Param("platform") GamePlatform gamePlatform);

    List<Game> findAllByOrderByNameAsc();
}
