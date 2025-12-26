package com.recipe.servlet;

import com.recipe.dao.RecipeDAO;
import com.recipe.model.User;
import com.recipe.util.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/AdminRecipeServlet")
public class AdminRecipeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("user") : null;
        if (admin == null || !"admin".equals(admin.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String recipeIdStr = request.getParameter("recipeId");

        Connection conn = DBConnect.getConnection();
        RecipeDAO recipeDAO = new RecipeDAO(conn);

        if (recipeIdStr != null) {
            try {
                int recipeId = Integer.parseInt(recipeIdStr);
                if ("approve".equals(action)) {
                    recipeDAO.updateStatus(recipeId, "approved");
                } else if ("reject".equals(action)) {
                    recipeDAO.updateStatus(recipeId, "rejected");
                }
            } catch (NumberFormatException ignored) {}
        }

        response.sendRedirect("admin/dashboard");
    }
}
