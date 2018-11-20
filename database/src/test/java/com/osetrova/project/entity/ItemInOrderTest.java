package com.osetrova.project.entity;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.embeddable.OrderGamePrice;
import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.entity.enumonly.PaymentForm;
import com.osetrova.project.jparepository.GamePriceRepository;
import com.osetrova.project.jparepository.ItemInOrderRepository;
import com.osetrova.project.jparepository.OrderRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class ItemInOrderTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePriceRepository gamePriceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemInOrderRepository itemInOrderRepository;

    @Test
    public void checkSaveAndGet() {
        Game game = Game.builder()
                .name("game test name")
                .description("new game")
                .build();
        gameRepository.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                .price(20)
                .build();
        gamePriceRepository.save(gamePrice);

        Order order = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 9, 29))
                .build();
        orderRepository.save(order);

        ItemInOrder itemInOrder = ItemInOrder.builder()
                .orderGamePrice(OrderGamePrice.of(gamePrice.getId(), order.getId()))
                .number(2)
                .build();
        ItemInOrder savedItem = itemInOrderRepository.save(itemInOrder);
        assertNotNull(savedItem);
        Long savedItemId = savedItem.getId();

        manager.detach(itemInOrder);

        Optional<ItemInOrder> optionalItem = itemInOrderRepository.findById(savedItemId);
        assertTrue(optionalItem.isPresent());
    }
}
