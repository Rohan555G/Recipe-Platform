<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.recipe.model.User" %>
<%@ page import="com.recipe.model.Recipe" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"user".equals(user.getRole())) {
        response.sendRedirect("../login.jsp");
        return;
    }
    List<Recipe> myRecipes = (List<Recipe>) request.getAttribute("myRecipes");
    List<Recipe> discoverRecipes = (List<Recipe>) request.getAttribute("discoverRecipes");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>User Dashboard - Recipe Platform</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<div class="container">
    <h2>User Dashboard</h2>
    <p>Welcome, <%= user.getName() %></p>
    <a href="../LogoutServlet">Logout</a>

    <h3>Profile Management</h3>
    <form action="../ProfileServlet" method="post">
        <input type="hidden" name="userId" value="<%= user.getId() %>">
        <label>Name:</label>
        <input type="text" name="name" value="<%= user.getName() %>" required><br>
        <label>Email:</label>
        <input type="email" name="email" value="<%= user.getEmail() %>" required><br>
        <label>Password:</label>
        <input type="password" name="password" placeholder="New password"><br>
        <input type="submit" value="Update Profile">
    </form>

    <h3>Share a Recipe</h3>
    <form action="../RecipeServlet" method="post">
        <input type="hidden" name="action" value="create">
        <label>Title:</label>
        <input type="text" name="title" required><br>
        <label>Ingredients:</label>
        <textarea name="ingredients" required></textarea><br>
        <label>Instructions:</label>
        <textarea name="instructions" required></textarea><br>
        <label>Photo URL:</label>
        <input type="text" name="photo"><br>
        <input type="submit" value="Share Recipe">
    </form>

    <h3>My Recipes</h3>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th><th>Title</th><th>Status</th><th>Actions</th>
        </tr>
        <%
            if (myRecipes != null) {
                for (Recipe r : myRecipes) {
        %>
        <tr>
            <td><%= r.getId() %></td>
            <td><%= r.getTitle() %></td>
            <td><%= r.getStatus() %></td>
            <td>
                <form action="../RecipeServlet" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="recipeId" value="<%= r.getId() %>">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <h3>Discover Recipes</h3>
    <form method="get" action="../user/dashboard">
        <input type="text" name="q" placeholder="Search by title">
        <input type="submit" value="Search">
    </form>
    <table border="1" cellpadding="5">
        <tr>
            <th>Title</th><th>Author</th><th>Status</th><th>Actions</th>
        </tr>
        <%
            if (discoverRecipes != null) {
                for (Recipe r : discoverRecipes) {
        %>
        <tr>
            <td><%= r.getTitle() %></td>
            <td><%= r.getUserId() %></td>
            <td><%= r.getStatus() %></td>
            <td>
                <a href="../viewRecipe?id=<%= r.getId() %>">View</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</div>
</body>
</html>
