<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(90deg, #8e2de2, #ff6a88);
        }

        .container {
            max-width: 1100px;
            margin: 40px auto;
            background-color: #fff;
            border-radius: 12px;
            padding: 40px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
        }

        .heading {
            text-align: center;
            color: #c94bd6;
            font-size: 32px;
            margin-bottom: 40px;
        }

        .section-box {
            padding: 20px;
        }

        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .section-header h2 {
            font-size: 22px;
            color: #6a63ff;
            margin: 0;
        }

        .btn {
            background-color: #343a40;
            color: white;
            padding: 10px 18px;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
            display: inline-block;
            transition: background 0.3s;
        }

        .btn:hover {
            background-color: #23272b;
        }

        .student-details h3 {
            color: #f15bb5;
            margin-top: 20px;
            font-size: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        .table-header {
            background-color: #7f6eff;
            color: white;
            font-weight: 600;
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            font-size: 14px;
            border-bottom: 1px solid #eee;
        }

        .table-row:hover {
            background-color: #f9f9f9;
        }

        .student-row {
            background-color: #fff8f0;
        }

        .tutor-row {
            background-color: #f0f9ff;
        }

        .admin-row {
            background-color: #f6f0ff;
        }

        .action-btn {
            margin-right: 10px;
            font-size: 14px;
            text-decoration: none;
            padding: 6px 12px;
            border-radius: 4px;
        }

        .text-blue-500 {
            color: #007bff;
            background-color: #e3f0ff;
        }

        .text-red-500 {
            color: #dc3545;
            background-color: #ffe3e3;
        }

        .text-blue-500:hover {
            background-color: #cce5ff;
        }

        .text-red-500:hover {
            background-color: #f8d7da;
        }

        .search-bar {
            margin: 20px 0;
            text-align: right;
        }

        .search-bar input {
            padding: 10px 14px;
            width: 250px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        @media (max-width: 768px) {
            .section-header {
                flex-direction: column;
                align-items: flex-start;
            }

            .btn {
                margin-top: 10px;
            }

            table, thead, tbody, th, td, tr {
                display: block;
            }

            th {
                background: #7f6eff;
                color: white;
            }

            td {
                border: none;
                padding: 10px 0;
            }
        }
    </style>
</head>

<body>
<header>
    <nav class="navbar"></nav>
    <div id="menu" class="fas fa-bars"></div>
</header>

<section class="container">
    <h1 class="heading">User Management</h1>

    <div class="section-box">
        <div class="section-header">
            <h2>User Records</h2>
            <a href="${pageContext.request.contextPath}/logout" class="btn">
                <i class="fas fa-sign-out-alt mr-1"></i> Logout
            </a>
        </div>

        <div class="search-bar">

            <input type="text" id="userSearch" placeholder="Search by username..." onkeyup="filterUsers()" />
            <a href="${pageContext.request.contextPath}/admin/userlist" class="btn" style="margin-top: 0;">
                <i class="fas fa-sync-alt"></i> Refresh Data
            </a>
        </div>




        <!-- Users Section -->
        <div class="student-details">
            <h3>Students</h3>
            <a href="${pageContext.request.contextPath}/viewPayments.jsp" class="btn">
                 View Payments
            </a>
            <table class="min-w-full">
                <thead>
                <tr class="table-header">
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr class="table-row student-row">
                        <td>${user.username}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/editUser?username=${user.username}&role=student" class="action-btn text-blue-500">
                                <i class="fas fa-edit"></i> Edit
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/deleteUser?username=${user.username}&role=student" class="action-btn text-red-500">
                                <i class="fas fa-trash-alt"></i> Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Tutors Section -->
        <div class="student-details" style="margin-top: 3rem;">
            <h3>Tutors</h3>
            <a href="${pageContext.request.contextPath}/addTutor.jsp" class="btn">
                <i class="fas fa-user-plus"></i> Add Tutor
            </a>
            <table class="min-w-full">
                <thead>
                <tr class="table-header">
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="tutor" items="${tutors}">
                    <tr class="table-row tutor-row">
                        <td>${tutor.username}</td>
                        <td>${tutor.username}</td>
                        <td>${tutor.email}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/editUser?username=${tutor.username}&role=tutor" class="action-btn text-blue-500">
                                <i class="fas fa-edit"></i> Edit
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/deleteUser?username=${tutor.username}&role=tutor" class="action-btn text-red-500">
                                <i class="fas fa-trash-alt"></i> Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Admin Section -->
        <div class="student-details" style="margin-top: 3rem;">
            <h3>Admins</h3>
            <table class="min-w-full">
                <thead>
                <tr class="table-header">
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="admin" items="${admins}">
                    <tr class="table-row admin-row">
                        <td>${admin.username}</td>
                        <td>${admin.username}</td>
                        <td>${admin.email}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/editUser?username=${admin.username}&role=admin" class="action-btn text-blue-500">
                                <i class="fas fa-edit"></i> Edit
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/deleteUser?username=${admin.username}&role=admin" class="action-btn text-red-500">
                                <i class="fas fa-trash-alt"></i> Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</section>

<script>
    document.getElementById('menu').addEventListener('click', function() {
        document.querySelector('.navbar').classList.toggle('active');
    });

    function filterUsers() {
        let input = document.getElementById("userSearch").value.toLowerCase();
        const rows = document.querySelectorAll(".table-row");

        rows.forEach(row => {
            const username = row.querySelector("td")?.textContent.toLowerCase();
            if (username && username.includes(input)) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    }
</script>
</body>
</html>
