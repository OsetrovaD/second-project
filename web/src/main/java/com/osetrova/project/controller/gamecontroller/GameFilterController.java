package com.osetrova.project.controller.gamecontroller;

import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@SessionAttributes({"filters", "items_on_page"})
public class GameFilterController {

    private static final String ITEMS_ON_PAGE = "items_on_page";
    private static final String FILTERS = "filters";
    private static final String PAGE = "page";
    private static final String PRICE = "price";
    private static final String ISSUE_YEAR = "issue_year";
    private static final String AGE_LIMIT = "age_limit";
    private static final String FILTERED_GAMES = "filteredGames";
    private static final String PAGES = "pages";
    private static final String CURRENT_PAGE = "currentPage";

    private final GameService gameService;

    @Autowired
    public GameFilterController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games-filter")
    public String getFilteredGames(Model model, HttpServletRequest req) {
        GameFilterDto filters = GameFilterDto.builder().build();
        LimitOffsetDto limitOffset = LimitOffsetDto.of(0, 0);
        List<Game> filteredGames;
        int pagesNumber = 0;
        int currentPage = 0;

        Integer limit = null;
        if (req.getSession().getAttribute(ITEMS_ON_PAGE) != null) {
            limit = Integer.valueOf((String) req.getSession().getAttribute(ITEMS_ON_PAGE));
        }

        if (req.getSession().getAttribute(FILTERS) != null) {
            filters = (GameFilterDto) req.getSession().getAttribute(FILTERS);
        }

        if (limit != null) {
            List<Game> allFilteredGames = gameService.filterGames(filters, limitOffset);
            pagesNumber = (int) Math.ceil(allFilteredGames.size() / Double.valueOf((String) req.getSession().getAttribute(ITEMS_ON_PAGE)));
            String page = req.getParameter(PAGE);
            if (page == null) {
                limitOffset = LimitOffsetDto.of(limit, 0);
                currentPage = 1;
            } else {
                Integer offset = limit * (Integer.valueOf(page) - 1);
                limitOffset = LimitOffsetDto.of(limit, offset);
                currentPage = Integer.valueOf(page);
            }
        }
        List<Integer> pages = IntStream.rangeClosed(1, pagesNumber).boxed().collect(Collectors.toList());
        filteredGames = gameService.filterGames(filters, limitOffset);
        model.addAttribute(FILTERED_GAMES, filteredGames);
        model.addAttribute(PAGES, pages);
        model.addAttribute(CURRENT_PAGE, currentPage);

        return "games-filter";
    }

    @PostMapping("/games-filter")
    public String getFilterParameters(Model model, HttpServletRequest req) {
        GameFilterDto filters = GameFilterDto.builder().build();

        if (req.getParameterValues(FILTERS) != null) {
            Arrays.stream(req.getParameterValues(FILTERS))
                    .filter(PRICE::equals)
                    .findFirst()
                    .ifPresent(x -> filters.setPrice(Integer.valueOf(req.getParameter(x))));
            Arrays.stream(req.getParameterValues(FILTERS))
                    .filter(ISSUE_YEAR::equals)
                    .findFirst()
                    .ifPresent(x -> filters.setIssueYear(Integer.valueOf(req.getParameter(x))));
            Arrays.stream(req.getParameterValues(FILTERS))
                    .filter(AGE_LIMIT::equals)
                    .findFirst()
                    .ifPresent(x -> filters.setAgeLimit(AgeLimit.valueOf(x)));
            model.addAttribute(FILTERS, filters);
        }

        if (!"".equals(req.getParameter(ITEMS_ON_PAGE))) {
            model.addAttribute(ITEMS_ON_PAGE, req.getParameter(ITEMS_ON_PAGE));
        }

        return "redirect:/games-filter";
    }
}
