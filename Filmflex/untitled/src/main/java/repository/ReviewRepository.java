package repository;

import model.Review;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {

    public List<Review> findAll() throws IOException {
        // Allocating a dynamic array arraylist inside heap memory to temporarily cache deserialized models
        List<Review> result = new ArrayList<>();

        // Streaming through raw string lines fetched from the text persistence boundaries
        for (String line : FileUtil.readAllReviewLines()) {
            // Invoking the Model's Factory abstraction to unmarshal raw line tokens securely
            Review r = Review.fromFileString(line);

            // Null-Defensive verification to skip empty entries or corrupted data row structures
            if (r != null) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Review> findByMovieId(String movieId) throws IOException {
        List<Review> result = new ArrayList<>();

        // Re-using the core findAll() pipeline to iterate through the entire active object dataset safely
        for (Review r : findAll()) {
            // String Reference Comparison Check: Matching the unique target movie identifier token safely
            if (movieId != null && movieId.equals(r.getMovieId())) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Review> findByUserId(String userId) throws IOException {
        List<Review> result = new ArrayList<>();
        for (Review r : findAll()) {
            if (userId != null && userId.equals(r.getUserId())) {
                result.add(r);
            }
        }
        return result;
    }

    public Review findById(String id) throws IOException {
        for (Review r : findAll()) {
            if (id != null && id.equals(r.getId())) {
                return r; // Immediate exit return execution upon discovering target matching row reference
            }
        }
        return null; // Return null default if target unique key is absent from text database index lines
    }

    public void save(Review review) throws IOException {
        List<Review> all = findAll();
        List<String> lines = new ArrayList<>();
        boolean found = false;

        // Data Sync Overwrites Loop: Iterating across historical collections to inspect tracking indices
        for (Review r : all) {
            if (review.getId() != null && review.getId().equals(r.getId())) {
                // Key identifier matched: Intercepting old record row layout and inserting the modified serialized state string
                lines.add(review.toFileString());
                found = true;
            } else {
                // Keeping non-matching data rows untouched within the active compilation output array
                lines.add(r.toFileString());
            }
        }

        // Append Mode: If scanning loops find no matching index, append the entity object as a brand new structural row entry
        if (!found) {
            lines.add(review.toFileString());
        }

        // Compiling the finalized memory collections payload back onto absolute local hard drive storage systems
        FileUtil.writeAllReviewLines(lines);
    }

    public boolean delete(String id) throws IOException {
        List<Review> all = findAll();
        List<String> lines = new ArrayList<>();
        boolean deleted = false;

        // Deletion Truncation Loop: Filtering out the matching index pointer mapping segment row
        for (Review r : all) {
            if (id != null && id.equals(r.getId())) {
                deleted = true; // Target matched: Skip addition to the write array to achieve truncation mechanics
            } else {
                // Unmatched records are pushed back into the safe execution bundle array list
                lines.add(r.toFileString());
            }
        }

        // IO Commit Optimization Check: Trigger file write execution strictly if a deletion mutation transaction occurred
        if (deleted) {
            FileUtil.writeAllReviewLines(lines);
        }
        return deleted;
    }
}