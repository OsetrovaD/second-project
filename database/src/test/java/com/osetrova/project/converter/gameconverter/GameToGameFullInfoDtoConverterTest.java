package com.osetrova.project.converter.gameconverter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.converter.GamePriceToGamePriceDtoConverter;
import com.osetrova.project.converter.ScreenshotToScreenshotDtoConverter;
import com.osetrova.project.converter.SubgenreToSubgenreDtoConverter;
import com.osetrova.project.dto.ScreenshotDto;
import com.osetrova.project.dto.gamedto.GameFullInfoDto;
import com.osetrova.project.dto.gamepricedto.GamePriceDto;
import com.osetrova.project.dto.subgenredto.SubgenreDto;
import com.osetrova.project.entity.DeveloperCompany;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Screenshot;
import com.osetrova.project.entity.Storage;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.embeddable.GameScreenshot;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.entity.enumonly.GamePlatform;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
public class GameToGameFullInfoDtoConverterTest {

    @Autowired
    public GameToGameFullInfoDtoConverter converter;

    @Autowired
    public ScreenshotToScreenshotDtoConverter screenshotConverter;

    @Autowired
    public GamePriceToGamePriceDtoConverter gamePriceConverter;

    @Autowired
    public SubgenreToSubgenreDtoConverter subgenreConverter;

    @Test
    public void checkConvert() {
        Game game = Game.builder()
                .id(1L)
                .name("converterFullInfoGame")
                .subgenres(new HashSet<>(Collections.singletonList(Subgenre.builder()
                                                                    .genre(Genre.builder().name("converterGenre").build())
                                                                    .name("converterSubgenre")
                                                                    .build())))
                .screenshots(new HashSet<>(Collections.singletonList(Screenshot.builder()
                        .gameScreenshot(GameScreenshot.of(1L, "converterUrl"))
                        .build())))
                .image("converterImgUrl")
                .recommendedSystemRequirements("recommended")
                .minimalSystemRequirements("minimal")
                .issueYear(2010)
                .ageLimit(AgeLimit.TEEN)
                .developerCompany(DeveloperCompany.builder().name("company").build())
                .gamePrices(new HashSet<>(Collections.singletonList(GamePrice.builder()
                        .price(15)
                        .id(1L)
                        .gameGamePlatform(GameGamePlatform.of(1L, GamePlatform.PC))
                        .storage(Storage.builder().number((short)12).build())
                        .build())))
                .description("new game")
                .build();

        GameFullInfoDto infoDto = GameFullInfoDto.builder()
                .id(1L)
                .ageLimit(AgeLimit.TEEN)
                .name("converterFullInfoGame")
                .subgenres(new HashSet<>(Collections.singletonList(SubgenreDto.builder()
                        .genreName("converterGenre")
                        .name("converterSubgenre")
                        .build())))
                .screenshots(new HashSet<>(Collections.singletonList(ScreenshotDto.builder()
                        .screenshotUrl("converterUrl")
                        .build())))
                .image("converterImgUrl")
                .recommendedSystemRequirements("recommended")
                .minimalSystemRequirements("minimal")
                .issueYear(2010)
                .developerCompany(DeveloperCompany.builder().name("company").build())
                .gamePrices(new HashSet<>(Collections.singletonList(GamePriceDto.builder()
                        .gamePlatform(GamePlatform.PC)
                        .id(1L)
                        .price(15)
                        .numberInStorage((short) 12)
                        .build())))
                .description("new game")
                .build();

        GameFullInfoDto convert = converter.convert(game);

        Assert.assertEquals(convert, infoDto);
    }
}
