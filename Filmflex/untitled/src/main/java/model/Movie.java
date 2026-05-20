package model;

import org.json.JSONObject;
import java.util.UUID;

public class Movie {

    // Applying the principle of Encapsulation: All data instance attributes are marked private
    // to secure state variables from direct external modification boundaries access.
    private String id;
    private String title;
    private int year;
    private double rating;
    private String genre;
    private String description;
    private String posterPath;
    private String filePath;
    private String trailerPath;

    public Movie(String id, String title, int year, double rating, String genre, String description,
                 String posterPath, String filePath, String trailerPath) {
        // Defensive Identity Mapping: If dynamic input ID is null/blank, instantiate an immutable unique token via UUID
        this.id = (id == null || id.trim().isEmpty()) ? UUID.randomUUID().toString() : id.trim();
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.genre = genre;
        this.description = description;
        this.posterPath = posterPath;
        this.filePath = filePath;
        this.trailerPath = trailerPath;
    }

    // ── Encapsulation Interface Layer Access Methods (Getters ONLY) ──
    // Providing public readable interfaces while preserving field-level immutability from outer classes modifications
    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public double getRating() { return rating; }
    public String getGenre() { return genre; }
    public String getDescription() { return description; }
    public String getPosterPath() { return posterPath; }
    public String getFilePath() { return filePath; }
    public String getTrailerPath() { return trailerPath; }

    public String toFileString() {
        // Passing fields to the private safe sanitizer to clean up dynamic input string fields layout tokens
        return String.join("|", safe(id), safe(title), String.valueOf(year), String.valueOf(rating),
                safe(genre), safe(description), safe(posterPath), safe(filePath), safe(trailerPath));
    }

    public static Movie fromFileString(String line) {
        // Safe Guard Clause: Rejecting null strings pointers or empty spaces to insulate application loops stability
        if (line == null || line.trim().isEmpty()) return null;

        // Slicing the database row utilizing explicit pipe delimiter token boundaries regulations configurations
        // Passing -1 preserves blank trailing optional values array density preventing missing index drops
        String[] p = line.split("\\|", -1);

        // Verification: Enforcing that the array length strictly maps onto the required 9 metadata variables matrix attributes
        if (p.length < 9) return null;

        // Calling local null-defensive helper methods to safely transform string elements into primitive numeric metrics
        int year = parseInt(p[2], 0);
        double rating = parseDouble(p[3], 0.0);

        return new Movie(p[0], p[1], year, rating, p[4], p[5], p[6], p[7], p[8]);
    }

    public static Movie fromJson(JSONObject json) {
        return new Movie(
                json.optString("id", null),
                json.optString("title", ""),
                json.optInt("year", 0),
                json.optDouble("rating", 0.0),
                json.optString("genre", ""),
                json.optString("description", ""),
                json.optString("posterPath", ""),
                json.optString("filePath", ""),
                json.optString("trailerPath", "")
        );
    }


    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("title", title);
        obj.put("year", year);
        obj.put("rating", rating);
        obj.put("genre", genre);
        obj.put("description", description);

        // UI Presentation Layer Guard: Injecting a default placeholder path if image assets tokens are missing
        obj.put("posterPath", posterPath == null || posterPath.isEmpty() ? "assets/image/default-poster.jpg" : posterPath);
        obj.put("filePath", filePath);
        obj.put("trailerPath", trailerPath);

        return obj;
    }

    private static String safe(String value) {
        return value == null ? "" : value.replace("|", " ").trim();
    }

    private static int parseInt(String s, int fallback) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return fallback; // Return safe default if input text is corrupted or empty
        }
    }

    private static double parseDouble(String s, double fallback) {
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return fallback;
        }
    }
}