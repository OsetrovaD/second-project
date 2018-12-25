package com.osetrova.project.service;

import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.exception.DaoException;
import com.osetrova.project.jparepository.SubgenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SubgenreService {

    private SubgenreRepository subgenreRepository;

    @Autowired
    public SubgenreService(SubgenreRepository subgenreRepository) {
        this.subgenreRepository = subgenreRepository;
    }

    public List<Subgenre> findAll() {
        List<Subgenre> subgenres = new ArrayList<>();
        subgenreRepository.findAll().forEach(subgenres::add);

        return subgenres;
    }

    public Subgenre findByName(String name) {
        return subgenreRepository.findByName(name)
                .orElseThrow(DaoException::new);
    }
}
