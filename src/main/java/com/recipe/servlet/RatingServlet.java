package com.recipe.servlet;

import com.recipe.dao.RatingDAO;
import com.recipe.model.User;
import com.recipe.util.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/RatingServlet")
public class RatingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int recipeId;
        int rating;
        try {
            recipeId = Integer.parseInt(request.getParameter("recipeId"));
            rating = Integer.parseInt(request.getParameter("rating"));
        } catch (NumberFormatException e) {
            response.sendRedirect("user/dashboard");
            return;
        }

        Connection conn = DBConnect.getConnection();
        RatingDAO ratingDAO = new RatingDAO(conn);
        ratingDAO.addOrUpdateRating(recipeId, user.getId(), rating);

        response.sendRedirect("viewRecipe?id=" + recipeId);
    }
}
