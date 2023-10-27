<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Client List</title>
</head>

<a href="login.jsp" target="_self">Logout</a><br><br> 

<h1>List all clients</h1>
<div align="center">
    <table border="1" cellpadding="6">
        <caption><h2>List of Clients</h2></caption>
        <tr>
            <th>Client ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Address</th>
            <th>Email</th>
            <th>Phone Number</th>
            <th>Credit Card Info</th> 
        </tr>
        <c:forEach var="client" items="${get_clients}">
            <tr style="text-align:center">
                <td>${client.ClientID}</td>
                <td>${client.FirstName}</td>
                <td>${client.LastName}</td>
                <td>${client.Address}</td>
                <td>${client.Email}</td>
                <td>${client.PhoneNumber}</td>
                <td>${client.CreditCardInfo}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<body>

</body>
</html>
