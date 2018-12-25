package com.osetrova.project.service;

import com.osetrova.project.entity.Genre;
import com.osetrova.project.exception.DaoException;
import com.osetrova.project.jparepository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GenreService {

    private GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        genreRepository.findAll().forEach(genres::add);

        return genres;
    }

    public Genre findByName(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(DaoException::new);
    }
}
