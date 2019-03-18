<%--
  Created by IntelliJ IDEA.
  User: pedrochicoria
  Date: 30/10/2018
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" type="text/css" href="style.css">
<c:if test="${empty user}">
    <c:redirect url="/Login.jsp"/>
</c:if>
<html>
<head>
    <title>WebFlix</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="style.css">

</head>
<body>
<div class="container">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="">WebFlix</a>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <form method="post">
                    <input type="hidden" name="action" value="register">
                    <button class="btn btn-danger navbar-btn" style="width: 100%"><span
                            class="glyphicon glyphicon-user"></span> Sign up
                    </button>

                </form>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <form method="post">
                    <input type="hidden" name="action" value="login">
                    <button class="btn btn-danger navbar-btn" style="width: 100%"><span
                            class="glyphicon glyphicon-log-in"></span> Login
                    </button>
                </form>
            </ul>
        </div>
    </nav>

    <div id="left" style="float:left; width:100%;" align="center">
        <h3 style="color:red">Create an account</h3>
        <form method="post" style="color: red;">
            Name <br>
            <input style="color: black" type="text" placeholder="Enter Name" name="name" required>
            <br>
            E-mail<br> <input style="color: black" type="text" placeholder="Enter e-mail" name="email" required>
            <br>
            Username <br>
            <input style="color: black" type="text" placeholder="Enter Username" name="username" required>
            <br>
            Password <br>
            <input style="color: black" type="password" placeholder="Enter Password" name="password" required>
            <br>
            Credit Card Number <br>
            <input style="color: black" type="text" placeholder="Enter Credit Card Number" name="card_number" required>
            <br>
            <input type="hidden" name="action" value="signup">
            <button type="submit" class="registerbtn">Register</button>
        </form>


    </div>


</div>

</body>
</html>
