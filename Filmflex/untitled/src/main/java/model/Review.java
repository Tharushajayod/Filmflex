package model; // Placed inside the core architectural model layer package

import org.json.JSONObject;

/**
 * Domain Model representing a Movie Review entity blueprint (Plain POJO).
 * Enforces strict Encapsulation principles, supports dual data transformation pathways
 * (JSON DTO and flat-file persistence layers), and implements advanced character escape
 * encoding schemes to ensure transactional data row integrity inside reviews.txt.
 */
public class Review {

    // Applying the core principle of Encapsulation: Explicitly defining private domain fields
    // to shield runtime object data properties states from direct modifications by external files.
    private String id;
    private String userId;
    private String movieId;
    private int    rating;
    private String reviewText;
    private String createdAt;
    private String status; // Operational moderation state parameters: Pending | Approved | Rejected

    /**
     * Default Nullary Constructor method required to support dynamic JavaBean instantiations.
     */
    public Review() {}

    /**
     * Fully Parameterized Constructor method to explicitly assign individual instance parameters
     * and initialize a complete structural Review object context state.
     */
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

    /**
     * FLAT-FILE SERIALIZATION: Collapses the internal properties map of the active Object instance
     * directly into a single pipe-delimited data record row text stream string for storage.
     * Invokes internal escape validation rules to sanitize prose fields.
     */
    public String toFileString() {
        // Formulating standard structural pipe notation layout while cleaning text content blocks
        return String.join("|",
                escape(id),
                escape(userId),
                escape(movieId),
                String.valueOf(rating), // Mapping native primitive integer value directly to its string equivalent
                escape(reviewText),
                escape(createdAt),
                escape(status)
        );
    }

    /**
     * FACTORY METHOD PATTERN (File Deserialization): Reconstructs a fully typed Java Review object
     * instance environment by slicing and decoding a raw pipe-delimited database file line string.
     * @param line A single structural tracking line string pulled from reviews.txt
     * @return Review object model context reference, or null if structural metrics checks fail
     */
    public static Review fromFileString(String line) {
        // Safe Guard Clause: Immediate rejection of empty data lines to prevent runtime exceptions loops
        if (line == null || line.trim().isEmpty()) return null;

        // Slicing database row matrix strings using the compiled Pipe character regex token delimiter
        // Passing -1 limit configuration ensures trailing spaces columns map properly without dropping indices
        String[] parts = line.split("\\|", -1);

        // Validation Guard: Asserts the sliced record layout contains at least 7 required schema variables attributes
        if (parts.length < 7) return null;
        try {
            // Unescaping prose blocks and safely downcasting the primitive integer rating parameter
            return new Review(
                    unescape(parts[0]),
                    unescape(parts[1]),
                    unescape(parts[2]),
                    Integer.parseInt(parts[3].trim()), // Fault-Tolerant checking: Parses numeric data types strings cleanly
                    unescape(parts[4]),
                    unescape(parts[5]),
                    unescape(parts[6])
            );
        } catch (NumberFormatException e) {
            // Traps numeric format mismatches data anomalies gracefully without breaking server container flow
            return null;
        }
    }

    /**
     * OBJECT SERIALIZATION (DTO Pattern): Packs the object instance variables attributes map
     * directly into an outbound transferrable JSON dictionary container layout for web transportation.
     */
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

    /**
     * FACTORY METHOD PATTERN (JSON Deserialization): Translates an incoming request payload JSON dictionary
     * directly into a newly instanced object memory boundary context with standard fallback rules.
     */
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

    /**
     * String Data Sanitization Countermeasure: Escapes sensitive raw formatting layout characters
     * to prevent structural injections from shifting delimiter array positions inside text database rows.
     */
    private static String escape(String s) {
        if (s == null) return "";
        // Replaces literal slashes with duplicates, drops dangerous raw pipe symbols into safety placeholders ('\\p'),
        // and serializes multiline enter entries directly into readable literal slash-n characters tokens
        return s.replace("\\", "\\\\").replace("|", "\\p").replace("\n", "\\n").replace("\r", "");
    }

    /**
     * Data Reconstruction Processor: Decodes raw escaped text strings pulled from database indices
     * back into native user formatting prose layouts for interface rendering processes.
     */
    private static String unescape(String s) {
        if (s == null) return "";
        // Restores literal tokens back into actual system newlines, pipe operators, and single slash signs
        return s.replace("\\n", "\n").replace("\\p", "|").replace("\\\\", "\\");
    }

    // ── ENCAPSULATION ACCESS INTERFACE LAYER (GETTERS & SETTERS) ──
    // Providing public abstraction layers to securely access and mutate private field-level attributes map parameters
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