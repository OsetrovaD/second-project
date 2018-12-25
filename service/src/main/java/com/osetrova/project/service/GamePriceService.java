package com.osetrova.project.service;

import com.osetrova.project.converter.GamePriceToDtoForBasketConverter;
import com.osetrova.project.dto.gamepricedto.GamePriceForBasketDto;
import com.osetrova.project.exception.DaoException;
import com.osetrova.project.jparepository.GamePriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GamePriceService {

    private final GamePriceRepository repository;
    private final GamePriceToDtoForBasketConverter forBasketConverter;

    public GamePriceForBasketDto findByIdForBasket(Long id) {
        return repository.findById(id).map(forBasketConverter::convert).orElseThrow(DaoException::new);
    }
}
