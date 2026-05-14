
document.addEventListener("DOMContentLoaded", function() {
    // Fetch Watched History and User Reviews for the logged-in user
    loadWatchedHistory();
    loadUserReviews();
});

// Function to load the watched history
function loadWatchedHistory() {
    // Simulating an API request to get the watched history
    const watchedMovies = [
        { title: "Movie 1", date: "2026-05-10" },
        { title: "Movie 2", date: "2026-05-09" }
    ];

    let watchedMoviesHtml = '';
    watchedMovies.forEach(movie => {
        watchedMoviesHtml += `<div><h3>${movie.title}</h3><p>Watched on: ${movie.date}</p></div>`;
    });

    document.getElementById('watchedMovies').innerHTML = watchedMoviesHtml;
}

// Function to load the user reviews
function loadUserReviews() {
    // Simulating an API request to get user reviews
    const reviews = [
        { movie: "Movie 1", rating: 4, reviewText: "Great movie!" },
        { movie: "Movie 2", rating: 3, reviewText: "It was okay." }
    ];

    let reviewsHtml = '';
    reviews.forEach(review => {
        reviewsHtml += `<div><h3>${review.movie}</h3><p>Rating: ${review.rating} stars</p><p>${review.reviewText}</p></div>`;
    });

    document.getElementById('userReviews').innerHTML = reviewsHtml;
}
