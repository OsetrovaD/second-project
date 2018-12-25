package com.osetrova.project.service;

import com.osetrova.project.entity.Storage;
import com.osetrova.project.exception.DaoException;
import com.osetrova.project.jparepository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StorageService {

    private StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public Storage addToStorage(Storage storage) {
        return storageRepository.save(storage);
    }

    public Storage findByGamePriceId(Long id) {
        return storageRepository.findByGamePriceId(id)
                .orElseThrow(DaoException::new);
    }
}
