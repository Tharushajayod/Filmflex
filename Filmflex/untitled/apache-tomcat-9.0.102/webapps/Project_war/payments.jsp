<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader, java.io.FileReader, java.io.IOException" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment History</title>
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

        table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid #ccc;
            text-align: center;
        }

        th, td {
            padding: 12px;
            font-size: 16px;
        }

        th {
            background-color: #f1f1f1;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
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
    <!-- Header Section -->
    <header>
        <a href="#" class="logo">Smart<span>Tutor</span></a>
    </header>

    <h1 class="heading">Payment History</h1>

    <table>
        <thead>
        <tr>
            <th>Payment ID</th>
            <th>Lesson Name</th>
            <th>Amount</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <%
            String studentName = (String) session.getAttribute("userName");
            String line;

            try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Oop\\Oop\\src\\main\\webapp\\WEB-INF\\payments.txt"))) {
                while ((line = reader.readLine()) != null) {
                    String[] paymentData = line.split(", "); // Split the line into payment details

                    // Ensure we have 6 elements: paymentId, lessonName, amount, paymentMethod, status, studentName
                    if (paymentData.length == 6) {
                        // Parse the amount
                        String amountStr = paymentData[2];  // This is where the amount should be
                        double amount = 0.0;

                        try {
                            // Only parse if the value is a valid number
                            if (amountStr != null && !amountStr.isEmpty()) {
                                amount = Double.parseDouble(amountStr);
                            } else {
                                out.println("<p>Error: Invalid amount format for payment ID: " + paymentData[0] + "</p>");
                                continue;  // Skip this entry if the amount is invalid
                            }
                        } catch (NumberFormatException e) {
                            out.println("<p>Error: Invalid amount format for payment ID: " + paymentData[0] + "</p>");
                            continue;  // Skip this entry if the amount is invalid
                        }

                        String paymentId = paymentData[0];
                        String lessonName = paymentData[1];
                        String status = paymentData[4];

                        // Check if the payment is for the current student
                        if (paymentData[5].equals(studentName)) {
                            out.println("<tr>");
                            out.println("<td>" + paymentId + "</td>");
                            out.println("<td>" + lessonName + "</td>");
                            out.println("<td>" + amount + "</td>");
                            out.println("<td>" + status + "</td>");
                            out.println("</tr>");
                        }
                    } else {
                        // If the data does not match the expected format, log it or skip it
                        out.println("<p>Invalid payment data format: " + line + "</p>");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        %>
        </tbody>
    </table>

    <a href="course.jsp" class="btn">Back to Courses</a>
</div>

</body>
</html>
