<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader, java.io.FileReader" %>
<%@ page import="java.io.BufferedWriter, java.io.FileWriter" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.URLDecoder" %> <!-- Import URLDecoder for decoding bookingData -->
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Booking</title>
  <link rel="stylesheet" href="../style.css">
  <style>
    body {
      margin: 0;
      padding: 0;
      background: linear-gradient(90deg, #8e2de2, #ff6a88);
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .container {
      max-width: 700px;
      margin: 50px auto;
      padding: 40px;
      background: #fff;
      border-radius: 10px;
      box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
    }

    .logo {
      font-size: 32px;
      font-weight: bold;
      color: #8e2de2;
      text-align: center;
      display: block;
      margin-bottom: 10px;
    }

    .logo span {
      color: #ff6a88;
    }

    .heading {
      text-align: center;
      font-size: 32px;
      color: #c94bd6;
      margin-bottom: 30px;
    }

    form {
      display: flex;
      flex-direction: column;
      gap: 20px;
    }

    label {
      font-weight: 600;
      color: #333;
    }

    .form-label {
      font-size: 18px;
      font-weight: 600;
      color: #333;
      margin-bottom: 6px;
      display: inline-block;
    }

    input[type="text"],
    input[type="date"],
    input[type="time"],
    select {
      padding: 10px 15px;
      font-size: 16px;
      border: 1px solid #ccc;
      border-radius: 6px;
      width: 100%;
    }

    .btn {
      background-color: #343a40;
      color: white;
      font-size: 18px;
      padding: 12px 25px;
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

  <h1 class="heading">Edit Booking</h1>

  <%
    // Get the URL-encoded booking data from the request
    String bookingDataEncoded = request.getParameter("bookingData");

    // Check if the bookingData parameter exists (not null)
    if (bookingDataEncoded == null || bookingDataEncoded.isEmpty()) {
      // Redirect to the student details page or show an error message if bookingData is missing
      response.sendRedirect("student-details.jsp");
      return;  // Prevent further processing
    }

    // Decode the booking data
    String bookingData = URLDecoder.decode(bookingDataEncoded, "UTF-8");
    String[] bookingDetails = bookingData.split(", "); // Split the booking data by ", "

    // Extract the individual parts of the booking
    String studentName = bookingDetails[0];
    String subjectName = bookingDetails[1];
    String lessonName = bookingDetails[2];
    String bookingDate = bookingDetails[3];
    String bookingTime = bookingDetails[4];
    String paymentMethod = bookingDetails[5];
  %>

  <!-- Form to Edit the Booking -->
  <form action="<%= request.getContextPath() %>/editBooking" method="post">

  <input type="hidden" name="originalBookingData" value="<%= bookingData %>">

    <label for="lessonName" class="form-label">Lesson Name:</label>
    <input type="text" id="lessonName" name="lessonName" value="<%= lessonName %>" readonly style="background-color: #e9ecef; cursor: not-allowed;">

    <label for="bookingDate" class="form-label">Date:</label>
    <input type="date" id="bookingDate" name="bookingDate" value="<%= bookingDate %>" required>

    <label for="bookingTime" class="form-label">Time:</label>
    <input type="time" id="bookingTime" name="bookingTime" value="<%= bookingTime %>" required>

    <label class="form-label">Payment Method:</label>
    <select id="paymentMethodDisplay" disabled style="font-size: 16px; background-color: #e9ecef; cursor: not-allowed;">
      <option value="creditCard" <%= paymentMethod.equals("creditCard") ? "selected" : "" %>>Credit Card</option>
      <option value="paypal" <%= paymentMethod.equals("paypal") ? "selected" : "" %>>PayPal</option>
    </select>

    <input type="hidden" name="paymentMethod" value="<%= paymentMethod %>">


    <button type="submit" class="btn">Update Booking</button>
  </form>

</div>

</body>
</html>
