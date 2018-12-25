package com.osetrova.project.service;

import com.osetrova.project.converter.gameconverter.GameToGameFullInfoDtoConverter;
import com.osetrova.project.converter.gameconverter.GameToGameSearchResultConverter;
import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.exception.DaoException;
import com.osetrova.project.exception.FileNotFoundInDatabaseException;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import com.osetrova.project.dto.gamedto.GameFullInfoDto;
import com.osetrova.project.dto.gamedto.GameSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "games")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameService {

    private final GameRepository gameRepository;
    private final GameToGameFullInfoDtoConverter converter;
    private final GameToGameSearchResultConverter gameSearchConverter;

    @Cacheable
    public List<Game> findAll() {
        return new ArrayList<>(gameRepository.findAllByOrderByNameAsc());
    }

    public List<Game> filterGames(GameFilterDto filters, LimitOffsetDto limitOffset) {
            return gameRepository.filterAllGames(filters, limitOffset);
    }

    public GameFullInfoDto findById(Long id) {
        return gameRepository.findById(id)
                .map(converter::convert)
                .orElseThrow(DaoException::new);
    }

    @Cacheable
    public GameFullInfoDto findByName(String name) {
        return gameRepository.findByNameIgnoreCase(name)
                .map(converter::convert)
                .orElseThrow(() -> new FileNotFoundInDatabaseException(name));
    }

    @Cacheable
    public List<GameSearchResultDto> findByPlatform(GamePlatform platform) {
        return gameRepository.findAllByGamePlatform(platform).stream()
                .map(gameSearchConverter::convert)
                .collect(toList());
    }

    @Cacheable
    public List<GameSearchResultDto> findByGenre(Genre genre) {
        return gameRepository.findAllByGenre(genre).stream()
                .map(gameSearchConverter::convert)
                .collect(toList());
    }

    @Cacheable
    public List<GameSearchResultDto> findBySubgenre(Subgenre subgenre) {
        return gameRepository.findAllBySubgenres(subgenre).stream()
                .map(gameSearchConverter::convert)
                .collect(toList());
    }

    @Cacheable
    public List<GameSearchResultDto> findByIssueYear(Integer issueYear) {
        return gameRepository.findAllByIssueYear(issueYear).stream()
                .map(gameSearchConverter::convert)
                .collect(toList());
    }
}
