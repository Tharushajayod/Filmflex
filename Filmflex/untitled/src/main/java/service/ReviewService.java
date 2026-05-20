package service;

import model.Review;
import repository.ReviewRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class ReviewService {

    // Thread-Safe Configuration: Enforcing uniform corporate timestamp patterns utilizing immutable DateTimeFormatter
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Dependency Injection: Tight coupling of the repository layer resource to handle low-level CRUD tasks
    private final ReviewRepository repo = new ReviewRepository();


    public Review saveReview(Review review) throws IOException {
        // Defensive Identity Allocation: If the incoming object completely lacks an ID parameter field,
        // dynamically manufacture a globally unique 128-bit alphanumeric token signature utilizing UUID
        if (review.getId() == null || review.getId().trim().isEmpty()) {
            review.setId(UUID.randomUUID().toString());
        }

        // Business Rule Hardening Strategy: Automatically override the initial state to "Approved"
        // to bypass multi-stage queues constraints, forcing the dataset row to reflect instantly on storefront layouts
        review.setStatus("Approved");

        // Chronological Serialization: Capturing precise runtime execution clock ticks matching required formats
        review.setCreatedAt(LocalDateTime.now().format(FMT));

        // Delegating physical data write transactions down onto the persistence repository nodes framework (reviews.txt)
        repo.save(review);
        return review;
    }

    public boolean updateReview(String id, Review updated) throws IOException {
        // Identity Verification: Locating the historical review record matrix tracking segment row via index lookup
        Review existing = repo.findById(id);
        if (existing == null) return false; // Guard clause exit tracking if targeted index pointer is vacant

        // Selective Mutation Allocation: Modifying purely non-systemic attributes, preserving static creation stamps
        existing.setReviewText(updated.getReviewText());
        existing.setRating(updated.getRating());

        // Committing updated memory object block states directly back into storage files boundaries
        repo.save(existing);
        return true;
    }

    public boolean changeReviewStatus(String id, String status) throws IOException {
        Review review = repo.findById(id);
        if (review == null) return false;

        review.setStatus(status); // Applying new administrative processing status attributes map values
        repo.save(review);
        return true;
    }

    public boolean deleteReview(String id) throws IOException {
        // Abstraction forwarding execution to prune the matching text database entry row securely
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