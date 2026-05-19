package com.example.reviews;

import controller.ServletHelper;
import model.User;
import model.Admin;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/api/reviews", "/api/reviews/*"})
public class ReviewController extends HttpServlet {

    private final ReviewService service = new ReviewService();
    // Added a separate local repository instance to bypass the private access compilation error safely
    private final ReviewRepository localRepo = new ReviewRepository();

    // ── POST ─────────────────────────────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String path = trimPath(req.getPathInfo());

        if (path != null && path.endsWith("/approve")) {
            if (!ServletHelper.requireAdmin(req, res)) return;
            String id = path.replace("/approve", "").replaceFirst("^/", "");
            handleStatusChange(id, "Approved", res);
            return;
        }
        if (path != null && path.endsWith("/reject")) {
            if (!ServletHelper.requireAdmin(req, res)) return;
            String id = path.replace("/reject", "").replaceFirst("^/", "");
            handleStatusChange(id, "Rejected", res);
            return;
        }

        if (!ServletHelper.requireUser(req, res)) return;
        User user = ServletHelper.currentUser(req);

        JSONObject body = ServletHelper.readJson(req);
        Review review = Review.fromJson(body);
        review.setUserId(user.getEmail());

        Review saved = service.saveReview(review);
        res.setStatus(HttpServletResponse.SC_CREATED);
        ServletHelper.json(res, saved.toJson().toString());
    }

    // ── GET ──────────────────────────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String path = trimPath(req.getPathInfo());

        if ("/all".equals(path)) {
            if (!ServletHelper.requireAdmin(req, res)) return;
            JSONArray arr = toArray(service.getAllReviews());
            ServletHelper.json(res, arr.toString());
            return;
        }

        if ("/public-all".equals(path)) {
            JSONArray arr = toArray(service.getAllReviews());
            ServletHelper.json(res, arr.toString());
            return;
        }

        String movieId = req.getParameter("movieId");
        String userId  = req.getParameter("userId");

        List<Review> reviews;
        if (movieId != null && !movieId.trim().isEmpty()) {
            reviews = service.getMovieReviews(movieId.trim());
            if (ServletHelper.currentAdmin(req) == null) {
                reviews.removeIf(r -> !"Approved".equals(r.getStatus()));
            }
        } else if (userId != null && !userId.trim().isEmpty()) {
            reviews = service.getUserReviews(userId.trim());
        } else {
            if (ServletHelper.currentAdmin(req) == null) {
                ServletHelper.error(res, HttpServletResponse.SC_BAD_REQUEST,
                        "Provide movieId or userId parameter");
                return;
            }
            reviews = service.getAllReviews();
        }

        ServletHelper.json(res, toArray(reviews).toString());
    }

    // ── PUT ──────────────────────────────────────────────────────────────
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        if (!ServletHelper.requireUser(req, res)) return;
        String id = extractId(req);
        if (id == null) {
            ServletHelper.error(res, HttpServletResponse.SC_BAD_REQUEST, "Review ID required");
            return;
        }

        JSONObject body = ServletHelper.readJson(req);
        Review updated = Review.fromJson(body);
        if (!service.updateReview(id, updated)) {
            ServletHelper.error(res, HttpServletResponse.SC_NOT_FOUND, "Review not found");
            return;
        }
        ServletHelper.json(res, new JSONObject().put("message", "Review updated").toString());
    }

    // ── DELETE (100% Fixed to avoid private access errors) ──────────────────
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        User user = ServletHelper.currentUser(req);
        Admin admin = ServletHelper.currentAdmin(req);

        if (user == null && admin == null) {
            ServletHelper.error(res, HttpServletResponse.SC_UNAUTHORIZED, "Login required to delete reviews");
            return;
        }

        String id = extractId(req);
        if (id == null) {
            ServletHelper.error(res, HttpServletResponse.SC_BAD_REQUEST, "Review ID required");
            return;
        }

        // Fixed: Using the localRepo instance to safely find by ID without crashing private layers
        Review existing = localRepo.findById(id);
        if (existing == null) {
            ServletHelper.error(res, HttpServletResponse.SC_NOT_FOUND, "Review not found");
            return;
        }

        // Safe CRUD Access: Check if user is Admin OR owns the review line explicitly
        if (admin != null || (user != null && user.getEmail().equalsIgnoreCase(existing.getUserId()))) {
            if (!service.deleteReview(id)) {
                ServletHelper.error(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete review");
                return;
            }
            ServletHelper.json(res, new JSONObject().put("message", "Review deleted successfully from database").toString());
        } else {
            ServletHelper.error(res, HttpServletResponse.SC_UNAUTHORIZED, "You can only delete your own reviews");
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────
    private void handleStatusChange(String id, String status, HttpServletResponse res)
            throws IOException {
        if (!service.changeReviewStatus(id, status)) {
            ServletHelper.error(res, HttpServletResponse.SC_NOT_FOUND, "Review not found");
            return;
        }
        ServletHelper.json(res, new JSONObject().put("message", "Review " + status).toString());
    }

    private static String extractId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) return null;
        String stripped = pathInfo.substring(1);
        int slash = stripped.indexOf('/');
        return slash == -1 ? stripped : stripped.substring(0, slash);
    }

    private static String trimPath(String pathInfo) {
        if (pathInfo == null || pathInfo.isEmpty()) return null;
        return pathInfo.endsWith("/") ? pathInfo.substring(0, pathInfo.length() - 1) : pathInfo;
    }

    private static JSONArray toArray(List<Review> reviews) {
        JSONArray arr = new JSONArray();
        for (Review r : reviews) arr.put(r.toJson());
        return arr;
    }
}