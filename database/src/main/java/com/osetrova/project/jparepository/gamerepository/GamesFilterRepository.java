package com.osetrova.project.jparepository.gamerepository;

import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.Game;

import java.util.List;

public interface GamesFilterRepository {

    List<Game> filterAllGames(GameFilterDto filters, LimitOffsetDto limitOffset);
}
