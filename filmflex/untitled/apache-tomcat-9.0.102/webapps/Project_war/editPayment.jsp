<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Payment</title>
  <!-- Add CSS for styling -->
  <link rel="stylesheet" href="style.css"> <!-- Assuming you have a CSS file for general styles -->
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 20px;
      background-color: #f4f4f4;
    }
    .form-container {
      background-color: #ffffff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      width: 50%;
      margin: auto;
    }
    h2 {
      text-align: center;
      font-size: 25px;
    }
    label {
      font-weight: bold;
      margin-top: 10px;
      display: block;
      font-size: 16px;
    }
    input[type="text"], input[type="number"], select {
      width: 100%;
      padding: 8px;
      margin: 8px 0;
      border-radius: 4px;
      border: 1px solid #ccc;
      font-size: 16px;
    }
    input[readonly] {
      background-color: #f0f0f0;
      cursor: not-allowed;
    }
    button {
      background-color: #343a40;
      color: white;
      padding: 10px 18px;
      border-radius: 6px;
      text-decoration: none;
      font-size: 16px;
      font-weight: 500;
      display: inline-block;
      transition: background 0.3s;
      transition: background 0.3s;
      width: 100%;
      margin-top: 20px;
    }

    button:hover {
      background-color: #23272b;
    }
  </style>
</head>
<body>

<div class="form-container">
  <h2>Edit Payment</h2>

  <form action="editPayment" method="POST">
    <!-- Hidden field to pass payment ID -->
    <input type="hidden" name="paymentId" value="${paymentId}" />

    <!-- Lesson Name Field (Read-Only) -->
    <label for="lessonName">Lesson Name:</label>
    <input type="text" name="lessonName" value="${lessonName}" readonly /><br>

    <!-- Amount Field (Read-Only) -->
    <label for="amount">Amount:</label>
    <input type="number" step="0.01" name="amount" value="${amount}" readonly /><br>

    <!-- Payment Method Field (Read-Only) -->
    <label for="paymentMethod">Payment Method:</label>
    <input type="text" name="paymentMethod" value="${paymentMethod}" readonly /><br>

    <!-- Status Field (Editable) -->
    <label for="status">Status:</label>
    <select name="status" required>
      <option value="Pending" ${status == 'Pending' ? 'selected' : ''}>Pending</option>
      <option value="Confirmed" ${status == 'Confirmed' ? 'selected' : ''}>Confirmed</option>
      <option value="Completed" ${status == 'Completed' ? 'selected' : ''}>Completed</option>
      <option value="Cancelled" ${status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
    </select><br>

    <!-- Student Name Field (Read-Only) -->
    <label for="studentName">Student Name:</label>
    <input type="text" name="studentName" value="${studentName}" readonly /><br>

    <!-- Submit Button -->
    <button type="submit">Update Payment</button>
  </form>
</div>

</body>
</html>
