<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Recipe Platform - Register</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
    <h2>Register</h2>
    <c:if test="${not empty errorMsg}">
    <p style="color:red;">${errorMsg}</p>
</c:if>


    <form method="post" action="${pageContext.request.contextPath}/RegisterServlet">

        <label>Name:</label>
        <input type="text" name="name" required><br>

        <label>Email:</label>
        <input type="email" name="email" required><br>

        <label>Password:</label>
        <input type="password" name="password" required><br>

        <input type="submit" value="Register">
    </form>

    <p><a href="login.jsp">Already have an account? Login</a></p>
</div>
</body>
</html>
