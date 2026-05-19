<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>View Tutors</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <link rel="stylesheet" href="style.css">
  <style>
    /* Main container styles */
    body {
      font-family: 'Arial', sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f8f9fa;
    }

    .container {
      max-width: 1400px;
      margin: 0 auto;
      padding: 60px 20px 20px;
    }

    h2 {
      color: #333;
      text-align: center;
      margin: 40px 0;
      font-size: 2.5rem;
    }

    /* Tutor grid layout */
    .tutors-grid {
      display: grid;
      grid-template-columns: repeat(5, 1fr);
      column-gap: 50px; /* Space between columns */
      row-gap: 40px;    /* Space between rows */
      padding: 20px;
    }

    /* Tutor card styles */
    .tutor-card {
      background: white;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      padding: 10px;
      transition: all 0.3s ease;
      text-align: center;
      display: flex;
      flex-direction: column;
      align-items: center;
      height: 100%;
    }

    .tutor-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 20px rgba(0,0,0,0.15);
    }

    .tutor-avatar {
      width: 100px;
      height: 100px;
      border-radius: 50%;
      object-fit: cover;
      margin-bottom: 15px;
      border: 3px solid #e0e0e0;
    }

    .tutor-info {
      width: 100%;
    }

    .tutor-name {
      font-size: 2rem;
      color: #2c3e50;
      margin-bottom: 8px;
      font-weight: 600;
    }

    .tutor-email {
      color: #555;
      margin-bottom: 12px;
      font-size: 1.4rem;
      word-break: break-word;
    }

    .tutor-role {
      display: inline-block;
      padding: 4px 12px;
      border-radius: 20px;
      font-size: 1.2rem;
      font-weight: bold;
    }

    .role-tutor {
      background-color: #e3f2fd;
      color: #0d47a1;
    }

    .role-admin {
      background-color: #e8f5e9;
      color: #2e7d32;
    }

    .no-tutors {
      text-align: center;
      color: #666;
      font-style: italic;
      grid-column: 1 / -1;
      padding: 40px 0;
      font-size: 1.2rem;
    }

    /* Responsive adjustments */
    @media (max-width: 1200px) {
      .tutors-grid {
        gap: 30px;
        grid-template-columns: repeat(4, 1fr);
      }
    }

    @media (max-width: 992px) {
      .tutors-grid {

        grid-template-columns: repeat(3, 1fr);
      }
    }

    @media (max-width: 768px) {
      .tutors-grid {
        gap: 25px;
        grid-template-columns: repeat(2, 1fr);
      }
    }

    @media (max-width: 576px) {
      .tutors-grid {
        gap: 20px;
        grid-template-columns: 1fr;
      }

      .tutor-card {
        max-width: 300px;
        margin: 0 auto;
      }
    }




  </style>
  <script>
    // Auto-refresh every second
    function refreshTutors() {
      fetch('viewTutors')
              .then(response => response.text())
              .then(html => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, 'text/html');
                const newContent = doc.querySelector('#tutors-container');

                if (newContent) {
                  document.querySelector('#tutors-container').innerHTML = newContent.innerHTML;
                }
              })
              .catch(error => console.error('Error refreshing tutors:', error));
    }

    document.addEventListener('DOMContentLoaded', function() {
      refreshTutors();
      setInterval(refreshTutors, 1000);
    });
  </script>
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

  <h2>Our Expert Tutors</h2>

  <div class="tutors-grid" id="tutors-container">
    <c:if test="${empty tutorsList}">
      <div class="no-tutors">No tutors found. Please check back later.</div>
    </c:if>

    <c:if test="${not empty tutorsList}">
      <c:forEach var="tutor" items="${tutorsList}">
        <div class="tutor-card">
          <img src="${pageContext.request.contextPath}/images/default-avatar.png"
               alt="${tutor.name}" class="tutor-avatar">

          <div class="tutor-info">
            <div class="tutor-name">${tutor.name}</div>
            <div class="tutor-email">${tutor.email}</div>
            <c:if test="${not empty tutor.role}">
              <div class="tutor-role role-${tutor.role.toLowerCase()}">
                  ${tutor.role}
              </div>
            </c:if>
          </div>
        </div>
      </c:forEach>
    </c:if>
  </div>

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


</body>
</html>