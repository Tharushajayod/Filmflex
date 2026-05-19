package com.example.reviews;

import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * File-based repository for reviews – uses FileUtil like every other repository.
 * No JPA / Spring.
 */
public class ReviewRepository {

    public List<Review> findAll() throws IOException {
        List<Review> result = new ArrayList<>();
        for (String line : FileUtil.readAllReviewLines()) {
            Review r = Review.fromFileString(line);
            if (r != null) result.add(r);
        }
        return result;
    }

    public List<Review> findByMovieId(String movieId) throws IOException {
        List<Review> result = new ArrayList<>();
        for (Review r : findAll()) {
            if (movieId != null && movieId.equals(r.getMovieId())) result.add(r);
        }
        return result;
    }

    public List<Review> findByUserId(String userId) throws IOException {
        List<Review> result = new ArrayList<>();
        for (Review r : findAll()) {
            if (userId != null && userId.equals(r.getUserId())) result.add(r);
        }
        return result;
    }

    public Review findById(String id) throws IOException {
        for (Review r : findAll()) {
            if (id != null && id.equals(r.getId())) return r;
        }
        return null;
    }

    public void save(Review review) throws IOException {
        List<Review> all = findAll();
        List<String> lines = new ArrayList<>();
        boolean found = false;
        for (Review r : all) {
            if (review.getId() != null && review.getId().equals(r.getId())) {
                lines.add(review.toFileString());
                found = true;
            } else {
                lines.add(r.toFileString());
            }
        }
        if (!found) lines.add(review.toFileString());
        FileUtil.writeAllReviewLines(lines);
    }

    public boolean delete(String id) throws IOException {
        List<Review> all = findAll();
        List<String> lines = new ArrayList<>();
        boolean deleted = false;
        for (Review r : all) {
            if (id != null && id.equals(r.getId())) {
                deleted = true;
            } else {
                lines.add(r.toFileString());
            }
        }
        if (deleted) FileUtil.writeAllReviewLines(lines);
        return deleted;
    }
}
