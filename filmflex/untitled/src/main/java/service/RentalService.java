package service;

import model.Rental;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RentalService {

    // Rent a movie for a user
    public Rental rentMovie(String userEmail, String movieId, String movieTitle,
                            String startDate, String endDate) throws IOException {
        if (isBlank(userEmail)) throw new IllegalArgumentException("Email is required");
        if (isBlank(movieId))   throw new IllegalArgumentException("Movie ID is required");
        if (isBlank(startDate)) throw new IllegalArgumentException("Start date is required");
        if (isBlank(endDate))   throw new IllegalArgumentException("End date is required");

        // Check if this movie is already rented (ACTIVE) by this user
        for (Rental r : getRentalsByEmail(userEmail)) {
            if (r.getMovieId().equals(movieId) && r.getStatus().equals("ACTIVE")) {
                throw new IllegalStateException("You have already rented this movie");
            }
        }

        Rental rental = new Rental(null, userEmail, movieId, movieTitle, startDate, endDate, "ACTIVE");
        List<String> lines = FileUtil.readAllRentalLines();
        lines.add(rental.toFileString());
        FileUtil.writeAllRentalLines(lines);
        return rental;
    }

    // Get all rentals for a user
    public List<Rental> getRentalsByEmail(String userEmail) throws IOException {
        List<Rental> result = new ArrayList<>();
        if (isBlank(userEmail)) return result;
        for (String line : FileUtil.readAllRentalLines()) {
            Rental r = Rental.fromFileString(line);
            if (r != null && r.getUserEmail().equalsIgnoreCase(userEmail.trim())) {
                result.add(r);
            }
        }
        return result;
    }

    // Return a rented movie (set status to RETURNED)
    public boolean returnRental(String rentalId, String userEmail) throws IOException {
        List<String> lines = FileUtil.readAllRentalLines();
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            Rental r = Rental.fromFileString(line);
            if (r != null && r.getRentalId().equals(rentalId)
                    && r.getUserEmail().equalsIgnoreCase(userEmail.trim())
                    && r.getStatus().equals("ACTIVE")) {
                r.setStatus("RETURNED");
                updated.add(r.toFileString());
                found = true;
            } else if (r != null) {
                updated.add(r.toFileString());
            }
        }
        if (found) FileUtil.writeAllRentalLines(updated);
        return found;
    }

    // Get all rentals (admin use)
    public List<Rental> getAllRentals() throws IOException {
        List<Rental> result = new ArrayList<>();
        for (String line : FileUtil.readAllRentalLines()) {
            Rental r = Rental.fromFileString(line);
            if (r != null) result.add(r);
        }
        return result;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
