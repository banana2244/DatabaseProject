<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<title>Submit Request</title>
</head>
<body>

<% Client loggedInClient = (Client)session.getAttribute("loggedClient"); %>
<% if(loggedInClient != null) { %>

<h2>Submit Your Request, <%= loggedInClient.getFirstName() %> <%= loggedInClient.getLastName() %> </h2>

<form action="ControlServlet" method="post">
    <label for="clientName">Name:</label><br>
    <input type="text" id="clientName" name="clientName" value="<%= loggedInClient.getFirstName() %> <%= loggedInClient.getLastName() %>" required readonly><br>

    <label for="clientEmail">Email:</label><br>
    <input type="email" id="clientEmail" name="clientEmail" value="<%= loggedInClient.getEmail() %>" required readonly><br>

    <label for="requestDetails">Request Details:</label><br>
    <textarea id="requestDetails" name="requestDetails" rows="4" cols="50" required></textarea><br>

    <input type="hidden" name="action" value="submitClientRequest">
    <input type="submit" value="Submit Request">
</form>

<% } else { %>
<p>Please <a href="login.jsp">login</a> to submit a request.</p>
<% } %>

</body>
</html>
