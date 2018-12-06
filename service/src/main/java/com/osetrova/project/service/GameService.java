package com.osetrova.project.service;

import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll() {
        List<Game> allGames = new ArrayList<>();
        gameRepository.findAll().forEach(allGames::add);
        return allGames;
    }

    public List<Game> filterGames(GameFilterDto filters, LimitOffsetDto limitOffset) {
            return gameRepository.filterAllGames(filters, limitOffset);
    }

    public Optional<Game> findById(Long id) {
        Optional<Game> game = gameRepository.findById(id);

        if (game.isPresent()) {
            Hibernate.initialize(game.get().getSubgenres());
            Hibernate.initialize(game.get().getScreenshots());
            Hibernate.initialize(game.get().getGamePrices());
        }

        return game;
    }
}
