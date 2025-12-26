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

@WebServlet("/RecipeServlet")
public class RecipeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        Connection conn = DBConnect.getConnection();
        RecipeDAO recipeDAO = new RecipeDAO(conn);

        if ("create".equals(action)) {
            Recipe r = new Recipe();
            r.setUserId(user.getId());
            r.setTitle(request.getParameter("title"));
            r.setIngredients(request.getParameter("ingredients"));
            r.setInstructions(request.getParameter("instructions"));
            r.setPhoto(request.getParameter("photo"));
            r.setStatus("pending");
            recipeDAO.addRecipe(r);
        } else if ("delete".equals(action)) {
            try {
                int recipeId = Integer.parseInt(request.getParameter("recipeId"));
                recipeDAO.deleteRecipe(recipeId, user.getId());
            } catch (NumberFormatException ignored) {}
        }

        response.sendRedirect("user/dashboard");
    }
}
