package com.osetrova.project.jparepository;

import com.osetrova.project.entity.DeveloperCompany;
import org.springframework.data.repository.CrudRepository;

public interface DeveloperCompanyRepository extends CrudRepository<DeveloperCompany, Integer> {
}
