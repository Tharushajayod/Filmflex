<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Details</title>

    <!-- Font Awesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

    <!-- Custom CSS File Link -->
    <link rel="stylesheet" href="../style.css">

    <style>
        /* Custom styles for the update form */
        .update-form {
            margin-top: 50px;
        }

        .update-form label {
            display: block;
            margin-bottom: 5px;
            font-size: 18px;
        }

        .update-form input[type="text"],
        .update-form input[type="password"],
        .update-form input[type="email"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0 20px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }

        .btn {
            padding: 10px 20px;
            background-color: var(--pink);
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn:hover {
            background-color: #cc5200;
        }
    </style>
</head>
<body>

<div class="container">
    <header>
        <a href="#" class="logo">Smart<span>Tutor</span></a>
    </header>

    <h1 class="heading">Update Your Details</h1>

    <!-- Update Details Form -->
    <section class="update-form">
        <form action="${pageContext.request.contextPath}/student/updateStudentDetails" method="post">
            <!-- Pre-populate the fields with the student's current details -->
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" value="${sessionScope.userName}" required readonly>

            <!-- Optional password field (student can update password) -->
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Enter new password (optional)" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${sessionScope.userEmail}" placeholder="Enter new email" required>

            <!-- Hidden role field to ensure that the correct role is passed to the servlet -->
            <input type="hidden" name="role" value="student" />

            <button onclick="location.href='${pageContext.request.contextPath}/student-details.jsp'" class="btn">
                <i class="fas fa-edit mr-1"></i> Update Information
            </button>

        </form>
    </section>
</div>

</body>
</html>
