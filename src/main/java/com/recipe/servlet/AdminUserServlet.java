package com.recipe.servlet;

import com.recipe.dao.UserDAO;
import com.recipe.model.User;
import com.recipe.util.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/AdminUserServlet")
public class AdminUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("user") : null;
        if (admin == null || !"admin".equals(admin.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String userIdStr = request.getParameter("userId");

        Connection conn = DBConnect.getConnection();
        UserDAO userDAO = new UserDAO(conn);

        if ("delete".equals(action) && userIdStr != null) {
            try {
                int userId = Integer.parseInt(userIdStr);
                userDAO.deleteUser(userId);
            } catch (NumberFormatException ignored) {}
        }

        response.sendRedirect("admin/dashboard");
    }
}
