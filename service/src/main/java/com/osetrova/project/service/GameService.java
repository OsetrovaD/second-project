package com.osetrova.project.service;

import com.osetrova.project.dao.entitydaointerface.GameDao;
import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GameService {

    private GameDao gameDao;

    @Autowired
    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public List<Game> findAll() {
            return gameDao.findAll();
    }

    public List<Game> filterGames(GameFilterDto filters, LimitOffsetDto limitOffset) {
            return gameDao.filterAllGames(filters, limitOffset);
    }
}
