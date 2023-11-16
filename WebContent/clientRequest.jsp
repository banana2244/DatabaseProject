<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Client Request Submission</title>
</head>
<body>

<h2>Submit Your Request</h2>

<form action="ControlServlet" method="post">
    <label for="clientName">Name:</label><br>
    <input type="text" id="clientName" name="clientName" required><br>

    <label for="clientEmail">Email:</label><br>
    <input type="email" id="clientEmail" name="clientEmail" required><br>

    <label for="requestDetails">Request Details:</label><br>
    <textarea id="requestDetails" name="requestDetails" rows="4" cols="50" required></textarea><br>

    <input type="hidden" name="action" value="submitClientRequest">
    <input type="submit" value="Submit Request">
</form>

</body>
</html>
