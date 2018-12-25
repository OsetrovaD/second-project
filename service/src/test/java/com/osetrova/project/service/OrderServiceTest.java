package com.osetrova.project.service;

import com.osetrova.project.dto.gamepricedto.GamePriceForBasketDto;
import com.osetrova.project.dto.orderdto.DetailedOrderInfoDto;
import com.osetrova.project.dto.orderdto.OrderInfoForAdminDto;
import com.osetrova.project.dto.orderdto.UserOrderDto;
import com.osetrova.project.entity.Admin;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.GamePrice;
import com.osetrova.project.entity.ItemInOrder;
import com.osetrova.project.entity.Order;
import com.osetrova.project.entity.Storage;
import com.osetrova.project.entity.User;
import com.osetrova.project.entity.embeddable.GameGamePlatform;
import com.osetrova.project.entity.embeddable.OrderGamePrice;
import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.entity.enumonly.PaymentForm;
import com.osetrova.project.entity.enumonly.Role;
import com.osetrova.project.jparepository.GamePriceRepository;
import com.osetrova.project.jparepository.ItemInOrderRepository;
import com.osetrova.project.jparepository.OrderRepository;
import com.osetrova.project.jparepository.StorageRepository;
import com.osetrova.project.jparepository.UserRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import com.osetrova.project.serviceconfig.ServiceConfiguration;
import com.osetrova.project.servicedto.NewOrderParameterDto;
import com.osetrova.project.servicedto.OrderConditionDto;
import com.osetrova.project.servicedto.OrderDeliveryDateDto;
import com.osetrova.project.servicedto.OrderForSaveDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class OrderServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager manager;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private ItemInOrderRepository itemInOrderRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePriceRepository gamePriceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void checkFindAll() {
        User admin = new Admin("orderServiceTestFirstUser", "password",
                "il@email.email", Role.ADMIN, 100);
        userRepository.save(admin);

        Order firstOrder = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 9, 29))
                .user(admin)
                .build();
        orderRepository.save(firstOrder);

        Order secondOrder = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 2, 21))
                .user(admin)
                .build();
        orderRepository.save(secondOrder);

        List<OrderInfoForAdminDto> allOrders = orderService.findAllOrders();
        assertThat(allOrders, hasSize(2));
    }

    @Test
    public void checkFindAllByUsername() {
        User admin = new Admin("orderServiceTestSecondUser", "password",
                "zz@email.email", Role.ADMIN, 100);
        userRepository.save(admin);

        Order firstOrder = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2017, 12, 11))
                .user(admin)
                .build();
        orderRepository.save(firstOrder);

        Order secondOrder = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 3, 17))
                .user(admin)
                .build();
        orderRepository.save(secondOrder);

        List<UserOrderDto> byUsername = orderService.findAllByUsername("orderServiceTestSecondUser");
        assertThat(byUsername, hasSize(2));
    }

    @Test
    public void checkFindById() {
        Game game = Game.builder()
                .name("orderServiceTestFirstGame")
                .description("new game")
                .build();
        Game savedGame = gameRepository.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(savedGame.getId(), GamePlatform.PC))
                .price(20)
                .build();
        GamePrice savedGamePrice = gamePriceRepository.save(gamePrice);

        Order order = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 7, 2))
                .build();
        Order savedOrder = orderRepository.save(order);

        ItemInOrder itemInOrder = ItemInOrder.builder()
                .orderGamePrice(OrderGamePrice.of(savedGamePrice.getId(), savedOrder.getId()))
                .number(2)
                .build();
        itemInOrderRepository.save(itemInOrder);
        manager.refresh(order);
        manager.refresh(itemInOrder);
        manager.refresh(gamePrice);

        DetailedOrderInfoDto byId = orderService.findById(savedOrder.getId());
        assertNotNull(byId);
    }

    @Test
    public void checkFindAllByDateBetween() {
        User admin = new Admin("orderServiceTestThirdUser", "password",
                "xx@email.email", Role.ADMIN, 100);
        userRepository.save(admin);

        Order firstOrder = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 7, 18))
                .user(admin)
                .build();
        orderRepository.save(firstOrder);

        Order secondOrder = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 2, 21))
                .user(admin)
                .build();
        orderRepository.save(secondOrder);

        Order thirdOrder = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 9, 25))
                .user(admin)
                .build();
        orderRepository.save(thirdOrder);

        List<OrderInfoForAdminDto> allOrders = orderService.findAllByDateBetween(LocalDate.of(2018, 1, 29),
                LocalDate.of(2018, 8, 14));
        assertThat(allOrders, hasSize(2));
    }

    @Test
    public void checkChangeOrderCondition() {
        Order order = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 7, 2))
                .build();
        Order savedOrder = orderRepository.save(order);

        int changeOrderCondition = orderService.changeOrderCondition(new OrderConditionDto(savedOrder.getId(), Condition.SEND));
        assertEquals(1, changeOrderCondition);
    }

    @Test
    public void checkChangeOrderDeliveryDate() {
        Order order = Order.builder()
                .paymentForm(PaymentForm.CASH)
                .deliveryMethod(DeliveryMethod.PICKUP)
                .condition(Condition.ACCEPTED)
                .date(LocalDate.of(2018, 4, 22))
                .build();
        Order savedOrder = orderRepository.save(order);

        int changeOrderDeliveryDate = orderService.changeOrderDeliveryDate(new OrderDeliveryDateDto(savedOrder.getId(),
                LocalDate.of(2018, 5, 11)));
        assertEquals(1, changeOrderDeliveryDate);
    }

    @Test
    public void checkSave() {
        User admin = new Admin("orderServiceTestFourthUser", "password",
                "qq@email.email", Role.ADMIN, 100);
        userRepository.save(admin);

        Game game = Game.builder()
                .name("orderServiceTestSecondGame")
                .description("new game")
                .build();
        Game savedGame = gameRepository.save(game);

        GamePrice gamePrice = GamePrice.builder()
                .gameGamePlatform(GameGamePlatform.of(game.getId(), GamePlatform.PC))
                .price(20)
                .build();
        GamePrice savedGamePrice = gamePriceRepository.save(gamePrice);

        Storage storage = Storage.builder()
                .gamePrice(gamePrice)
                .number((short)15)
                .lastAdditionDate(LocalDate.of(2018, 7, 18))
                .build();
        storageRepository.save(storage);
        storage.setNumber((short)(storage.getNumber() - 2));

        OrderForSaveDto orderForSaveDto = OrderForSaveDto.builder()
                .gamesInBasket(Collections.singletonList(GamePriceForBasketDto.builder()
                        .gameId(savedGame.getId())
                        .number((short) 2)
                        .numberInStorage((short) 15)
                        .price(20)
                        .gamePlatform(GamePlatform.PC)
                        .gameName(savedGame.getName())
                        .id(savedGamePrice.getId())
                        .build()))
                .login("orderServiceTestFourthUser")
                .orderParameter(NewOrderParameterDto.builder()
                        .deliveryMethod(DeliveryMethod.COURIER)
                        .paymentForm(PaymentForm.ONLINE).build())
                .storage(Collections.singletonList(storage))
                .build();

        orderService.saveOrder(orderForSaveDto);
        List<UserOrderDto> byUsername = orderService.findAllByUsername("orderServiceTestFourthUser");

        assertThat(byUsername, hasSize(1));
    }
}
