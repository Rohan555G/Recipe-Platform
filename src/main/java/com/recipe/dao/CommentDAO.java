package com.recipe.dao;

import com.recipe.model.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private Connection conn;

    public CommentDAO(Connection conn) {
        this.conn = conn;
    }

    public void addComment(int recipeId, int userId, String text) {
        String sql = "INSERT INTO comments (recipe_id, user_id, comment) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            ps.setInt(2, userId);
            ps.setString(3, text);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Comment> getCommentsByRecipe(int recipeId) {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE recipe_id=? ORDER BY created_at DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comment c = new Comment();
                    c.setId(rs.getInt("id"));
                    c.setRecipeId(rs.getInt("recipe_id"));
                    c.setUserId(rs.getInt("user_id"));
                    c.setComment(rs.getString("comment"));
                    c.setCreatedAt(String.valueOf(rs.getTimestamp("created_at")));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
