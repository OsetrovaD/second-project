package servlet.user;

import project.entity.User;
import service.UserService;
import util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        User user = UserService.getInstance().getById(Long.valueOf(userId));
        req.setAttribute("user", user);

        getServletContext().getRequestDispatcher(JspPathUtil.getPath("user-info")).forward(req, resp);
    }
}
