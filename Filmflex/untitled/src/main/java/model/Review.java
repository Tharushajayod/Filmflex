package model;

import org.json.JSONObject;

public class Review {

    private String id;
    private String userId;
    private String movieId;
    private int    rating;
    private String reviewText;
    private String createdAt;
    private String status; // Operational moderation state parameters: Pending | Approved | Rejected

    public Review() {}

    public Review(String id, String userId, String movieId,
                  int rating, String reviewText, String createdAt, String status) {
        this.id         = id;
        this.userId     = userId;
        this.movieId    = movieId;
        this.rating     = rating;
        this.reviewText = reviewText;
        this.createdAt  = createdAt;
        this.status     = status;
    }

    // ── DATA SERIALIZATION & DESERIALIZATION HELPERS ─────────────────────────────────────────────

    public String toFileString() {
        return String.join("|",
                escape(id),
                escape(userId),
                escape(movieId),
                String.valueOf(rating),
                escape(reviewText),
                escape(createdAt),
                escape(status)
        );
    }

    public static Review fromFileString(String line) {
        if (line == null || line.trim().isEmpty()) return null;

        String[] parts = line.split("\\|", -1);

        if (parts.length < 7) return null;
        try {
            return new Review(
                    unescape(parts[0]),
                    unescape(parts[1]),
                    unescape(parts[2]),
                    Integer.parseInt(parts[3].trim()),
                    unescape(parts[4]),
                    unescape(parts[5]),
                    unescape(parts[6])
            );
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public JSONObject toJson() {
        JSONObject o = new JSONObject();
        o.put("id",         id);
        o.put("userId",     userId);
        o.put("movieId",    movieId);
        o.put("rating",     rating);
        o.put("reviewText", reviewText);
        o.put("createdAt",  createdAt);
        o.put("status",     status);
        return o;
    }

    public static Review fromJson(JSONObject o) {
        return new Review(
                o.optString("id", null),
                o.optString("userId", ""),
                o.optString("movieId", ""),
                o.optInt("rating", 0),
                o.optString("reviewText", ""),
                o.optString("createdAt", ""),
                o.optString("status", "Pending") // Default Business Rule: Enforcing a new submittal initializes as 'Pending'
        );
    }

    // ── ADVANCED INTERCEPTOR PIPE-SAFE ENCODING SCHEME ────────────────────────────────────────────────

    private static String escape(String s) {
        if (s == null) return "";

        return s.replace("\\", "\\\\").replace("|", "\\p").replace("\n", "\\n").replace("\r", "");
    }

    private static String unescape(String s) {
        if (s == null) return "";
        return s.replace("\\n", "\n").replace("\\p", "|").replace("\\\\", "\\");
    }

    // ── ENCAPSULATION ACCESS INTERFACE LAYER (GETTERS & SETTERS) ──
    public String getId()              { return id; }
    public void   setId(String v)      { this.id = v; }
    public String getUserId()          { return userId; }
    public void   setUserId(String v)  { this.userId = v; }
    public String getMovieId()         { return movieId; }
    public void   setMovieId(String v) { this.movieId = v; }
    public int    getRating()          { return rating; }
    public void   setRating(int v)     { this.rating = v; }
    public String getReviewText()      { return reviewText; }
    public void   setReviewText(String v) { this.reviewText = v; }
    public String getCreatedAt()       { return createdAt; }
    public void   setCreatedAt(String v) { this.createdAt = v; }
    public String getStatus()          { return status; }
    public void   setStatus(String v)  { this.status = v; }
}