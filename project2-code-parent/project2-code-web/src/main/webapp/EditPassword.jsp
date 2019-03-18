<%--
  Created by IntelliJ IDEA.
  User: pedrochicoria
  Date: 02/11/2018
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

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
<header>
</header>

<div class="container">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand">WebFlix</a>
            </div>
            <div class="navbar-header">
                <form method="post">
                    <input type="hidden" name="action" value="homepage">
                    <button class="btn btn-danger navbar-btn" style="width: 100%">Home</button>
                </form>
            </div>

            <ul class="nav navbar-nav navbar-right">
                <form method="post">
                    <input type="hidden" name="action" value="logout">
                    <button class="btn btn-danger navbar-btn" style="width: 100%"><span
                            class="glyphicon glyphicon-log-out"></span> Logout
                    </button>
                </form>
            </ul>
        </div>
    </nav>
    <div id="left" style="float:left; width:100%;" align="center">
        <h3 style="color:red">Edit Password</h3>
        <form method="post" style="color: red;">
            New password<br> <input style="color: black" type="password" placeholder="Enter new password"
                                    name="password" required>
            <br>
            <input type="hidden" name="action" value="changepassword">
            <button type="submit" class="registerbtn">Change Password</button>
        </form>

    </div>
</div>

</body>
</html>