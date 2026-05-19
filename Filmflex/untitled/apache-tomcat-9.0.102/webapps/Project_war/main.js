// Toggle between Sign Up and Sign In forms
const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');

if (registerBtn && loginBtn) {
  registerBtn.addEventListener('click', () => {
    container.classList.add("active");
  });

  loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
  });
}

// Handle Sign Up Form Submission
if (document.getElementById('signupForm')) {
  document.getElementById('signupForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const name = document.getElementById('signupName').value;
    const email = document.getElementById('signupEmail').value;
    const password = document.getElementById('signupPassword').value;

    try {
      const response = await fetch('http://localhost:5000/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, password }),
      });

      const data = await response.json();
      if (response.ok) {
        alert('Registration successful! Please login.');
        container.classList.remove("active"); // Switch to Sign In form
      } else {
        alert(data.message || 'Error registering. Please try again.');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('An error occurred. Please try again.');
    }
  });
}

// Handle Sign In Form Submission
if (document.getElementById('signinForm')) {
  document.getElementById('signinForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('signinEmail').value;
    const password = document.getElementById('signinPassword').value;

    try {
      const response = await fetch('http://localhost:5000/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.json();
      if (response.ok) {
        localStorage.setItem('token', data.token); // Save token to localStorage
        alert('Login successful!');
        window.location.href = 'home.html'; // Redirect to home page
      } else {
        alert(data.message || 'Invalid email or password.');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('An error occurred. Please try again.');
    }
  });
}

// DOMContentLoaded - General site functionality
document.addEventListener("DOMContentLoaded", function() {
  // Menu functionality
  const navBtn = document.getElementById("menu-btn");
  const navLinks = document.getElementById("nav-links");

  if (navBtn && navLinks) {
    navBtn.addEventListener("click", (e) => {
      if (navLinks.classList.contains("open")) {
        navLinks.classList.add("close");
        navLinks.addEventListener(
          "animationend",
          (e) => {
            navLinks.classList.remove("close");
            navLinks.classList.remove("open");
          },
          { once: true }
        );
      } else {
        navLinks.classList.add("open");
      }
    });

    navLinks.addEventListener("click", (e) => {
      navLinks.classList.add("close");
      navLinks.addEventListener(
        "animationend",
        (e) => {
          navLinks.classList.remove("close");
          navLinks.classList.remove("open");
        },
        { once: true }
      );
    });
  }

  // Explore section duplication
  const explore = document.querySelector(".subjects__wrapper-inner");
  if (explore) {
    const exploreContent = Array.from(explore.children);
    exploreContent.forEach((item) => {
      const duplicateNode = item.cloneNode(true);
      duplicateNode.setAttribute("aria-hidden", true);
      explore.appendChild(duplicateNode);
    });
  }

  // Swiper initialization
  if (document.querySelector(".swiper")) {
    const swiper = new Swiper(".swiper", {
      slidesPerView: "auto",
      spaceBetween: 20,
    });
  }

  // FAQ functionality
  const faqGrid = document.querySelector(".faq__grid");
  if (faqGrid) {
    faqGrid.addEventListener("click", (e) => {
      const faqHeader = e.target.closest(".faq__header");
      if (!faqHeader) return;

      const faqCard = faqHeader.parentElement;
      const isActive = faqCard.classList.contains("active");

      // Close all other FAQ cards
      document.querySelectorAll(".faq__card.active").forEach(card => {
        if (card !== faqCard) {
          card.classList.remove("active");
        }
      });

      // Toggle current card
      faqCard.classList.toggle("active", !isActive);
    });
  }
});