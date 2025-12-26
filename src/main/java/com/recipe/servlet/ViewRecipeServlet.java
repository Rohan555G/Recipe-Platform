package com.recipe.servlet;

import com.recipe.dao.RecipeDAO;
import com.recipe.dao.RatingDAO;
import com.recipe.dao.CommentDAO;
import com.recipe.model.Recipe;
import com.recipe.model.Comment;
import com.recipe.util.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/viewRecipe")
public class ViewRecipeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ADD THESE 3 LINES AT THE VERY TOP
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String idStr = request.getParameter("id");
        int recipeId;
        try {
            recipeId = Integer.parseInt(idStr);
        } catch (Exception e) {
            response.sendRedirect("user/dashboard");
            return;
        }

        Connection conn = DBConnect.getConnection();
        RecipeDAO recipeDAO = new RecipeDAO(conn);
        RatingDAO ratingDAO = new RatingDAO(conn);
        CommentDAO commentDAO = new CommentDAO(conn);

        Recipe recipe = recipeDAO.getRecipeById(recipeId);
        double avgRating = ratingDAO.getAverageRating(recipeId);
        int ratingCount = ratingDAO.getRatingCount(recipeId);
        List<Comment> comments = commentDAO.getCommentsByRecipe(recipeId);

        request.setAttribute("recipe", recipe);
        request.setAttribute("avgRating", avgRating);
        request.setAttribute("ratingCount", ratingCount);
        request.setAttribute("comments", comments);

        request.getRequestDispatcher("/viewRecipe.jsp").forward(request, response);
    }
}
