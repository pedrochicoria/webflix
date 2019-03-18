<%@ page import="data.Content" %>
<%@ page import="java.util.List" %>
<<%--
  Created by IntelliJ IDEA.
  User: pedrochicoria
  Date: 30/10/2018
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<div  class="container">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand">WebFlix</a>
            </div>
            <div class ="navbar-header">
                <form method="post">
                    <input type="hidden" name="action" value="homepage">
                    <button class="btn btn-danger navbar-btn">Home</button>
                </form>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <form  method="post">
                    <input type="hidden" name="action" value="logout">
                    <button class="btn btn-danger navbar-btn"><span class="glyphicon glyphicon-log-out"></span> Logout</button>
                </form>
            </ul>

        </div>
    </nav>

    <h2 style="color: red;font-size: 30px">Delete Content from WatchLists</h2>
    <% List<Content> allContent = (List<Content>) request.getSession().getAttribute("watchlist");
        if (!allContent.isEmpty()) {
    %>
    <table style="color: #f1f1f1">
        <tr>
            <th style="color: red"> Title</th>
            <th style="color: red"> Category</th>
            <th style="color: red"> Director</th>
            <th style="color: red"> Year</th>
        </tr>
        <%
            for (Content c : allContent) {
        %>
        <td style="color: #f1f1f1">
            <form method="post">
                <input type="hidden" name="deletefromlist" value="<%out.print(c.getTitle());%>">
                <button style="background-color: #222;color: white;border-color: #222" class="btn btn-danger navbar-btn"
                        name="action" value="deletefromwatchlist"><% out.println(c.getTitle()); %>
                </button>
            </form>
        </td>
        <td style="color: #f1f1f1"><% out.println(c.getCategory()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getDirector()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getYear()); %></td>

        </tr>
        <%

            }
        %>

    </table>
    <%
        }%>
</div>
</body>
</html>