<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.Smarttutor.service.SubjectService, com.Smarttutor.model.Subject, com.Smarttutor.model.Lesson" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Smarttutor.model.Subject" %>
<%@ page import="com.Smarttutor.model.Lesson" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Courses</title>
    <!-- Font Awesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <!-- Custom CSS File Link -->
    <link rel="stylesheet" href="style.css">
    <style>
        .heading {
            text-align: center;
            font-size: 32px;
            color: #c94bd6;
            margin: 40px 0 20px;
        }

        .course {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 30px;
            padding: 20px 0;
        }

        .course .box {
            background: #f9f9f9;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
            width: 280px;
            text-align: center;
            padding: 20px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .course .box:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
        }

        .course .box img {
            width: 100%;
            border-radius: 10px;
            margin-bottom: 15px;
        }

        .course .box h3 {
            font-size: 20px;
            color: #f15bb5;
            margin-bottom: 10px;
        }

        .course .btn {
            background-color: #343a40;
            color: #fff;
            padding: 10px 20px;
            font-size: 15px;
            text-decoration: none;
            display: inline-block;
            margin-top: 10px;
            border-radius: 6px;
            transition: background-color 0.3s ease;
        }

        .course .btn:hover {
            background-color: #212529;
        }

        .course .icons {
            margin-top: 12px;
            font-size: 14px;
            color: #555;
        }

        .course .icons i {
            color: #f15bb5;
            margin-right: 6px;
        }

        @media (max-width: 768px) {
            .course {
                flex-direction: column;
                align-items: center;
            }
        }
    </style>

</head>
<body>

<div class="container">
    <header>
        <a href="#" class="logo">Smart<span>Tutor</span></a>
        <div id="menu" class="fas fa-bars"></div>
        <nav class="navbar">
            <a href="home.jsp">home</a>
            <a href="course.jsp">course</a>
            <a href="viewTutors.jsp">tutors</a>
            <a href="contact.jsp">contact</a>
            <a href="student/student-details.jsp">Dashboard</a>
        </nav>
        <a href="${pageContext.request.contextPath}/logout" class="btn">
            <i class="fas fa-sign-out-alt mr-1"></i> Logout
        </a>
    </header>

    <h1 class="heading">Popular Courses</h1>

    <div style="text-align:center; margin-bottom: 30px;">
        <input type="text" id="searchInput" placeholder="Search by Subject Name" style="padding: 10px; width: 250px; border-radius: 6px; border: 1px solid #ccc; font-size: 15px;" />

        <select id="gradeFilter" style="padding: 10px; border-radius: 6px; margin-left: 10px; font-size: 15px;">
            <option value="">All Grades</option>
            <option value="Grade9">Grade 9</option>
            <option value="Grade10">Grade 10</option>
            <option value="Grade11">Grade 11</option>
            <option value="Grade12">Grade 12</option>
        </select>

        <select id="streamFilter" style="padding: 10px; border-radius: 6px; margin-left: 10px; font-size: 15px;">
            <option value="">All Streams</option>
            <option value="Science">Science</option>
            <option value="Commerce">Commerce</option>
            <option value="Arts">Arts</option>
        </select>
    </div>

    <section class="course" id="courseSection">

        <%
            // Get all subjects from the SubjectService
            SubjectService subjectService = new SubjectService();
            List<Subject> subjects = subjectService.getAllSubjects();

            // Loop through the subjects and display them
            for (Subject subject : subjects) {
        %>
        <div class="box" data-grade="<%= subject.getGrade() %>" data-stream="<%= subject.getStream() %>">
            <img src="images/<%= subject.getImage() %>" alt="<%= subject.getName() %>">
            <h3><%= subject.getName() %></h3>

            <!-- Link to the details page with subject details passed as parameters -->
            <a href="course-details.jsp?subjectName=<%= subject.getName() %>&grade=<%= subject.getGrade() %>&stream=<%= subject.getStream() %>" class="btn">Learn More</a>

            <div class="icons">
                <p><i class="fas fa-book"></i> <%= subject.getLessons().size() %> Lessons</p>
            </div>
        </div>
        <% } %>



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
                <a href="home.jsp">home</a>
                <a href="course.jsp">course</a>
                <a href="viewTutors.jsp">tutors</a>
                <a href="contact.jsp">contact</a>
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


<script>
    const searchInput = document.getElementById("searchInput");
    const gradeFilter = document.getElementById("gradeFilter");
    const streamFilter = document.getElementById("streamFilter");
    const boxes = document.querySelectorAll(".course .box");

    function filterCourses() {
        const searchText = searchInput.value.toLowerCase();
        const selectedGrade = gradeFilter.value;
        const selectedStream = streamFilter.value;

        boxes.forEach(box => {
            const name = box.querySelector("h3").innerText.toLowerCase();
            const grade = box.getAttribute("data-grade");
            const stream = box.getAttribute("data-stream");

            const matchesSearch = name.includes(searchText);
            const matchesGrade = selectedGrade === "" || grade === selectedGrade;
            const matchesStream = selectedStream === "" || stream === selectedStream;

            if (matchesSearch && matchesGrade && matchesStream) {
                box.style.display = "block";
            } else {
                box.style.display = "none";
            }
        });
    }

    searchInput.addEventListener("input", filterCourses);
    gradeFilter.addEventListener("change", filterCourses);
    streamFilter.addEventListener("change", filterCourses);
</script>

</body>
</html>
