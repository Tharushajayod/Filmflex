package service;

import model.Movie;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieService {
    public List<Movie> getAllMovies(String search) throws IOException {
        List<Movie> movies = new ArrayList<>();
        String query = search == null ? "" : search.trim().toLowerCase(Locale.ROOT);
        for (String line : FileUtil.readAllMovieLines()) {
            Movie movie = Movie.fromFileString(line);
            if (movie == null) continue;
            if (query.isEmpty()
                    || movie.getTitle().toLowerCase(Locale.ROOT).contains(query)
                    || movie.getGenre().toLowerCase(Locale.ROOT).contains(query)) {
                movies.add(movie);
            }
        }
        return movies;
    }

    public Movie getMovie(String id) throws IOException {
        if (id == null) return null;
        for (Movie movie : getAllMovies(null)) {
            if (movie.getId().equals(id)) return movie;
        }
        return null;
    }

    public Movie addMovie(Movie movie) throws IOException {
        List<String> lines = FileUtil.readAllMovieLines();
        lines.add(movie.toFileString());
        FileUtil.writeAllMovieLines(lines);
        return movie;
    }

    public boolean updateMovie(String id, Movie updated) throws IOException {
        List<Movie> movies = getAllMovies(null);
        List<String> lines = new ArrayList<>();
        boolean found = false;
        for (Movie movie : movies) {
            if (movie.getId().equals(id)) {
                lines.add(updated.toFileString());
                found = true;
            } else {
                lines.add(movie.toFileString());
            }
        }
        if (found) FileUtil.writeAllMovieLines(lines);
        return found;
    }

    public boolean deleteMovie(String id) throws IOException {
        List<String> lines = new ArrayList<>();
        boolean deleted = false;
        for (Movie movie : getAllMovies(null)) {
            if (movie.getId().equals(id)) deleted = true;
            else lines.add(movie.toFileString());
        }
        if (deleted) FileUtil.writeAllMovieLines(lines);
        return deleted;
    }
}
