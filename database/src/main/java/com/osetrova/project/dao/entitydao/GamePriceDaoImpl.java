package com.osetrova.project.dao.entitydao;

import com.osetrova.project.dao.BaseDaoImpl;
import com.osetrova.project.dao.entitydaointerface.GamePriceDao;
import com.osetrova.project.entity.GamePrice;
import org.springframework.stereotype.Repository;

@Repository
public class GamePriceDaoImpl extends BaseDaoImpl<GamePrice, Long> implements GamePriceDao {

    @Override
    public Class<GamePrice> getEntityClass() {
        return GamePrice.class;
    }
}
