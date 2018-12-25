package com.osetrova.project.converter;

import com.osetrova.project.entity.Screenshot;
import com.osetrova.project.dto.ScreenshotDto;
import org.springframework.stereotype.Component;

@Component
public class ScreenshotToScreenshotDtoConverter implements Converter<ScreenshotDto, Screenshot> {

    @Override
    public ScreenshotDto convert(Screenshot screenshot) {
        return ScreenshotDto.of(screenshot.getGameScreenshot().getScreenshotUrl());
    }
}
