package com.recipe.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String URL =
        "jdbc:mysql://localhost:3306/RecipePlatform?useSSL=false&serverTimezone=UTC&characterEncoding=utf8&connectionCollation=utf8_general_ci&useUnicode=true";


    private static final String USER = "root";      // change
    private static final String PASS = "UNICRON";  // change

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
