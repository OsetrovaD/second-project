package com.osetrova.project.jparepository;

import com.osetrova.project.configuration.DatabaseSpringDataConfiguration;
import com.osetrova.project.entity.Order;
import com.osetrova.project.entity.SimpleUser;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.PaymentForm;
import com.osetrova.project.entity.enumonly.Role;
import com.osetrova.project.exception.DaoException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseSpringDataConfiguration.class)
@Transactional
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager manager;

    @Test
    public void checkFindAllByUserLogin() {
        User user = new SimpleUser("orderTestUser", "password", "m@mail.mail",
                Role.USER, LocalDate.of(2018, 5, 10));
        User savedUser = userRepository.save(user);

        Order firstOrder = Order.builder()
                .user(user)
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 10, 14))
                .build();
        repository.save(firstOrder);

        Order secondOrder = Order.builder()
                .user(user)
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 11, 14))
                .build();
        repository.save(secondOrder);

        manager.detach(firstOrder);
        manager.detach(secondOrder);

        List<Order> orders = repository.findAllByUserLogin(savedUser.getLogin());
        assertThat(orders, hasSize(2));
    }

    @Test
    public void checkFindAllByDateBetween() {
        User user = new SimpleUser("orderSecondTestUser", "password", "e@mail.mail",
                Role.USER, LocalDate.of(2018, 7, 10));
        userRepository.save(user);

        Order firstOrder = Order.builder()
                .user(user)
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 5, 12))
                .build();
        repository.save(firstOrder);

        Order secondOrder = Order.builder()
                .user(user)
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 10, 5))
                .build();
        repository.save(secondOrder);

        Order thirdOrder = Order.builder()
                .user(user)
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 11, 7))
                .build();
        repository.save(thirdOrder);

        manager.detach(firstOrder);
        manager.detach(secondOrder);
        manager.detach(thirdOrder);

        List<Order> orders = repository.findAllByDateBetween(LocalDate.of(2018, 7, 11),
                                                            LocalDate.of(2018, 11, 12));
        assertThat(orders, hasSize(2));
    }

    @Test
    public void checkFindAllByOrderByIdDesc() {
        User user = new SimpleUser("orderThirdTestUser", "password", "e@mail.mail",
                Role.USER, LocalDate.of(2018, 7, 10));
        userRepository.save(user);

        Order firstOrder = Order.builder()
                .user(user)
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 5, 12))
                .build();
        Order firstSavedOrder = repository.save(firstOrder);

        Order secondOrder = Order.builder()
                .user(user)
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 10, 5))
                .build();
        Order secondSavedOrder = repository.save(secondOrder);

        manager.detach(firstOrder);
        manager.detach(secondOrder);

        List<Order> orders = repository.findAllByOrderByIdDesc();
        List<Long> idList = orders.stream().map(Order::getId).collect(Collectors.toList());
        assertThat(orders, hasSize(2));
        assertThat(idList, contains(secondSavedOrder.getId(), firstSavedOrder.getId()));
    }

    @Test
    public void checkChangeOrderCondition() {
        User user = new SimpleUser("orderFourthTestUser", "password", "m@il.mail",
                Role.USER, LocalDate.of(2018, 7, 10));
        userRepository.save(user);

        Order firstOrder = Order.builder()
                .user(user)
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 5, 12))
                .build();
        Order firstSavedOrder = repository.save(firstOrder);

        repository.changeOrderCondition(firstSavedOrder.getId(), Condition.COMPLETING);
        manager.flush();
        manager.detach(firstOrder);
        Order order = repository.findById(firstSavedOrder.getId()).orElseThrow(DaoException::new);

        assertEquals(Condition.COMPLETING, order.getCondition());
    }

    @Test
    public void checkChangeOrderDeliveryDate() {
        User user = new SimpleUser("orderFifthTestUser", "password", "em@il.mail",
                Role.USER, LocalDate.of(2018, 10, 10));
        userRepository.save(user);

        Order firstOrder = Order.builder()
                .user(user)
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 2, 6))
                .build();
        Order firstSavedOrder = repository.save(firstOrder);
        LocalDate date = LocalDate.of(2018, 3, 18);
        repository.changeOrderDeliveryDate(firstSavedOrder.getId(), date);
        manager.flush();
        manager.detach(firstOrder);
        Order order = repository.findById(firstSavedOrder.getId()).orElseThrow(DaoException::new);

        assertEquals(date, order.getDeliveryDate());
    }
}
