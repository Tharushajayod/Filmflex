import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void saveReview(Review review) {
        review.setStatus("Pending");
        review.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reviewRepository.save(review);
    }

    public void updateReview(Long reviewId, Review updatedReview) {
        Review existingReview = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
        existingReview.setReviewText(updatedReview.getReviewText());
        existingReview.setRating(updatedReview.getRating());
        reviewRepository.save(existingReview);
    }

    public void changeReviewStatus(Long reviewId, String status) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
        review.setStatus(status);
        reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getMovieReviews(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }
}
