<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Tutor</title>
    <!-- Custom CSS Link -->
    <link rel="stylesheet" href="style.css">

    <style>
        /* Success and Error message styles */
        .success { color: green; font-size: 1.2rem; }
        .error { color: red; font-size: 1.2rem; }

        /* Form container */
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }

        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
            font-size: 2.5rem;
        }

        .form-container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            margin: auto;
        }

        .form-container label {
            font-size: 1.4rem;
            color: #333;
            display: block;
            margin-bottom: 10px;
        }

        .form-container input, .form-container select {
            width: 100%;
            padding: 1rem;
            font-size: 1.4rem;
            border: 1px solid #ccc;
            border-radius: 0.5rem;
            margin-bottom: 20px;
        }

        .form-container input:focus, .form-container select:focus {
            border-color: #007bff;
        }

        .form-container button {
            padding: 12px 25px;
            background-color: #28a745;
            color: white;
            font-size: 1.4rem;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .form-container button:hover {
            background-color: #218838;
        }

        .form-container a {
            text-decoration: none;
            color: #007bff;
            display: block;
            text-align: center;
            font-size: 1.4rem;
            margin-top: 20px;
        }

        .form-container a:hover {
            text-decoration: underline;
        }
    </style>

    <script>
        // Email validation pattern check
        function validateEmail() {
            const email = document.getElementById('email').value;
            const pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

            if (!pattern.test(email)) {
                alert("Please enter a valid email address.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

<h2>Add a Tutor</h2>

<c:if test="${not empty success}">
    <p class="success">${success}</p>
</c:if>

<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>

<div class="form-container">
    <form action="addTutor" method="post" onsubmit="return validateEmail()">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>

        <label for="role">Role:</label>
        <select id="role" name="role" required>
            <option value="tutor">Tutor</option>
            <option value="admin">Admin</option>
        </select><br><br>

        <button type="submit">Add Tutor</button>
    </form>
    <a href="${pageContext.request.contextPath}/admin/userlist"
       class="btn">
        Back to Admin Dashboard
    </a>
</div>

<br>


</body>
</html>
