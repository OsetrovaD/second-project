package com.osetrova.project.controller.gamecontroller;

import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.requestdto.GamesFilterRequestDto;
import com.osetrova.project.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@SessionAttributes({"filters", "items_on_page"})
public class GameFilterController {

    private static final String ITEMS_ON_PAGE = "items_on_page";
    private static final String FILTERS = "filters";
    private static final String PAGE = "page";
    private static final String FILTERED_GAMES = "filteredGames";
    private static final String PAGES = "pages";
    private static final String CURRENT_PAGE = "currentPage";

    private final GameService gameService;

    @Autowired
    public GameFilterController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games-filter")
    public String getFilteredGames(Model model,
                                   @SessionAttribute(ITEMS_ON_PAGE) String itemsOnPage,
                                   @SessionAttribute(FILTERS) GameFilterDto requestFilters,
                                   @RequestParam(PAGE) Integer requestPage) {
        LimitOffsetDto limitOffset = LimitOffsetDto.of(0, 0);
        int pagesNumber = 0;
        int currentPage = 0;

        if (!itemsOnPage.isEmpty()) {
            Integer limit = Integer.valueOf(itemsOnPage);
            List<Game> allFilteredGames = gameService.filterGames(requestFilters, limitOffset);
            pagesNumber = (int) Math.ceil(allFilteredGames.size() / Double.valueOf(itemsOnPage));

            Integer offset = limit * (requestPage - 1);
            limitOffset = LimitOffsetDto.of(limit, offset);
            currentPage = requestPage;
        }

        List<Integer> pages = IntStream.rangeClosed(1, pagesNumber).boxed().collect(Collectors.toList());
        List<Game> filteredGames = gameService.filterGames(requestFilters, limitOffset);
        model.addAttribute(FILTERED_GAMES, filteredGames);
        model.addAttribute(PAGES, pages);
        model.addAttribute(CURRENT_PAGE, currentPage);

        return "games-filter";
    }

    @PostMapping("/games-filter")
    public String getFilterParameters(Model model, GamesFilterRequestDto filterRequest) {
        GameFilterDto filters = GameFilterDto.builder().build();

        if (filterRequest.isAgeLimitFilterChosen()) {
            filters.setAgeLimit(AgeLimit.valueOf(filterRequest.getAgeLimit()));
        }

        if (filterRequest.isIssueYearFilterChosen()) {
            filters.setIssueYear(Integer.valueOf(filterRequest.getIssueYear()));
        }

        if (filterRequest.isPriceFilterChosen()) {
            filters.setPrice(Integer.valueOf(filterRequest.getPrice()));
        }
        model.addAttribute(ITEMS_ON_PAGE, filterRequest.getItemsOnPage());
        model.addAttribute(FILTERS, filters);
        model.addAttribute(PAGE, 1);

        return "redirect:/games-filter";
    }
}
