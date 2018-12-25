package com.osetrova.project.service;

import com.osetrova.project.dto.DeveloperCompanyDto;
import com.osetrova.project.dto.ScreenshotDto;
import com.osetrova.project.dto.gamedto.NewGameDto;
import com.osetrova.project.dto.gamepricedto.NewGamePriceDto;
import com.osetrova.project.dto.subgenredto.SubgenreNameDto;
import com.osetrova.project.entity.DeveloperCompany;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.jparepository.DeveloperCompanyRepository;
import com.osetrova.project.jparepository.GenreRepository;
import com.osetrova.project.jparepository.SubgenreRepository;
import com.osetrova.project.serviceconfig.ServiceConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class NewGameServiceTest {

    @Autowired
    private NewGameService newGameService;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SubgenreRepository subgenreRepository;

    @Autowired
    private DeveloperCompanyRepository developerCompanyRepository;

    @Before
    public void initEntities() {
        Genre genre = Genre.builder()
                .name("newGameServiceTestGenre")
                .build();
        genreRepository.save(genre);

        Subgenre subgenre = Subgenre.builder()
                .name("newGameServiceTestSubgenre")
                .genre(genre)
                .build();
        subgenreRepository.save(subgenre);

        DeveloperCompany developerCompany = DeveloperCompany.builder()
                .name("newGameServiceTestCompany")
                .build();
        developerCompanyRepository.save(developerCompany);
    }

    @Test
    public void checkSave() {
        NewGameDto newGameDto = NewGameDto.builder()
                .name("newGameServiceTestGame")
                .ageLimit(AgeLimit.EVERYONE)
                .description("description")
                .developerCompany(new DeveloperCompanyDto("newGameServiceTestCompany"))
                .issueYear(2015)
                .minimalSystemRequirements("minimal requirements")
                .recommendedSystemRequirements("recommended requirements")
                .image("imageUrl")
                .prices(new HashSet<>(Collections.singletonList(NewGamePriceDto.builder()
                        .gamePlatform(GamePlatform.XBOX_ONE)
                        .numberInStorage((short) 12)
                        .price(47)
                        .build()
                )))
                .screenshots(new HashSet<>(Collections.singletonList(ScreenshotDto.of("screenshotTestUrl"))))
                .subgenres(new HashSet<>(Collections.singletonList(new SubgenreNameDto("newGameServiceTestSubgenre"))))
                .build();

        Game savedGame = newGameService.save(newGameDto);
        assertNotNull(savedGame);
    }
}
