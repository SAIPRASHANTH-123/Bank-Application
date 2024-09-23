<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login</title>
    
    
</head>
<body class="page">
    <form class="form-control-custom" action="adminlogin" method="post">
        <p class="title">Admin Login</p>
        <div class="input-field">
            <input required="" class="input" type="text" name="username" placeholder=" " />
            <label class="label" for="username">Enter Username:</label>
        </div>
        <div class="input-field">
            <input required="" class="input" type="password" name="password" placeholder=" " />
            <label class="label" for="password">Enter Password</label>
        </div>
        <button class="submit-btn" type="submit">Login</button>
        <div class="admin">
            <a href="customerlogin.jsp" class="shift">Customer Login</a>
        </div>
    </form>
</body>
</html>
