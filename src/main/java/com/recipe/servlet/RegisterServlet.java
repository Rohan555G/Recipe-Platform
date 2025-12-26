package com.recipe.servlet;

import com.recipe.dao.UserDAO;
import com.recipe.model.User;
import com.recipe.util.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // basic validation
        if (name == null || name.isEmpty()
                || email == null || email.isEmpty()
                || password == null || password.isEmpty()) {
            req.setAttribute("errorMsg", "All fields are required.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        Connection conn = DBConnect.getConnection();
        if (conn == null) {
            req.setAttribute("errorMsg", "Database connection error.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        UserDAO userDAO = new UserDAO(conn);
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("user");

        boolean ok = userDAO.registerUser(user);

        if (ok) {
            // after successful registration, go to login
            resp.sendRedirect("login.jsp");
        } else {
            req.setAttribute("errorMsg", "Registration failed. Try a different email.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}
