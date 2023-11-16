<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Client Page</title>
</head>
<body>

    <h1>Welcome, ${loggedClientReal.firstName}!</h1>

    <h2>Submit a new Request</h2>
    <form action="ControlServlet" method="post">
        <label for="clientName">Name:</label><br>
        <input type="text" id="clientName" name="clientName" value="${loggedClientReal.firstName} ${loggedClientReal.lastName}" required readonly><br>

        <label for="clientEmail">Email:</label><br>
        <input type="email" id="clientEmail" name="clientEmail" value="${loggedClientReal.email}" required readonly><br>

        <label for="requestDetails">Request Details:</label><br>
        <textarea id="requestDetails" name="requestDetails" rows="4" cols="50" required ></textarea><br>

        <input type="hidden" name="action" value="submitClientRequest">
        <input type="submit" value="Submit Request">
    </form>

   <h2>Your Outgoing Requests</h2>
<c:choose>
    <c:when test="${not empty listClientRequests}">
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Client Name</th>
                <th>Email</th>
                <th>Request Details</th>
                <th>Status</th>
                <th>Response from David</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="request" items="${listClientRequests}">
                <tr>
                    <td>${request.id}</td>
                    <td>${request.clientName}</td>
                    <td>${request.clientEmail}</td>
                    <td>${request.requestDetails}</td>
                    <td>
                        <c:choose>
                            <c:when test="${request.accepted != null}">
                                <c:out value="${request.accepted ? 'Accepted' : 'Denied'}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${request.ready ? 'Response Ready' : 'Pending'}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${request.ready}">
                            ${request.response}
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${request.ready && request.accepted == null}">
                            <!-- Respond to Initial Quote -->
                            <form action="ControlServlet" method="post">
                                <input type="hidden" name="requestId" value="${request.id}" />
                                <input type="hidden" name="action" value="respond" />
                                <textarea name="responseText"></textarea>
                                <input type="submit" value="Respond" />
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>You have no outgoing requests.</p>
    </c:otherwise>
</c:choose>

</body>
</html>
