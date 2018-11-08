package servlet.game;

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

@WebServlet("/all-games")
public class AllGamesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<Game> games = GameService.getInstance().findAll();
            List<AgeLimit> ageLimits = Arrays.asList(AgeLimit.values());
            req.setAttribute("games", games);
            req.setAttribute("ageLimits", ageLimits);
            getServletContext().getRequestDispatcher(JspPathUtil.getPath("all-games")).forward(req, resp);
    }
}
