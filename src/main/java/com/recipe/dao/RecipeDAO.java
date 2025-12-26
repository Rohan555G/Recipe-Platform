package com.recipe.dao;

import com.recipe.model.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO {
    private Connection conn;

    public RecipeDAO(Connection conn) {
        this.conn = conn;
    }

    public void addRecipe(Recipe r) {
        String sql = "INSERT INTO recipes (user_id, title, ingredients, instructions, photo, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getUserId());
            ps.setString(2, r.getTitle());
            ps.setString(3, r.getIngredients());
            ps.setString(4, r.getInstructions());
            ps.setString(5, r.getPhoto());
            ps.setString(6, r.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getRecipesByUser(int userId) {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipes WHERE user_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Recipe> getRecipesByStatus(String status) {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipes WHERE status=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Recipe> searchApprovedRecipesByTitle(String title) {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipes WHERE status='approved' AND title LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStatus(int recipeId, String status) {
        String sql = "UPDATE recipes SET status=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, recipeId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRecipe(int recipeId, int userId) {
        String sql = "DELETE FROM recipes WHERE id=? AND user_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Recipe getRecipeById(int id) {
        String sql = "SELECT * FROM recipes WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Recipe mapRow(ResultSet rs) throws SQLException {
        Recipe r = new Recipe();
        r.setId(rs.getInt("id"));
        r.setUserId(rs.getInt("user_id"));
        r.setTitle(rs.getString("title"));
        r.setIngredients(rs.getString("ingredients"));
        r.setInstructions(rs.getString("instructions"));
        r.setPhoto(rs.getString("photo"));
        r.setStatus(rs.getString("status"));
        return r;
    }
}
