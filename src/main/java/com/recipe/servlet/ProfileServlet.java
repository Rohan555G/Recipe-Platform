package com.recipe.servlet;

import com.recipe.dao.UserDAO;
import com.recipe.model.User;
import com.recipe.util.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User sessionUser = (session != null) ? (User) session.getAttribute("user") : null;
        if (sessionUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection conn = DBConnect.getConnection();
        UserDAO userDAO = new UserDAO(conn);

        sessionUser.setName(name);
        sessionUser.setEmail(email);
        if (password != null && !password.isEmpty()) {
            sessionUser.setPassword(password);
        }

        userDAO.updateUser(sessionUser);
        session.setAttribute("user", sessionUser);
        response.sendRedirect("user/dashboard");
    }
}
