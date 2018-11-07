package servlet.game;

import project.dto.GameFilterDto;
import project.dto.LimitOffsetDto;
import project.entity.Game;
import project.entity.enumonly.AgeLimit;
import service.GameService;
import util.JspPathUtil;

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
                List<Game> allFilteredGames = GameService.getInstance().filterGames(filters, limitOffset);
                pagesNumber = (int) Math.ceil(allFilteredGames.size() / Double.valueOf((String) req.getSession().getAttribute(ITEMS_ON_PAGE)));
            String page = req.getParameter("page");
            if (page == null) {
                limitOffset = LimitOffsetDto.of(limit, 0);
                currentPage = 1;
            } else {
                Integer offset = limit * (Integer.valueOf(page) - 1);
                limitOffset = LimitOffsetDto.of(limit, offset);
                currentPage = Integer.valueOf(page);
            }
        }
        filteredGames = GameService.getInstance().filterGames(filters, limitOffset);
        req.setAttribute("filteredGames", filteredGames);
        req.setAttribute("pagesNumber", pagesNumber);
        req.setAttribute("currentPage", currentPage);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("games-filter")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameFilterDto filters = GameFilterDto.builder().build();

        if (req.getParameterValues(FILTERS) != null) {
            Arrays.stream(req.getParameterValues(FILTERS))
                    .filter("price"::equals)
                    .findFirst()
                    .ifPresent(x -> filters.setPrice(Integer.valueOf(req.getParameter(x))));
            Arrays.stream(req.getParameterValues(FILTERS))
                    .filter("issue_year"::equals)
                    .findFirst()
                    .ifPresent(x -> filters.setIssueYear(Integer.valueOf(req.getParameter(x))));
            Arrays.stream(req.getParameterValues(FILTERS))
                    .filter("age_limit"::equals)
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
