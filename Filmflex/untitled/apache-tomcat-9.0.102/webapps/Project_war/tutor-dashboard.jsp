<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tutor Dashboard</title>
    <link rel="stylesheet" href="../style.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
    <style>
        body {
            margin: 0;
            padding: 0;
            background: linear-gradient(90deg, #8e2de2, #ff6a88);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .container {
            max-width: 1000px;
            margin: 60px auto;
            background: #fff;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
        }

        .heading {
            text-align: center;
            color: #a646dd;
            margin-bottom: 30px;
            font-size: 32px;
        }

        .add-btn {
            float: right;
            background: linear-gradient(90deg, #7867ff, #ff6493);
            color: white;
            padding: 10px 20px;
            border-radius: 6px;
            font-weight: bold;
            text-decoration: none;
            transition: 0.3s;
        }

        .add-btn:hover {
            opacity: 0.9;
        }

        .box {
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
        }

        .box h3 {
            margin-top: 0;
            color: #333;
            font-size: 22px;
        }

        .box p {
            margin: 5px 0;
            font-size: 15px;
        }

        .box ul {
            padding-left: 18px;
        }

        .box li {
            margin-bottom: 5px;
        }

        .btn {
            display: inline-block;
            padding: 8px 15px;
            margin: 8px 5px 0 0;
            font-size: 14px;
            color: white;
            background-color: #6c63ff;
            text-decoration: none;
            border-radius: 5px;
        }

        .btn:hover {
            background-color: #574b90;
        }

        .logout-btn {
            display: inline-block;
            padding: 8px 15px;
            margin: 8px 5px 0 0;
            font-size: 14px;
            color: white;
            background-color: #333;
            text-decoration: none;
            border-radius: 5px;
        }

        .logout-btn:hover {
            background-color: hotpink;
        }

        .cancel-btn {
            background-color: #dc3545;
        }

        .cancel-btn:hover {
            background-color: #b02a37;
        }


        .empty-state {
            text-align: center;
            padding: 30px;
            font-size: 18px;
            color: #666;
        }
    </style>
</head>
<body>

<div class="container">

    <div style="display: flex; justify-content: space-between; margin-bottom: 20px;">
        <a href="addSubject.jsp" class="add-btn">
            <i class="fas fa-plus-circle"></i> Add New Subject
        </a>

        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
            <i class="fas fa-sign-out-alt mr-1"></i> Logout
        </a>

    </div>




    <h1 class="heading">My Subjects</h1>

    <c:choose>
        <c:when test="${empty mySubjects}">
            <div class="empty-state">
                <p><i class="fas fa-folder-open fa-2x" style="color: #ccc;"></i></p>
                <p>No subjects found. Click "Add New Subject" to create one.</p>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="subject" items="${mySubjects}">
                <div class="box">
                    <h3>${subject.name}</h3>
                    <p><strong>Grade:</strong> ${subject.grade}</p>
                    <p><strong>Stream:</strong> ${subject.stream}</p>
                    <h4>Lessons:</h4>
                    <ul>
                        <c:forEach var="lesson" items="${subject.lessons}">
                            <li>${lesson.lessonName} - $${lesson.price}</li>
                        </c:forEach>
                    </ul>
                    <a href="editSubject?name=${subject.name}" class="btn">Edit</a>

                    <a href="deleteSubject?name=${subject.name}" class="btn cancel-btn"
                       onclick="return confirm('Are you sure you want to delete this subject?');">
                        <i class="fas fa-trash"></i> Delete
                    </a>

                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
