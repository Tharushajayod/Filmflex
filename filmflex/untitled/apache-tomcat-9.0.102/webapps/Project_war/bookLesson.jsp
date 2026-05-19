<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedWriter, java.io.FileWriter" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Booking Confirmation</title>
  <link rel="stylesheet" href="style.css"> <!-- Link to the global CSS file -->
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: linear-gradient(90deg, #8e2de2, #ff6a88);
    }

    .container {
      max-width: 1100px;
      margin: 40px auto;
      padding: 30px;
      background: #fff;
      border-radius: 12px;
      box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    h1.heading {
      text-align: center;
      color: #c94bd6;
      font-size: 36px;
      width: 100%;
      margin-top: 40px;
    }

    .booking-details {
      width: 100%;
      margin-top: 20px;
    }

    .booking-details p {
      font-size: 18px;
      margin-bottom: 15px;
    }

    .booking-details .btn {
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

    .booking-details .btn:hover {
      background-color: #212529;
    }

    .booking-details h3 {
      font-size: 24px;
      color: #444;
      margin-bottom: 20px;
    }
  </style>
</head>
<body>

<div class="container">
  <!-- Header Section -->
  <header>
    <a href="#" class="logo">Smart<span>Tutor</span></a>
  </header>

  <h1 class="heading">Booking Confirmation</h1>

  <div class="booking-details">
    <%
      // Retrieve form data from the request
      String studentName = (String) session.getAttribute("userName"); // Get logged-in student name
      String lessonName = request.getParameter("lessonName");  // Correctly retrieve lesson name
      String subjectName = request.getParameter("subjectName");  // Correctly retrieve subject name
      String lessonPriceStr = request.getParameter("lessonPrice");
      String paymentMethod = request.getParameter("paymentMethod");
      String bookingDate = request.getParameter("bookingDate");  // Get date
      String bookingTime = request.getParameter("bookingTime");  // Get time

      double lessonPrice = 0.0;  // Default value in case of missing price

      // Validate lesson price
      if (lessonPriceStr != null && !lessonPriceStr.isEmpty()) {
        try {
          lessonPrice = Double.parseDouble(lessonPriceStr);
        } catch (NumberFormatException e) {
          response.getWriter().println("<p>Error: Invalid lesson price format!</p>");
          return;
        }
      }

      // Display the entered details as confirmation
      out.println("<h3>Booking Details:</h3>");
      out.println("<p><strong>Student Name:</strong> " + studentName + "</p>");
      out.println("<p><strong>Course Name:</strong> " + subjectName + "</p>");
      out.println("<p><strong>Lesson Name:</strong> " + lessonName + "</p>");
      out.println("<p><strong>Lesson Price:</strong> " + lessonPrice + "</p>");
      out.println("<p><strong>Payment Method:</strong> " + paymentMethod + "</p>");
      out.println("<p><strong>Booking Date:</strong> " + bookingDate + "</p>");
      out.println("<p><strong>Booking Time:</strong> " + bookingTime + "</p>");

      // Debugging: Log the data to the console
      System.out.println("Lesson Name: " + lessonName);
      System.out.println("Lesson Price: " + lessonPrice);
      System.out.println("Payment Method: " + paymentMethod);

      // Save to bookings.txt
      try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Oop\\Oop\\src\\main\\webapp\\WEB-INF\\bookings.txt", true))) {
        String bookingData = studentName + ", " + subjectName + ", " + lessonName + ", " + bookingDate + ", " + bookingTime + ", " + paymentMethod;
        writer.write(bookingData);
        writer.newLine();
      } catch (IOException e) {
        e.printStackTrace();
      }

      // Save to payments.txt
      String paymentId = "PAY" + System.currentTimeMillis();
      String paymentStatus = "Pending";
      try (BufferedWriter paymentWriter = new BufferedWriter(new FileWriter("D:\\Oop\\Oop\\src\\main\\webapp\\WEB-INF\\payments.txt", true))) {
        String paymentData = paymentId + ", " + lessonName + ", " + lessonPrice + ", " + paymentMethod + ", " + paymentStatus + ", " + studentName;
        paymentWriter.write(paymentData);
        paymentWriter.newLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
    %>

    <h3>Thank you for your booking!</h3>
    <p>Your lesson has been successfully booked. You will receive a confirmation email shortly.</p>

    <!-- Redirect to courses page -->
    <a href="course.jsp" class="btn">Back to Courses</a>
  </div>
</div>

</body>
</html>
