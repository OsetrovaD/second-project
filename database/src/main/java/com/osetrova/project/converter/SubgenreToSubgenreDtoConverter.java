package com.osetrova.project.converter;

import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.dto.subgenredto.SubgenreDto;
import org.springframework.stereotype.Component;

@Component
public class SubgenreToSubgenreDtoConverter implements Converter<SubgenreDto, Subgenre> {

    @Override
    public SubgenreDto convert(Subgenre subgenre) {
        return SubgenreDto.builder()
                .name(subgenre.getName())
                .genreName(subgenre.getGenre().getName())
                .build();
    }
}
