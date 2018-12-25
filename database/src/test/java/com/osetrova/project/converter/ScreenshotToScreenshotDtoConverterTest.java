package com.osetrova.project.converter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.ScreenshotDto;
import com.osetrova.project.entity.Screenshot;
import com.osetrova.project.entity.embeddable.GameScreenshot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
public class ScreenshotToScreenshotDtoConverterTest {

    @Autowired
    private ScreenshotToScreenshotDtoConverter converter;

    @Test
    public void checkConvert() {
        Screenshot screenshot = Screenshot.builder()
                .gameScreenshot(GameScreenshot.of(17L, "screenshotUrl"))
                .build();

        ScreenshotDto screenshotDto = ScreenshotDto.of("screenshotUrl");

        ScreenshotDto convert = converter.convert(screenshot);
        assertEquals(convert, screenshotDto);
    }
}
