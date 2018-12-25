package com.osetrova.project.controller;

import com.osetrova.project.dto.gamepricedto.GamePriceForBasketDto;
import com.osetrova.project.entity.Storage;
import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.PaymentForm;
import com.osetrova.project.service.OrderService;
import com.osetrova.project.service.StorageService;
import com.osetrova.project.servicedto.NewOrderParameterDto;
import com.osetrova.project.servicedto.Basket;
import com.osetrova.project.servicedto.OrderForSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes("newOrderParameters")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NewOrderController {

    private final OrderService orderService;
    private final StorageService storageService;

    @GetMapping("/order-parameter")
    public String getOrderParameterPage(Model model) {
        model.addAttribute("orderParameter", new NewOrderParameterDto());
        model.addAttribute("paymentForms", Arrays.asList(PaymentForm.values()));
        model.addAttribute("deliveryMethods", Arrays.asList(DeliveryMethod.values()));

        return "order-parameter";
    }

    @PostMapping("/order-parameter")
    public String getOrderParameters(NewOrderParameterDto orderParameter, HttpSession session, Model model) {
        if (session.getAttribute("newOrderParameters") != null) {
            NewOrderParameterDto orderParameters = (NewOrderParameterDto) session.getAttribute("newOrderParameters");
            orderParameters.setDeliveryMethod(orderParameter.getDeliveryMethod());
            orderParameters.setPaymentForm(orderParameter.getPaymentForm());
        } else {
            model.addAttribute("newOrderParameters", orderParameter);
        }

        return "redirect:/check-order-parameter";
    }

    @GetMapping("/check-order-parameter")
    public String getCheckOrderParameterPage(@SessionAttribute Basket basket,
                                             @SessionAttribute NewOrderParameterDto newOrderParameters,
                                             Model model) {
        Integer gameSum = basket.getGamesInBasket().stream()
                .map(x -> (x.getNumber() * x.getPrice()))
                .reduce(0, (a, b) -> (a + b));
        Integer finalSum = gameSum + newOrderParameters.getDeliveryMethod().getPrice();

        model.addAttribute("gameSum", gameSum);
        model.addAttribute("finalSum", finalSum);

        return "check-order-parameter";
    }

    @PostMapping("/order-confirm")
    public String saveOrder(@SessionAttribute Basket basket,
                            @SessionAttribute NewOrderParameterDto newOrderParameters,
                            SessionStatus status, HttpSession session) {
        List<Storage> storage = new ArrayList<>();
        List<GamePriceForBasketDto> gamesInBasket = basket.getGamesInBasket();
        for (GamePriceForBasketDto gamePrice : gamesInBasket) {
            Storage currentStorage = storageService.findByGamePriceId(gamePrice.getId());
            currentStorage.setNumber((short) (gamePrice.getNumberInStorage() - gamePrice.getNumber()));
            storage.add(currentStorage);
        }
        orderService.saveOrder(new OrderForSaveDto(storage, basket.getGamesInBasket(), newOrderParameters, getCurrentUser().getUsername()));
        status.setComplete();
        session.removeAttribute("newOrderParameters");
        session.removeAttribute("basket");

        return "redirect:/user-orders";
    }

    private UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (UserDetails) authentication.getPrincipal();
    }
}
