<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Account Login</title>
    <link href="https://fonts.googleapis.com/css?family=Poppins&display=swap" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    
</head>
<body class="page">
    <form class="form-control-custom" action="LoginServlet" method="post">
        <p class="title">Customer Login</p>
        <div class="input-field">
            <input required="" class="input" type="text" name="accountNo" placeholder=" " />
            <label class="label" for="accountNo">Enter Account Number</label>
        </div>
        <div class="input-field">
            <input required="" class="input" type="password" name="password" placeholder=" " />
            <label class="label" for="password">Enter Password</label>
        </div>
        <button class="submit-btn" type="submit">Sign In</button>
        <div class="admin">
            <a href="AdminLogin.jsp" class="shift">Login as Admin</a>
        </div>
    </form>
</body>
</html>
