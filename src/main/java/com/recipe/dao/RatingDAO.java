package com.recipe.dao;

import java.sql.*;

public class RatingDAO {
    private Connection conn;

    public RatingDAO(Connection conn) {
        this.conn = conn;
    }

    public void addOrUpdateRating(int recipeId, int userId, int rating) {
        String checkSql = "SELECT id FROM ratings WHERE recipe_id=? AND user_id=?";
        String insertSql = "INSERT INTO ratings (recipe_id, user_id, rating) VALUES (?, ?, ?)";
        String updateSql = "UPDATE ratings SET rating=? WHERE recipe_id=? AND user_id=?";
        try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            checkPs.setInt(1, recipeId);
            checkPs.setInt(2, userId);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                        ps.setInt(1, rating);
                        ps.setInt(2, recipeId);
                        ps.setInt(3, userId);
                        ps.executeUpdate();
                    }
                } else {
                    try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                        ps.setInt(1, recipeId);
                        ps.setInt(2, userId);
                        ps.setInt(3, rating);
                        ps.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getAverageRating(int recipeId) {
        String sql = "SELECT AVG(rating) AS avg_rating FROM ratings WHERE recipe_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_rating");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public int getRatingCount(int recipeId) {
        String sql = "SELECT COUNT(*) AS cnt FROM ratings WHERE recipe_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cnt");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
