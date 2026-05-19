<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.io.BufferedReader, java.io.FileReader" %>
<%@ page import="java.io.BufferedWriter, java.io.FileWriter" %>
<%@ page import="java.io.IOException" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cancel Booking</title>

  <!-- Inline CSS for styling -->
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: linear-gradient(90deg, #8e2de2, #ff6a88);
      margin: 0;
      padding: 0;
    }

    .container {
      max-width: 600px;
      margin: 80px auto;
      padding: 30px 40px;
      background-color: #fff;
      border-radius: 12px;
      box-shadow: 0 6px 18px rgba(0, 0, 0, 0.1);
    }

    h1 {
      text-align: center;
      color: #c94bd6;
      font-size: 28px;
      margin-bottom: 25px;
    }

    .confirm-message {
      font-size: 17px;
      line-height: 1.7;
      color: #333;
      margin-bottom: 30px;
    }

    .confirm-message p {
      margin: 10px 0;
    }

    .btn {
      display: block;
      width: 100%;
      padding: 12px;
      margin-top: 15px;
      font-size: 16px;
      font-weight: 600;
      text-align: center;
      color: #fff;
      background-color: #28a745;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    .btn:hover {
      background-color: #218838;
    }

    .back-btn {
      width: auto;
      display: inline-block;
      padding: 10px 25px;
      margin: 20px auto 0;
      text-align: center;
    }


    .cancel-btn {
      background-color: #dc3545;
    }

    .cancel-btn:hover {
      background-color: #c82333;
    }

    .footer {
      text-align: center;
      margin-top: 60px;
      font-size: 14px;
      color: #eee;
    }
  </style>

</head>
<body>

<%
  // Retrieve the booking data passed from the URL (query parameter)
  String bookingDataEncoded = request.getParameter("bookingData");
  String bookingData = URLDecoder.decode(bookingDataEncoded, "UTF-8"); // Decode the booking data
  String[] bookingDetails = bookingData.split(","); // Split the data into individual details
  String subject = bookingDetails[1];
  String lesson = bookingDetails[2];
  String date = bookingDetails[3];
  String time = bookingDetails[4];
  String paymentMethod = bookingDetails[5];
%>

<div class="container">
  <h1>Cancel Booking</h1>

  <div class="confirm-message">
    <p>Are you sure you want to cancel the booking for the following session?</p>
    <p><strong>Subject:</strong> <%= subject %></p>
    <p><strong>Lesson:</strong> <%= lesson %></p>
    <p><strong>Date:</strong> <%= date %></p>
    <p><strong>Time:</strong> <%= time %></p>
    <p><strong>Payment Method:</strong> <%= paymentMethod %></p>
  </div>

  <form action="<%= request.getContextPath() %>/cancelBookingAction" method="post">

  <!-- Hidden field to pass the original booking data to the backend -->
    <input type="hidden" name="bookingData" value="<%= bookingData %>">

    <button type="submit" class="btn">Confirm Cancellation</button>
  </form>

  <a href="student-details.jsp" class="btn cancel-btn back-btn">Go Back</a>

</div>

<div class="footer">
  <p>&copy; 2025 SmartTutor. All rights reserved.</p>
</div>

</body>
</html>

