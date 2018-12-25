package com.osetrova.project.service;

import com.osetrova.project.converter.orderconverter.OrderToDetailedOrderInfoDtoConverter;
import com.osetrova.project.converter.orderconverter.OrderToOrderInfoForAdminConverter;
import com.osetrova.project.converter.orderconverter.OrderToUserOrderDtoConverter;
import com.osetrova.project.dto.gamepricedto.GamePriceForBasketDto;
import com.osetrova.project.dto.orderdto.DetailedOrderInfoDto;
import com.osetrova.project.dto.orderdto.OrderInfoForAdminDto;
import com.osetrova.project.dto.orderdto.UserOrderDto;
import com.osetrova.project.entity.ItemInOrder;
import com.osetrova.project.entity.Order;
import com.osetrova.project.entity.embeddable.OrderGamePrice;
import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.exception.DaoException;
import com.osetrova.project.jparepository.ItemInOrderRepository;
import com.osetrova.project.jparepository.OrderRepository;
import com.osetrova.project.jparepository.StorageRepository;
import com.osetrova.project.jparepository.UserRepository;
import com.osetrova.project.servicedto.OrderConditionDto;
import com.osetrova.project.servicedto.OrderDeliveryDateDto;
import com.osetrova.project.servicedto.OrderForSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderToOrderInfoForAdminConverter converter;
    private final OrderToUserOrderDtoConverter userOrderConverter;
    private final OrderToDetailedOrderInfoDtoConverter detailedInfoConverter;
    private final UserRepository userRepository;
    private final StorageRepository storageRepository;
    private final ItemInOrderRepository itemInOrderRepository;

    public List<OrderInfoForAdminDto> findAllOrders() {
        List<OrderInfoForAdminDto> allOrders = new ArrayList<>();
        orderRepository.findAllByOrderByIdDesc().forEach(x -> allOrders.add(converter.convert(x)));

        return allOrders;
    }

    public List<UserOrderDto> findAllByUsername(String username) {
        return orderRepository.findAllByUserLogin(username).stream()
                .map(userOrderConverter::convert)
                .collect(Collectors.toList());
    }

    public DetailedOrderInfoDto findById(Long id) {
        return orderRepository.findById(id)
                .map(detailedInfoConverter::convert)
                .orElseThrow(DaoException::new);
    }

    public List<OrderInfoForAdminDto> findAllByDateBetween(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findAllByDateBetween(startDate, endDate).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public int changeOrderCondition(OrderConditionDto orderCondition) {
        return orderRepository.changeOrderCondition(orderCondition.getId(), orderCondition.getCondition());
    }

    @Transactional
    public int changeOrderDeliveryDate(OrderDeliveryDateDto orderDeliveryDate) {
        return orderRepository.changeOrderDeliveryDate(orderDeliveryDate.getId(), orderDeliveryDate.getDeliveryDate());
    }

    @Transactional
    public void saveOrder(OrderForSaveDto orderForSave) {
        Order newOrder = Order.builder()
                .condition(Condition.ACCEPTED)
                .date(LocalDate.now())
                .deliveryMethod(orderForSave.getOrderParameter().getDeliveryMethod())
                .paymentForm(orderForSave.getOrderParameter().getPaymentForm())
                .user(userRepository.findByLogin(orderForSave.getLogin()).orElseThrow(DaoException::new))
                .build();

        orderRepository.save(newOrder);

        List<GamePriceForBasketDto> gamesInBasket = orderForSave.getGamesInBasket();
        for (int i = 0; i < gamesInBasket.size(); i++) {
            ItemInOrder itemInOrder = ItemInOrder.builder()
                    .orderGamePrice(OrderGamePrice.of(gamesInBasket.get(i).getId(), newOrder.getId()))
                    .number(Integer.valueOf(gamesInBasket.get(i).getNumber()))
                    .build();
            itemInOrderRepository.save(itemInOrder);

            storageRepository.save(orderForSave.getStorage().get(i));
        }
    }
}
