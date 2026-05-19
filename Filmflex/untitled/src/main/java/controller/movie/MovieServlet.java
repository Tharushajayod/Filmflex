package controller.movie; // Organized under the dedicated movie sub-package structure

import controller.ServletHelper;
import model.Movie;
import org.json.JSONArray;
import org.json.JSONObject;
import service.MovieService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Advanced RESTful controller servlet managing the complete lifecycle of Movie entities.
 * Implements full CRUD functionality mapped across standard HTTP methods:
 * GET (Read), POST (Create), PUT (Update), and DELETE (Delete).
 */
@WebServlet(urlPatterns = {"/api/movies", "/api/movies/*"})
public class MovieServlet extends HttpServlet {

    // Dependency Injection of the Service Layer to abstract computational logic from servlet request routing
    private final MovieService movieService = new MovieService();

    /**
     * READ Operation: Intercepts HTTP GET requests to fetch all movies or filter them by search queries.
     * Accessible by both regular customers and administrators.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Initializing a top-level JSON Array container to structure the collection response
        JSONArray array = new JSONArray();

        // Iterating through data models returned by the service layer after evaluating optional search parameters
        for (Movie movie : movieService.getAllMovies(request.getParameter("search"))) {
            // Polymorphic Serialization: Utilizing the domain model's internal data conversion structure
            array.put(movie.toJson());
        }

        // Transmitting the compiled catalog JSON text dataset back to the async client interface layer
        ServletHelper.json(response, array.toString());
    }

    /**
     * CREATE Operation: Intercepts HTTP POST requests to write a new movie record into storage.
     * Protected by strict administrative authorization barriers.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Authorization Barrier: Rejecting execution early if request credentials lack admin privileges
        if (!ServletHelper.requireAdmin(request, response)) return;

        // Factory Deserialization: Constructing a structured Movie object directly from the incoming JSON payload stream
        Movie movie = Movie.fromJson(ServletHelper.readJson(request));

        // Delegating the write algorithm execution down to the service layer processing node (movies.txt)
        movieService.addMovie(movie);

        // Enforcing standard REST architecture by configuring HTTP 201 Created status inside headers
        response.setStatus(HttpServletResponse.SC_CREATED);

        // Confirming transaction status by returning the fully written movie object blueprint to the UI
        ServletHelper.json(response, movie.toJson().toString());
    }

    /**
     * UPDATE Operation: Intercepts HTTP PUT requests to modify an existing movie record mapped by ID.
     * Protected by strict administrative authorization barriers.
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Security Gatekeeping: Ensuring authorization level matches strict administrative criteria
        if (!ServletHelper.requireAdmin(request, response)) return;

        // Extracting the dynamic resource unique key identification token directly from the URI path
        String id = extractId(request);
        if (id == null) {
            // Invalid data packet structure payload validation fallback handling (HTTP 400 Bad Request)
            ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, "Movie ID is required");
            return;
        }

        // Reading incoming modify fields dictionary and manually binding the extracted URI key token
        JSONObject json = ServletHelper.readJson(request);
        json.put("id", id);

        // Instantiating the modified entity state matrix from the sanitized data bundle
        Movie movie = Movie.fromJson(json);

        // Triggering the file-rewrite data modification transaction pipeline inside the service node
        if (!movieService.updateMovie(id, movie)) {
            // Gracefully handling missing targets if index pointer maps do not overlap any active text rows (HTTP 404)
            ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "Movie not found");
            return;
        }

        ServletHelper.json(response, movie.toJson().toString());
    }

    /**
     * DELETE Operation: Intercepts HTTP DELETE requests to remove a specified movie permanently.
     * Protected by strict administrative authorization barriers.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Authorization Guard Clause Check: Restricting destructive transactions exclusively to authenticated admins
        if (!ServletHelper.requireAdmin(request, response)) return;

        // Extracting target record identity string from path parameter tokens
        String id = extractId(request);

        // Executing truncation pipelines and trapping return state markers safely
        if (id == null || !movieService.deleteMovie(id)) {
            ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "Movie not found");
            return;
        }

        // Returning standard transaction success confirmation parameters array object
        ServletHelper.json(response, new JSONObject().put("message", "Movie deleted successfully").toString());
    }

    /**
     * Defensive Path Parsing Utility: Strips structural delimiter symbols to isolate path parameter tokens.
     * e.g., converts URI string "/api/movies/13" path info fragment "/13" directly into target ID value "13".
     */
    private String extractId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) return null;
        return pathInfo.substring(1); // Stripping out the leading forward slash character indicator token
    }
}