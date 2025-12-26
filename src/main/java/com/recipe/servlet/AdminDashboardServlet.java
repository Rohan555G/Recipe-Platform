package com.recipe.servlet;

import com.recipe.dao.UserDAO;
import com.recipe.dao.RecipeDAO;
import com.recipe.model.User;
import com.recipe.model.Recipe;
import com.recipe.util.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("user") : null;
        if (admin == null || !"admin".equals(admin.getRole())) {
            response.sendRedirect("../login.jsp");
            return;
        }

        Connection conn = DBConnect.getConnection();
        UserDAO userDAO = new UserDAO(conn);
        RecipeDAO recipeDAO = new RecipeDAO(conn);

        List<User> users = userDAO.getAllUsers();
        List<Recipe> pendingRecipes = recipeDAO.getRecipesByStatus("pending");

        request.setAttribute("userList", users);
        request.setAttribute("pendingRecipes", pendingRecipes);
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}
