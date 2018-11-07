package servlet.game;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import project.connection.ApplicationSessionFactory;
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

    private static final SessionFactory FACTORY = ApplicationSessionFactory.getSessionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Session session = FACTORY.openSession()) {
            List<Game> games = GameService.getInstance().findAll(session);
            List<AgeLimit> ageLimits = Arrays.asList(AgeLimit.values());
            req.setAttribute("games", games);
            req.setAttribute("ageLimits", ageLimits);
            getServletContext().getRequestDispatcher(JspPathUtil.getPath("all-games")).forward(req, resp);
        }
    }
}
