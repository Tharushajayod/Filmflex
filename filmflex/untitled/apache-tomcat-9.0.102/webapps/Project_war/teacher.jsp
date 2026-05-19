<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Available Tutors</title>
</head>
<body>
<h2>Available Tutors</h2>

<!-- Display the size of the tutors list for debugging -->
<p>Number of tutors: ${tutors.size()}</p>

<c:if test="${not empty tutors}">
    <ul>
        <c:forEach var="tutor" items="${tutors}">
            <li>
                <strong>Name:</strong> ${tutor.name} <br>
                <strong>Email:</strong> ${tutor.email} <br>
                <button>Contact Tutor</button>
            </li>
        </c:forEach>
    </ul>
</c:if>

<c:if test="${empty tutors}">
    <p>No tutors available at the moment.</p>
</c:if>
</body>
</html>
