<%@ page import="data.Content" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: pedrochicoria
  Date: 03/11/2018
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<c:if test="${empty user}">
    <c:redirect url="/Login.jsp"/>
</c:if>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Webflix</title>
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
            width: 100px;
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
                    <button class="btn btn-danger navbar-btn" style="width: 100%"><span
                            class="glyphicon glyphicon-log-out"></span> Logout
                    </button>
                </form>
            </ul>

        </div>
    </nav>

    <div id="left" style="float:left; width:100%;" align="center">
        <h3 style="color:red">Edit Year</h3>
        <form method="post" style="color: red">

            Content <br><select style="color: black" type="text" name="contentname">
            <%
                List<Content> list = (List<Content>) request.getSession().getAttribute("allContent");
                for (Content content : list) {
            %>
            <option value="<%out.print(content.getTitle());%>"><%out.print(content.getTitle());%></option>
            <%
                }
            %>
        </select>
            <br><br>
            Year <br>
            <input style="color: black" type="text" placeholder="Enter new year" name="year" required>
            <br><br>
            <input type="hidden" name="action" value="changeyear">
            <button type="submit" class="registerbtn">Change year</button>

        </form>
    </div>

</div>
</body>
</html>