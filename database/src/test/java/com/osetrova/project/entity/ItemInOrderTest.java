package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseConfiguration;
import com.osetrova.project.dao.entitydao.GameDaoImpl;
import com.osetrova.project.dao.entitydao.GamePriceDaoImpl;
import com.osetrova.project.dao.entitydao.ItemInOrderDaoImpl;
import com.osetrova.project.dao.entitydao.OrderDaoImpl;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.embeddable.OrderGamePrice;
import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.entity.enumonly.PaymentForm;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
@Transactional
public class ItemInOrderTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private GameDaoImpl gameDao;

    @Autowired
    private GamePriceDaoImpl gamePriceDao;

    @Autowired
    private OrderDaoImpl orderDao;

    @Autowired
    private ItemInOrderDaoImpl itemInOrderDao;

    @Test
    public void checkSaveAndGet() {
        Game game = Game.builder()
                .name("game test name")
                .description("new game")
                .build();
        gameDao.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                .price(20)
                .build();
        gamePriceDao.save(gamePrice);

        Order order = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 9, 29))
                .build();
        orderDao.save(order);

        ItemInOrder itemInOrder = ItemInOrder.builder()
                .orderGamePrice(OrderGamePrice.of(gamePrice.getId(), order.getId()))
                .number(2)
                .build();
        Long savedItemId = itemInOrderDao.save(itemInOrder);
        assertNotNull(savedItemId);

        sessionFactory.getCurrentSession().evict(itemInOrder);

        ItemInOrder savedItem = itemInOrderDao.findById(savedItemId);
        assertNotNull(savedItem);
    }
}
