package controller;

import model.Rental;
import org.json.JSONArray;
import org.json.JSONObject;
import service.RentalService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/rental")
public class RentalServlet extends HttpServlet {

    private final RentalService rentalService = new RentalService();

    // GET /rental?email=user@example.com  → rental history for user
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!ServletHelper.requireUser(request, response)) return;
        try {
            String email = request.getParameter("email");
            List<Rental> rentals = rentalService.getRentalsByEmail(email);
            JSONArray arr = new JSONArray();
            for (Rental r : rentals) {
                JSONObject obj = new JSONObject();
                obj.put("rentalId",    r.getRentalId());
                obj.put("userEmail",   r.getUserEmail());
                obj.put("movieId",     r.getMovieId());
                obj.put("movieTitle",  r.getMovieTitle());
                obj.put("startDate",   r.getStartDate());
                obj.put("endDate",     r.getEndDate());
                obj.put("status",      r.getStatus());
                arr.put(obj);
            }
            ServletHelper.json(response, arr.toString());
        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // POST /rental  → rent a movie
    // Body: { email, movieId, movieTitle, startDate, endDate }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!ServletHelper.requireUser(request, response)) return;
        try {
            JSONObject json = ServletHelper.readJson(request);
            Rental rental = rentalService.rentMovie(
                    json.optString("email"),
                    json.optString("movieId"),
                    json.optString("movieTitle"),
                    json.optString("startDate"),
                    json.optString("endDate")
            );
            JSONObject result = new JSONObject();
            result.put("message",   "Movie rented successfully");
            result.put("rentalId",  rental.getRentalId());
            result.put("status",    rental.getStatus());
            ServletHelper.json(response, result.toString());
        } catch (IllegalArgumentException | IllegalStateException e) {
            ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // PUT /rental?rentalId=xxx&email=user@example.com  → return rental
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!ServletHelper.requireUser(request, response)) return;
        try {
            String rentalId = request.getParameter("rentalId");
            String email    = request.getParameter("email");
            boolean success = rentalService.returnRental(rentalId, email);
            if (!success) {
                ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "Rental not found or already returned");
                return;
            }
            ServletHelper.json(response, new JSONObject().put("message", "Movie returned successfully").toString());
        } catch (Exception e) {
            ServletHelper.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
