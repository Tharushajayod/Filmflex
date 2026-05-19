<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.Smarttutor.model.Subject, com.Smarttutor.model.Lesson, com.Smarttutor.service.SubjectService" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Course Details</title>
  <link rel="stylesheet" href="style.css">
  <style>
    body {
      margin: 0;
      padding: 0;
      background: linear-gradient(90deg, #8e2de2, #ff6a88);
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .container {
      max-width: 1100px;
      margin: 40px auto;
      padding: 30px;
      background: #fff;
      border-radius: 12px;
      box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
      display: flex;
      flex-wrap: wrap;
      gap: 40px;
    }

    .logo {
      font-size: 32px;
      font-weight: bold;
      color: #8e2de2;
      margin-bottom: 10px;
      display: inline-block;
    }

    .logo span {
      color: #ff6a88;
    }

    h1.heading {
      text-align: center;
      color: #c94bd6;
      font-size: 36px;
      width: 100%;
      margin-top: 40px;
    }

    .subject-details {
      flex: 1 1 100%;
    }

    .subject-details img {
      max-width: 100%;
      border-radius: 10px;
      margin-bottom: 20px;
    }

    .subject-details h2 {
      font-size: 28px;
      color: #444;
    }

    .subject-details h3 {
      margin: 20px 0 10px;
      font-size: 20px;
      color: #555;
    }

    .subject-details ul {
      padding-left: 20px;
      margin-bottom: 25px;
    }

    .subject-details li {
      font-size: 16px;
      margin-bottom: 6px;
      color: #333;
    }

    form {
      display: flex;
      flex-direction: column;
    }

    form label {
      font-weight: 600;
      font-size: 15px;
      margin: 12px 0 5px;
    }

    form input[type="date"],
    form input[type="time"],
    form select {
      padding: 10px;
      font-size: 15px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }

    .btn {
      margin-top: 20px;
      background-color: #343a40;
      color: #fff;
      font-size: 16px;
      padding: 12px 20px;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    .btn:hover {
      background-color: #212529;
    }
  </style>

</head>
<body>

<div class="container">
  <header>
    <a href="#" class="logo">Smart<span>Tutor</span></a>
  </header>

  <h1 class="heading">Course Details</h1>

  <%
    // Get parameters from URL (subjectName, grade, stream)
    String subjectName = request.getParameter("subjectName");
    String grade = request.getParameter("grade");
    String stream = request.getParameter("stream");

    // Get the subject from the SubjectService based on the name
    SubjectService subjectService = new SubjectService();
    List<Subject> subjects = subjectService.getAllSubjects();
    Subject selectedSubject = null;

    for (Subject subject : subjects) {
      if (subject.getName().equals(subjectName) && subject.getGrade().equals(grade) && subject.getStream().equals(stream)) {
        selectedSubject = subject;
        break;
      }
    }

    if (selectedSubject != null) {
  %>

  <div class="subject-details">
    <h2><%= selectedSubject.getName() %></h2>
    <img src="images/<%= selectedSubject.getImage() %>" alt="<%= selectedSubject.getName() %>">

    <h3>Grade: <%= selectedSubject.getGrade() %> | Stream: <%= selectedSubject.getStream() %></h3>

    <h3>Lessons:</h3>
    <ul>
      <%
        // Display the lessons and their prices
        for (Lesson lesson : selectedSubject.getLessons()) {
      %>
      <li><%= lesson.getLessonName() %> - $<%= lesson.getPrice() %></li>
      <%
        }
      %>
    </ul>

    <!-- Booking Form -->
    <h3>Book a Lesson</h3>
    <form action="bookLesson.jsp" method="post">
      <input type="hidden" name="subjectName" value="<%= selectedSubject.getName() %>">
      <input type="hidden" name="grade" value="<%= selectedSubject.getGrade() %>">
      <input type="hidden" name="stream" value="<%= selectedSubject.getStream() %>">

      <!-- Hidden inputs for lesson details -->
      <input type="hidden" name="lessonName" value="<%= selectedSubject.getLessons().get(0).getLessonName() %>">
      <input type="hidden" name="lessonPrice" value="<%= selectedSubject.getLessons().get(0).getPrice() %>">

      <label for="lesson">Select a Lesson:</label>
      <select name="lesson" id="lesson" required>
        <%
          // Populate the lesson options for booking
          for (Lesson lesson : selectedSubject.getLessons()) {
        %>
        <option value="<%= lesson.getLessonName() %>"><%= lesson.getLessonName() %> - $<%= lesson.getPrice() %></option>
        <%
          }
        %>
      </select>
      <label for="bookingDate">Select Date:</label>
      <input type="date" name="bookingDate" id="bookingDate" required>

      <label for="bookingTime">Select Time:</label>
      <input type="time" name="bookingTime" id="bookingTime" required>

      <label for="paymentMethod">Payment Method:</label>
      <select name="paymentMethod" id="paymentMethod" required>
        <option value="creditCard">Credit Card</option>
        <option value="paypal">PayPal</option>
      </select>

      <button type="submit" class="btn">Book Lesson</button>
    </form>
  </div>

  <% } else { %>
  <p>Sorry, this course is not available.</p>
  <% } %>

</div>

</body>
</html>
