package project.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import project.connectionutil.ConnectionUtil;
import project.entity.embeddable.GameGamePlatform;
import project.entity.embeddable.OrderGamePrice;
import project.entity.enumonly.Condition;
import project.entity.enumonly.DeliveryMethod;
import project.entity.enumonly.GamePlatform;
import project.entity.enumonly.PaymentForm;

import java.io.Serializable;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class OrderTest {

    private static final SessionFactory SESSION_FACTORY = ConnectionUtil.getSessionFactory();

    @Test
    public void checkSaveAndGet() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Order order = Order.builder()
                    .paymentForm(PaymentForm.CASH)
                    .deliveryMethod(DeliveryMethod.PICKUP)
                    .condition(Condition.ACCEPTED)
                    .date(LocalDate.of(2018, 9, 29))
                    .build();
            Serializable savedOrderId = session.save(order);
            assertNotNull(savedOrderId);

            session.evict(order);

            Order savedOrder = session.find(Order.class, savedOrderId);
            assertNotNull(savedOrder);
        }
    }

    @Test
    public void checkGetItemsInOrder() {
        try (Session session = SESSION_FACTORY.openSession()) {
            Game game = Game.builder()
                    .name("game_2")
                    .description("new game")
                    .build();
            session.save(game);

            GamePrice gamePrice = GamePrice.builder()
                    .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                    .price(20)
                    .build();
            session.save(gamePrice);

            Order order = Order.builder()
                    .paymentForm(PaymentForm.CASH)
                    .deliveryMethod(DeliveryMethod.PICKUP)
                    .condition(Condition.ACCEPTED)
                    .date(LocalDate.of(2018, 9, 29))
                    .build();
            session.save(order);

            ItemInOrder itemInOrder = ItemInOrder.builder()
                    .orderGamePrice(OrderGamePrice.of(gamePrice.getGameGamePlatform().getGameId(), order.getId()))
                    .number(2)
                    .build();
            session.save(itemInOrder);

            session.refresh(order);

            assertThat(order.getItemsInOrder(), hasSize(1));
        }
    }
}
