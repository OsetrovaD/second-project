package com.osetrova.project.controller;

import com.osetrova.project.exception.WrongDeliveryDateException;
import com.osetrova.project.servicedto.OrderConditionDto;
import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.service.OrderService;
import com.osetrova.project.dto.orderdto.DetailedOrderInfoDto;
import com.osetrova.project.dto.orderdto.OrderInfoForAdminDto;
import com.osetrova.project.dto.orderdto.UserOrderDto;
import com.osetrova.project.servicedto.OrderDeliveryDateDto;
import com.osetrova.project.webmoduledto.DatePeriodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all-orders")
    public String getAllOrders(Model model) {
        List<OrderInfoForAdminDto> allOrders = orderService.findAllOrders();
        model.addAttribute("allOrders", allOrders);

        return "all-orders";
    }

    @GetMapping("/user-orders")
    public String getUserOrders(Model model, @RequestParam(name = "username", required = false) String username) {
        String login;

        if (username == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails user = (UserDetails) authentication.getPrincipal();
            login = user.getUsername();
        } else {
            login = username;
        }

        List<UserOrderDto> userOrders = orderService.findAllByUsername(login);
        model.addAttribute("userOrders", userOrders);

        return "user-orders";
    }

    @GetMapping("/order-info")
    public String getDetailedOrderInfo(Model model, @RequestParam("id") Long id) {
        DetailedOrderInfoDto orderInfo = orderService.findById(id);
        model.addAttribute("orderInfo", orderInfo);

        return "order-info";
    }

    @GetMapping("/choose-date-period")
    public String getChooseDatePeriodPage(Model model) {
        model.addAttribute("datePeriod", new DatePeriodDto());

        return "choose-date-period";
    }

    @PostMapping("/order-search-result")
    public String getSearchResult(Model model, DatePeriodDto datePeriod) {
        List<OrderInfoForAdminDto> orders = orderService.findAllByDateBetween(datePeriod.getStartDate(), datePeriod.getEndDate());
        model.addAttribute("orders", orders);

        return "order-search-result";
    }

    @GetMapping("/change-order-condition")
    public String getConditionPage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("conditions", Arrays.asList(Condition.values()));
        model.addAttribute("orderData", OrderConditionDto.builder()
                                                .id(id)
                                                .build());

        return "change-order-condition";
    }

    @PostMapping("/change-order-condition")
    public String changeOrderCondition(OrderConditionDto orderData) {
        orderService.changeOrderCondition(orderData);

        return "redirect:/all-orders";
    }

    @GetMapping("/change-order-delivery-date")
    public String getDeliveryDatePage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("orderData", OrderDeliveryDateDto.builder()
                .id(id)
                .build());

        return "change-order-delivery-date";
    }

    @PostMapping("/change-order-delivery-date")
    public String changeOrderDeliveryDate(OrderDeliveryDateDto orderData) {
        DetailedOrderInfoDto orderInfo = orderService.findById(orderData.getId());

        if (orderInfo.getDate().isAfter(orderData.getDeliveryDate())) {
            throw new WrongDeliveryDateException();
        }
        orderService.changeOrderDeliveryDate(orderData);

        return "redirect:/all-orders";
    }
}
