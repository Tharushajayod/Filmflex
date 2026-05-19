<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>contact</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

    <!-- custom css file link  -->
    <link rel="stylesheet" href="style.css">

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

<h1 class="heading">contact us</h1>

<!-- contact section  -->

<section class="contact">

<div class="image">
    <img src="images/contact.png" alt="">
</div>

<form action="">

    <div class="inputBox">
        <input type="text" placeholder="name">
        <input type="email" placeholder="email">
    </div>

    <input type="text" placeholder="subject" class="box">

    <textarea placeholder="message" name="" id="" cols="30" rows="10"></textarea>

    <input type="submit" class="btn" value="send">

</form>

</section>


<!-- footer section  -->

<section class="footer">
    <div class="box-container">
        <div class="box">
            <h3>about us</h3>
            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Illo, maxime!</p>
        </div>
        <div class="box">
            <h3>quick links</h3>
            <a href="home.html">home</a>
        <a href="course.html">course</a>
        <a href="viewTutors.jsp">tutors</a>
        <a href="contact.html">contact</a>
        <a href="student-details.html">Dashboard</a>
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















<!-- custom js file link -->
<script src="script.js"></script>

</body>
</html>