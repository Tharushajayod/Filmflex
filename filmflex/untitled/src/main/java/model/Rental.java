package model;

import java.util.UUID;

public class Rental {
    private String rentalId;
    private String userEmail;
    private String movieId;
    private String movieTitle;
    private String startDate;
    private String endDate;
    private String status; // ACTIVE, RETURNED, EXPIRED

    public Rental(String rentalId, String userEmail, String movieId, String movieTitle,
                  String startDate, String endDate, String status) {
        this.rentalId = (rentalId == null || rentalId.trim().isEmpty())
                ? UUID.randomUUID().toString() : rentalId.trim();
        this.userEmail = userEmail;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = (status == null || status.trim().isEmpty()) ? "ACTIVE" : status.trim();
    }

    public String getRentalId()    { return rentalId; }
    public String getUserEmail()   { return userEmail; }
    public String getMovieId()     { return movieId; }
    public String getMovieTitle()  { return movieTitle; }
    public String getStartDate()   { return startDate; }
    public String getEndDate()     { return endDate; }
    public String getStatus()      { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toFileString() {
        return String.join("|",
                safe(rentalId), safe(userEmail), safe(movieId), safe(movieTitle),
                safe(startDate), safe(endDate), safe(status));
    }

    public static Rental fromFileString(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] p = line.split("\\|", -1);
        if (p.length < 7) return null;
        return new Rental(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(),
                p[4].trim(), p[5].trim(), p[6].trim());
    }

    private static String safe(String value) {
        return value == null ? "" : value.replace("|", " ").trim();
    }
}
