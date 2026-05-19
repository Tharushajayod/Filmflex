<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.FileWriter" %>
<%@ page import="java.io.BufferedWriter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Delete Payment</title>
</head>
<body>

<%
  String paymentDataEncoded = request.getParameter("paymentData");
  String paymentDataDecoded = java.net.URLDecoder.decode(paymentDataEncoded, "UTF-8");
  String[] paymentDetails = paymentDataDecoded.split(", ");

  // Get the payment ID and delete it from the file
  String paymentId = paymentDetails[0];

  // Read all payments, and delete the one with the matching paymentId
  File file = new File("D:\\Oop\\Oop\\src\\main\\webapp\\WEB-INF\\payments.txt");
  List<String> payments = new ArrayList<>();

  try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
    String line;
    while ((line = reader.readLine()) != null) {
      if (!line.startsWith(paymentId)) { // Skip the payment record to delete
        payments.add(line);
      }
    }
  }

  // Write the remaining payments back to the file
  try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
    for (String payment : payments) {
      writer.write(payment);
      writer.newLine();
    }
  }

  response.sendRedirect("viewPayments.jsp"); // Redirect back to the payments list page
%>

</body>
</html>
