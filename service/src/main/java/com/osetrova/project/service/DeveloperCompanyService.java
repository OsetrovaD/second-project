package com.osetrova.project.service;

import com.osetrova.project.entity.DeveloperCompany;
import com.osetrova.project.jparepository.DeveloperCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DeveloperCompanyService {

    private DeveloperCompanyRepository repository;

    @Autowired
    public DeveloperCompanyService(DeveloperCompanyRepository repository) {
        this.repository = repository;
    }

    public List<DeveloperCompany> findAll() {
        return new ArrayList<>(repository.findAllByOrderByNameAsc());
    }
}
