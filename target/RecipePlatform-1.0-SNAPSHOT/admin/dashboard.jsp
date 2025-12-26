<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.recipe.model.User" %>
<%@ page import="com.recipe.model.Recipe" %>
<%
    User admin = (User) session.getAttribute("user");
    if (admin == null || !"admin".equals(admin.getRole())) {
        response.sendRedirect("../login.jsp");
        return;
    }
    List<User> userList = (List<User>) request.getAttribute("userList");
    List<Recipe> pendingRecipes = (List<Recipe>) request.getAttribute("pendingRecipes");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Admin Dashboard - Recipe Platform</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<div class="container">
    <h2>Admin Dashboard</h2>
    <p>Welcome, <%= admin.getName() %></p>
    <a href="../LogoutServlet">Logout</a>

    <h3>User Management</h3>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th><th>Name</th><th>Email</th><th>Role</th><th>Actions</th>
        </tr>
        <%
            if (userList != null) {
                for (User u : userList) {
        %>
        <tr>
            <td><%= u.getId() %></td>
            <td><%= u.getName() %></td>
            <td><%= u.getEmail() %></td>
            <td><%= u.getRole() %></td>
            <td>
                <form action="../AdminUserServlet" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="userId" value="<%= u.getId() %>">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <h3>Recipe Management (Pending)</h3>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th><th>Title</th><th>Author</th><th>Status</th><th>Actions</th>
        </tr>
        <%
            if (pendingRecipes != null) {
                for (Recipe r : pendingRecipes) {
        %>
        <tr>
            <td><%= r.getId() %></td>
            <td><%= r.getTitle() %></td>
            <td><%= r.getUserId() %></td>
            <td><%= r.getStatus() %></td>
            <td>
                <form action="../AdminRecipeServlet" method="post" style="display:inline;">
                    <input type="hidden" name="recipeId" value="<%= r.getId() %>">
                    <input type="hidden" name="action" value="approve">
                    <input type="submit" value="Approve">
                </form>
                <form action="../AdminRecipeServlet" method="post" style="display:inline;">
                    <input type="hidden" name="recipeId" value="<%= r.getId() %>">
                    <input type="hidden" name="action" value="reject">
                    <input type="submit" value="Reject">
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <h3>Recipe Statistics</h3>
    <p>Total recipes, average ratings, and trends can be shown here later.</p>

    <h3>User Activity Monitoring</h3>
    <p>Recent logins, comments, and ratings can be displayed here.</p>
</div>
</body>
</html>
