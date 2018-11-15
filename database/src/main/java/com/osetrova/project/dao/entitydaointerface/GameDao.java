package com.osetrova.project.dao.entitydaointerface;

import com.osetrova.project.dao.BaseDao;
import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.entity.enumonly.GamePlatform;

import java.util.List;
import java.util.Optional;

public interface GameDao extends BaseDao<Game, Long> {

    List<Game> filterAllGames(GameFilterDto filters, LimitOffsetDto limitOffset);

    List<Game> findByGenre(Genre genre);

    List<Game> findBySubgenre(Subgenre subgenre);

    List<Game> findByIssueYear(Integer issueYear);

    Optional<Game> findByName(String name);

    List<Game> findByGamePlatform(GamePlatform platform);
}
