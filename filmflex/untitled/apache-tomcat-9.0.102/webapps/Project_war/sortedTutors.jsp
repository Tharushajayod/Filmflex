<%@ page import="com.Smarttutor.service.TutorService" %>
<%@ page import="com.Smarttutor.model.User" %>
<%@ page import="java.util.List" %> <!-- Import List to resolve the issue -->

<%!
    TutorService tutorService = new TutorService();
    List<User> sortedTutorsBySubject = tutorService.getSortedTutorsBySubject(); // Get the sorted list of tutors
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sorted Tutors by Subject</title>
    <link rel="stylesheet" href="style.css">  <!-- Link your CSS file for styling -->
</head>
<body>

<header>
    <h1>Tutors Sorted by Subject Expertise</h1>
</header>

<section>
    <div class="container">
        <h2>List of Tutors Sorted by Subject</h2>
        <ul>
            <%
                if (sortedTutorsBySubject != null && !sortedTutorsBySubject.isEmpty()) {
                    for (User user : sortedTutorsBySubject) {
            %>
            <li>
                <strong>Name:</strong> <%= user.getUsername() %>
                - <strong>Subject:</strong> <%= user.getRole() %> <!-- Assuming 'role' stores the subject expertise -->
            </li>
            <%
                }
            } else {
            %>
            <li>No tutors available.</li>
            <%
                }
            %>
        </ul>
    </div>
</section>

<footer>
    <p>&copy; 2025 Smart Tutor Platform. All rights reserved.</p>
</footer>

</body>
</html>
