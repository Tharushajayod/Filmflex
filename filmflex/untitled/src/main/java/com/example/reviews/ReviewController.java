import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody Review review) {
        reviewService.saveReview(review);
        return new ResponseEntity<>("Review added successfully!", HttpStatus.OK);
    }

    @PostMapping("/update/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
        reviewService.updateReview(reviewId, review);
        return new ResponseEntity<>("Review updated successfully!", HttpStatus.OK);
    }

    @PostMapping("/approve/{reviewId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> approveReview(@PathVariable Long reviewId) {
        reviewService.changeReviewStatus(reviewId, "Approved");
        return new ResponseEntity<>("Review approved!", HttpStatus.OK);
    }

    @PostMapping("/reject/{reviewId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> rejectReview(@PathVariable Long reviewId) {
        reviewService.changeReviewStatus(reviewId, "Rejected");
        return new ResponseEntity<>("Review rejected!", HttpStatus.OK);
    }

    @GetMapping("/all")
    @Secured("ROLE_ADMIN")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/movie/{movieId}")
    public List<Review> getMovieReviews(@PathVariable Long movieId) {
        return reviewService.getMovieReviews(movieId);
    }
}
