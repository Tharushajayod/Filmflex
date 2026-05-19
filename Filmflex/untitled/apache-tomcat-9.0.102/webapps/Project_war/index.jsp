<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.0.0/fonts/remixicon.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
    <link rel="stylesheet" href="main.css" />
    <title>Online tutor Landing page</title>
</head>
<body>
<header class="header" id="home">
    <div class="section__container header__container">
        <nav>
            <div class="nav__menu__btn" id="menu-btn">
                <span><i class="ri-menu-2-line"></i></span>
                <h4>Menu</h4>
            </div>
            <ul class="nav__links" id="nav-links">
                <li><a href="#home">Home</a></li>
                <li><a href="#benefits">Benefits</a></li>
                <li><a href="#tutors">Tutors</a></li>
                <li><a href="#client">Client</a></li>
                <li><a href="#faq">FAQ</a></li>
            </ul>
        </nav>
        <div class="header__image">
            <img src="assets/header.png" alt="header" />
        </div>
        <div class="header__content">
            <h1>Find the Perfect Tutor for You<br />Anytime, Anywhere!</h1>
            <p class="section__description">
                Search, compare, and book the best tutors in your area or online in just a few clicks
            </p>
            <div class="header__btns">
                <a href="login.jsp">
                    <button class="btn">Get Started</button>
                </a>
            </div>
        </div>
    </div>
</header>

<section class="section__container benefits__container" id="benefits">
    <div class="benefits__image">
        <img src="assets/benefits.jpg" alt="benefits" />
    </div>
    <div class="benefits__content">
        <h2 class="section__header">Benefits of the<br />Platform</h2>
        <p class="section__description">
            <b>• Personalized Learning</b> <br /> Find tutors that match your needs.<br />
            <b>• Flexible Scheduling</b> <br /> Book at your convenience.<br />
            <b>• Verified Tutors</b> <br /> Rated and reviewed by students.<br />
            <b>• Affordable Prices</b> <br /> Choose a tutor within your budget.<br />
            <b>• Secure Payments</b> <br /> Hassle-free and safe transactions.<br />
            <b>• Visuals</b> <br /> A comparison table or a dynamic list.
        </p>
        <img src="assets/map.jpg" alt="map" />
    </div>
</section>

<section class="section__container subjects__container" id="subjects">
    <div class="subjects__content">
        <h2 class="section__header">
            Discover <br/> fascinating <br />subjects
        </h2>
        <a href="register.jsp">
            <button class="btn">Subjects</button>
        </a>
    </div>
    <div class="subjects__wrapper">
        <div class="subjects__wrapper-inner">
            <img src="assets/explore-1.jpg" alt="explore" />
            <img src="assets/explore-2.jpg" alt="explore" />
            <img src="assets/explore-3.jpg" alt="explore" />
            <img src="assets/explore-4.jpg" alt="explore" />
        </div>
    </div>
</section>

<section class="section__container tutors__container" id="tutors">
    <h2 class="section__header">
        Find and book the best tutors <br />in just a few steps!
    </h2>
    <div class="tutors__grid">
        <div class="tutors__destination">
            <div class="tutors__destination__image">
                <img src="assets/destination-1.jpg" alt="destination" />
                <img src="assets/destination-2.jpg" alt="destination" />
            </div>
            <h4>Search for a Tutor</h4>
            <p class="section__description">
                Browse tutors based on subject, experience, and ratings.
                Check detailed tutor profiles, including qualifications and reviews.Compare availability and pricing.
            </p>
            <a href="login.jsp">
                <button class="btn">Find a Tutor</button>
            </a>
        </div>
        <div class="tutors__plan">
            <div class="tutors__plan__content">
                <h4>Book a Session<br /> with just few taps</h4>
                <p class="section__description">
                    Select your preferred time slot and confirm the booking.
                </p>
                <a href="register.jsp">
                    <button class="btn">Start Learning</button>
                </a>
            </div>
            <img src="assets/plan.png" alt="plan" />
        </div>
    </div>
</section>

<section class="section__container customer__container" id="client">
    <h2 class="section__header">
        What Our Students & Parents Say
    </h2>
    <!-- Slider main container -->
    <div class="swiper">
        <!-- Additional required wrapper -->
        <div class="swiper-wrapper">
            <!-- Slides -->
            <div class="swiper-slide">
                <div class="customer__card">
                    <img src="assets/customer-1.jpg" alt="customer" class="customer__image" />
                    <div class="customer__card__content">
                        I found the perfect math tutor through this platform! My grades have improved, and I feel more confident in my studies
                    </div>
                </div>
            </div>
            <div class="swiper-slide">
                <div class="customer__card">
                    <img src="assets/customer-2.jpg" alt="customer" class="customer__image" />
                    <div class="customer__card__content">
                        Booking a tutor for my child has never been easier. The process is smooth, and I love that I can see tutor ratings before booking
                    </div>
                </div>
            </div>
            <div class="swiper-slide">
                <div class="customer__card">
                    <img src="assets/customer-3.jpg" alt="customer" class="customer__image" />
                    <div class="customer__card__content">
                        The online learning experience is fantastic! The tutor was engaging and helped me understand difficult concepts easily
                    </div>
                </div>
            </div>
            <div class="swiper-slide">
                <div class="customer__card">
                    <img src="assets/customer-4.jpg" alt="customer" class="customer__image" />
                    <div class="customer__card__content">
                        I appreciate the flexibility of this platform. My daughter can schedule sessions at convenient times, and the tutors are top-notch!
                    </div>
                </div>
            </div>
            <div class="swiper-slide">
                <div class="customer__card">
                    <img src="assets/customer-5.jpg" alt="customer" class="customer__image" />
                    <div class="customer__card__content">
                        This platform made it so easy to find a tutor for my physics exam. The tutor explained everything clearly, and I passed with flying colors!
                    </div>
                </div>
            </div>
            <div class="swiper-slide">
                <div class="customer__card">
                    <img src="assets/customer-6.jpg" alt="customer" class="customer__image" />
                    <div class="customer__card__content">
                        User-friendly interface and seamless navigation
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="section__container faq__container" id="faq">
    <h2 class="section__header">FAQ</h2>
    <div class="faq__grid">
        <div class="faq__card">
            <div class="faq__header">
                <h4>How do I find the right tutor?</h4>
                <span><i class="ri-arrow-down-s-line"></i></span>
            </div>
            <div class="faq__content">
                You can search for tutors based on subject, grade level, experience, and student reviews. Use filters to narrow down your options and choose the best tutor for your needs.
            </div>
        </div>
        <div class="faq__card">
            <div class="faq__header">
                <h4>How do I book a tutoring session?</h4>
                <span><i class="ri-arrow-down-s-line"></i></span>
            </div>
            <div class="faq__content">
                Once you find a tutor, select your preferred date and time from their availability calendar, then confirm the booking by making a secure payment.
            </div>
        </div>
        <div class="faq__card">
            <div class="faq__header">
                <h4>Can I have a trial session before booking?</h4>
                <span><i class="ri-arrow-down-s-line"></i></span>
            </div>
            <div class="faq__content">
                Some tutors offer free or discounted trial sessions. You can check their profile or contact them to inquire about trial lessons before making a full booking.
            </div>
        </div>
        <div class="faq__card">
            <div class="faq__header">
                <h4>What happens if I need to reschedule or cancel a session?</h4>
                <span><i class="ri-arrow-down-s-line"></i></span>
            </div>
            <div class="faq__content">
                You can reschedule or cancel a session through your account. Cancellation policies vary by tutor, so be sure to check their terms before booking.
            </div>
        </div>
        <div class="faq__card">
            <div class="faq__header">
                <h4>How do I pay for my tutoring sessions?</h4>
                <span><i class="ri-arrow-down-s-line"></i></span>
            </div>
            <div class="faq__content">
                We offer multiple payment options, including credit/debit cards, digital wallets, and other secure payment methods. Payments are processed securely to ensure a safe transaction.
            </div>
        </div>
    </div>
</section>

<footer class="footer">
    <div class="section__container footer__container">
        <h2 class="section__header">Smart Tutor</h2>
        <div class="footer__socials">
            <a href="#"><i class="ri-facebook-fill"></i></a>
            <a href="#"><i class="ri-twitter-fill"></i></a>
            <a href="#"><i class="ri-instagram-fill"></i></a>
        </div>
        <ul class="footer__links">
            <li><a href="#home">Home</a></li>
            <li><a href="#benefits">Benefits</a></li>
            <li><a href="#tutors">Tutors</a></li>
            <li><a href="#client">Client</a></li>
            <li><a href="#faq">FAQ</a></li>
        </ul>
    </div>
    <div class="footer__bar">
        Copyright © 2025 Smart Tutor. All rights reserved.
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<script src="main.js"></script>
</body>
</html>