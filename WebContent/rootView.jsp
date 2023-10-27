<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Root page</title>
</head>
<body>

<div align = "center">
	
	<form action = "initialize">
		<input type = "submit" value = "Initialize the Database"/>
	</form>
	<a href="login.jsp"target ="_self" > logout</a><br><br> 

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
        <c:forEach var="client" items="${listclient}">
            <tr style="text-align:center">
            	<td>${client.clientID}</td>
                <td>${client.firstName}</td>
                <td>${client.lastName}</td>
                <td>${client.address}</td>
                <td>${client.email}</td>
                <td>${client.phoneNumber}</td>
                <td>${client.creditCardInfo}</td>
            </tr>
        </c:forEach>
    </table>
</div>
	</div>

</body>
</html>