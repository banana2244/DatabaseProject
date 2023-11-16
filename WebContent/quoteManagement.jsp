<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Quote Management</title>
</head>
<body>

<div align="center">
    <h1>Manage Client Requests</h1>
    <div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of Client Requests</h2></caption>
            <tr>
                <th>ID</th>
                <th>Client Name</th>
                <th>Email</th>
                <th>Request Details</th>
                <th>Response</th>
                <th>Actions</th>
                <th>Ready Debug</th>
                <th>Accepted Debug</th>
            </tr>
            <c:forEach var="request" items="${listclient}">
                <tr style="text-align:center">
                    <td>${request.id}</td>
                    <td>${request.clientName}</td>
                    <td>${request.clientEmail}</td>
                    <td>${request.requestDetails}</td>
                    <td>${request.response}</td>
                    <td>
                        <c:if test="${request.ready == false && request.accepted == null}">
                            <!-- Accept Button -->
                            <form action="ControlServlet" method="post">
                                <input type="hidden" name="requestId" value="${request.id}" />
                                <input type="hidden" name="action" value="accept" />
                                <input type="submit" value="Accept" />
                            </form>

                            <!-- Deny Button -->
                            <form action="ControlServlet" method="post">
                                <input type="hidden" name="requestId" value="${request.id}" />
                                <input type="hidden" name="action" value="deny" />
                                <input type="submit" value="Deny" />
                            </form>

                            <!-- Response Box -->
                            <form action="ControlServlet" method="post">
                                <input type="hidden" name="requestId" value="${request.id}" />
                                <input type="hidden" name="action" value="respond" />
                                <textarea name="responseText"></textarea>
                                <input type="submit" value="Submit Response" />
                            </form>
                        </c:if>
                        <c:if test="${request.ready == true}">
                            <textarea readonly>${request.response}</textarea>
                        </c:if>
                    </td>
                    <td>${request.ready}</td>
                    <td>${request.accepted}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

</body>
</html>
