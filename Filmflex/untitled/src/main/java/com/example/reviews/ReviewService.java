package com.example.reviews;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Business logic for reviews.
 * Plain Java – no Spring @Service / @Autowired.
 */
public class ReviewService {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ReviewRepository repo = new ReviewRepository();

    public Review saveReview(Review review) throws IOException {
        if (review.getId() == null || review.getId().trim().isEmpty()) {
            review.setId(UUID.randomUUID().toString());
        }
        // Changed to Approved so it saves instantly to reviews.txt and shows on user page
        review.setStatus("Approved");
        review.setCreatedAt(LocalDateTime.now().format(FMT));
        repo.save(review);
        return review;
    }

    public boolean updateReview(String id, Review updated) throws IOException {
        Review existing = repo.findById(id);
        if (existing == null) return false;
        existing.setReviewText(updated.getReviewText());
        existing.setRating(updated.getRating());
        repo.save(existing);
        return true;
    }

    public boolean changeReviewStatus(String id, String status) throws IOException {
        Review review = repo.findById(id);
        if (review == null) return false;
        review.setStatus(status);
        repo.save(review);
        return true;
    }

    public boolean deleteReview(String id) throws IOException {
        return repo.delete(id);
    }

    public List<Review> getAllReviews() throws IOException {
        return repo.findAll();
    }

    public List<Review> getMovieReviews(String movieId) throws IOException {
        return repo.findByMovieId(movieId);
    }

    public List<Review> getUserReviews(String userId) throws IOException {
        return repo.findByUserId(userId);
    }
}