<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Subject</title>
    <link rel="stylesheet" href="style.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
    <style>
        body {
            margin: 0;
            padding: 0;
            background: linear-gradient(90deg, #7b61ff, #ff6ba9);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .container {
            max-width: 820px;
            background-color: #fff;
            margin: 60px auto;
            padding: 50px 40px;
            border-radius: 14px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }

        .heading {
            text-align: center;
            color: #cc4cd4;
            font-size: 34px;
            margin-bottom: 40px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        label {
            font-weight: bold;
            font-size: 17px;
            display: block;
            margin-bottom: 6px;
        }

        input[type="text"],
        input[type="number"],
        select {
            padding: 12px;
            font-size: 16px;
            width: 100%;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        .grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 24px;
        }

        .grid-1 {
            display: grid;
            grid-template-columns: 1fr;
            gap: 20px;
        }

        .lesson-group {
            border-top: 1px solid #eee;
            padding-top: 25px;
        }

        button[type="submit"] {
            margin-top: 25px;
            padding: 14px;
            background-color: #343a40;
            color: white;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: 0.3s ease;
            width: 220px;
            align-self: center;
        }

        button[type="submit"]:hover {
            background-color: #212529;
        }

        @media (max-width: 640px) {
            .grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="heading">Edit Subject</h1>

    <form action="updateSubject" method="post">
        <input type="hidden" name="originalName" value="${subject.name}">

        <div class="grid">
            <div>
                <label>Subject Name:</label>
                <input type="text" name="name" value="${subject.name}" required>
            </div>

            <div>
                <label for="grade">Grade:</label>
                <select name="grade" id="grade" required>
                    <option value="Grade9" ${subject.grade == 'Grade9' ? 'selected' : ''}>Grade 9</option>
                    <option value="Grade10" ${subject.grade == 'Grade10' ? 'selected' : ''}>Grade 10</option>
                    <option value="Grade11" ${subject.grade == 'Grade11' ? 'selected' : ''}>Grade 11</option>
                    <option value="Grade12" ${subject.grade == 'Grade12' ? 'selected' : ''}>Grade 12</option>
                </select>
            </div>

            <div>
                <label for="stream">Stream:</label>
                <select name="stream" id="stream" required>
                    <option value="Science" ${subject.stream == 'Science' ? 'selected' : ''}>Science</option>
                    <option value="Commerce" ${subject.stream == 'Commerce' ? 'selected' : ''}>Commerce</option>
                    <option value="Arts" ${subject.stream == 'Arts' ? 'selected' : ''}>Arts</option>
                </select>
            </div>
        </div>

        <div class="lesson-group">
            <h3 style="margin: 20px 0 10px;">Lessons</h3>
            <c:forEach var="lesson" items="${subject.lessons}" varStatus="loop">
                <div class="grid">
                    <div>
                        <label>Lesson Name:</label>
                        <input type="text" name="lessonName${loop.index}" value="${lesson.lessonName}" required>
                    </div>
                    <div>
                        <label>Price:</label>
                        <input type="number" name="price${loop.index}" value="${lesson.price}" step="0.01" required>
                    </div>
                </div>
            </c:forEach>
        </div>

        <input type="hidden" name="lessonCount" value="${subject.lessons.size()}">

        <button type="submit"><i class="fas fa-save"></i> Update Subject</button>
    </form>
</div>

</body>
</html>
