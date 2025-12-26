package com.recipe.servlet;

import com.recipe.dao.CommentDAO;
import com.recipe.model.User;
import com.recipe.util.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int recipeId;
        try {
            recipeId = Integer.parseInt(request.getParameter("recipeId"));
        } catch (NumberFormatException e) {
            response.sendRedirect("user/dashboard");
            return;
        }
        String text = request.getParameter("comment");

        Connection conn = DBConnect.getConnection();
        CommentDAO commentDAO = new CommentDAO(conn);
        commentDAO.addComment(recipeId, user.getId(), text);

        response.sendRedirect("viewRecipe?id=" + recipeId);
    }
}
