package controller.movie; // Organized under the dedicated movie sub-package structure

import controller.ServletHelper;
import repository.ReviewRepository;
import service.ReviewService;
import model.Review;
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

/**
 * Enterprise RESTful Controller handling the complete validation and moderation lifecycle of Movie Reviews.
 * Supports standard CRUD actions mapped onto HTTP methods and implements strict Role-Based Access Control (RBAC)
 * to separate basic user submittals from administrative moderation routines.
 */
@WebServlet(urlPatterns = {"/api/reviews", "/api/reviews/*"})
public class ReviewController extends HttpServlet {

    // Dependency Injection of the Service and Repository Layers to achieve architectural encapsulation
    private final ReviewService service = new ReviewService();
    private final ReviewRepository localRepo = new ReviewRepository();

    // ── HTTP POST: CREATE REVIEW & ADMINISTRATIVE MODERATION ROUTING ──────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Cleaning the raw URI suffix string to process specific sub-route parameters accurately
        String path = trimPath(req.getPathInfo());

        // ── 1. ADMINISTRATIVE APPROVAL PATHWAY ──
        if (path != null && path.endsWith("/approve")) {
            // Guard Clause: Instantly rejecting the write request if session lacks active Admin authority tokens
            if (!ServletHelper.requireAdmin(req, res)) return;
            // Parsing out target identifier parameters from the string segments
            String id = path.replace("/approve", "").replaceFirst("^/", "");
            handleStatusChange(id, "Approved", res);
            return;
        }

        // ── 2. ADMINISTRATIVE REJECTION PATHWAY ──
        if (path != null && path.endsWith("/reject")) {
            if (!ServletHelper.requireAdmin(req, res)) return;
            String id = path.replace("/reject", "").replaceFirst("^/", "");
            handleStatusChange(id, "Rejected", res);
            return;
        }

        // ── 3. STANDARD CUSTOMER USER REVIEW SUBMISSION PATHWAY ──
        // Gatekeeping: Ensuring caller has logged onto a standard customer consumer profile context
        if (!ServletHelper.requireUser(req, res)) return;
        User user = ServletHelper.currentUser(req);

        // Parsing the inbound raw text payload stream into a deserialized JSON model framework
        JSONObject body = ServletHelper.readJson(req);
        Review review = Review.fromJson(body);
        review.setUserId(user.getEmail()); // Explicitly binding server-side session identity to protect against identity spoofing

        // Saving transaction permanently down into flat files infrastructure layers via service node
        Review saved = service.saveReview(review);
        res.setStatus(HttpServletResponse.SC_CREATED); // Mapping standard HTTP 201 Created parameter code
        ServletHelper.json(res, saved.toJson().toString());
    }

    // ── HTTP GET: MULTI-CRITERIA READ SEGMENTATION ─────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String path = trimPath(req.getPathInfo());

        // Administrative Endpoint: Fetching all logged entries across approval state boundaries
        if ("/all".equals(path)) {
            if (!ServletHelper.requireAdmin(req, res)) return;
            JSONArray arr = toArray(service.getAllReviews());
            ServletHelper.json(res, arr.toString());
            return;
        }

        // Public Endpoint: Fetching complete collection structures for storefront viewing arrays
        if ("/public-all".equals(path)) {
            JSONArray arr = toArray(service.getAllReviews());
            ServletHelper.json(res, arr.toString());
            return;
        }

        // Extracting query string parameters mapping to isolate targeted data segment rows
        String movieId = req.getParameter("movieId");
        String userId  = req.getParameter("userId");

        List<Review> reviews;

        // ── CONDITIONAL FILTER RESOLUTION LOGIC MATRIX ──
        if (movieId != null && !movieId.trim().isEmpty()) {
            reviews = service.getMovieReviews(movieId.trim());
            // Security Constraint: Filtering out non-approved reviews if caller is an unauthenticated general user
            if (ServletHelper.currentAdmin(req) == null) {
                reviews.removeIf(r -> !"Approved".equals(r.getStatus()));
            }
        } else if (userId != null && !userId.trim().isEmpty()) {
            reviews = service.getUserReviews(userId.trim());
        } else {
            // Safe fallback logic block triggered if lookup parameters identifiers arrays are missing
            if (ServletHelper.currentAdmin(req) == null) {
                ServletHelper.error(res, HttpServletResponse.SC_BAD_REQUEST, "Provide movieId or userId parameter");
                return;
            }
            reviews = service.getAllReviews();
        }

        ServletHelper.json(res, toArray(reviews).toString());
    }

    // ── HTTP PUT: PARAMETER RECORD MODIFICATION ─────────────────────────────────────────
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Access Authorization Barrier: Ensuring caller holds regular verified customer access context rules
        if (!ServletHelper.requireUser(req, res)) return;

        String id = extractId(req);
        if (id == null) {
            ServletHelper.error(res, HttpServletResponse.SC_BAD_REQUEST, "Review ID required");
            return;
        }

        // Deserializing updated JSON layout segments directly into instance parameters
        JSONObject body = ServletHelper.readJson(req);
        Review updated = Review.fromJson(body);

        if (!service.updateReview(id, updated)) {
            ServletHelper.error(res, HttpServletResponse.SC_NOT_FOUND, "Review not found");
            return;
        }
        ServletHelper.json(res, new JSONObject().put("message", "Review updated").toString());
    }

    // ── HTTP DELETE: AUTHORIZED RECORDRIGHT TRUNCATION ──────────────────────────────────
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Gathering active operational security scope traits cached inside current request thread
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

        // Fetching reference properties directly from local storage boundaries layer tracking index maps
        Review existing = localRepo.findById(id);
        if (existing == null) {
            ServletHelper.error(res, HttpServletResponse.SC_NOT_FOUND, "Review not found");
            return;
        }

        // Dual-Authorization Rule Logic Validation: Allow operation ONLY if caller is Admin OR owns the targeted record segment line
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

    // ── DEFENSIVE PRIVATE UTILITY UTILITIES METHODS LAYER ──────────────────────────────
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