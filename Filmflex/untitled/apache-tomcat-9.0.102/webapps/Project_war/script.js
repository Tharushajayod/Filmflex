// Filter Courses Function
function filterCourses() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const gradeFilter = document.getElementById('gradeFilter').value;
    const streamFilter = document.getElementById('streamFilter').value;
    const courseBoxes = document.querySelectorAll('.course .box');

    courseBoxes.forEach(box => {
        const courseName = box.querySelector('h3').textContent.toLowerCase();
        const courseGrade = box.getAttribute('data-grade');
        const courseStream = box.getAttribute('data-stream');

        const matchesSearch = courseName.includes(searchInput);
        const matchesGrade = gradeFilter ? courseGrade === gradeFilter : true;
        const matchesStream = streamFilter ? courseStream === streamFilter : true;

        if (matchesSearch && matchesGrade && matchesStream) {
            box.style.display = 'block'; // Show matching courses
        } else {
            box.style.display = 'none'; // Hide non-matching courses
        }
    });
}

function showSubjectDetails(subjectId) {
    const subject = getSubjectById(subjectId);  // You would fetch subject data
    const modal = document.getElementById('subjectDetailsModal');
    const subjectName = modal.querySelector('h3');
    const lessonList = modal.querySelector('ul');
    const bookButton = modal.querySelector('button');

    subjectName.textContent = subject.name;
    lessonList.innerHTML = ''; // Clear existing list

    subject.lessons.forEach(lesson => {
        const listItem = document.createElement('li');
        listItem.textContent = `${lesson.lessonName} - ${lesson.price}`;
        lessonList.appendChild(listItem);
    });

    modal.style.display = 'block'; // Show the modal
}

function bookSubject(subjectId) {
    const subject = getSubjectById(subjectId);  // Fetch subject data
    localStorage.setItem('bookedSubject', JSON.stringify(subject));
    window.location.href = "booked-sessions.html";  // Redirect to booking confirmation page
}


// Booking Modal Logic
const bookingModal = document.getElementById('bookingModal');
const closeModal = document.querySelector('.close');
const bookingForm = document.getElementById('bookingForm');
const courseNameInput = document.getElementById('courseName');

// Open Modal when "Learn More" is clicked
document.querySelectorAll('.course .btn').forEach(button => {
    button.addEventListener('click', (e) => {
        e.preventDefault();
        const courseName = button.closest('.box').querySelector('h3').textContent;
        courseNameInput.value = courseName;
        bookingModal.style.display = 'block';
    });
});

// Close Modal
closeModal.addEventListener('click', () => {
    bookingModal.style.display = 'none';
});

// Handle Booking Form Submission
bookingForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const courseName = courseNameInput.value;
    const bookingDate = document.getElementById('bookingDate').value;
    const bookingTime = document.getElementById('bookingTime').value;
    const paymentMethod = document.getElementById('paymentMethod').value;

    if (!courseName || !bookingDate || !bookingTime || !paymentMethod) {
        alert('Please fill out all fields.');
        return;
    }

    // Save Booking to Local Storage
    const booking = {
        courseName,
        bookingDate,
        bookingTime,
        paymentMethod,
        status: 'Pending Payment'
    };
    saveBooking(booking);

    // Redirect to Payment Page (or integrate payment gateway)
    alert('Redirecting to payment...');
    bookingModal.style.display = 'none';
    window.location.href = 'booked-sessions.html'; // Redirect to booked sessions page
});

// Save Booking to Local Storage
function saveBooking(booking) {
    let bookings = JSON.parse(localStorage.getItem('bookings')) || [];
    bookings.push(booking);
    localStorage.setItem('bookings', JSON.stringify(bookings));
}

// Add event listeners to filters
document.getElementById('gradeFilter').addEventListener('change', filterCourses);
document.getElementById('streamFilter').addEventListener('change', filterCourses);

// Toggle Menu for Mobile
let menu = document.querySelector('#menu');
let navbar = document.querySelector('.navbar');

menu.onclick = () => {
    menu.classList.toggle('fa-times');
    navbar.classList.toggle('active');
}

window.onscroll = () => {
    menu.classList.remove('fa-times');
    navbar.classList.remove('active');
}

// Load Student Details
function loadStudentDetails() {
    // Example Student Data (replace with data from local storage or backend)
    const student = {
        name: "John Doe",
        email: "john.doe@example.com",
        phone: "+123-456-7890"
    };

    // Display Student Details
    document.getElementById('studentName').textContent = student.name;
    document.getElementById('studentEmail').textContent = student.email;
    document.getElementById('studentPhone').textContent = student.phone;
}

// Load Booked Sessions
function loadBookedSessions() {
    const bookings = JSON.parse(localStorage.getItem('bookings')) || [];
    const bookedSessionsSection = document.getElementById('bookedSessions');

    if (bookings.length === 0) {
        bookedSessionsSection.innerHTML = '<p>No sessions booked yet.</p>';
        return;
    }

    let html = '';
    bookings.forEach((booking, index) => {
        html += `
            <div class="box">
                <h3>${booking.courseName}</h3>
                <p><strong>Date:</strong> ${booking.bookingDate}</p>
                <p><strong>Time:</strong> ${booking.bookingTime}</p>
                <p><strong>Payment Method:</strong> ${booking.paymentMethod}</p>
                <p><strong>Status:</strong> ${booking.status}</p>
            </div>
        `;
    });
    bookedSessionsSection.innerHTML = html;
}

// Initialize Page
function initializePage() {
    loadStudentDetails();
    loadBookedSessions();
}

// Run when the page loads
window.onload = initializePage;