package service; // Placed inside the core domain business logic service package layer

import model.Movie;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Service Layer component orchestrating core business workflows for Movie Management.
 * Handles search filtering, dynamic retrieval validation metrics, and interfaces directly
 * with the underlying file processing layer to execute full CRUD operations.
 */
public class MovieService {

    /**
     * READ Operation (Filtered / Complete Catalog): Fetches rows from storage and applies multi-criteria filters.
     * Enforces case-insensitive parameters evaluation using the system's root locale settings.
     * @param search Optional search keyword string mapped from the frontend client text interface
     */
    public List<Movie> getAllMovies(String search) throws IOException {
        List<Movie> movies = new ArrayList<>();

        // Data Sanitization: Transforming query tokens to lower case dynamically using Locale.ROOT to avoid platform mismatch defects
        String query = search == null ? "" : search.trim().toLowerCase(Locale.ROOT);

        // Linear Scanning Execution Loop: Fetching raw entries rows directly from the flat text file limits
        for (String line : FileUtil.readAllMovieLines()) {
            // Invoking the Model's creational factory pattern to deserialize the pipe-delimited text sequence
            Movie movie = Movie.fromFileString(line);

            // Safe Guard Clause: Skipping corrupted data tracks or missing index positions gracefully
            if (movie == null) continue;

            // Multi-Criteria Business Filter Logic: Match entry if query text evaluates empty,
            // OR if the input characters are tracked within the movie's title string OR its genre string properties
            if (query.isEmpty()
                    || movie.getTitle().toLowerCase(Locale.ROOT).contains(query)
                    || movie.getGenre().toLowerCase(Locale.ROOT).contains(query)) {
                movies.add(movie); // Appending valid instances targets into the compiled output collection
            }
        }
        return movies;
    }

    /**
     * READ Operation (Single Unique Lookup): Scans the active text record layers to match a unique ID token string.
     * Re-uses the base getAllMovies pipeline to safely retrieve data matrices boundaries.
     */
    public Movie getMovie(String id) throws IOException {
        // Defensive Guard: Return null pointer immediately if specified input key parameters evaluate null
        if (id == null) return null;

        // Iterating through all records by running the base lookup pipeline bypassing search queries attributes
        for (Movie movie : getAllMovies(null)) {
            // Exact matching identity trace evaluation check rule execution
            if (movie.getId().equals(id)) {
                return movie; // Match discovered: returning instanced blueprint properties instantly
            }
        }
        return null; // Return null default if target unique key is absent from storage matrices boundaries
    }

    /**
     * CREATE Operation: Commits a newly marshalled Movie object's text string representation onto disk blocks.
     */
    public Movie addMovie(Movie movie) throws IOException {
        // Extracting historical records lines data bundles safely from the file management utils node
        List<String> lines = FileUtil.readAllMovieLines();

        // Serializing the model object runtime attributes cleanly and appending it as a fresh dataset entry row
        lines.add(movie.toFileString());

        // Flushing updated collection strings array streams back into local storage systems
        FileUtil.writeAllMovieLines(lines);
        return movie;
    }

    /**
     * UPDATE Operation: In-memory parsing algorithm to modify properties fields based on unique ID parameters mapping.
     * Overwrites matching target metadata rows while keeping adjacent datasets entirely untouched.
     */
    public boolean updateMovie(String id, Movie updated) throws IOException {
        List<Movie> movies = getAllMovies(null);
        List<String> lines = new ArrayList<>();
        boolean found = false;

        // Record Traversal Matrix Transformation Loop
        for (Movie movie : movies) {
            if (movie.getId().equals(id)) {
                // Key matched: Replacing obsolete layout sequence line with the updated object's serialized file string
                lines.add(updated.toFileString());
                found = true;
            } else {
                // Preserving non-targeted adjacent row records untouched within the output stream bundle
                lines.add(movie.toFileString());
            }
        }

        // IO Commit Optimization Gatekeeper: Write execution runs strictly if a modification transition took place
        if (found) {
            FileUtil.writeAllMovieLines(lines);
        }
        return found;
    }

    /**
     * DELETE Operation: Removes a unique movie dataset transaction mapping line segment from text records boundaries.
     */
    public boolean deleteMovie(String id) throws IOException {
        List<String> lines = new ArrayList<>();
        boolean deleted = false;

        // Processing records iteratively using the unified abstract collection extraction channel
        for (Movie movie : getAllMovies(null)) {
            if (movie.getId().equals(id)) {
                deleted = true; // Target found: skip addition to the write bundle array list to execute file truncation
            } else {
                lines.add(movie.toFileString());
            }
        }

        if (deleted) {
            FileUtil.writeAllMovieLines(lines);
        }
        return deleted;
    }
}