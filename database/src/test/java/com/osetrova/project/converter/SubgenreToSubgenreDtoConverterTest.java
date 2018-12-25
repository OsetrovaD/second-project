package com.osetrova.project.converter;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.dto.subgenredto.SubgenreDto;
import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Subgenre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
public class SubgenreToSubgenreDtoConverterTest {

    @Autowired
    private SubgenreToSubgenreDtoConverter converter;

    @Test
    public void checkConvert() {
        Subgenre subgenre = Subgenre.builder()
                .genre(Genre.builder().name("genreNewName").build())
                .name("converterSubgenreName")
                .build();

        SubgenreDto subgenreDto = SubgenreDto.builder()
                .name("converterSubgenreName")
                .genreName("genreNewName")
                .build();

        SubgenreDto convert = converter.convert(subgenre);
        assertEquals(convert, subgenreDto);
    }
}
