package com.osetrova.project.servlet.game;

import com.osetrova.project.connection.ContextUtil;
import com.osetrova.project.dto.GameFilterDto;
import com.osetrova.project.dto.LimitOffsetDto;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.service.GameService;
import com.osetrova.project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/games-filter")
public class GameFilterServlet extends HttpServlet {

    private static final String FILTERS = "filters";
    private static final String ITEMS_ON_PAGE = "items_on_page";
    private static final GameService GAME_SERVICE = ContextUtil.getBean("gameService", GameService.class);
    private static final String PAGE = "page";
    private static final String PRICE = "price";
    private static final String ISSUE_YEAR = "issue_year";
    private static final String AGE_LIMIT = "age_limit";
    private static final String FILTERED_GAMES = "filteredGames";
    private static final String PAGES_NUMBER = "pagesNumber";
    private static final String CURRENT_PAGE = "currentPage";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                List<Game> allFilteredGames = GAME_SERVICE.filterGames(filters, limitOffset);
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
        filteredGames = GAME_SERVICE.filterGames(filters, limitOffset);
        req.setAttribute(FILTERED_GAMES, filteredGames);
        req.setAttribute(PAGES_NUMBER, pagesNumber);
        req.setAttribute(CURRENT_PAGE, currentPage);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("games-filter")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            req.getSession().setAttribute(FILTERS, filters);
        }

        if (!"".equals(req.getParameter(ITEMS_ON_PAGE))) {
            req.getSession().setAttribute(ITEMS_ON_PAGE, req.getParameter(ITEMS_ON_PAGE));
        }
        resp.sendRedirect("/games-filter");
    }
}
