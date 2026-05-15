package controller;

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
    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        for (Movie movie : movieService.getAllMovies(request.getParameter("search"))) {
            array.put(movie.toJson());
        }
        ServletHelper.json(response, array.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireAdmin(request, response)) return;
        Movie movie = Movie.fromJson(ServletHelper.readJson(request));
        movieService.addMovie(movie);
        response.setStatus(HttpServletResponse.SC_CREATED);
        ServletHelper.json(response, movie.toJson().toString());
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireAdmin(request, response)) return;
        String id = extractId(request);
        if (id == null) {
            ServletHelper.error(response, HttpServletResponse.SC_BAD_REQUEST, "Movie ID is required");
            return;
        }
        JSONObject json = ServletHelper.readJson(request);
        json.put("id", id);
        Movie movie = Movie.fromJson(json);
        if (!movieService.updateMovie(id, movie)) {
            ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "Movie not found");
            return;
        }
        ServletHelper.json(response, movie.toJson().toString());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletHelper.requireAdmin(request, response)) return;
        String id = extractId(request);
        if (id == null || !movieService.deleteMovie(id)) {
            ServletHelper.error(response, HttpServletResponse.SC_NOT_FOUND, "Movie not found");
            return;
        }
        ServletHelper.json(response, new JSONObject().put("message", "Movie deleted successfully").toString());
    }

    private String extractId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) return null;
        return pathInfo.substring(1);
    }
}
