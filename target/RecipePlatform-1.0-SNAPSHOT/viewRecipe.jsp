<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.recipe.model.Recipe" %>
<%@ page import="com.recipe.model.Comment" %>
<%@ page import="java.util.List" %>
<%
    Recipe recipe = (Recipe) request.getAttribute("recipe");
    Double avgRating = (Double) request.getAttribute("avgRating");
    if (avgRating == null) avgRating = 0.0;
    Integer ratingCount = (Integer) request.getAttribute("ratingCount");
    if (ratingCount == null) ratingCount = 0;
    List<Comment> comments = (List<Comment>) request.getAttribute("comments");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title><%= (recipe != null ? recipe.getTitle() : "Recipe") %> - Recipe Platform</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
    <a href="user/dashboard">Back to Dashboard</a>
    <%
        if (recipe == null) {
    %>
    <p>Recipe not found.</p>
    <%
        } else {
    %>
    <h2><%= recipe.getTitle() %></h2>
    <p><strong>Status:</strong> <%= recipe.getStatus() %></p>
    <%
        if (recipe.getPhoto() != null && !recipe.getPhoto().isEmpty()) {
    %>
    <img src="<%= recipe.getPhoto() %>" alt="Recipe photo" style="max-width:300px;">
    <%
        }
    %>

   <h3>Ingredients</h3>
<div class="recipe-content"><%= recipe.getIngredients() %></div>

<h3>Instructions</h3>
<div class="recipe-content"><%= recipe.getInstructions() %></div>


    <h3>Rating</h3>
    <p>Average rating: <%= String.format("%.1f", avgRating) %> (from <%= ratingCount %> ratings)</p>

    <h4>Rate this recipe</h4>
    <form action="RatingServlet" method="post">
        <input type="hidden" name="recipeId" value="<%= recipe.getId() %>">
        <select name="rating" required>
            <option value="">Select rating</option>
            <%
                for (int i = 1; i <= 5; i++) {
            %>
            <option value="<%= i %>"><%= i %></option>
            <%
                }
            %>
        </select>
        <input type="submit" value="Submit Rating">
    </form>

    <h3>Comments</h3>
    <%
        if (comments != null && !comments.isEmpty()) {
            for (Comment c : comments) {
    %>
    <div class="comment">
        <p><strong>User #<%= c.getUserId() %></strong> at <%= c.getCreatedAt() %></p>
        <p><%= c.getComment() %></p>
    </div>
    <hr>
    <%
            }
        } else {
    %>
    <p>No comments yet.</p>
    <%
        }
    %>

    <h4>Add a comment</h4>
    <form action="CommentServlet" method="post">
        <input type="hidden" name="recipeId" value="<%= recipe.getId() %>">
        <textarea name="comment" required></textarea><br>
        <input type="submit" value="Submit Comment">
    </form>
    <%
        }
    %>
</div>
</body>
</html>
