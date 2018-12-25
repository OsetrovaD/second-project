package com.osetrova.project.service;

import com.osetrova.project.dto.gamedto.NewGameDto;
import com.osetrova.project.entity.DeveloperCompany;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.entity.Screenshot;
import com.osetrova.project.entity.Storage;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.embeddable.GameScreenshot;
import com.osetrova.project.exception.DaoException;
import com.osetrova.project.jparepository.DeveloperCompanyRepository;
import com.osetrova.project.jparepository.GamePriceRepository;
import com.osetrova.project.jparepository.ScreenshotRepository;
import com.osetrova.project.jparepository.StorageRepository;
import com.osetrova.project.jparepository.SubgenreRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NewGameService {

    private final GameRepository gameRepository;
    private final GamePriceRepository gamePriceRepository;
    private final StorageRepository storageRepository;
    private final DeveloperCompanyRepository developerCompanyRepository;
    private final ScreenshotRepository screenshotRepository;
    private final SubgenreRepository subgenreRepository;

    public Game save(NewGameDto newGame) {
        DeveloperCompany company = developerCompanyRepository.findByName(newGame.getDeveloperCompany().getName());
        Set<Subgenre> subgenres = new HashSet<>();

        newGame.getSubgenres()
                .forEach(x -> subgenres.add(subgenreRepository.findByName(x.getName()).orElseThrow(DaoException::new)));

        Game savedGame = gameRepository.save(Game.builder()
                .name(newGame.getName())
                .developerCompany(company)
                .description(newGame.getDescription())
                .image(newGame.getImage())
                .ageLimit(newGame.getAgeLimit())
                .issueYear(newGame.getIssueYear())
                .minimalSystemRequirements(newGame.getMinimalSystemRequirements())
                .recommendedSystemRequirements(newGame.getRecommendedSystemRequirements())
                .subgenres(subgenres)
                .build());

        newGame.getPrices()
                .forEach(x -> storageRepository.save(Storage.builder()
                                                    .gamePrice(gamePriceRepository.save(GamePrice.builder()
                                                                .gameGamePlatform(GameGamePlatform.of(savedGame.getId(), x.getGamePlatform()))
                                                                .price(x.getPrice())
                                                                .build()))
                                                    .number(x.getNumberInStorage())
                                                    .lastAdditionDate(LocalDate.now())
                                                    .build()));

        newGame.getScreenshots().stream()
                .filter(x -> !x.getScreenshotUrl().isEmpty())
                .forEach(x -> screenshotRepository.save(Screenshot.builder()
                                            .gameScreenshot(GameScreenshot.of(savedGame.getId(), x.getScreenshotUrl()))
                                            .build()));

        return savedGame;
    }
}
