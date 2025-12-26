package com.recipe.servlet;

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

@WebServlet("/user/dashboard")
public class UserDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null || !"user".equals(user.getRole())) {
            response.sendRedirect("../login.jsp");
            return;
        }

        String query = request.getParameter("q");

        Connection conn = DBConnect.getConnection();
        RecipeDAO recipeDAO = new RecipeDAO(conn);

        List<Recipe> myRecipes = recipeDAO.getRecipesByUser(user.getId());
        List<Recipe> discoverRecipes = (query == null || query.isEmpty())
                ? recipeDAO.getRecipesByStatus("approved")
                : recipeDAO.searchApprovedRecipesByTitle(query);

        request.setAttribute("myRecipes", myRecipes);
        request.setAttribute("discoverRecipes", discoverRecipes);
        request.getRequestDispatcher("/user/dashboard.jsp").forward(request, response);
    }
}
