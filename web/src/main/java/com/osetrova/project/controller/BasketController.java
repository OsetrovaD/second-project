package com.osetrova.project.controller;

import com.osetrova.project.dto.gamepricedto.GamePriceForBasketDto;
import com.osetrova.project.exception.ItemAlreadyInBasketException;
import com.osetrova.project.exception.WrongNumberOfElementException;
import com.osetrova.project.service.GamePriceService;
import com.osetrova.project.servicedto.Basket;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("basket")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasketController {

    private final GamePriceService gamePriceService;
    private static final Basket BASKET = new Basket(new LinkedList<>());

    @GetMapping("/add-to-basket")
    public String addToBasket(Model model, @RequestParam("id") Long gamePriceId) {
        if (BASKET.getGamesInBasket().size() != 0) {
            boolean isItemAlreadyPresent = BASKET.getGamesInBasket().stream().anyMatch(x -> x.getId().equals(gamePriceId));
            if (isItemAlreadyPresent) {
                throw new ItemAlreadyInBasketException();
            }
        }

        GamePriceForBasketDto gameForBasket = gamePriceService.findByIdForBasket(gamePriceId);
        BASKET.getGamesInBasket().add(gameForBasket);
        model.addAttribute("basket", BASKET);

        return "redirect:/basket";
    }

    @GetMapping("/basket")
    public String getBasketPage() {
        return "basket";
    }

    @PostMapping("/basket")
    public String getItemsData(@ModelAttribute Basket basket) {
        List<GamePriceForBasketDto> itemsWithWrongNumber = basket.getGamesInBasket().stream()
                .filter(x -> x.getNumber() > x.getNumberInStorage()).collect(Collectors.toList());
        if (itemsWithWrongNumber.size() > 0) {
            itemsWithWrongNumber.forEach(x -> x.setNumber((short) 1));
            throw new WrongNumberOfElementException();
        }

        System.out.println();
        return "redirect:/order-parameter";
    }

    @GetMapping("/delete-from-basket")
    public String deleteFromBasket(@RequestParam("id") Long id) {
        String resultPage;
        BASKET.getGamesInBasket().stream()
                .filter(x -> x.getId().equals(id)).findFirst()
                .ifPresent(x -> BASKET.getGamesInBasket().remove(x));

        if (BASKET.getGamesInBasket().size() > 0) {
            resultPage = "redirect:/basket";
        } else {
            resultPage = "redirect:/all-games";
        }

        return resultPage;
    }
}
