package model;

import org.json.JSONObject;
import java.util.UUID;

public class Movie {
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
        return String.join("|", safe(id), safe(title), String.valueOf(year), String.valueOf(rating),
                safe(genre), safe(description), safe(posterPath), safe(filePath), safe(trailerPath));
    }

    public static Movie fromFileString(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] p = line.split("\\|", -1);
        if (p.length < 9) return null;
        int year = parseInt(p[2], 0);
        double rating = parseDouble(p[3], 0.0);
        return new Movie(p[0], p[1], year, rating, p[4], p[5], p[6], p[7], p[8]);
    }

    public static Movie fromJson(JSONObject json) {
        return new Movie(json.optString("id", null), json.optString("title", ""), json.optInt("year", 0),
                json.optDouble("rating", 0.0), json.optString("genre", ""), json.optString("description", ""),
                json.optString("posterPath", ""), json.optString("filePath", ""), json.optString("trailerPath", ""));
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("title", title);
        obj.put("year", year);
        obj.put("rating", rating);
        obj.put("genre", genre);
        obj.put("description", description);
        obj.put("posterPath", posterPath == null || posterPath.isEmpty() ? "assets/image/default-poster.jpg" : posterPath);
        obj.put("filePath", filePath);
        obj.put("trailerPath", trailerPath);
        return obj;
    }

    private static String safe(String value) { return value == null ? "" : value.replace("|", " ").trim(); }
    private static int parseInt(String s, int fallback) { try { return Integer.parseInt(s.trim()); } catch (Exception e) { return fallback; } }
    private static double parseDouble(String s, double fallback) { try { return Double.parseDouble(s.trim()); } catch (Exception e) { return fallback; } }
}
