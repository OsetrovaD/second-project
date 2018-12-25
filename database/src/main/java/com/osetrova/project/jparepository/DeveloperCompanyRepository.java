package com.osetrova.project.jparepository;

import com.osetrova.project.entity.DeveloperCompany;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeveloperCompanyRepository extends CrudRepository<DeveloperCompany, Integer> {

    DeveloperCompany findByName(String name);

    List<DeveloperCompany> findAllByOrderByNameAsc();
}
