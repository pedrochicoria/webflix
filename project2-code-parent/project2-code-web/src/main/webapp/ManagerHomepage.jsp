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
        input[type=text] {
            width: 110px;
            box-sizing: border-box;
            border: 2px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
            background-color: white;
            background-image: url('searchicon.png');
            background-position: 10px 10px;
            background-repeat: no-repeat;
            padding: 12px 20px 12px 40px;
            -webkit-transition: width 0.4s ease-in-out;
            transition: width 0.4s ease-in-out;
        }

        input[type=text]:focus {
            width: 100%;
        }

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
                    <button class="btn btn-danger navbar-btn" style="width: 100%">Home</button>
                </form>
            </div>
            <div class="navbar-header">
                <form method="post">

                    <div class="dropdown">
                        <button class="btn btn-danger navbar-btn dropdown-toggle" style="width: 100%" type="button"
                                data-toggle="dropdown">
                            My Profile
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <form method="post">
                                <input type="hidden" name="action" value="editname">
                                <button class="btn btn-danger" style="width: 100%">Edit Name</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="editemail">
                                <button class="btn btn-danger" style="width: 100%">Edit E-mail</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="editpassword">
                                <button class="btn btn-danger" style="width: 100%">Edit Password</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="editusername">
                                <button class="btn btn-danger" style="width: 100%">Edit Username</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="editcardnumber">
                                <button class="btn btn-danger" style="width: 100%">Edit Credit Card</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="deleteUser">
                                <button class="btn btn-danger" style="width: 100%">Delete Account</button>
                            </form>

                        </ul>
                    </div>
                </form>
            </div>
            <div class="navbar-header">
                <form method="post">

                    <div class="dropdown">
                        <button class="btn btn-danger navbar-btn dropdown-toggle" style="width: 100%" type="button"
                                data-toggle="dropdown">
                            Manage Content
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <form method="post">
                                <input type="hidden" name="action" value="insertcontent">
                                <button class="btn btn-danger" style="width: 100%">Insert Content</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="editcontenttitle">
                                <button class="btn btn-danger" style="width: 100%">Edit Title</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="editcontentdirector">
                                <button class="btn btn-danger" style="width: 100%">Edit Director</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="editcontentcategory">
                                <button class="btn btn-danger" style="width: 100%">Edit Category</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="editcontentyear">
                                <button class="btn btn-danger" style="width: 100%">Edit Year</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="editfile">
                                <button class="btn btn-danger" style="width: 100%">Edit Multimedia file</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="deletecontent">
                                <button class="btn btn-danger" style="width: 100%">Delete Content</button>
                            </form>
                        </ul>
                    </div>
                </form>
            </div>
            <div class="navbar-header">
                <form method="post">

                    <div class="dropdown">
                        <button class="btn btn-danger navbar-btn dropdown-toggle" style="width: 100%" type="button"
                                data-toggle="dropdown">
                            Search
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <form method="post">
                                <input type="hidden" name="action" value="searchallcontent">
                                <button class="btn btn-danger" style="width: 100%">Search All Content</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="searchbydirector">
                                <button class="btn btn-danger" style="width: 100%">Search by Director</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="searchbycategory">
                                <button class="btn btn-danger" style="width: 100%">Search by Category</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="searchbyyears">
                                <button class="btn btn-danger" style="width: 100%">Search by Years</button>
                            </form>
                        </ul>
                    </div>
                </form>
            </div>
            <div class="navbar-header">
                <form method="post">

                    <div class="dropdown">
                        <button class="btn btn-danger navbar-btn dropdown-toggle" style="width: 100%" type="button"
                                data-toggle="dropdown">
                            WatchList
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <form method="post">
                                <input type="hidden" name="action" value="addwatchlist">
                                <button class="btn btn-danger" style="width: 100%">Add</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="viewwatchlist">
                                <button class="btn btn-danger" style="width: 100%">View</button>
                            </form>
                            <form method="post">
                                <input type="hidden" name="action" value="removewatchlist">
                                <button class="btn btn-danger" style="width: 100%">Remove</button>
                            </form>
                        </ul>
                    </div>
                </form>
            </div>
            <div class="navbar-header">
                <form method="post">
                    <input type="hidden" name="action" value="usermode">
                    <button class="btn btn-danger navbar-btn" style="width: 100%">User Mode</button>
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

    <h2 style="color: red;font-size: 30px">Suggested Content</h2>
    <% List<Content> actionContents = (List<Content>) request.getSession().getAttribute("allActionContent");
        if (!actionContents.isEmpty()) {
    %>
    <table style="color: #f1f1f1">
        <tr>
            <th style="color: red"> Titlee</th>
            <th style="color: red"> Category</th>
            <th style="color: red"> Director</th>
            <th style="color: red"> Year</th>
        </tr>
        <%
            for (Content c : actionContents) {
                if (c.getCategory().equals("Action")) {
        %>
        <td style="color: #f1f1f1"><% out.print(c.getTitle()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getCategory()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getDirector()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getYear()); %></td>

        </tr>
        <%
                }
            }
        %>

    </table>
    <%
        }%>
    <br>
    <% List<Content> comedyContents = (List<Content>) request.getSession().getAttribute("allComedyContent");
        if (!comedyContents.isEmpty()) {
    %>
    <table style="color: #f1f1f1">
        <tr>
            <th style="color: red"> Title</th>
            <th style="color: red"> Category</th>
            <th style="color: red"> Director</th>
            <th style="color: red"> Year</th>
        </tr>
        <%
            for (Content c : comedyContents) {
                if (c.getCategory().equals("Comedy")) {
        %>
        <tr>
            <td style="color: #f1f1f1"><% out.print(c.getTitle()); %></td>
            <td style="color: #f1f1f1"><% out.println(c.getCategory()); %></td>
            <td style="color: #f1f1f1"><% out.println(c.getDirector()); %></td>
            <td style="color: #f1f1f1"><% out.println(c.getYear()); %></td>

        </tr>
        <%
                }
            }
        %>
    </table>
    <%
        }%>
    <br>
    <% List<Content> romanceContents = (List<Content>) request.getSession().getAttribute("allRomanceContent");
        if (!romanceContents.isEmpty()) {
    %>
    <table style="color: #f1f1f1">
        <tr>
            <th style="color: red"> Title</th>
            <th style="color: red"> Category</th>
            <th style="color: red"> Director</th>
            <th style="color: red"> Year</th>
        </tr>
        <%
            for (Content c : romanceContents) {
                if (c.getCategory().equals("Romance")) {
        %>
        <td style="color: #f1f1f1"><% out.print(c.getTitle()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getCategory()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getDirector()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getYear()); %></td>

        </tr>
        <%
                }
            }
        %>
    </table>
    <%
        }%>
    <br>

    <% List<Content> fantasyContents = (List<Content>) request.getSession().getAttribute("allFantasyContent");
        if (!fantasyContents.isEmpty()) {
    %>
    <table style="color: #f1f1f1">
        <tr>


            <th style="color: red"> Title</th>
            <th style="color: red"> Category</th>
            <th style="color: red"> Director</th>
            <th style="color: red"> Year</th>
        </tr>
        <%
            for (Content c : fantasyContents) {
                if (c.getCategory().equals("Fantasy")) {
        %>
        <td style="color: #f1f1f1"><% out.print(c.getTitle()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getCategory()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getDirector()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getYear()); %></td>

        </tr>
        <%
                }
            }
        %>
    </table>
    <%
        }%>

    <br>

    <% List<Content> historyContents = (List<Content>) request.getSession().getAttribute("allHistoryContent");
        if (!historyContents.isEmpty()) {
    %>
    <table style="color: #f1f1f1">
        <tr>

            <th style="color: red"> Title</th>
            <th style="color: red"> Category</th>
            <th style="color: red"> Director</th>
            <th style="color: red"> Year</th>
        </tr>
        <%
            for (Content c : historyContents) {
                if (c.getCategory().equals("History")) {
        %>
        <td style="color: #f1f1f1"><% out.print(c.getTitle()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getCategory()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getDirector()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getYear()); %></td>

        </tr>
        <%
                }
            }
        %>
    </table>
    <%
        }%>
    <br>
    <% List<Content> thrillerContents = (List<Content>) request.getSession().getAttribute("allThrillerContent");
        if (!thrillerContents.isEmpty()) {
    %>
    <table style="color: #f1f1f1">
        <tr>
            <th style="color: red"> Title</th>
            <th style="color: red"> Category</th>
            <th style="color: red"> Director</th>
            <th style="color: red"> Year</th>
        </tr>
        <%
            for (Content c : thrillerContents) {
                if (c.getCategory().equals("Thriller")) {
        %>
        <td style="color: #f1f1f1"><% out.print(c.getTitle()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getCategory()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getDirector()); %></td>
        <td style="color: #f1f1f1"><% out.println(c.getYear()); %></td>

        </tr>
        <%
                }
            }
        %>
    </table>
    <%
        }%>
</div>
</body>
</html>