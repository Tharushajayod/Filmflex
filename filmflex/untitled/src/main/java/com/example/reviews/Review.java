import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private Long userId;
    private Long movieId;
    private int rating;
    private String reviewText;
    private Timestamp createdAt;
    private String status; // "Pending", "Approved", "Rejected"

    // Getters and setters
}
