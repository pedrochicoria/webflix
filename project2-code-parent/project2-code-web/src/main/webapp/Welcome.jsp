<%--
  Created by IntelliJ IDEA.
  User: pedrochicoria
  Date: 03/11/2018
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html><c:if test="${empty user}">
    <c:redirect url="/Login.jsp"/>
</c:if>

<html lang="en">
<head>
    <title>WebFlix</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="style.css">
    <style>
        body {
            background: black url("webflix.jpg") no-repeat fixed center;
        }

    </style>
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
</div>

</body>
</html>