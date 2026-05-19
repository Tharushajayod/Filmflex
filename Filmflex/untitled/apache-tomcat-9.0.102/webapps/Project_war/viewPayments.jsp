<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader, java.io.FileReader, java.io.IOException" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Payments</title>
    <link rel="stylesheet" href="../style.css"> <!-- Link to global CSS file -->
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

        .btn.edit-btn {
            background-color: #ffc107; /* Yellow for Edit */
        }

        .btn.delete-btn {
            background-color: #dc3545; /* Red for Delete */
        }

        .btn:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>

<div class="container">


    <h1 class="heading">Manage Payments</h1>

    <table>
        <thead>
        <tr>
            <th>Payment ID</th>
            <th>Lesson Name</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Student Name</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            // Reading payments data from payments.txt file
            String line;
            try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Oop\\Oop\\src\\main\\webapp\\WEB-INF\\payments.txt"))) {
                while ((line = reader.readLine()) != null) {
                    String[] paymentData = line.split(", ");

                    if (paymentData.length == 6) {
                        String paymentId = paymentData[0];
                        String lessonName = paymentData[1];
                        double amount = Double.parseDouble(paymentData[2]);
                        String status = paymentData[4];  // Status field
                        String studentName = paymentData[5];

                        // URL-encode the entire line to pass the payment data securely
                        String paymentDataEncoded = URLEncoder.encode(line, "UTF-8");

                        // Output the data in table rows
                        out.println("<tr>");
                        out.println("<td>" + paymentId + "</td>");
                        out.println("<td>" + lessonName + "</td>");
                        out.println("<td>" + amount + "</td>");
                        out.println("<td>" + status + "</td>");  // Status column
                        out.println("<td>" + studentName + "</td>");
                        out.println("<td>");
                        out.println("<a href='editPayment?paymentData=" + paymentDataEncoded + "' class='btn edit-btn'>Edit</a>");
                        out.println("<a href='deletePayment?paymentData=" + paymentDataEncoded + "' class='btn delete-btn'>Delete</a>");
                        out.println("</td>");
                        out.println("</tr>");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        %>
        </tbody>
    </table>
    <br>
    <a href="${pageContext.request.contextPath}/admin/userlist"
       class="btn">
         Back to Admin Dashboard
    </a>
</div>

</body>
</html>
