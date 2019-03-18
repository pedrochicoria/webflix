<<%--
  Created by IntelliJ IDEA.
  User: pedrochicoria
  Date: 30/10/2018
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
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
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;

            width: 100%;

        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
            width: 30%;
        }

        tr:nth-child(even) {
            background-color: #222;
        }
    </style>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand">WebFlix</a>
            </div>
            <div class="navbar-header">
                <form method="post">
                    <input type="hidden" name="action" value="homepage">
                    <button class="btn btn-danger navbar-btn">Home</button>
                </form>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <form method="post">
                    <input type="hidden" name="action" value="logout">
                    <button class="btn btn-danger navbar-btn"><span class="glyphicon glyphicon-log-out"></span> Logout
                    </button>
                </form>
            </ul>

        </div>
    </nav>
    <div id="left" style="float:left; width:100%;" align="center">
        <h3 style="color:red">Search By Category</h3>
        <form method="post" style="color: red">

            </select>
            <br><br>
            Category <br><select style="color: black" type="text" placeholder="Select category" name="category">
            <option value="Action">Action</option>
            <option value="Romance">Romance</option>
            <option value="Comedy">Comedy</option>
            <option value="History">History</option>
            <option value="Thriller">Thriller</option>
            <option value="Fantasy">Fantasy</option>
            <br><br>
            <input type="hidden" name="action" value="searchcontentbycategory">
            <button type="submit" class="registerbtn">Search by category</button>
        </select>
        </form>
    </div>
</div>
</body>
</html>