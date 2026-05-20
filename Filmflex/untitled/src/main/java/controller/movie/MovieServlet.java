package controller.movie;

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

@WebServlet(urlPatterns = {"/api/movies", "/api/movies/*"})
public class MovieServlet extends HttpServlet {

    // Dependency Injection of the Service Layer to abstract computational logic from servlet request routing
    private final MovieService movieService = new MovieService();

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

    private String extractId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) return null;
        return pathInfo.substring(1); // Stripping out the leading forward slash character indicator token
    }
}