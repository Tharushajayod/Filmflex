<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader, java.io.FileReader" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.URLEncoder" %> <!-- Import URLEncoder -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Details</title>

    <!-- Font Awesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

    <!-- Custom CSS File Link -->
    <link rel="stylesheet" href="../style.css">
    <style>
        .booked-sessions {
            padding: 30px 0;
        }

        .booked-sessions .session {
            font-size: 18px;
            background-color: #f9f9ff;
            padding: 25px;
            margin-bottom: 25px;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
            transition: transform 0.2s ease-in-out;
        }



        .booked-sessions .session p {
            margin: 12px 0;
            font-weight: 500;
            color: #333;
        }

        .booked-sessions .btn {
            font-size: 16px;
            padding: 10px 20px;
            margin-top: 12px;
            margin-right: 10px;
            border-radius: 5px;
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            display: inline-block;
        }

        .booked-sessions .btn.cancel-btn {
            background-color: #dc3545;
        }

        .booked-sessions .btn:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>

<div class="container">

    <header>
        <a href="#" class="logo">Smart<span>Tutor</span></a>
        <div id="menu" class="fas fa-bars"></div>
        <nav class="navbar">
            <a href="../home.jsp">home</a>
            <a href="../course.jsp">course</a>
            <a href="../viewTutors.jsp">tutors</a>
            <a href="../contact.jsp">contact</a>
            <a href="student-details.jsp">Dashboard</a>
        </nav>
        <a href="${pageContext.request.contextPath}/logout" class="btn">
            <i class="fas fa-sign-out-alt mr-1"></i> Logout
        </a>
    </header>

    <h1 class="heading">Student Details</h1>

    <!-- Student Details Section -->
    <section class="student-details">
        <div class="box">
            <h2>Student Information</h2>
            <p><strong>Name:</strong> <span id="studentName"><%= session.getAttribute("userName") %></span></p>
            <p><strong>Email:</strong> <span id="studentEmail"><%= session.getAttribute("userEmail") %></span></p>

            <button onclick="location.href='editinfo.jsp'" class="btn">
                <i class="fas fa-edit mr-1"></i> Update Information
            </button>
            <br>
            <a href="../payments.jsp" class="btn">View Payment History</a>
        </div>
    </section>

    <h1 class="heading">Booked Sessions</h1>

    <!-- Booked Sessions Section -->
    <section class="booked-sessions" id="bookedSessions">
        <%
            // Get student name (logged-in user)
            String studentName = (String) session.getAttribute("userName");

            // Read the bookings from bookings.txt and display them
            try (BufferedReader br = new BufferedReader(new FileReader("D:\\Oop\\Oop\\src\\main\\webapp\\WEB-INF\\bookings.txt"))) {
                String line;
                boolean hasBookings = false;
                while ((line = br.readLine()) != null) {
                    String[] bookingData = line.split(",");
                    if (bookingData.length == 6 && bookingData[0].equals(studentName)) {
                        // If the booking belongs to the logged-in student, display the session
                        out.println("<div class='session'>");
                        out.println("<p><strong>Subject:</strong> " + bookingData[1] + "</p>");
                        out.println("<p><strong>Lesson:</strong> " + bookingData[2] + "</p>");
                        out.println("<p><strong>Date:</strong> " + bookingData[3] + "</p>");
                        out.println("<p><strong>Time:</strong> " + bookingData[4] + "</p>");
                        out.println("<p><strong>Payment Method:</strong> " + bookingData[5] + "</p>");

                        // Encode the booking data for the URL
                        String bookingDataEncoded = URLEncoder.encode(line, "UTF-8");
                        out.println("<a class='btn edit-btn' href='editBooking.jsp?bookingData=" + bookingDataEncoded + "'>Edit</a>");
                        out.println("<a class='btn cancel-btn' href='cancelBooking.jsp?bookingData=" + bookingDataEncoded + "'>Cancel</a>");
                        out.println("</div>");
                        hasBookings = true;
                    }
                }
                if (!hasBookings) {
                    out.println("<p>No bookings found.</p>");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        %>
    </section>




    <!-- Footer Section -->
    <section class="footer">
        <div class="box-container">
            <div class="box">
                <h3>about us</h3>
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Illo, maxime!</p>
            </div>
            <div class="box">
                <h3>quick links</h3>
                <a href="../home.jsp">home</a>
                <a href="../course.jsp">course</a>
                <a href="../viewTutors.jsp">tutors</a>
                <a href="../contact.jsp">contact</a>
                <a href="student-details.jsp">Dashboard</a>
            </div>
            <div class="box">
                <h3>follow us</h3>
                <a href="#">facebook</a>
                <a href="#">twitter</a>
                <a href="#">instagram</a>
                <a href="#">linkedin</a>
            </div>
            <div class="box">
                <h3>contact us</h3>
                <p> <i class="fas fa-phone"></i> +123-456-7890 </p>
                <p> <i class="fas fa-envelope"></i> smarttutor@gmail.com </p>
                <p> <i class="fas fa-map-marker-alt"></i> matara, sri lanka - 400104 </p>
            </div>
        </div>
        <div class="credit"> Copyright © 2025 <span> smartTutor </span> | all rights reserved </div>
    </section>
</div>

<!-- Custom JS File Link -->
<script src="../scripts.js"></script>



</body>
</html>
