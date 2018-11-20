package com.osetrova.project.servlet.game;

import com.osetrova.project.connection.ContextUtil;
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

@WebServlet("/all-games")
public class AllGamesServlet extends HttpServlet {

    private static final GameService GAME_SERVICE = ContextUtil.getBean("gameService", GameService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<Game> games = GAME_SERVICE.findAll();
            List<AgeLimit> ageLimits = Arrays.asList(AgeLimit.values());
            req.setAttribute("games", games);
            req.setAttribute("ageLimits", ageLimits);
            getServletContext().getRequestDispatcher(JspPathUtil.getPath("all-games")).forward(req, resp);
    }
}
