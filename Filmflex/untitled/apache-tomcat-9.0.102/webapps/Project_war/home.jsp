<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>

    <!-- Font Awesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

    <!-- Custom CSS File Link -->
    <link rel="stylesheet" href="home.css">


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




        <div class="section__container header__container">
            <div class="header__image">
                <img src="assets/header-1.jpg" alt="header" />
                <img src="assets/header-2.jpg" alt="header" />
            </div>
            <div class="header__content">
                <div>
                    <p class="sub__header">Start Learning</p>
                    <h1>Connect with Expert<br />Home Tutors Today</h1>
                    <p class="section__subtitle">
                        Discover qualified tutors across various subjects and levels. Personalized learning experiences tailored to your needs.
                    </p>
                    <div class="action__btns">
                        <a href="course.jsp">
                            <button class="btn">Get Started</button>
                        </a>
                    </div>
                </div>
            </div>
        </div>


    <section class="section__container destination__container">
        <div class="section__header">
            <div>
                <h2 class="section__title">Explore Top Subjects</h2>
                <p class="section__subtitle">
                    Browse through a variety of subjects and find the right tutor to help you excel.
                </p>
            </div>
            <div class="destination__nav">
                <span><i class="ri-arrow-left-s-line"></i></span>
                <span><i class="ri-arrow-right-s-line"></i></span>
            </div>
        </div>
        <div class="destination__grid">
            <div class="destination__card">
                <img src="assets/subject-math.jpg" alt="Mathematics" />
                <div class="destination__details">
                    <p class="destination__title">Mathematics</p>
                    <p class="destination__subtitle">All Levels</p>
                </div>
            </div>
            <div class="destination__card">
                <img src="assets/subject-science.jpg" alt="Science" />
                <div class="destination__details">
                    <p class="destination__title">Science</p>
                    <p class="destination__subtitle">Grades 6-12</p>
                </div>
            </div>
            <div class="destination__card">
                <img src="assets/subject-english.jpg" alt="English" />
                <div class="destination__details">
                    <p class="destination__title">English</p>
                    <p class="destination__subtitle">Grammar & Literature</p>
                </div>
            </div>
            <div class="destination__card">
                <img src="assets/subject-coding.jpg" alt="Coding" />
                <div class="destination__details">
                    <p class="destination__title">Coding</p>
                    <p class="destination__subtitle">Beginner to Advanced</p>
                </div>
            </div>
        </div>
    </section>

    <section class="trip">
        <div class="section__container trip__container">
            <h2 class="section__title">Featured Tutors</h2>
            <p class="section__subtitle">
                Meet some of our top-rated tutors ready to help you achieve your academic goals.
            </p>
            <div class="trip__grid">
                <div class="trip__card">
                    <img src="assets/tutor-1.jpg" alt="Tutor" />
                    <div class="trip__details">
                        <p>Alex Johnson - Mathematics</p>
                        <div class="rating"><i class="ri-star-fill"></i> 4.9</div>
                    </div>
                </div>
                <div class="trip__card">
                    <img src="assets/tutor-2.jpg" alt="Tutor" />
                    <div class="trip__details">
                        <p>Maria Gonzalez - Science</p>
                        <div class="rating"><i class="ri-star-fill"></i> 4.8</div>
                    </div>
                </div>
                <div class="trip__card">
                    <img src="assets/tutor-3.jpg" alt="Tutor" />
                    <div class="trip__details">
                        <p>David Lee - English</p>
                        <div class="rating"><i class="ri-star-fill"></i> 4.7</div>
                    </div>
                </div>
            </div>
            <div class="view__all">
                <button class="btn">View All Tutors</button>
            </div>
        </div>
    </section>

    <section class="gallary">
        <div class="section__container gallary__container">
            <div class="image__gallary">
                <div class="gallary__col">
                    <img src="assets/gallery-1.jpg" alt="gallery" />
                </div>
                <div class="gallary__col">
                    <img src="assets/gallery-2.jpg" alt="gallery" />
                    <img src="assets/gallery-3.jpg" alt="gallery" />
                </div>
            </div>
            <div class="gallary__content">
                <div>
                    <h2 class="section__title">
                        Success Stories from Our Students
                    </h2>
                    <p class="section__subtitle">
                        Hear from students who have improved their grades and confidence with our expert tutors.
                    </p>
                    <button class="btn">Read Testimonials</button>
                </div>
            </div>
        </div>
    </section>

    <section class="subscribe">
        <div class="section__container subscribe__container">
            <div class="subscribe__content">
                <h2 class="section__title">Subscribe for Learning Tips</h2>
                <p class="section__subtitle">
                    Get the latest study tips, tutor recommendations, and exclusive offers delivered to your inbox.
                </p>
            </div>
            <div class="subscribe__form">
                <form>
                    <input type="email" placeholder="Your email here" />
                    <button class="subscribe-btn" type="submit">Subscribe</button>
                </form>
            </div>
        </div>
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

<!-- Custom JS File Link -->
<script src="script.js"></script>

</body>
</html>