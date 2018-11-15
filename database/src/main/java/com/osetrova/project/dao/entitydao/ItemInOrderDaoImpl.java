package com.osetrova.project.dao.entitydao;

import com.osetrova.project.dao.BaseDaoImpl;
import com.osetrova.project.dao.entitydaointerface.ItemInOrderDao;
import com.osetrova.project.entity.ItemInOrder;
import org.springframework.stereotype.Repository;

@Repository
public class ItemInOrderDaoImpl extends BaseDaoImpl<ItemInOrder, Long> implements ItemInOrderDao {

    @Override
    public Class<ItemInOrder> getEntityClass() {
        return ItemInOrder.class;
    }
}
